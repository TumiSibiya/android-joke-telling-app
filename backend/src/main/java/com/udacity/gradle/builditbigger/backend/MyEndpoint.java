package com.udacity.gradle.builditbigger.backend;

import com.example.android.jokeprovider.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a category and pull jokes */
    @ApiMethod(name = "pullJokes")
    public MyBean pullJokes(@Named("category") String category) {
        JokeProvider jokeProvider = new JokeProvider();
        List<String> jokes = jokeProvider.getJokes(category);
        MyBean response = new MyBean();
        response.setListData(jokes);

        return response;
    }

}
