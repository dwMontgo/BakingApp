package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerViewSuccess() {
        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()));
    }

    @Test
    public void onClickSuccess() {
        onView(withId(R.id.recycler_view_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }


}
