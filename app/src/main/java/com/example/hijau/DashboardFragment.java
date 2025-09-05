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
        produkList.add(new Produk("Seledri", "23 Kal", "Rp 2.500/Ikat", R.drawable.seledri));
        produkList.add(new Produk("Selada", "18 Kal", "Rp 3.000/Ikat", R.drawable.selada));
        produkList.add(new Produk("Tomat", "52 Kal", "Rp 30.000/Kg", R.drawable.tomat));
        produkList.add(new Produk("Wortel", "47 Kal", "Rp 10.000/Kg", R.drawable.wortel));
        produkList.add(new Produk("Wortel", "47 Kal", "Rp 10.000/Kg", R.drawable.wortel));
        produkList.add(new Produk("Paprika", "47 Kal", "Rp 10.000/Kg", R.drawable.paprika));
        produkList.add(new Produk("Bawang Merah", "47 Kal", "Rp 10.000/Kg", R.drawable.bawang_merah));
        produkList.add(new Produk("Bawang Putih", "47 Kal", "Rp 10.000/Kg", R.drawable.bawang_putih));
        produkList.add(new Produk("Ketumbar", "47 Kal", "Rp 10.000/Kg", R.drawable.ketumbar));





        // Set adapter
        produkAdapter = new ProdukAdapter(produkList);
        rvProduk.setAdapter(produkAdapter);

        return view;
    }
}
