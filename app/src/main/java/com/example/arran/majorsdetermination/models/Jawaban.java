package com.example.arran.majorsdetermination.models;

/**
 * Created by Arran on 1/2/2017.
 */

public class Jawaban {
    public String pertanyaan;
    public String pilihan_1;
    public String pilihan_2;
    public String pilihan_3;
    public String pilihan_4;
    public int JawabanID;

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getPilihan_1() {
        return pilihan_1;
    }

    public void setPilihan_1(String pilihan_1) {
        this.pilihan_1 = pilihan_1;
    }

    public String getPilihan_2() {
        return pilihan_2;
    }

    public void setPilihan_2(String pilihan_2) {
        this.pilihan_2 = pilihan_2;
    }

    public String getPilihan_3() {
        return pilihan_3;
    }

    public void setPilihan_3(String pilihan_3) {
        this.pilihan_3 = pilihan_3;
    }

    public String getPilihan_4() {
        return pilihan_4;
    }

    public void setPilihan_4(String pilihan_4) {
        this.pilihan_4 = pilihan_4;
    }

    public int getJawabanID() {
        return JawabanID;
    }

    public void setJawabanID(int jawabanID) {
        JawabanID = jawabanID;
    }

    @Override
    public String toString(){
        return pertanyaan;
    }

}
