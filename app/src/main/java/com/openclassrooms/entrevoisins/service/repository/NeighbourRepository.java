package com.openclassrooms.entrevoisins.service.repository;

import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class NeighbourRepository {

    private NeighbourApiService mApiService;

    public NeighbourRepository(NeighbourApiService apiService) {
        this.mApiService = apiService;
    }

    public List<Neighbour> getNeighbours(boolean showFavorites) {
        if (showFavorites) {
            return mApiService.getFavoriteNeighbours();
        } else {
            return mApiService.getNeighbours();
        }
    }
}
