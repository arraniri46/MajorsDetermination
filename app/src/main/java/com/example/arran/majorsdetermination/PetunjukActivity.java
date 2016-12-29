package com.example.arran.majorsdetermination;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PetunjukActivity extends AppCompatActivity {

    Button bPaham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petunjuk);

        bPaham = (Button) findViewById(R.id.bPaham);

        bPaham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PetunjukActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });
    }
}
