package com.example.cruttt;

public class Barang {
    private String id;
    private String namaBarang;
    private String merkBarang;
    private int hargaBarang;

    public Barang() {
        // Default constructor diperlukan untuk Firebase
    }

    public Barang(String id, String namaBarang, String merkBarang, int hargaBarang) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.merkBarang = merkBarang;
        this.hargaBarang = hargaBarang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getMerkBarang() {
        return merkBarang;
    }

    public void setMerkBarang(String merkBarang) {
        this.merkBarang = merkBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }
}