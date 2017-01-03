package com.example.arran.majorsdetermination;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arran.majorsdetermination.models.Pertanyaan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionPertamaActivity extends AppCompatActivity {
    TextView tvSoal;
    RadioButton tvPilihan1, tvPilihan2, tvPilihan3, tvPilihan4, tvTerpilih;
    String getDataURL = "http://determination.hol.es/majors_determination/getData.php";
    String insertJawabanURL = "http://192.168.8.101/majors_determination/insertJawaban.php";
    RequestQueue requestQueue;
    ProgressDialog loading;
    RadioGroup rgPilihanJawaban;
    Button bNext;

    Integer nomorSoal = 0;
    ArrayList<Pertanyaan> pertanyaanList;
    HashMap<String,String> jawabanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pertama);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Majors Determination");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        tvSoal = (TextView) findViewById(R.id.pertanyaan);
        tvPilihan1 = (RadioButton) findViewById(R.id.choice_1);
        tvPilihan2 = (RadioButton) findViewById(R.id.choice_2);
        tvPilihan3 = (RadioButton) findViewById(R.id.choice_3);
        tvPilihan4 = (RadioButton) findViewById(R.id.choice_4);

        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        rgPilihanJawaban = (RadioGroup) findViewById(R.id.pilihan_jawaban);

        retrieveData();

        bNext = (Button) findViewById(R.id.bNext);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ++nomorSoal;
                boolean soalBersisa = nomorSoal < pertanyaanList.size();

                if(rgPilihanJawaban.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(),"Wajib pilih salah satu", Toast.LENGTH_SHORT).show();
                    nomorSoal--;
                }
                else
                {
                    if(soalBersisa)
                    {
                        getJawaban();
                        setSoal(nomorSoal);
                    }

                    else
                    {
                        Intent i = new Intent(QuestionPertamaActivity.this, SplashTahapKedua.class);
                        startActivity(i);
                    }
                    rgPilihanJawaban.clearCheck();

                }

            }
        });

    }

    private void setSoal(Integer nomorSoal)
    {
        Pertanyaan pertanyaan = pertanyaanList.get(nomorSoal);

        tvSoal.setText(pertanyaan.pertanyaan);
        tvPilihan1.setText(pertanyaan.pilihan_1);
        tvPilihan2.setText(pertanyaan.pilihan_2);
        tvPilihan3.setText(pertanyaan.pilihan_3);
        tvPilihan4.setText(pertanyaan.pilihan_4);

    }

    private void getJawaban(){
        jawabanList = new HashMap<>();

        int selectedJawaban = rgPilihanJawaban.getCheckedRadioButtonId();
        View tvTerpilih = rgPilihanJawaban.findViewById(selectedJawaban);
        int indexRadio = rgPilihanJawaban.indexOfChild(tvTerpilih);

        RadioButton r = (RadioButton) rgPilihanJawaban.getChildAt(indexRadio);
        String selectedText = r.getText().toString();

            Toast.makeText(QuestionPertamaActivity.this,selectedText,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Peringatan !!!");
        builder.setMessage("Semua jawaban yang telah diinput akan hilang. Kembali ? ");
        builder.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                QuestionPertamaActivity.super.onBackPressed();
            }
        });
        builder.setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                QuestionPertamaActivity.super.onResume();
            }
        });
        builder.show();
    }

    /*private void noticeMeSenpai(){
        int selectedId = rgPilihanJawaban.getCheckedRadioButtonId();

        tvTerpilih = (RadioButton) findViewById(selectedId);

        if(rgPilihanJawaban.getCheckedRadioButtonId()== -1){
            Toast.makeText(getApplicationContext(),"Wajib pilih salah satu", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(QuestionPertamaActivity.this,
                    tvTerpilih.getText(),Toast.LENGTH_SHORT).show();
        rgPilihanJawaban.clearCheck();

    }*/

    private void retrieveData()
    {
        Log.v("pertanyaan_debug", "Mulai Retrieve....");
        pertanyaanList = new ArrayList<>();
        loading = ProgressDialog.show(this, "Tunggu Sebentar...","Mengambil Data...", false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getDataURL, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray res = response.getJSONArray("result");
                    for(int i = 0 ; i  < res.length() ; i++){
                        JSONObject result = res.getJSONObject(i);

                        String Soal = result.getString("pertanyaan");
                        String Pilihan1 = result.getString("pilihan_1");
                        String Pilihan2 = result.getString("pilihan_2");
                        String Pilihan3 = result.getString("pilihan_3");
                        String Pilihan4 = result.getString("pilihan_4");

                        Pertanyaan quest = new Pertanyaan();
                        quest.pertanyaan = Soal;
                        quest.pilihan_1 = Pilihan1;
                        quest.pilihan_2 = Pilihan2;
                        quest.pilihan_3 = Pilihan3;
                        quest.pilihan_4 = Pilihan4;
                        pertanyaanList.add(quest);

                        Log.v("pertanyaan_debug", quest.toString());
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.v("pertanyaan_debug", e.getMessage() + ":ERROR");
                }


                setSoal(0);

                loading.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("pertanyaan_debug", error.getMessage() + ":ERRORVOLLEY");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void insertJawaban(){
        StringRequest request = new StringRequest(Request.Method.POST, insertJawabanURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<>();
                parameters.put("a",tvTerpilih.getText().toString());
                parameters.put("b",tvTerpilih.getText().toString());

                return parameters;
            }*/
        };
        requestQueue.add(request);

    }



}




