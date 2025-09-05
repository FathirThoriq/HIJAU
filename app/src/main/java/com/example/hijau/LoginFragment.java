package com.example.hijau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvBuatAkun, tvLupaPassword;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvBuatAkun = view.findViewById(R.id.tvBuatAkun);
        tvLupaPassword = view.findViewById(R.id.tvLupaPassword);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                // Validasi sederhana
                Toast.makeText(requireContext(), "Username dan Password wajib diisi", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: validasi login atau koneksi Firebase
                // Untuk sementara kita anggap login berhasil

                // Pindah ke MainActivity (dashboard)
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish(); // agar tombol back tidak kembali ke login
            }
        });

        // Klik "Belum punya akun? Daftar"
        tvBuatAkun.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Klik "Lupa Password?"
        tvLupaPassword.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ForgetPassFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
