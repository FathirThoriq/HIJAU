package com.example.hijau;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private EditText etName, etUsername, etPassword, etEmail, etPhone, etAddress, etBirthDate;
    private Spinner spGender;
    private Button btnSignup;
    private FirebaseFirestore db;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Inisialisasi semua input
        etName = view.findViewById(R.id.etName);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etAddress = view.findViewById(R.id.etAddress);
        spGender = view.findViewById(R.id.spinnerGender);   // Spinner Gender
        etBirthDate = view.findViewById(R.id.etBirthDate); // EditText untuk tanggal
        btnSignup = view.findViewById(R.id.btnSignup);

        // 🔹 Setup Spinner Gender
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.gender_options, // pastikan kamu buat array gender di res/values/strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        // 🔹 DatePicker untuk BirthDate
        etBirthDate.setOnClickListener(v -> showDatePicker());

        // Aksi tombol Sign Up
        btnSignup.setOnClickListener(v -> handleRegister());

        return view;
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (DatePicker view, int year1, int month1, int dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    etBirthDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void handleRegister() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String gender = spGender.getSelectedItem().toString();
        String birthDate = etBirthDate.getText().toString().trim();

        // 🔹 Cek field kosong
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(address) || TextUtils.isEmpty(birthDate)) {

            Toast.makeText(getActivity(), "Isi semua field!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Validasi email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Format email tidak valid!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gender.equals("Pilih Gender")) {
            Toast.makeText(getActivity(), "Silakan pilih gender!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Semua valid → simpan ke database
        saveUserToDatabase(name, username, password, email, phone, address, gender, birthDate);
    }

    private void saveUserToDatabase(String name, String username, String password,
                                    String email, String phone, String address,
                                    String gender, String birthDate) {

        // 🔹 Init Firestore
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }

        // 🔹 Buat objek data user
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("username", username);
        user.put("password", password); // ⚠️ sebaiknya hash password di real project
        user.put("email", email);
        user.put("phone", phone);
        user.put("address", address);
        user.put("gender", gender);
        user.put("birthDate", birthDate);

        // 🔹 Simpan ke Firestore (koleksi "users")
        db.collection("users")
                .document(username) // bisa juga pakai email atau auto-id
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show();

                    // Setelah simpan → arahkan ke LoginFragment
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new LoginFragment())
                            .commit();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Gagal simpan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
