package com.example.hijau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Tombol "Belum punya akun? Daftar"
        TextView tvBuatAkun = view.findViewById(R.id.tvBuatAkun);
        tvBuatAkun.setOnClickListener(v -> {
            // Pindah ke RegisterFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null) // biar bisa back ke login
                    .commit();
        });

        return view;
    }
}
