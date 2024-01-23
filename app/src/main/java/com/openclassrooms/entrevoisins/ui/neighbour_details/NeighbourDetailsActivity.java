// NBA
package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailsActivity extends AppCompatActivity {

    // Constant for the extra Parcelable
    public static final String EXTRA_NEIGHBOUR = "extra_neighbour";

    // Bind views using ButterKnife
    @BindView(R.id.neighbour_details_avatar)
    ImageView avatarImageView;

    @BindView(R.id.neighbour_details_name)
    TextView neighbourNameTextView;

    @BindView(R.id.neighbour_details_name2)
    TextView neighbourNameTextView2;

    @BindView(R.id.neighbour_details_address)
    TextView addressTextView;

    @BindView(R.id.neighbour_details_phone_number)
    TextView phoneNumberTextView;

    @BindView(R.id.neighbour_details_about_me)
    TextView aboutMeTextView;

    @BindView(R.id.neighbour_details_favorite_button)
    ImageButton favoriteButton;

    @BindView(R.id.back_button)
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);

        // Hide the action bar
        getSupportActionBar().hide();

        // Bind views
        ButterKnife.bind(this);

        // Recover the neighbor of the extra Parcelable
        Neighbour neighbour = getIntent().getParcelableExtra(EXTRA_NEIGHBOUR);

        // Check if the neighbor is null
        if (neighbour != null) {
            // Set user details in the UI
            Glide.with(this)
                    .load(neighbour.getAvatarUrl())
                    .into(avatarImageView);

            neighbourNameTextView.setText(neighbour.getName());
            neighbourNameTextView2.setText(neighbour.getName());
            addressTextView.setText(neighbour.getAddress());
            phoneNumberTextView.setText(neighbour.getPhoneNumber());
            aboutMeTextView.setText(neighbour.getAboutMe());

            // Update favorite button based on the current favorite status
            updateFavoriteButton(neighbour.isFavorite());
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manage adding or removing the user from favorites here
                toggleFavoriteStatus(neighbour);
            }
        });

        // Add an event listener on the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call onBackPressed to simulate the system back button behavior
                onBackPressed();
            }
        });

    }

    private void toggleFavoriteStatus(Neighbour neighbour) {
        // Log before changing the state
        // Log.d("NeighbourDetailsActivity", "toggleFavoriteStatus - Before: Neighbor ID: " + neighbour.getId() + ", isFavorite: " + neighbour.isFavorite());

        // Toggle the favorite status
        neighbour.setFavorite(!neighbour.isFavorite());

        // Update the view accordingly (change the icon, color, etc.)
        updateFavoriteButton(neighbour.isFavorite());

        // Logs for debugging
        // Log.d("NeighbourDetailsActivity", "toggleFavoriteStatus - Neighbor ID: " + neighbour.getId() + ", isFavorite: " + neighbour.isFavorite());

        // Use the updateNeighbour method to update the neighbour in the list
        DI.getNeighbourApiService().updateNeighbour(neighbour);
    }

    private void updateFavoriteButton(boolean isFavorite) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_star_gold_24dp); // Use the filled star icon to indicate a favorite
        } else {
            favoriteButton.setImageResource(R.drawable.ic_star_white_24dp); // Use the outlined star icon to indicate a non-favorite
        }
    }

}
