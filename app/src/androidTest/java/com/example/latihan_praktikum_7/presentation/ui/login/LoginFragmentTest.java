package com.example.latihan_praktikum_7.presentation.ui.login;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.latihan_praktikum_7.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLoginFormInput() {
        // Input email
        Espresso.onView(ViewMatchers.withId(R.id.email_input))
                .perform(ViewActions.typeText("test@example.com"), ViewActions.closeSoftKeyboard());

        // Input password
        Espresso.onView(ViewMatchers.withId(R.id.password_input))
                .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());

        // Klik tombol login
        Espresso.onView(ViewMatchers.withId(R.id.email_login_btn))
                .perform(ViewActions.click());
    }
}
