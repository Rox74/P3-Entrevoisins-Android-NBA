package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    // Unit test for retrieving neighbours successfully
    @Test
    public void getNeighboursWithSuccess() {
        // Arrange
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;

        // Act and Assert
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    // Unit test for deleting a neighbour successfully
    @Test
    public void deleteNeighbourWithSuccess() {
        // Arrange
        Neighbour neighbourToDelete = service.getNeighbours().get(0);

        // Act
        service.deleteNeighbour(neighbourToDelete);

        // Assert
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }


    // Unit test for Neighbour listing feature - Start
    @Test
    public void neighborsListNotEmptyAfterInitialization() {
        // Act
        List<Neighbour> neighbours = service.getNeighbours();

        // Assert
        assertFalse(neighbours.isEmpty());
    }

    @Test
    public void neighborsListUpdatedAfterNeighborAddition() {
        // Arrange
        Neighbour newNeighbour = new Neighbour(13, "Tomas", "https://i.pravatar.cc/150?u=a042581f3e39026702d", "Village-des-sirops ; 4km",
                "+33 6 22 41 41 50", "Salut ! Je suis amateur de musique et je cherche des créatifs pour collaborer sur des projets.", false);

        // Act
        service.createNeighbour(newNeighbour);
        List<Neighbour> neighbours = service.getNeighbours();

        // Assert
        assertTrue(neighbours.contains(newNeighbour));
    }

    @Test
    public void neighborsListUpdatedAfterNeighborDeletion() {
        // Arrange
        Neighbour neighbourToDelete = service.getNeighbours().get(0);

        // Act
        service.deleteNeighbour(neighbourToDelete);
        List<Neighbour> neighbours = service.getNeighbours();

        // Assert
        assertFalse(neighbours.contains(neighbourToDelete));
    }
    // Unit test for Neighbour listing feature - End


    // Unit test for Add Neighbour feature
    @Test
    public void neighborAddedSuccessfully() {
        // Arrange
        Neighbour newNeighbour = new Neighbour(14, "Daniel", "https://i.pravatar.cc/150?u=a042581f3e39026702d", "Village-des-chips ; 2km",
                "+33 6 74 74 10 51", "Salut ! Je suis amateur de forgeage et je cherche des créatifs pour collaborer sur des projets.", false);

        // Act
        service.createNeighbour(newNeighbour);
        List<Neighbour> neighbours = service.getNeighbours();

        // Assert
        assertTrue(neighbours.contains(newNeighbour));
    }

    // Unit test for Delete Neighbour feature
    @Test
    public void neighborDeletedSuccessfully() {
        // Arrange
        Neighbour neighbourToDelete = service.getNeighbours().get(0);

        // Act
        service.deleteNeighbour(neighbourToDelete);
        List<Neighbour> neighbours = service.getNeighbours();

        // Assert
        assertFalse(neighbours.contains(neighbourToDelete));
    }


    // Unit test for Favorites management feature - Start
    @Test
    public void addNeighborToFavorites() {
        // Arrange
        Neighbour neighbourToAddToFavorites = service.getNeighbours().get(0);

        // Act
        neighbourToAddToFavorites.setFavorite(true);
        service.updateNeighbour(neighbourToAddToFavorites);
        List<Neighbour> favorites = service.getFavoriteNeighbours();

        // Assert
        assertTrue(favorites.contains(neighbourToAddToFavorites));
    }

    @Test
    public void removeNeighborFromFavorites() {
        // Arrange
        // Add a neighbour to favorites
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToAddToFavorites = neighbours.get(0);
        neighbourToAddToFavorites.setFavorite(true);
        service.updateNeighbour(neighbourToAddToFavorites);

        // Act
        // Remove the neighbour from favorites
        neighbourToAddToFavorites.setFavorite(false);
        service.updateNeighbour(neighbourToAddToFavorites);
        List<Neighbour> favoritesAfterRemoval = service.getFavoriteNeighbours();

        // Assert
        assertFalse(favoritesAfterRemoval.contains(neighbourToAddToFavorites));
    }
    // Unit test for Favorites management feature - End

}
