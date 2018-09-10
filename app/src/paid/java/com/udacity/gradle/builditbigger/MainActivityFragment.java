package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokedisplay.JokeActivity;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements CategoryAdapter.CategoryAdapterOnClickHandler,
        EndpointsAsyncTask.OnTaskComplete {

    /** Member variable for the list of categories */
    private List<Category> mCategoryList;
    /** Member variable for CategoryAdapter */
    private CategoryAdapter mCategoryAdapter;
    /** This field is used for data binding */
    private FragmentMainBinding mFragmentMainBinding;

    /** The joke category the user will select */
    private String mCategory;

    public static final String CATEGORY_MATH = "math";
    public static final String CATEGORY_ANIMAL = "animal";
    public static final String CATEGORY_MARRIAGE = "marriage";
    public static final String CATEGORY_TECH = "tech";
    public static final String CATEGORY_FAMILY = "family";

    /**
     * Mandatory empty constructor
     */
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Instantiate FragmentMainBinding using DataBindingUtil
        mFragmentMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);
        View root = mFragmentMainBinding.getRoot();

        // Initialize CategoryAdapter and RecyclerView
        initAdapter();

        return root;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mFragmentMainBinding.rvCategory.setLayoutManager(layoutManager);
        mFragmentMainBinding.rvCategory.setHasFixedSize(true);
        mFragmentMainBinding.rvCategory.setAdapter(mCategoryAdapter);
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
        mFragmentMainBinding.pbLoadingIndicator.setVisibility(View.GONE);

        // Start a JokeActivity to display a joke
        startJokeActivity(jokeResult);
    }

    /**
     * Start a JokeActivity to display a joke.
     *
     * @param result The result returned by doInBackground method
     */
    private void startJokeActivity(List<String> result) {
        Intent intent = new Intent(this.getActivity(), JokeActivity.class);
        intent.putStringArrayListExtra(JokeActivity.JOKE_KEY, (ArrayList<String>) result);
        startActivity(intent);
    }

    /**
     * This method will make the loading indicator visible and hide the categories View.
     */
    private void showLoading() {
        // First, hide the categories
        mFragmentMainBinding.rvCategory.setVisibility(View.GONE);
        // Then, show the loading indicator
        mFragmentMainBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the View for the categories visible and hide the loading indicator.
     */
    private void showCategory() {
        // First, hide the loading indicator
        mFragmentMainBinding.pbLoadingIndicator.setVisibility(View.GONE);
        // Then, make sure the categories visible
        mFragmentMainBinding.rvCategory.setVisibility(View.VISIBLE);
    }
}
