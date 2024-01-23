package com.openclassrooms.entrevoisins.service;

//import android.util.Log;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;
import java.util.ArrayList;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    // NBA - START
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favorites = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavorite()) {
                favorites.add(neighbour);
            }
        }

        // Logs to see favorite neighbors
        // Log.d("DummyNeighbourApiService", "Favorites List:");
        // for (Neighbour favorite : favorites) {
        //     Log.d("DummyNeighbourApiService", favorite.toString());
        // }

        return favorites;
    }
    // NBA - STOP

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        // Debug logs
        // Log.d("DummyNeighbourApiService", "deleteNeighbour - Deleting Neighbor ID: " + neighbour.getId());

        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        // Debug logs
        // Log.d("DummyNeighbourApiService", "createNeighbour - Creating Neighbor ID: " + neighbour.getId());

        neighbours.add(neighbour);
    }

    // Method to update an existing neighbor
    @Override
    public void updateNeighbour(Neighbour neighbour) {
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i).getId() == neighbour.getId()) {
                neighbours.set(i, neighbour);
                // Log.d("NeighbourApiService", "Neighbour updated: " + neighbour.toString());
                break;
            }
        }
    }
}
