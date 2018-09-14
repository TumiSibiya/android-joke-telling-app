package com.udacity.gradle.builditbigger.backend;

import java.util.List;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private List<String> myListData;

    public List<String> getListData() {
        return myListData;
    }

    public void setListData(List<String> listData) {
        myListData = listData;
    }
}