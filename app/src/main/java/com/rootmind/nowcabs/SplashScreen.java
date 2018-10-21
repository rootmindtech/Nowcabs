package com.rootmind.nowcabs;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by saikirangandham on 7/5/17.
 */

public class SplashScreen extends Activity {

    private static final String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startHeavyProcessing();

    }

    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d(TAG, "Splash Screen onPreExecute");

            Intent i = new Intent(SplashScreen.this, RiderMapActivity.class);

            Rider rider = (Rider) getIntent().getSerializableExtra("Rider");
            Bundle bundle = new Bundle();
            bundle.putSerializable("Rider", rider);
            i.putExtras(bundle);

            startActivity(i);
            finish();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }



}
