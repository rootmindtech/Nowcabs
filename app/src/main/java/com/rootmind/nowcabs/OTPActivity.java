package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth=null;
    String mVerificationId=null;
    String mResendToken=null;

    String mobileNo;
    //Parameter parameter;
    String locale;

    private EditText otpEditText1;
    private EditText otpEditText2;
    private EditText otpEditText3;
    private EditText otpEditText4;
    private EditText otpEditText5;
    private EditText otpEditText6;

    public Toolbar toolbar;
    SharedPreferences sharedPreferences;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        mobileNo = (String) getIntent().getSerializableExtra("mobileNo");
        //parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
        locale = (String) getIntent().getSerializableExtra("locale");


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        //29-Sep-2018
        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        otpEditText1 = findViewById(R.id.otpEditText1);
        otpEditText2 = findViewById(R.id.otpEditText2);
        otpEditText3 = findViewById(R.id.otpEditText3);
        otpEditText4 = findViewById(R.id.otpEditText4);
        otpEditText5 = findViewById(R.id.otpEditText5);
        otpEditText6 = findViewById(R.id.otpEditText6);


        //FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();

        // Configure faking the auto-retrieval with the whitelisted numbers.
        //firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);

        sendVerificationCode(mobileNo);


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp1 = otpEditText1.getText().toString().trim();
                if (otp1.isEmpty() || otp1.length() < 1) {
                    otpEditText1.setError("Enter valid code");
                    otpEditText1.requestFocus();
                    return;
                }
                String otp2 = otpEditText2.getText().toString().trim();
                if (otp2.isEmpty() || otp2.length() < 1) {
                    otpEditText2.setError("Enter valid code");
                    otpEditText2.requestFocus();
                    return;
                }
                String otp3 = otpEditText3.getText().toString().trim();
                if (otp3.isEmpty() || otp3.length() < 1) {
                    otpEditText3.setError("Enter valid code");
                    otpEditText3.requestFocus();
                    return;
                }
                String otp4 = otpEditText4.getText().toString().trim();
                if (otp4.isEmpty() || otp4.length() < 1) {
                    otpEditText4.setError("Enter valid code");
                    otpEditText4.requestFocus();
                    return;
                }
                String otp5 = otpEditText5.getText().toString().trim();
                if (otp5.isEmpty() || otp5.length() < 1) {
                    otpEditText5.setError("Enter valid code");
                    otpEditText5.requestFocus();
                    return;
                }
                String otp6 = otpEditText6.getText().toString().trim();
                if (otp6.isEmpty() || otp6.length() < 1) {
                    otpEditText6.setError("Enter valid code");
                    otpEditText6.requestFocus();
                    return;
                }


                //verifying the code entered manually
                verifyVerificationCode(otp1.concat(otp2).concat(otp3).concat(otp4).concat(otp5).concat(otp6));
            }
        });



        //------start move to next field after click -------

        otpEditText1.addTextChangedListener(new TextWatcher() {


            // onTextChanged
            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count)
            {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();

                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged (Editable s){
            }

            // beforeTextChanged
            @Override
            public void beforeTextChanged (CharSequence s,int start,
                                           int count, int after){
            }

        });


        otpEditText2.addTextChangedListener(new TextWatcher() {


            // onTextChanged
            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count)
            {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();

                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged (Editable s){
            }

            // beforeTextChanged
            @Override
            public void beforeTextChanged (CharSequence s,int start,
                                           int count, int after){
            }

        });

        otpEditText3.addTextChangedListener(new TextWatcher() {


            // onTextChanged
            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count)
            {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();

                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged (Editable s){
            }

            // beforeTextChanged
            @Override
            public void beforeTextChanged (CharSequence s,int start,
                                           int count, int after){
            }

        });

        otpEditText4.addTextChangedListener(new TextWatcher() {


            // onTextChanged
            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count)
            {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();

                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged (Editable s){
            }

            // beforeTextChanged
            @Override
            public void beforeTextChanged (CharSequence s,int start,
                                           int count, int after){
            }

        });


        otpEditText5.addTextChangedListener(new TextWatcher() {


            // onTextChanged
            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count)
            {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();

                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged (Editable s){
            }

            // beforeTextChanged
            @Override
            public void beforeTextChanged (CharSequence s,int start,
                                           int count, int after){
            }

        });

        otpEditText6.addTextChangedListener(new TextWatcher() {


            // onTextChanged
            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count)
            {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null) {
                        next.requestFocus();
                    }

                    CommonService.hideKeyboard(OTPActivity.this);


                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged (Editable s){


            }



            // beforeTextChanged
            @Override
            public void beforeTextChanged (CharSequence s,int start,
                                           int count, int after){
            }

        });

        //------end move to next field after click -------


        //---------start of focus listener
