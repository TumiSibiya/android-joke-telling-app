/*
 * Copyright 2018 Soojeong Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.jokedisplay;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.jokedisplay.databinding.ActivityJokeBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.example.android.jokedisplay.JokeFragment.JOKES;
import static com.example.android.jokedisplay.JokeFragment.JOKE_INDEX;

/**
 * The JokeActivity will display jokes passed to it as intent extras.
 */
public class JokeActivity extends AppCompatActivity {

    /** Constant value for the key of an intent extra */
    public static final String JOKE_KEY = "Joke key";
    /** Constant string for saving the current state of play/pause button */
    private static final String JOKE_PLAY = "joke_play";
    /** Type of the share intent data */
    private static final String SHARE_INTENT_TYPE_TEXT = "text/plain";
    /** Unicode for Emoji used for sharing a joke */
    private static final int UNICODE_GRIN = 0x1F601;
    private static final int UNICODE_ROFL = 0x1F923;
    /** The delay (in milliseconds) until the Runnable will be executed */
    private static final int DELAY_MILLIS = 7000;

    /** Member variable for the list of jokes and list index */
    private List<String> mJokes;
    private int mJokeIndex;

    /** This field is used for data binding */
    private ActivityJokeBinding mJokeBinding;

    /** True when the user clicks the play button to switch the fragment automatically, otherwise false */
    private Boolean mIsPlaying;

