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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arran.majorsdetermination.adapters.Session;
import com.example.arran.majorsdetermination.models.Jawaban;
import com.example.arran.majorsdetermination.models.Pertanyaan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QuestionKeduaActivity extends AppCompatActivity {

    TextView tvSoal;
    RadioButton tvPilihan1, tvPilihan2, tvPilihan3, tvPilihan4, tvTerpilih;
    String getDataURL = "http://determination.hol.es/majors_determination/getData2.php";
    String insertJawabanURL = "http://192.168.8.101/majors_determination/insertJawaban.php";
    RequestQueue requestQueue;
    ProgressDialog loading;
    RadioGroup rgPilihanJawaban;
    Button bNext;

    Integer nomorSoal = 0;
    ArrayList<Pertanyaan> pertanyaanList;
    ArrayList<Integer> jawabanList;
    HashMap<Integer,Integer> jawabanMap;
    String[] converter = {"g","o","b","d"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_kedua);

        jawabanMap = new HashMap<>();

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
                    getJawaban();

                    if(soalBersisa)
                    {
                        setSoal(nomorSoal);
                    }

                    else
                    {
                        Session.secondAnswer = getHasilKedua();
                        Log.d("JAWAB", Session.secondAnswer);

                        Intent i = new Intent(QuestionKeduaActivity.this, SplashResult.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        QuestionKeduaActivity.this.finish();
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
        jawabanList = new ArrayList<>();
        HashMap<String,String> college = new HashMap<>();

        Jawaban jawab = new Jawaban();

        int selectedJawaban = rgPilihanJawaban.getCheckedRadioButtonId();
        View Terpilih = rgPilihanJawaban.findViewById(selectedJawaban);
        int indexRadio = rgPilihanJawaban.indexOfChild(Terpilih);

        RadioButton r = (RadioButton) rgPilihanJawaban.getChildAt(indexRadio);
        String selectedText = r.getText().toString();

        Toast.makeText(QuestionKeduaActivity.this,selectedText,Toast.LENGTH_SHORT).show();

        jawabanMap.put(nomorSoal + 1, indexRadio);
    }

    private void analisisJawaban(){
        for(int i = 0;i<jawabanList.size();i++){
            jawabanList.get(i);

            Log.d(String.valueOf(jawabanList),"garda2");
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Peringatan !!!");
        builder.setMessage("Semua jawaban yang telah diinput akan hilang. Kembali ? ");
        builder.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                QuestionKeduaActivity.super.onBackPressed();
            }
        });
        builder.setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                QuestionKeduaActivity.super.onResume();
            }
        });
        builder.show();
    }

    private String getHasilKedua(){
        Integer value;
        String answer = null;
        String answerColl = null;

        ArrayList<Integer> twoAnswer = new ArrayList<>();
        HashMap<Integer, Integer> answerCount = new HashMap<>();

        for(Map.Entry<Integer, Integer> entry: jawabanMap.entrySet()){
            if(answerCount.get(entry.getValue()) != null){
                answerCount.put(entry.getValue(), answerCount.get(entry.getValue()) + 1);
            }
            else {
                answerCount.put(entry.getValue(), 1);
            }

            value = answerCount.get(entry.getValue());

            if(value == 3){
                answer = converter[entry.getValue()];
                break;
            }
            else if(value == 2){
                twoAnswer.add(entry.getValue());
            }
        }

        if(answer != null){
            answerColl = answer.toString();
        }
        else {
            answerColl = "";
            Collections.sort(twoAnswer);

            for(Integer i : twoAnswer){
                answerColl += converter[i];
            }
        }

        return answerColl;
    }

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

                        /*tvSoal.setText(Soal);
                        tvPilihan1.setText(Pilihan1);
                        tvPilihan2.setText(Pilihan2);
                        tvPilihan3.setText(Pilihan3);
                        tvPilihan4.setText(Pilihan4);*/
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

    }


}
