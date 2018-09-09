package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.example.android.jokedisplay.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, List<String>> {

    private static MyApi myApiService = null;
    private Context mContext;

    /** Member variable for exception to handle errors */
    private Exception mException = null;

    /**
     * Define a new interface EndpointAsyncTaskListener that triggers a Callback.
     * The callback is a method named onComplete that contains String joke and exception.
     */
    private EndpointsAsyncTaskListener mListener = null;

    public interface EndpointsAsyncTaskListener {
        void onComplete(List<String> jokeResult, Exception e);
    }

    /**
     * Define a new interface OnTaskComplete that triggers a Callback.
     *
     * Reference: @see "https://discussions.udacity.com/t/unable-to-start-activity-componentinfo-free-flavor/243702/10"
     * @see "https://discussions.udacity.com/t/waiting-for-the-task-to-get-the-joke-before-launching-android-library-activity-in-build-it-bigger/166488"
     * @see "https://stackoverflow.com/questions/9963691/android-asynctask-sending-callbacks-to-ui"
     */
    private OnTaskComplete mCallback;

    public interface OnTaskComplete {
        void onTaskComplete(List<String> jokeResult);
        void onPreTask();
    }

    public EndpointsAsyncTask(OnTaskComplete callback) {
        mCallback = callback;
    }

    /**
     * Sets the EndpointsAsyncTaskListener and returns it.
     */
    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            // Trigger the callback onPreTask to show a loading indicator
            mCallback.onPreTask();
        }
    }

    @Override
    protected List<String> doInBackground(Pair<Context, String>... params) {
        if (myApiService == null) { // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local dev app server
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local dev app server
                    .setRootUrl("http://192.168.0.19:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
                    // end options for dev app server

            myApiService = builder.build();
        }

        mContext = params[0].first;
        String category = params[0].second;

        try {
            return myApiService.pullJokes(category).execute().getListData();
        } catch (IOException e) {
            mException = e;
            Timber.e( "Failed to fetch jokes: "+ e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<String> result) {
        Intent intent = new Intent(mContext, JokeActivity.class);
        intent.putStringArrayListExtra(JokeActivity.JOKE_KEY, (ArrayList<String>) result);
        mContext.startActivity(intent);

        if (mListener != null) {
            // Trigger the callback onComplete after the background computation finishes
            mListener.onComplete(result, mException);
        }
        // Trigger the callback onTaskComplete
        if (mCallback != null) {
            mCallback.onTaskComplete(result);
        }
    }

    @Override
    protected void onCancelled() {
        if (mListener != null) {
            mException = new InterruptedException("AsyncTask cancelled");
            mListener.onComplete(null, mException);
        }
    }
}