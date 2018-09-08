package com.udacity.gradle.builditbigger.backend;

import java.util.List;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;
    private List<String> myListData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }

    public List<String> getListData() {
        return myListData;
    }

    public void setListData(List<String> listData) {
        myListData = listData;
    }
}