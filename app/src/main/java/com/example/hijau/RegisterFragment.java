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

import java.util.Calendar;

public class RegisterFragment extends Fragment {

    private EditText etName, etUsername, etPassword, etEmail, etPhone, etAddress, etBirthDate;
    private Spinner spinnerGender;
    private Button btnSignup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etName = view.findViewById(R.id.etName);
        etUsername = view.findViewById(R.id.etUsername);
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
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String birthDate = etBirthDate.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || birthDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: simpan ke Firebase atau Database
                Toast.makeText(requireContext(), "Register Success\n" +
                        "Name: " + name + "\nGender: " + gender + "\nBirth: " + birthDate, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
