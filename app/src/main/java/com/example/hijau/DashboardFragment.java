package com.example.hijau;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView rvProduk;
    private ProdukAdapter produkAdapter;
    private List<Produk> produkList;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        rvProduk = view.findViewById(R.id.rvProduk);

        // Pakai Grid 2 kolom
        rvProduk.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Data dummy produk (isi semua param: nama, kalori, harga, gambar)
        produkList = new ArrayList<>();
        produkList.add(new Produk("Bayam", "23 Kal", "Rp 2.500/ikat", R.drawable.bayam));
        produkList.add(new Produk("Tomat", "18 Kal", "Rp 3.000/kg", R.drawable.tomat));
        produkList.add(new Produk("Apel", "52 Kal", "Rp 5.000/buah", R.drawable.apel));
        produkList.add(new Produk("Jeruk", "47 Kal", "Rp 4.500/buah", R.drawable.jeruk));

        // Set adapter
        produkAdapter = new ProdukAdapter(produkList);
        rvProduk.setAdapter(produkAdapter);

        return view;
    }
}
