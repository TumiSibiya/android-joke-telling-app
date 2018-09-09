package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements CategoryAdapter.CategoryAdapterOnClickHandler {

    /** Member variable for the list of categories */
    private List<Category> mCategoryList;
    /** Member variable for CategoryAdapter */
    private CategoryAdapter mCategoryAdapter;
    /** This field is used for data binding */
    private FragmentMainBinding mFragmentMainBinding;

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
        String category = mCategoryList.get(position).getCategoryName();
        new EndpointsAsyncTask().execute(new Pair<Context, String>(getActivity(), category));
    }
}
