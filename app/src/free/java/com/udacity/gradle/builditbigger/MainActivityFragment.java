package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokedisplay.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


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

    /**
     * An Interstitial ad object is used to request and display ads after the user hits the category
     * item, but before showing a joke.
     * Reference: @see "https://github.com/googleads/googleads-mobile-android-examples/tree/master/java/admob/InterstitialExample"
     */
    private InterstitialAd mInterstitialAd;

    /** The joke category the user will select */
    private String mCategory;

    public static final String CATEGORY_MATH = "math";
    public static final String CATEGORY_ANIMAL = "animal";
    public static final String CATEGORY_MARRIAGE = "marriage";
    public static final String CATEGORY_TECH = "tech";

    /**
     * Mandatory empty constructor
     */
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Instantiate FragmentMainBinding using DataBindingUtil
        mFragmentMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);
        View root = mFragmentMainBinding.getRoot();

        // Initialize CategoryAdapter and RecyclerView
        initAdapter();

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // Create the InterstitialAd and set the adUnitId
        mInterstitialAd = new InterstitialAd(this.getContext());
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
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(getActivity(), mCategory));
    }

    /**
     * Define the behavior for onPreTask.
     * Show the loading indicator while the joke is being retrieved.
     */
    @Override
    public void onPreTask() {
        mFragmentMainBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        mFragmentMainBinding.rvCategory.setVisibility(View.GONE);
    }

    /**
     * Define the behavior for onTaskComplete.
     * Hide the loading indicator when a joke is ready.
     *
     * @param jokeResult The result returned by doInBackground method
     */
    @Override
    public void onTaskComplete(List<String> jokeResult) {
        mFragmentMainBinding.pbLoadingIndicator.setVisibility(View.GONE);
        mFragmentMainBinding.rvCategory.setVisibility(View.VISIBLE);

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

}
