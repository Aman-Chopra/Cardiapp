package com.example.win_8.cardigram;

/**
 * Created by win-8 on 27-02-2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
}