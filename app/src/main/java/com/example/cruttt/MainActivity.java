package com.example.cruttt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNamaBarang, etHargaBarang, etMerkBarang;
    private Button btnTambahBarang;
    private RecyclerView rvBarang;
    private DatabaseReference databaseReference;
    private List<Barang> barangList;
    private BarangAdapter barangAdapter;
    private Barang selectedBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etNamaBarang = findViewById(R.id.etNamaBarang);
        etHargaBarang = findViewById(R.id.etHargaBarang);
        etMerkBarang = findViewById(R.id.etMerkBarang);
        btnTambahBarang = findViewById(R.id.btnTambahBarang);
        rvBarang = findViewById(R.id.rvBarang);


        databaseReference = FirebaseDatabase.getInstance().getReference("barang");


        barangList = new ArrayList<>();

        // Inisialisasi Adapter dan RecyclerView
        rvBarang.setLayoutManager(new LinearLayoutManager(this));
        barangAdapter = new BarangAdapter(barangList, this);
        rvBarang.setAdapter(barangAdapter);

        
        btnTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = etNamaBarang.getText().toString().trim();
                String merkBarang = etMerkBarang.getText().toString().trim();
                String hargaBarangStr = etHargaBarang.getText().toString().trim();

                if (TextUtils.isEmpty(namaBarang) || TextUtils.isEmpty(merkBarang) || TextUtils.isEmpty(hargaBarangStr)) {
                    Toast.makeText(MainActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                int hargaBarang;
                try {
                    hargaBarang = Integer.parseInt(hargaBarangStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Harga barang harus berupa angka", Toast.LENGTH_SHORT).show();
                    return;
                }

                String id = databaseReference.push().getKey();
                Barang barang = new Barang(id, namaBarang, merkBarang, hargaBarang);

                if (id != null) {
                    databaseReference.child(id).setValue(barang)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(MainActivity.this, "Barang ditambahkan", Toast.LENGTH_SHORT).show();
                                clearForm();
                            })
                            .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Gagal menambahkan barang: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }
        });

        // Membaca data dari Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                barangList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Barang barang = dataSnapshot.getValue(Barang.class);
                    if (barang != null) {
                        barangList.add(barang);
                    }
                }
                barangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        etNamaBarang.setText("");
        etMerkBarang.setText("");
        etHargaBarang.setText("");
    }

    public void editBarang(Barang barang) {
        selectedBarang = barang;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Barang");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_barang, null);
        builder.setView(dialogView);

        EditText etNamaBarangEdit = dialogView.findViewById(R.id.etNamaBarangEdit);
        EditText etMerkBarangEdit = dialogView.findViewById(R.id.etMerkBarangEdit);
        EditText etHargaBarangEdit = dialogView.findViewById(R.id.etHargaBarangEdit);

        etNamaBarangEdit.setText(barang.getNamaBarang());
        etMerkBarangEdit.setText(barang.getMerkBarang());
        etHargaBarangEdit.setText(String.valueOf(barang.getHargaBarang()));

        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String namaBarang = etNamaBarangEdit.getText().toString().trim();
                String merkBarang = etMerkBarangEdit.getText().toString().trim();
                String hargaBarangStr = etHargaBarangEdit.getText().toString().trim();

                if (TextUtils.isEmpty(namaBarang) || TextUtils.isEmpty(merkBarang) || TextUtils.isEmpty(hargaBarangStr)) {
                    Toast.makeText(MainActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                int hargaBarang;
                try {
                    hargaBarang = Integer.parseInt(hargaBarangStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Harga barang harus berupa angka", Toast.LENGTH_SHORT).show();
                    return;
                }

                Barang updatedBarang = new Barang(selectedBarang.getId(), namaBarang, merkBarang, hargaBarang);
                databaseReference.child(selectedBarang.getId()).setValue(updatedBarang)
                        .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Barang diperbarui", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Gagal memperbarui barang: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void hapusBarang(Barang barang) {
        if (barang.getId() != null) {
            databaseReference.child(barang.getId()).removeValue()
                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Barang dihapus", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Gagal menghapus barang: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}