    /** Schedule a runnable to be executed at some point in the future */
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_joke);

        // Set up Timber
        Timber.plant(new Timber.DebugTree());

        // Set the value of mIsPlaying to false not to switch the fragment automatically
        mIsPlaying = false;

        // Load the saved state (the list of jokes, joke index, and play/pause) if there is one
        if (savedInstanceState != null) {
            mJokes = savedInstanceState.getStringArrayList(JOKES);
            mJokeIndex = savedInstanceState.getInt(JOKE_INDEX);
            mIsPlaying = savedInstanceState.getBoolean(JOKE_PLAY);
        }

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

        // Show up button in the actionbar
        showUpButton();

        // Create the Handler object
        mHandler = new Handler();
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

    /**
     * Shows up button in the actionbar.
     */
    private void showUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            // When the up button in action bar is clicked, finish the JokeActivity.
            finish();
            return true;
        } else if (itemId == R.id.action_share) {
            // If a list of joke exists, share a joke using intent. Otherwise, show a toast message.
            shareJoke();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user clicks the previous button, it navigates to the previous joke page.
     */
    public void onPreviousButtonClick(View view) {
        // Decrement position by one if the index is greater than zero
        if (mJokeIndex > 0) {
            mJokeIndex--;
        } else {
            // Check if a list of jokes exists before trying to use mJoke.size()
            if (mJokes != null) {
                // The start of joke list has been reached, so return to ending index
                mJokeIndex = mJokes.size() - 1;
            } else {
                // If a list of jokes is null, show a toast message.
                Toast.makeText(this, getString(R.string.toast_backend_offline),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Give the correct joke index to the new fragment and replace the old fragment with a new one
        replaceFragment();
    }

    /**
     * When the user clicks the next button, it navigates to the next joke page.
     */
    public void onNextButtonClick(View view) {
        // Replace the old fragment with the next fragment
        replaceWithNextFragment();
    }

    /**
     * Replace the old fragment with the next fragment.
     */
    private void replaceWithNextFragment() {
        // Check if a list of jokes exists before trying to use mJoke.size()
        if (mJokes != null) {
            // Increment position as long as the index remains (the size of the joke list)
            if (mJokeIndex < mJokes.size() - 1) {
                mJokeIndex++;
            } else {
                // The end of joke list has been reached, so return to beginning index
                mJokeIndex = 0;
            }
            // Give the correct joke index to the new fragment and replace the old fragment with a new one
            replaceFragment();
        } else {
            // If a list of jokes is null, show a toast message.
            Toast.makeText(this, getString(R.string.toast_backend_offline),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the user clicks the play/pause button.
     *
     * Reference: @see "https://stackoverflow.com/questions/18120174/how-to-play-and-pause-in-only-one-button-android"
     */
    public void onPlayButtonClick(View view) {
        // Check if a list of jokes exists
        if (mJokes != null) {
            if (mIsPlaying) {
                // Remove the scheduled execution of a runnable
                pause();
                // Set the image to 'play' image
                mJokeBinding.navigationPlay.setImageResource(R.drawable.ic_play);
            } else {
                // Switch the fragment automatically and change play image to pause image
                play();
            }
            // Reverse the value of a boolean
            mIsPlaying = !mIsPlaying;
        } else {
            // If a list of jokes is null, show a toast message.
            Toast.makeText(this, getString(R.string.toast_backend_offline),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Switches the fragment automatically.
     *
     * Reference: @see "https://stackoverflow.com/questions/35497844/handler-postdelayedrunnable-vs-countdowntimer"
     * @see "https://guides.codepath.com/android/Repeating-Periodic-Tasks#execute-recurring-code-with-specified-interval"
     * @see "https://stackoverflow.com/questions/39024588/android-postdelayed-handler-inside-a-for-loop"
     */
    private void play() {
        // Set the image to 'pause' image
        mJokeBinding.navigationPlay.setImageResource(R.drawable.ic_pause);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                // After 7000 milliseconds in future, it navigates to the next joke page.
                replaceWithNextFragment();

                // Repeat this the same runnable code block again another 7 seconds
                // 'this' is referencing the Runnable object
                mHandler.postDelayed(this, DELAY_MILLIS);
            }
        };
        // Start the initial runnable task by posting through the handler
        mHandler.post(mRunnable);
    }

    /**
     * Removes any pending posts of Runnable.
     */
    private void pause() {
        mHandler.removeCallbacks(mRunnable);
    }

    /**
     * Replace the old fragment with a new one to display a new joke.
     */
    private void replaceFragment() {
        // Create a joke fragment
        JokeFragment jokeFragment = new JokeFragment();
        // Give the correct joke index to the new fragment
        jokeFragment.setJokeIndex(mJokeIndex);
        jokeFragment.setJokeList(mJokes);
        // Replace the old fragment with a new one
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.joke_container, jokeFragment)
                .commit();
    }

    /**
     * Save the current state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Store the list of jokes, joke index, and play/pause state to our bundle
        outState.putStringArrayList(JOKES, (ArrayList<String>) mJokes);
        outState.putInt(JOKE_INDEX, mJokeIndex);
        outState.putBoolean(JOKE_PLAY, mIsPlaying);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mIsPlaying) {
            // If mIsPlaying is true, switches the fragment automatically.
            // Reference: @see "https://stackoverflow.com/questions/23538843/android-handler-and-runnable-nullpointer"
            play();
        }
    }

    /**
     * Make sure remove any pending posts of Runnable that are in the message queue.
     * Reference: @see "https://stackoverflow.com/questions/23538843/android-handler-and-runnable-nullpointer"
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mIsPlaying) {
            pause();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.joke, menu);
        return true;
    }

    /**
     * If a list of joke exists, share a joke using intent. Otherwise, show a toast message.
     */
    private void shareJoke() {
        if (mJokes != null) {
            // Share a joke using share intent
            startActivity(createShareIntent());
        } else {
            Toast.makeText(this, getString(R.string.toast_backend_offline),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Uses the ShareCompat Intent builder to create our share intent for sharing.
     * Return the newly created intent. Before using this method, we should check if a list of jokes
     * exists.
     */
    private Intent createShareIntent() {
        // Text message to share
        String shareText = mJokes.get(mJokeIndex) + getString(R.string.space)
                + getEmojiByUnicode(UNICODE_GRIN) + getEmojiByUnicode(UNICODE_ROFL);

        // Create share intent
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType(SHARE_INTENT_TYPE_TEXT)
                .setText(shareText)
                .setChooserTitle(getString(R.string.share_title))
                .createChooserIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }

    /**
     * Returns String for Emoji.
     *
     * Reference: @see "https://stackoverflow.com/questions/26893796/how-set-emoji-by-unicode-in-a-textview"
     */
    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
