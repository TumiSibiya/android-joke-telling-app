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

package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.jokedisplay.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.udacity.gradle.builditbigger.Constants.CATEGORY_ANIMAL;
import static com.udacity.gradle.builditbigger.Constants.CATEGORY_MARRIAGE;
import static com.udacity.gradle.builditbigger.Constants.CATEGORY_MATH;
import static com.udacity.gradle.builditbigger.Constants.CATEGORY_TECH;
import static com.udacity.gradle.builditbigger.Constants.LAYOUT_MANAGER_STATE;

/**
 * The MainActivity displays the list of joke categories.
 */
public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryAdapterOnClickHandler,
        EndpointsAsyncTask.OnTaskComplete {

    /** Member variable for the list of categories */
    private List<Category> mCategoryList;
    /** Member variable for CategoryAdapter */
    private CategoryAdapter mCategoryAdapter;
    /** This field is used for data binding */
    private ActivityMainBinding mMainBinding;

    /**
     * An Interstitial ad object is used to request and display ads after the user hits the category
     * item, but before showing a joke.
     * Reference: @see "https://github.com/googleads/googleads-mobile-android-examples/tree/master/java/admob/InterstitialExample"
     */
    private InterstitialAd mInterstitialAd;

    /** The joke category the user will select */
    private String mCategory;

    /** Member variable for restoring list items positions on device rotation */
    private Parcelable mSavedLayoutState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Initialize CategoryAdapter and RecyclerView
        initAdapter();

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mMainBinding.adView.loadAd(adRequest);

        // Create the InterstitialAd and set the adUnitId
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        // Load an interstitial ad
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial ad if one isn't already loaded. Then, kick off a task
                // to retrieve a joke
                startTask();
            }
        });

        if (savedInstanceState != null) {
            // Get the scroll position
            mSavedLayoutState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
            // Restore the scroll position
            mMainBinding.rvCategory.getLayoutManager().onRestoreInstanceState(mSavedLayoutState);
        }
    }

    /**
     * Creates a CategoryAdapter and set it to the RecyclerView
     */
    public void initAdapter() {
        // Create joke categories
        addCategory();
        // The CategoryAdapter is responsible for displaying each category in the list
        mCategoryAdapter = new CategoryAdapter(mCategoryList, this);

        // A LinearLayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView into a linear list
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMainBinding.rvCategory.setLayoutManager(layoutManager);
        mMainBinding.rvCategory.setHasFixedSize(true);
        mMainBinding.rvCategory.setAdapter(mCategoryAdapter);

        // Restore the scroll position after setting up the adapter with the list of categories
        mMainBinding.rvCategory.getLayoutManager().onRestoreInstanceState(mSavedLayoutState);
    }

    /**
     * Creates a list of joke categories.
     */
    private void addCategory() {
        mCategoryList = new ArrayList<>();
        mCategoryList.add(new Category(R.drawable.math, CATEGORY_MATH));
        mCategoryList.add(new Category(R.drawable.dog, CATEGORY_ANIMAL));
        mCategoryList.add(new Category(R.drawable.couple, CATEGORY_MARRIAGE));
        mCategoryList.add(new Category(R.drawable.tech, CATEGORY_TECH));
    }

    /**
     * Handles RecyclerView item clicks. When a user clicks each category, start a task to
     * retrieve a joke.
     *
     * @param position Position of the category in the list
     */
    @Override
    public void onItemClick(int position) {
        mCategory = mCategoryList.get(position).getCategoryName();
        showInterstitial();
    }

    /**
     * Show the ad if it's ready. Otherwise, use log and start a task to retrieve a joke.
     */
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Timber.d("The interstitial wasn't loaded yet.");
            startTask();
        }
    }

    /**
     * Request a new ad if one isn't already loaded, and kick off a task to retrieve a joke.
     */
    private void startTask() {
        // Load the next interstitial ad if one isn't already loaded
        if (!mInterstitialAd.isLoaded() && !mInterstitialAd.isLoaded()) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
        // Kick off a task to retrieve a joke
        new EndpointsAsyncTask(this).execute(mCategory);
    }

    /**
     * Define the behavior for onPreTask.
     * Show the loading indicator while the joke is being retrieved.
     */
    @Override
    public void onPreTask() {
        mMainBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        mMainBinding.rvCategory.setVisibility(View.GONE);
    }

    /**
     * Define the behavior for onTaskComplete.
     * Hide the loading indicator when a joke is ready.
     *
     * @param jokeResult The result returned by doInBackground method
     */
    @Override
    public void onTaskComplete(List<String> jokeResult) {
        mMainBinding.pbLoadingIndicator.setVisibility(View.GONE);
        mMainBinding.rvCategory.setVisibility(View.VISIBLE);

        // Start a JokeActivity to display a joke
        startJokeActivity(jokeResult);
    }

    /**
     * Start a JokeActivity to display a joke.
     *
     * @param result The result returned by doInBackground method
     */
    private void startJokeActivity(List<String> result) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putStringArrayListExtra(JokeActivity.JOKE_KEY, (ArrayList<String>) result);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method for persisting data across Activity recreation
     *
     * Reference: @see "https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll
     * -position-using-recyclerview-state"
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Store the scroll position in our bundle
        outState.putParcelable(LAYOUT_MANAGER_STATE,
                mMainBinding.rvCategory.getLayoutManager().onSaveInstanceState());
    }
}
