package com.example.android.jokedisplay;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokedisplay.databinding.FragmentJokeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * The JokeFragment displays a joke.
 */
public class JokeFragment extends Fragment {

    /** This field is used for data binding */
    private FragmentJokeBinding mFragmentJokeBinding;

    /** Final Strings to store state information about the list of jokes and joke index */
    public static final String JOKES = "jokes";
    public static final String JOKE_INDEX = "joke_index";

    /** Tag for logging */
    private static final String TAG = "JokeFragment";

    /** Variables to store a list of jokes and the index of the joke that this fragment displays */
    private List<String> mJokes;
    private int mJokeIndex;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public JokeFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the joke to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list of jokes and joke index) if there is one
        if (savedInstanceState != null) {
            mJokes = savedInstanceState.getStringArrayList(JOKES);
            mJokeIndex = savedInstanceState.getInt(JOKE_INDEX);
        }

        // Instantiate FragmentJokeBinding using DataBindingUtil
        mFragmentJokeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_joke, container, false);
        // Inflate the Joke fragment layout
        View rootView = mFragmentJokeBinding.getRoot();

        // If a list of jokes exists, set the joke string
        // Otherwise, create a Log statement that indicates that the list was not found
        if (mJokes != null) {
            // Set the joke to the TextView
            mFragmentJokeBinding.tvJoke.setText(mJokes.get(mJokeIndex));
            // Set a click listener on the TextView
            mFragmentJokeBinding.tvJoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mJokeIndex < mJokes.size() - 1) {
                        mJokeIndex ++;
                    } else {
                        mJokeIndex = 0;
                    }
                    // Set the joke string
                    mFragmentJokeBinding.tvJoke.setText(mJokes.get(mJokeIndex));
                }
            });
        } else {
            Log.v(TAG, "This fragment has a null list of jokes");
        }
        return rootView;
    }

    /**
     * Setter method for keeping track of the list jokes this fragment can display and which joke
     * in the list is currently being displayed
     *
     * @param jokeList The list of jokes
     */
    public void setJokeList(List<String> jokeList) {
        mJokes = jokeList;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList(JOKES, (ArrayList<String>) mJokes);
        outState.putInt(JOKE_INDEX, mJokeIndex);
    }
}