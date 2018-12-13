package com.rootmind.nowcabs;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpSingleton {

    private static OkHttpSingleton singletonInstance;

    // No need to be static; OkHttpSingleton is unique so is this.
    private OkHttpClient okHttpClient;

    // Private so that this cannot be instantiated.
    private OkHttpSingleton() {

        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10,TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10,TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30,TimeUnit.SECONDS);

    }

    public static OkHttpSingleton getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new OkHttpSingleton();
        }
        return singletonInstance;
    }

    // In case you just need the unique OkHttpClient instance.
    public OkHttpClient getClient() {
        return okHttpClient;
    }

    public void closeConnections() {
        //okHttpClient.cancel(TAG);
    }

//    public List<PlacePredictions> getPredictions(){
//        // TODO: 26/09/2017  do the request!
//        return null;
//    }
}
