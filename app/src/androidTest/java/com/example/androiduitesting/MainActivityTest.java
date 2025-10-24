package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before public void init() { Intents.init(); }
    @After  public void release() { Intents.release(); }

    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(name));
        onView(withId(R.id.button_confirm)).perform(click());
    }

    private void openFirstRow() {
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
    }

    @Test
    public void opensShowActivityOnItemClick() {
        addCity("Edmonton");
        openFirstRow();
        intended(hasComponent(ShowActivity.class.getName()));
    }

    @Test
    public void showsSameCityName() {
        addCity("Edmonton");
        openFirstRow();
        intended(hasExtra(ShowActivity.EXTRA_CITY, "Edmonton"));
        onView(withId(R.id.text_city)).check(matches(withText("Edmonton")));
    }

    @Test
    public void backButtonGoesBack() {
        addCity("Edmonton");
        openFirstRow();
        onView(withId(R.id.button_back)).perform(click());
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
