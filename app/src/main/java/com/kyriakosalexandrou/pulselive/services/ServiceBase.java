package com.kyriakosalexandrou.pulselive.services;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * base class for all the services
 */
public abstract class ServiceBase extends AsyncTask<String, String, String> {
    public static String BASE_URL = "http:";
    protected HttpURLConnection mUrlConnection;
    protected ServiceBaseCallback mServiceBaseCallback;
    private String mErrorMsg;

    public ServiceBase(ServiceBaseCallback serviceBaseCallback, String errorMsg) {
        this.mServiceBaseCallback = serviceBaseCallback;
        this.mErrorMsg = errorMsg;
    }

    @Override
    protected String doInBackground(String... args) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(args[0]);
            mUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(mUrlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            cancel(true);

        } finally {
            mUrlConnection.disconnect();
        }

        return result.toString();
    }

    @Override
    protected void onCancelled(String s) {
        if(isCancelled()){
            mServiceBaseCallback.onServiceRequestFailure(mErrorMsg);
        }
    }

    public interface ServiceBaseCallback {
        void onServiceRequestFailure(String errorMsg);
    }
}
