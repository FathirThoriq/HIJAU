package com.example.hijau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ForgetPassFragment extends Fragment {

    public ForgetPassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_pass, container, false);

        // Ambil tombol dari layout fragment_forget_pass.xml
        Button btnSentVerif = view.findViewById(R.id.btnSentVerif);

        // Event klik tombol
        btnSentVerif.setOnClickListener(v -> {
            // Ganti fragment ke Verifikasi
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new VerificationFragment())
                    .addToBackStack(null) // supaya bisa balik ke lupa password
                    .commit();
        });

        return view;
    }
}
