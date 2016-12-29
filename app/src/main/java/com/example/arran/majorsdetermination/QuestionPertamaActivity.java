package com.example.arran.majorsdetermination;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionPertamaActivity extends AppCompatActivity{

    TextView tvSoal;
    RadioButton tvPilihan1, tvPilihan2, tvPilihan3, tvPilihan4;
    String getDataURL = "https://192.168.8.101/majors_determination/getData.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pertama);

        tvSoal = (TextView) findViewById(R.id.pertanyaan);
        tvPilihan1 = (RadioButton) findViewById(R.id.choice_1);
        tvPilihan2 = (RadioButton) findViewById(R.id.choice_2);
        tvPilihan3 = (RadioButton) findViewById(R.id.choice_3);
        tvPilihan4 = (RadioButton) findViewById(R.id.choice_4);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getDataURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray pertanyaan = response.getJSONArray("result");
                    for(int i = 0 ; i  < pertanyaan.length() ; i++){
                        JSONObject result = pertanyaan.getJSONObject(i);

                        String Soal = result.getString("pertanyaan");
                        String Pilihan1 = result.getString("pilihan_1");
                        String Pilihan2 = result.getString("pilihan_2");
                        String Pilihan3 = result.getString("pilihan_3");
                        String Pilihan4 = result.getString("pilihan_4");

                        tvSoal.setText(Soal);
                        tvPilihan1.setText(Pilihan1);
                        tvPilihan2.setText(Pilihan2);
                        tvPilihan3.setText(Pilihan3);
                        tvPilihan4.setText(Pilihan4);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });requestQueue.add(jsonObjectRequest);

    }
}
