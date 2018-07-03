package zhuangzhi.android.baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import zhuangzhi.android.baking.ui.recipe.RecipeActivity;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    private static final String LABEL_INGREDIENTS = "Ingredients List:";

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensRecipeDetailActivity() {
        onView(withRecyclerView(R.id.recipe_list).atPosition(1)).perform(click());
        onView(withId(R.id.recipe_detail_container)).check(matches(isDisplayed()));
        onView(withId(R.id.label_ingredients)).check(matches(withText(LABEL_INGREDIENTS)));
    }

    @Test
    public void recipeListUITest() {
        onView(withRecyclerView(R.id.recipe_list).atPosition(1)).check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.recipe_list).atPositionOnView(1, R.id.recipe_name))
                .check(matches(isDisplayed()))
                .check(matches(withText("Brownies")));

        onView(withRecyclerView(R.id.recipe_list).atPositionOnView(1, R.id.recipe_serve))
                .check(matches(isDisplayed()))
                .check(matches(withText("8")));

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

}
