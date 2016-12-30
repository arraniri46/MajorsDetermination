package com.example.arran.majorsdetermination;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    RadioButton tvPilihan1, tvPilihan2, tvPilihan3, tvPilihan4, tvTerpilih;
    String getDataURL = "http://192.168.8.101/majors_determination/getData.php";
    RequestQueue requestQueue;
    private ProgressDialog loading;
    RadioGroup rgPilihanPertanyaan;
    Button bNext;

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

        loading = ProgressDialog.show(this, "Tunggu Sebentar...","Mengambil Data...", false, false);

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

                        loading.dismiss();
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


        rgPilihanPertanyaan = (RadioGroup) findViewById(R.id.pilihan_pertanyaan);
        bNext = (Button) findViewById(R.id.bNext);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rgPilihanPertanyaan.getCheckedRadioButtonId();

                tvTerpilih = (RadioButton) findViewById(selectedId);

                Toast.makeText(QuestionPertamaActivity.this,
                        tvTerpilih.getText(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
