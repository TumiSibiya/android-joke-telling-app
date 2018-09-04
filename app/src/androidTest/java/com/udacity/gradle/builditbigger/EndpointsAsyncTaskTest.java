package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * This test checks that EndpointsAsyncTask successfully retrieves a non-empty string.
 *
 * Reference: @see "http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html"
 * @see "https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html"
 */
@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    /** The given count for a CountDownLatch */
    private static final int COUNT = 1;
    /** The waiting time */
    private static final int TIME_OUT = 10;
    /** The joke string that EndpointAsyncTask retrieves */
    private String mJoke = null;
    /** The Exception to handle errors */
    private Exception mException = null;

    /**
     * A synchronization aid that allows one or more threads to wait until a set of operations being
     * performed in other threads completes.
     */
    private CountDownLatch mSignal = null;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Construct a CountDownLatch initialized with the given count
        mSignal = new CountDownLatch(COUNT);
    }

    @Test
    public void checkNonEmptyString() throws InterruptedException {
        // Create an EndpointsAsyncTask
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            //
            @Override
            public void onComplete(String jokeResult, Exception e) {
                mJoke = jokeResult;
                mException = e;
                // Decrement the count of the latch, releasing all waiting threads if the count
                // reaches zero
                mSignal.countDown();
            }
        }).execute(InstrumentationRegistry.getTargetContext());

        // Causes the current thread to wait until the latch has counted down to zero,
        // unless the thread is interrupted, or the specified waiting time elapses.
        mSignal.await(TIME_OUT, TimeUnit.SECONDS);

        // Verify that the exception is null
        assertNull(mException);
        // Verify that the received joke string is not null
        assertTrue(mJoke, mJoke != null);
        // Verify that the received joke string is not empty
        assertTrue(mJoke, !mJoke.isEmpty());

        // Check the joke string is displayed in the TextView
        onView(withId(R.id.tv_joke_display))
                .check(matches(isDisplayed()));
    }

}
