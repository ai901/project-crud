package com.example.cruttt;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private List<Barang> barangList;
    private Context context;

    public BarangAdapter(List<Barang> barangList, Context context) {
        this.barangList = barangList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barang barang = barangList.get(position);


        holder.tvNamaBarang.setText(barang.getNamaBarang());
        holder.tvMerkBarang.setText(barang.getMerkBarang());
        holder.tvHargaBarang.setText(String.valueOf(barang.getHargaBarang()));


        holder.btnEdit.setOnClickListener(v -> {

            ((MainActivity) context).editBarang(barang);
        });


        holder.btnHapus.setOnClickListener(v -> {

            ((MainActivity) context).hapusBarang(barang);
        });


        holder.btnDetail.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("BARANG_ID", barang.getId());
            intent.putExtra("NAMA_BARANG", barang.getNamaBarang());
            intent.putExtra("MERK_BARANG", barang.getMerkBarang());
            intent.putExtra("HARGA_BARANG", barang.getHargaBarang());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return barangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBarang, tvMerkBarang, tvHargaBarang;
        Button btnEdit, btnHapus, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvNamaBarang = itemView.findViewById(R.id.tvNamaBarang);
            tvMerkBarang = itemView.findViewById(R.id.tvMerkBarang);
            tvHargaBarang = itemView.findViewById(R.id.tvHargaBarang);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnHapus = itemView.findViewById(R.id.btnHapus);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}