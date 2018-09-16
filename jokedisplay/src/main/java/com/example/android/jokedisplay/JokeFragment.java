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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokedisplay.databinding.FragmentJokeBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * The JokeFragment displays a joke.
 */
public class JokeFragment extends Fragment {

    /** This field is used for data binding */
    private FragmentJokeBinding mFragmentJokeBinding;

    /** Final Strings to store state information about the list of jokes and joke index */
    public static final String JOKES = "jokes";
    public static final String JOKE_INDEX = "joke_index";

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

        // If a list of jokes exists, set the joke string.
        // Otherwise, create a Log statement that indicates that the list was not found and
        // display the image that indicates that the backend is offline.
        if (mJokes != null) {
            // Make sure the View for a joke visible
            showJokes();
            // Set the joke to the TextView
            mFragmentJokeBinding.tvJoke.setText(mJokes.get(mJokeIndex));
        } else {
            Timber.v("This fragment has a null list of jokes");
            showOffline();
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

    public void setJokeIndex(int jokeIndex) {
        mJokeIndex = jokeIndex;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList(JOKES, (ArrayList<String>) mJokes);
        outState.putInt(JOKE_INDEX, mJokeIndex);
    }

    /**
     * This method will make the View for the joke data visible and hide the empty ImageView.
     */
    private void showJokes() {
        mFragmentJokeBinding.ivEmpty.setVisibility(View.GONE);
        mFragmentJokeBinding.tvJoke.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the empty ImageView visible and hide the joke string.
     */
    private void showOffline() {
        mFragmentJokeBinding.tvJoke.setVisibility(View.GONE);
        mFragmentJokeBinding.ivEmpty.setVisibility(View.VISIBLE);
    }
}
