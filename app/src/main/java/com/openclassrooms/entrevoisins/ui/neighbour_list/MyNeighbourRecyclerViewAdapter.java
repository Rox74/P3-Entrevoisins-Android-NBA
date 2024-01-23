package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
// import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_details.NeighbourDetailsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private List<Neighbour> mNeighbours;
    private boolean showFavorites;

    // Constructor to initialize the adapter with a list of neighbours and a boolean flag to show favorites
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, boolean showFavorites) {
        mNeighbours = items;
        this.showFavorites = showFavorites;  // Initialize the showFavorites variable
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        // NBA - START

        // Update the visibility of the delete icon based on showFavorites
        if (showFavorites) {
            holder.mDeleteButton.setVisibility(View.GONE);  // Hide the delete icon
        } else {
            holder.mDeleteButton.setVisibility(View.VISIBLE);  // Make the delete icon visible
        }

        // Add an OnClickListener to handle clicks on a list item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the neighbour details screen with the necessary information
                neighbourDetails(neighbour, holder.itemView.getContext());
            }
        });
        // NBA - END

        // Set an OnClickListener on the delete button to handle delete events
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });
    }

    // NBA - START
    // Method to open the neighbour details screen
    // Use Parcelable to spend a single extra
    private void neighbourDetails(Neighbour neighbour, Context context) {
        // Use Intent to start NeighbourDetailActivity activity
        Intent intent = new Intent(context, NeighbourDetailsActivity.class);

        // Pass the neighbor as extra Parcelable
        intent.putExtra(NeighbourDetailsActivity.EXTRA_NEIGHBOUR, neighbour);

        // Start activity
        context.startActivity(intent);
    }
    // NBA - END

    // Return the size of the dataset
    @Override
    public int getItemCount() {
        if (mNeighbours != null) {
            return mNeighbours.size();
        }
        return 0;
    }

    // ViewHolder class to hold the views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        // ViewHolder constructor
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // Method to set the list of neighbours and notify the adapter of the data change
    public void setNeighbours(List<Neighbour> neighbours) {
        mNeighbours = neighbours;
        notifyDataSetChanged();
        // Log.d("Adapter", "Neighbours set. Count: " + getItemCount());
    }
}
