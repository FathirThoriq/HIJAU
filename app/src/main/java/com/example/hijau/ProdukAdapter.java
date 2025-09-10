package com.example.hijau;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {

    private List<Produk> produkList;

    public ProdukAdapter(List<Produk> produkList) {
        this.produkList = produkList;
    }

    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produk, parent, false);
        return new ProdukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder holder, int position) {
        Produk produk = produkList.get(position);

        holder.tvNama.setText(produk.getNama());
        holder.tvKalori.setText(produk.getKalori());
        holder.tvHarga.setText(produk.getHarga());

        // load gambar dari URL
        Glide.with(holder.itemView.getContext())
                .load(produk.getImageURL())
                .placeholder(R.drawable.seledri) // gambar default
                .into(holder.imgProduk);

        holder.btnTambah.setOnClickListener(v -> {
            // contoh aksi: tampilkan toast
            // Toast.makeText(v.getContext(), produk.getNama() + " ditambahkan!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public static class ProdukViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduk;
        TextView tvNama, tvKalori, tvHarga;
        Button btnTambah;

        public ProdukViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.imgProduk);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvKalori = itemView.findViewById(R.id.tvKalori);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            btnTambah = itemView.findViewById(R.id.btnTambah);
        }
    }
}
