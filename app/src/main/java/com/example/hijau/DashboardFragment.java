package com.example.hijau;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String name = document.getString("name");
                            long price = document.getLong("price");
                            String priceRp = currencyFormatter.formatRp(price); // format ke Rp
                            String unit = document.getString("unit");
                            long stock = document.getLong("stock");
                            String imageURL = document.getString("imageURL");
                            produkList.add(new Produk(name,
                                    "Tersedia:  " + stock + " " + unit,
                                    priceRp + "/" + unit,  // contoh : Rp 20.000/kg
                                    imageURL));
                        }
                        produkAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error getting documents", e);
                    }
                });

        // sisa ambil gambar dari storage firebase & kalori

//        produkList.add(new Produk("Seledri", "23 Kal", "Rp 2.500/Ikat", R.drawable.seledri));
//        produkList.add(new Produk("Selada", "18 Kal", "Rp 3.000/Ikat", R.drawable.selada));
//        produkList.add(new Produk("Tomat", "52 Kal", "Rp 30.000/Kg", R.drawable.tomat));
//        produkList.add(new Produk("Wortel", "47 Kal", "Rp 10.000/Kg", R.drawable.wortel));
//        produkList.add(new Produk("Wortel", "47 Kal", "Rp 10.000/Kg", R.drawable.wortel));
//        produkList.add(new Produk("Paprika", "47 Kal", "Rp 10.000/Kg", R.drawable.paprika));
//        produkList.add(new Produk("Bawang Merah", "47 Kal", "Rp 10.000/Kg", R.drawable.bawang_merah));
//        produkList.add(new Produk("Bawang Putih", "47 Kal", "Rp 10.000/Kg", R.drawable.bawang_putih));
//        produkList.add(new Produk("Ketumbar", "47 Kal", "Rp 10.000/Kg", R.drawable.ketumbar));

        // Set adapter
        produkAdapter = new ProdukAdapter(produkList);
        rvProduk.setAdapter(produkAdapter);

        return view;
    }
}
