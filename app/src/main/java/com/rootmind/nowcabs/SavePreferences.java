package com.rootmind.nowcabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.os.StrictMode;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rootmindtechsoftprivatelimited on 23/06/17.
 */

public  class SavePreferences extends Activity{

    private static final String TAG = "SavePreferences";

    public static final String MY_PREFS_NAME = "MyPrefs" ;

   // public static final String PREFS_NAME = "MyApp_Settings";

    //Context mContext=this;

    Context context=this;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         sharedPreferences =context.getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE );

    }


    public void savedPreferences(String mobileNoKey,String mobileNo, String firstNameKey,String firstName,String userGroupKey,String userGroup,String idKey,String id){

        try{

            Log.d(TAG, "savedPreferences: "+ mobileNoKey +" "+mobileNo);

            //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

            //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, 0);

           // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

           // SharedPreferences sharedPreferences = context.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



            Log.d(TAG, "alert ");

            SharedPreferences.Editor editor = sharedPreferences.edit();

            Log.d(TAG, "alert-0 ");
            editor.putString(mobileNoKey, mobileNo);
            editor.putString(firstNameKey, firstName);
            editor.putString(userGroupKey, userGroup);
            editor.putString(idKey, id);
            editor.commit();

            Log.d(TAG, "alert-1 ");

//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            Log.d(TAG, "alert-2 ");
//
//            editor.putString(mobileNoKey, mobileNo);
//            editor.putString(firstNameKey, firstName);
//            editor.putString(userGroupKey, userGroup);
//            editor.putString(idKey, id);
//
//            Log.d(TAG, "alert-3 ");
//
//            editor.commit();

            Log.d(TAG, "success save preferences ");

            //Toast.makeText(mContext, "success shared preferences ::", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){
            //Toast.makeText(mContext, "failed to load shared preferences ::"+e, Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }

    }

    public String loadPreferences(String key){

        String result=null;
        try{

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//            result=sharedPreferences.getString(key, "");


            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            result = prefs.getString(key, "");


        }
        catch(Exception e){

            //Toast.makeText(mContext, "failed to load LoadPreferences ::"+e, Toast.LENGTH_SHORT).show();

        }

        Log.d(TAG, "loadPreferences result: "+result);

        return result;

    }


    public void clearPreferences(){


        try{

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//            SharedPreferences.Editor editor=sharedPreferences.edit();
//            editor.clear();
//            editor.commit();

        }
        catch(Exception e){

            //Toast.makeText(mContext, "failed to load LoadPreferences ::"+e, Toast.LENGTH_SHORT).show();

        }


    }
}
