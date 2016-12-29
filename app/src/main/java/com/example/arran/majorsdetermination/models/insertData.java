package com.example.arran.majorsdetermination.models;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Arran on 12/4/2016.
 */

public class insertData {

    private EditText nama_user, prestasi;
    private Spinner hobi, jurusan;

    public insertData(EditText nama_user, EditText prestasi, Spinner hobi, Spinner jurusan) {
        this.nama_user = nama_user;
        this.prestasi = prestasi;
        this.hobi = hobi;
        this.jurusan = jurusan;
    }

    public EditText getNama_user() {
        return nama_user;
    }

    public void setNama_user(EditText nama_user) {
        this.nama_user = nama_user;
    }

    public EditText getPrestasi() {
        return prestasi;
    }

    public void setPrestasi(EditText prestasi) {
        this.prestasi = prestasi;
    }

    public Spinner getHobi() {
        return hobi;
    }

    public void setHobi(Spinner hobi) {
        this.hobi = hobi;
    }

    public Spinner getJurusan() {
        return jurusan;
    }

    public void setJurusan(Spinner jurusan) {
        this.jurusan = jurusan;
    }
}
