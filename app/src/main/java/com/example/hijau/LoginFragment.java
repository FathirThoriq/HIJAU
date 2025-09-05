package com.example.hijau;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvBuatAkun, tvLupaPassword;
    private FirebaseFirestore db;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inisialisasi view
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvBuatAkun = view.findViewById(R.id.tvBuatAkun);
        tvLupaPassword = view.findViewById(R.id.tvLupaPassword);

        // Init Firestore
        db = FirebaseFirestore.getInstance();

        // Aksi pindah ke RegisterFragment
        tvBuatAkun.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Aksi pindah ke LupaPasswordFragment
        tvLupaPassword.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ForgetPassFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Tombol Login
        btnLogin.setOnClickListener(v -> handleLogin());

        return view;
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username wajib diisi");
            etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password wajib diisi");
            etPassword.requestFocus();
            return;
        }

        // 🔹 Ambil data user dari Firestore berdasarkan username
        db.collection("users")
                .document(username) // username jadi key waktu register
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String storedPassword = documentSnapshot.getString("password");

                        if (storedPassword != null && storedPassword.equals(password)) {
                            Toast.makeText(getActivity(), "Login berhasil!", Toast.LENGTH_SHORT).show();

                            // Pindah ke MainActivity
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);

                            // Tutup AuthActivity supaya tidak bisa balik pakai tombol Back
                            requireActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Password salah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Gagal login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
