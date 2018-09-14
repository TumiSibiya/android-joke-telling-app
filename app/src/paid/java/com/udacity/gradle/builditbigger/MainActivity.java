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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.jokedisplay.JokeActivity;
import com.udacity.gradle.builditbigger.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.udacity.gradle.builditbigger.Constants.CATEGORY_ANIMAL;
import static com.udacity.gradle.builditbigger.Constants.CATEGORY_FAMILY;
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

    /** The joke category the user will select */
    private String mCategory;

    /** Member variable for restoring list items positions on device rotation */
    private Parcelable mSavedLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Set up Timber
        Timber.plant(new Timber.DebugTree());

        // Initialize CategoryAdapter and RecyclerView
        initAdapter();

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
        // Category family is only included in the 'paid' flavor
        mCategoryList.add(new Category(R.drawable.family, CATEGORY_FAMILY));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Make the view for the categories visible and hide the loading indicator
        showCategory();
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
        startTask();
    }

    /**
     * Request a new ad if one isn't already loaded, and kick off a task to retrieve a joke.
     */
    private void startTask() {
        // Kick off a task to retrieve a joke
        new EndpointsAsyncTask(this).execute(mCategory);
    }

    /**
     * Define the behavior for onPreTask.
     * Show the loading indicator while the joke is being retrieved.
     */
    @Override
    public void onPreTask() {
        showLoading();
    }

    /**
     * Define the behavior for onTaskComplete.
     * Hide the loading indicator when a joke is ready.
     *
     * @param jokeResult The result returned by doInBackground method
     */
    @Override
    public void onTaskComplete(List<String> jokeResult) {
        // Hide the loading indicator
        mMainBinding.pbLoadingIndicator.setVisibility(View.GONE);

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

    /**
     * This method will make the loading indicator visible and hide the categories View.
     */
    private void showLoading() {
        // First, hide the categories
        mMainBinding.rvCategory.setVisibility(View.GONE);
        // Then, show the loading indicator
        mMainBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the View for the categories visible and hide the loading indicator.
     */
    private void showCategory() {
        // First, hide the loading indicator
        mMainBinding.pbLoadingIndicator.setVisibility(View.GONE);
        // Then, make sure the categories visible
        mMainBinding.rvCategory.setVisibility(View.VISIBLE);
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
