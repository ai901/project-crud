package com.example.cruttt;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        TextView tvNamaBarang = findViewById(R.id.tvNamaBarangDetail);
        TextView tvMerkBarang = findViewById(R.id.tvMerkBarangDetail);
        TextView tvHargaBarang = findViewById(R.id.tvHargaBarangDetail);


        String namaBarang = getIntent().getStringExtra("NAMA_BARANG");
        String merkBarang = getIntent().getStringExtra("MERK_BARANG");
        int hargaBarang = getIntent().getIntExtra("HARGA_BARANG", 0);


        tvNamaBarang.setText("Nama: " + namaBarang);
        tvMerkBarang.setText("Merk: " + merkBarang);
        tvHargaBarang.setText("Harga: Rp " + hargaBarang);
    }
}
