package com.example.hijau;

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

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvBuatAkun, tvLupaPassword;

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
        btnLogin.setOnClickListener(v -> {
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

            // TODO: Tambahkan logika validasi username & password (misalnya cek ke database)
            boolean loginSukses = true; // sementara true biar langsung masuk

            if (loginSukses) {
                Toast.makeText(getActivity(), "Login berhasil!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new DashboardFragment())
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Username / Password salah", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
