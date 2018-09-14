package com.udacity.gradle.builditbigger;

/**
 * Java Object representing a single Category.
 */
public class Category {

    private int mImageId;
    private String mCategoryName;

    public Category(int imageId, String categoryName) {
        mImageId = imageId;
        mCategoryName = categoryName;
    }

    // Getters

    public int getImageId() {
        return mImageId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }
}
