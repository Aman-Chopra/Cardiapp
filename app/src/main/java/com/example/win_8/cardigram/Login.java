package com.example.win_8.cardigram;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    TextView _see;
    public static int a = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _loginButton = (Button)findViewById(R.id.btn_login);
        _signupLink = (TextView)findViewById(R.id.link_signup);
        _see = (TextView)findViewById(R.id.link_see);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                signIn(email,password);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        _see.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validate()) {
            return;
        }





        if(!isOnline())
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Connectivity Error.");
            builder.setMessage("Check your internet connection.");
            builder.setPositiveButton("OK", null);
            builder.show();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        long delayInMillis = 6000;
        Timer timer = new Timer();




        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressDialog.dismiss();
                //toast("Process is taking too long");
                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Yo",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }, delayInMillis);






        final AlertDialog.Builder builder1 =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            changeActivity();
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            progressDialog.dismiss();
                            builder1.setTitle("Error.");
                            builder1.setMessage(task.getException().getMessage());
                            builder1.setPositiveButton("OK", null);
                            builder1.show();
                            return;
                        }



                    }
                });

    }

    private void changeActivity()
    {
        finish();
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }






    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        TextInputLayout til = (TextInputLayout) findViewById(R.id.text_input_layout);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            til.setErrorEnabled(true);
            til.setError("Enter a valid email address.");
            valid = false;
        } else {
            til.setErrorEnabled(false);
        }

        TextInputLayout till = (TextInputLayout) findViewById(R.id.password_input_layout);
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            till.setErrorEnabled(true);
            till.setError("4-10 alphanumeric characters.");
            valid = false;
        } else {
            till.setErrorEnabled(false);
        }

        return valid;
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();}




}