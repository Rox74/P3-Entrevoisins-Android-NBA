package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private ViewInteraction interaction;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        interaction = onView(allOf(withId(R.id.list_neighbours), isDisplayed()));
    }

    /**
     * Test to ensure that our recyclerview is displaying at least one item.
     */
    @Test
    public void neighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        interaction.check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Test to verify that, when an item is deleted, the item is no longer shown.
     */
    @Test
    public void neighboursList_shouldRemoveItem() {
        // Given: Check the initial number of items in the list
        interaction.check(withItemCount(ITEMS_COUNT));

        // When: Perform a click on a delete icon
        interaction.perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then: Check if the number of items in the list has decreased by one
        interaction.check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Test to verify that clicking on an item opens the details screen and
     * the TextView indicating the name of the user is filled.
     */
    @Test
    public void neighboursList_clickAction_shouldOpenDetailsScreen() {
        // Click on the first item in the list
        interaction.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Verify that the NeighbourDetailsActivity is launched
        onView(allOf(withId(R.id.neighbour_details_name), isDisplayed()))
                .check(matches(isDisplayed()));

        // Get the name of the first neighbour from DummyNeighbourGenerator
        String firstNeighbourName = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0).getName();

        // Check if the TextView indicating the name of the user is not empty
        onView(allOf(withId(R.id.neighbour_details_name), isDisplayed()))
                .check(matches(withText(firstNeighbourName)));
    }


    /**
     * Test to verify that the Favorites tab displays only neighbors marked as favorites.
     */
    @Test
    public void neighboursList_showFavoritesTab_shouldDisplayOnlyFavorites() {
        // Given: Check the initial number of items in the list
        interaction.check(withItemCount(ITEMS_COUNT));

        // When: Open the details of the first neighbor
        interaction.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // In the details screen, mark the neighbor as favorite (assuming there is a star button for that)
        onView(withId(R.id.neighbour_details_favorite_button)).perform(click());

        // Go back to the list
        pressBack();

        // Switch to the Favorites tab
        onView(withText("FAVORITES")).perform(click());

        // Then: Check if the number of items in the list is equal to 1 (only the favorite neighbor)
        interaction.check(withItemCount(1));

        // Check if the TextView indicating the name of the favorite neighbor is displayed
        onView(allOf(withId(R.id.item_list_name), isDisplayed())).check(matches(isDisplayed()));
    }
}