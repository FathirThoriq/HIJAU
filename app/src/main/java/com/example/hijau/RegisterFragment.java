package com.example.hijau;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private EditText etName, etPassword, etEmail, etPhone, etAddress, etBirthDate;
    private Spinner spinnerGender;
    private Button btnSignup;
    private FirebaseAuth mAuth; // Firebase Auth

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Init Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Binding view
        etName = view.findViewById(R.id.etName);
        etPassword = view.findViewById(R.id.etPassword);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etAddress = view.findViewById(R.id.etAddress);
        etBirthDate = view.findViewById(R.id.etBirthDate);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        btnSignup = view.findViewById(R.id.btnSignup);

        // Setup spinner Gender
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Setup DatePicker untuk Birth Date
        etBirthDate.setFocusable(false);
        etBirthDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                        String selectedDate = String.format("%02d-%02d-%04d",
                                selectedDay, (selectedMonth + 1), selectedYear);
                        etBirthDate.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Tombol Register
        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String birthDate = etBirthDate.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();

            if (name.isEmpty() || password.isEmpty() || email.isEmpty() || birthDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid = user.getUid();

                                    // Buat map untuk data tambahan
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("name", name);
                                    userData.put("email", email);
                                    userData.put("phone", phone);
                                    userData.put("address", address);
                                    userData.put("gender", gender);
                                    userData.put("birthDate", birthDate);

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users").document(uid)
                                            .set(userData)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(requireContext(), "Register & simpan data berhasil", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(requireContext(), "Gagal simpan data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });

                                // TODO: Simpan data tambahan (name, phone, address, gender, birthDate) ke Firestore / Realtime DB
                                } else {
                                    // Register gagal
                                    Toast.makeText(requireContext(), "Register gagal: " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        return view;
    }
}
