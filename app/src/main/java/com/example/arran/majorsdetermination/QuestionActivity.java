package com.example.arran.majorsdetermination;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuestionActivity extends AppCompatActivity {

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(QuestionActivity.this, QuestionPertamaActivity.class);
                startActivity(i);
                finish();
            }
        },3000);

        loading = ProgressDialog.show(this, "Tunggu Sebentar...","Mengambil Data...", false, false);
    }
}