//        otpEditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    otpEditText1.clearFocus();
//                    Log.d("OTP", "OTP onFocusChange:");
//                    CommonService.hideKeyboardView(OTPActivity.this, otpEditText1);
//                }
//            }
//        });
//        otpEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    otpEditText2.clearFocus();
//                    Log.d("OTP", "OTP onFocusChange:");
//                    CommonService.hideKeyboardView(OTPActivity.this, otpEditText2);
//                }
//            }
//        });
//        otpEditText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    otpEditText3.clearFocus();
//                    Log.d("OTP", "OTP onFocusChange:");
//                    CommonService.hideKeyboardView(OTPActivity.this, otpEditText3);
//                }
//            }
//        });
//        otpEditText4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    otpEditText4.clearFocus();
//                    Log.d("OTP", "OTP onFocusChange:");
//                    CommonService.hideKeyboardView(OTPActivity.this, otpEditText4);
//                }
//            }
//        });
//        otpEditText5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    otpEditText5.clearFocus();
//                    Log.d("OTP", "OTP onFocusChange:");
//                    CommonService.hideKeyboardView(OTPActivity.this, otpEditText5);
//                }
//            }
//        });
        otpEditText6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    otpEditText6.clearFocus();
                    Log.d("OTP", "OTP onFocusChange:");
                    CommonService.hideKeyboardView(OTPActivity.this, otpEditText6);
                }
            }
        });
        //--------end of focus listener


    }


    private void sendVerificationCode(String phoneNumber) {

        try {


            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+" + phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void verifyVerificationCode(String code) {
        try {


            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);


            //signing the user
            signInWithPhoneAuthCredential(credential);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            CommonService.Toast(OTPActivity.this, "Invalid OTP", Toast.LENGTH_LONG);


        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        try {

            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signInWithCredential", "signInWithCredential:success");

                                Intent i = new Intent(getApplicationContext(), RiderLoginActivity.class);
                                Bundle bundle = new Bundle();
                                //bundle.putSerializable("Parameter", parameter);
                                bundle.putSerializable("mobileNo", mobileNo);
                                bundle.putSerializable("locale", locale);
                                i.putExtras(bundle);
                                startActivity(i);


                                FirebaseUser user = task.getResult().getUser();


                                // ...
                            } else {

                                String message = "Somthing is wrong, we will fix it soon...";

                                // Sign in failed, display a message and update the UI
                                Log.w("signInWithCredential", "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid

                                    message = "Invalid code entered...";
                                    CommonService.Toast(OTPActivity.this, "Invalid OTP", Toast.LENGTH_LONG);

                                }

//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
//                            snackbar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
//                            snackbar.show();
                            }
                        }
                    });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("onVerificationCompleted", "onVerificationCompleted:" + credential);

            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("OnVerification", "onVerificationFailed", e);

            CommonService.Toast(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            CommonService.Toast(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            CommonService.Toast(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG);

            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(String verificationId,
                PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("onCodeSent", "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            //mResendToken = token;

            // ...
        }
    };


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }





//    public static void hideKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }



}
