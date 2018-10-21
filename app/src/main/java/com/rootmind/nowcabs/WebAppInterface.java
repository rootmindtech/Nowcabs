package com.rootmind.nowcabs;

/**
 * Created by ROOTMIND on 2/11/2016.
 */

import android.app.AlertDialog;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.widget.Toast;
import android.webkit.JavascriptInterface;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;

import java.security.Key;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

public class WebAppInterface {

    Context mContext;


    private static final String ALGORITHM = "AES/ECB/PKCS7Padding";
    /*  private static final byte[] keyValue = new byte[]{'A', 'b', 'c', 'D', 'e', 'f', 'G', 'h', 'I', 'L', 'm', 'n', 'o', 'P', 'Q', 'r'};*/
    private static final byte[] keyValue = new byte[]{'S','r','i','5','3','4','2','6','0','$','#','%','&','@','^','$'};


    WebAppInterface(Context c) {



        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toastMessage) {

        Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();

    }



    @JavascriptInterface
    public void openDialog(String alertTitle, String alertMessage){

       // ((MainActivity)mContext).openAndroidDialog();

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder((MainActivity)mContext);
        myDialog.setTitle(alertTitle);
        myDialog.setMessage(alertMessage);
        myDialog.setPositiveButton("Ok", null);
        myDialog.show();

    }




    @JavascriptInterface
    public void SavePreferences(String mobileNoKey,String mobileNo, String firstNameKey,String firstName,String userGroupKey,String userGroup,String idKey,String id){

        try{

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(mobileNoKey, mobileNo);
            editor.putString(firstNameKey, firstName);
            editor.putString(userGroupKey, userGroup);
            editor.putString(idKey, id);

            editor.commit();

            //Toast.makeText(mContext, "success shared preferences ::", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){
           // Toast.makeText(mContext, "failed to load shared preferences ::"+e, Toast.LENGTH_SHORT).show();

        }

    }
    @JavascriptInterface
    public String LoadPreferences(String key){

        String result=null;
        try{

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            result=sharedPreferences.getString(key, "");

        }
        catch(Exception e){

            //Toast.makeText(mContext, "failed to load LoadPreferences ::"+e, Toast.LENGTH_SHORT).show();

        }
        return result;

    }

    @JavascriptInterface
    public void ClearPreferences(){


        try{

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.commit();

        }
        catch(Exception e){

            //Toast.makeText(mContext, "failed to load LoadPreferences ::"+e, Toast.LENGTH_SHORT).show();

        }


    }


    @JavascriptInterface
    public String aes128Encryption(String passwordToEnc){

                String encryptedValue=null;

                try{

                        Key generateKey = generateKey();
                        Cipher c = Cipher.getInstance(ALGORITHM, "BC");
                        c.init(Cipher.ENCRYPT_MODE, generateKey);
                        byte[] encValue = c.doFinal(passwordToEnc.getBytes("UTF8"));
                        encryptedValue = new String(Base64.encode(encValue, Base64.DEFAULT), "UTF-8");

                        //System.out.println("Encrypted " + encryptedValue);

                }
                catch(Exception e){

                       // Toast.makeText(mContext, "failed to complete encryption ::"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                return encryptedValue;

    }


    @JavascriptInterface
    public String aes128Decryption(String encryptedText){

                String decryptedValue=null;

                try{

                        Key generateKey = generateKey();
                        Cipher c = Cipher.getInstance(ALGORITHM, "BC");
                        c.init(Cipher.DECRYPT_MODE, generateKey);
                        byte[] data = Base64.decode(encryptedText, Base64.DEFAULT);
                        byte[] decValue = c.doFinal(data);
                        decryptedValue = new String(decValue);
                        //System.out.println("Decoded "+decryptedValue);

                }
                catch(Exception e){
                         //e.printStackTrace();
                }
                return decryptedValue;

    }

    private  Key generateKey() throws Exception{


                    Key key = new SecretKeySpec(keyValue, ALGORITHM);
                    return key;
    }



   /* @JavascriptInterface
    public void Vibrate(long milliseconds){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(milliseconds);
    }*/

    /*@JavascriptInterface
    public void SendSMS(String phoneNumber, String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null, message,null,null);
    }*/

    @JavascriptInterface
    public boolean isNetworkAvailable() {
        // Get Connectivity Manager class object from Systems Service
        ConnectivityManager cm = (ConnectivityManager)  mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get Network Info from connectivity Manager
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
