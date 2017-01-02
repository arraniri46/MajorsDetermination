package com.example.arran.majorsdetermination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}
