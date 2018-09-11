package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    /** The constant for category animal */
    private static final String CATEGORY_ANIMAL = "animal";
    /** The list of jokes that EndpointAsyncTask retrieves */
    private List<String> mJokes = null;
    /** The Exception to handle errors */
    private Exception mException = null;
    /**
     * Anonymous interface instance
     * Reference @see "https://discussions.udacity.com/t/issue-with-testing-asynctask/244261/11"
     * @see "https://stackoverflow.com/questions/26353020/android-how-to-pass-interface-to-asynctask/26353184#26353184"
     */
    private EndpointsAsyncTask.OnTaskComplete mCallback = new EndpointsAsyncTask.OnTaskComplete() {
        @Override
        public void onTaskComplete(List<String> jokeResult) {
        }

        @Override
        public void onPreTask() {
        }
    };

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
        EndpointsAsyncTask task = new EndpointsAsyncTask(mCallback);
        task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            //
            @Override
            public void onComplete(List<String> jokeResult, Exception e) {
                mJokes = jokeResult;
                mException = e;
                // Decrement the count of the latch, releasing all waiting threads if the count
                // reaches zero
                mSignal.countDown();
            }
        }).execute(CATEGORY_ANIMAL);

        // Causes the current thread to wait until the latch has counted down to zero,
        // unless the thread is interrupted, or the specified waiting time elapses.
        mSignal.await(TIME_OUT, TimeUnit.SECONDS);

        // Verify that the exception is null
        assertNull(mException);
        // Verify that the received joke string is not null
        assertTrue(mJokes.get(0), mJokes.get(0) != null);
        // Verify that the received joke string is not empty
        assertTrue(mJokes.get(0), !mJokes.get(0).isEmpty());

    }

}
