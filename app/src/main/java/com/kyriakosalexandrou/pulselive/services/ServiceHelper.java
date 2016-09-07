package com.kyriakosalexandrou.pulselive.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class ServiceHelper {
    public static String BASE_URL = "http:";
    protected HttpURLConnection mUrlConnection;
    private final String mErrorMsg;

    public ServiceHelper(String errorMsg) {
        this.mErrorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public String fetchUrlData(String arg) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        URL url = new URL(arg);
        mUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(mUrlConnection.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }
}