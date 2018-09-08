package com.example.android.jokedisplay;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.jokedisplay.databinding.ActivityJokeBinding;

import java.util.List;

/**
 * The JokeActivity will display jokes passed to it as intent extras.
 */
public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "Joke key";

    /** Member variable for the list of jokes */
    private List<String> mJokes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJokeBinding jokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_joke);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            // Create a new JokeFragment
            JokeFragment jokeFragment = new JokeFragment();

            // Get the list of jokes
            mJokes = getJokes();
            // Set the list of jokes for the joke fragment
            jokeFragment.setJokeList(mJokes);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.joke_container, jokeFragment)
                    .commit();
        }
    }

    /**
     * Returns the list of jokes passed to JokeActivity as an intent extra.
     */
    private List<String> getJokes() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(JokeActivity.JOKE_KEY)) {
                mJokes = intent.getStringArrayListExtra(JokeActivity.JOKE_KEY);
            }
        }
        return mJokes;
    }
}
