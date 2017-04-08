package com.example.win_8.cardigram;

/**
 * Created by win-8 on 27-02-2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = new Intent(this, Charts.class);

        startActivity(intent);
        finish();


    }
}