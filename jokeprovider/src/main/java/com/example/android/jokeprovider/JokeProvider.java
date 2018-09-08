package com.example.android.jokeprovider;

import java.util.List;

/**
 * The JokeProvider is responsible for providing jokes.
 */
public class JokeProvider {

    /**
     * Returns the list of jokes
     *
     * @param category The joke category
     */
    public List<String> getJokes(String category) {
        return JokeUtils.getJokes(category);
    }
}
