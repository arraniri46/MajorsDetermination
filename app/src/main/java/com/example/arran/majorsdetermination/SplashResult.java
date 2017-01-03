package com.example.arran.majorsdetermination;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashResult extends AppCompatActivity {

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_result);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashResult.this, ResultActivity.class);
                startActivity(i);
                finish();
            }
        },2500);
        loading = ProgressDialog.show(this, "Tunggu Sebentar !","Menganalisis Jawaban...", false, false);
    }
}
