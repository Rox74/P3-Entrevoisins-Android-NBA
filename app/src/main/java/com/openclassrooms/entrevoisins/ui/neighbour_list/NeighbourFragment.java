package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.service.repository.NeighbourRepository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


public class NeighbourFragment extends Fragment {

    private NeighbourRepository mRepository;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;
    private boolean showFavorites;


    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(boolean showFavorites) {
        NeighbourFragment fragment = new NeighbourFragment();
        Bundle args = new Bundle();
        args.putBoolean("showFavorites", showFavorites);
        fragment.setArguments(args);
        return fragment;
    }

    public void setTabLayout(TabLayout tabLayout) {
        mTabLayout = tabLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        mRepository = new NeighbourRepository(mApiService);

        if (mApiService == null) {
            Log.e("NeighbourFragment", "NeighbourApiService is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Initialize showFavorites before creating the adapter
        if (getArguments() != null) {
            showFavorites = getArguments().getBoolean("showFavorites", false);
        }

        // Initialize the adapter by passing showFavorites
        MyNeighbourRecyclerViewAdapter adapter = new MyNeighbourRecyclerViewAdapter(new ArrayList<>(), showFavorites);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * Init the List of neighbours
     */
    protected void initList() {
        mNeighbours = mRepository.getNeighbours(showFavorites);
        MyNeighbourRecyclerViewAdapter adapter = (MyNeighbourRecyclerViewAdapter) mRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.setNeighbours(mNeighbours);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        // Logs pour déboguer
        Log.d("NeighbourFragment", "onDeleteNeighbour - Deleting Neighbor ID: " + event.neighbour.getId());

        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

    // NBA - START
    public boolean isFavoritesTabSelected() {
        return mTabLayout != null && mTabLayout.getSelectedTabPosition() == 1;
    }

    public void showFavorites() {
        if (isFavoritesTabSelected()) {
            mNeighbours = mRepository.getNeighbours(true); // Utiliser le repository
            Log.d("NeighbourFragment", "showFavorites - Favorites List:");
            for (Neighbour favourite : mNeighbours) {
                Log.d("NeighbourFragment", favourite.toString());
            }
            MyNeighbourRecyclerViewAdapter adapter = (MyNeighbourRecyclerViewAdapter) mRecyclerView.getAdapter();

            if (adapter != null) {
                adapter.setNeighbours(mNeighbours);
                adapter.notifyDataSetChanged();
            }
        } else {
            // If the "FAVORITES" tab is not selected, display the complete list
            initList();
        }
    }

    private void updateNeighbourList(List<Neighbour> neighbours) {
        MyNeighbourRecyclerViewAdapter adapter = (MyNeighbourRecyclerViewAdapter) mRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.setNeighbours(neighbours);
            adapter.notifyDataSetChanged();
        }
    }

    // NBA - STOP
}
