package com.example.hijau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvBuatAkun, tvLupaPassword;
    FirebaseAuth mAuth;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        etEmail = view.findViewById(R.id.etEmail);  // pastikan EditText ini di XML dipakai untuk email
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvBuatAkun = view.findViewById(R.id.tvBuatAkun);
        tvLupaPassword = view.findViewById(R.id.tvLupaPassword);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email dan Password wajib diisi", Toast.LENGTH_SHORT).show();
            } else {
                // Login dengan Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Login sukses
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(requireContext(), "Login berhasil: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                    // Contoh: pindah ke activity dashboard
                                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                                    startActivity(intent);
                                    requireActivity().finish();
                                } else {
                                    // Gagal login
                                    Toast.makeText(requireContext(), "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
