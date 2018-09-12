package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

    public static final String CATEGORY_MATH = "math";
    public static final String CATEGORY_ANIMAL = "animal";
    public static final String CATEGORY_MARRIAGE = "marriage";
    public static final String CATEGORY_TECH = "tech";
    public static final String CATEGORY_FAMILY = "family";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Set up Timber
        Timber.plant(new Timber.DebugTree());

        // Initialize CategoryAdapter and RecyclerView
        initAdapter();
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
}
