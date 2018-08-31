package com.example.android.jokedisplay;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.jokedisplay.databinding.ActivityJokeBinding;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "Joke key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJokeBinding jokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_joke);

        Intent intent = getIntent();
        String jokeFromProvider = intent.getStringExtra(JOKE_KEY);
        jokeBinding.tvJokeDisplay.setText(jokeFromProvider);
    }
}
