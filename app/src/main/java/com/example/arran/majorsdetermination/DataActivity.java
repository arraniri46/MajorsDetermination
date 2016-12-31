package com.example.arran.majorsdetermination;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    static private int SPLASH_TIME_OUT = 2000;

    EditText username, prestasi, hobi;
    Spinner spJurusan;
    Button btSubmit;
    RequestQueue requestQueue;
    String insertURL = "http://192.168.8.101/majors_determination/insertData.php";

    public enum MyEnum {
        ENUM1("IPA"),
        ENUM2("IPS");

        private String jurusan;

        MyEnum(String theJurusan) {
            this.jurusan = theJurusan;
        }

        @Override
        public String toString() {
            return jurusan;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        username = (EditText) findViewById(R.id.etUsername);
        prestasi = (EditText) findViewById(R.id.etPrestasi);
        hobi = (EditText) findViewById(R.id.etHobi);
        btSubmit = (Button) findViewById(R.id.btSubmit);
        spJurusan = (Spinner) findViewById(R.id.spinnerJurusan);

        spJurusan.setAdapter(new ArrayAdapter<MyEnum>(this, android.R.layout.simple_list_item_1, MyEnum.values()));
        final String selectedJurusan = spJurusan.getSelectedItem().toString(); //Masih salah disini

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("")){
                    username.setError("Field ini harus diisi");
                }

                if(prestasi.getText().toString().equals("")){
                    prestasi.setError("Field ini harus diisi");
                }

                if(hobi.getText().toString().equals("")){
                    hobi.setError("Field ini harus diisi");
                }
                else {

                StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), "Data Berhasil Diinput", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(DataActivity.this, PetunjukActivity.class);
                                startActivity(i);
                                finish();
                            }
                        },SPLASH_TIME_OUT);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error instanceof com.android.volley.TimeoutError){
                            Toast.makeText(getApplicationContext(),"Time Out Error", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof com.android.volley.NetworkError){
                            Toast.makeText(getApplicationContext(),"Network Error", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof com.android.volley.ServerError){
                            Toast.makeText(getApplicationContext(),"Server Error", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof com.android.volley.AuthFailureError){
                            Toast.makeText(getApplicationContext(),"Authentication Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("nama_user", username.getText().toString());
                        parameters.put("hobi", hobi.getText().toString());
                        parameters.put("jurusan", selectedJurusan); ///Masih bingung disini
                        parameters.put("prestasi", prestasi.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
                } // penutup else
            }

        });

    }
}
