package com.udacity.gradle.builditbigger;

public class Category {

    private int mImageId;
    private String mCategoryName;

    public Category(int imageId, String categoryName) {
        mImageId = imageId;
        mCategoryName = categoryName;
    }

    public int getImageId() {
        return mImageId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }
}
