package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.jokedisplay.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

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
        void onComplete(String jokeResult, Exception e);
    }

    /**
     * Sets the EndpointsAsyncTaskListener and returns it.
     */
    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected String doInBackground(Context... params) {
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

        mContext = params[0];

        try {
            return myApiService.pullJokes().execute().getData();
        } catch (IOException e) {
            mException = e;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(mContext, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, result);
        mContext.startActivity(intent);

        if (mListener != null) {
            // Trigger the callback onComplete after the background computation finishes
            mListener.onComplete(result, mException);
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