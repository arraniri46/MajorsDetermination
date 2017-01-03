package com.example.arran.majorsdetermination;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    String retrieveURL = "http://192.168.8.101/majors_determination/insertJawaban.php";
    TextView Hasil;
    Button bKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Majors Determination");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        bKeluar = (Button) findViewById(R.id.bKeluar);

        bKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                finish();

            }
        });

        Hasil = (TextView) findViewById(R.id.tvResult);
        Hasil.setText("Analisis Error");

    }

    private void analisisJawaban(){

    }

}
