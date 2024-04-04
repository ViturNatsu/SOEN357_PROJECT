package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_appointments_and_reminders extends AppCompatActivity {

    private EditText dateEditText, timeEditText, companionNameEditText, extraInfoEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointments_and_reminders);

        dateEditText = findViewById(R.id.appointment_date_input);
        timeEditText = findViewById(R.id.appointment_time_input);
        companionNameEditText = findViewById(R.id.dog_name_for_appointment);
        extraInfoEditText = findViewById(R.id.appointment_location_input);
        saveButton = findViewById(R.id.saveBtn);

        clearOnTouch();
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(add_appointments_and_reminders.this, AppointmentsAndReminders.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    void clearOnTouch() {
        dateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dateEditText.setText(""); // Clear the text when touched
                return false;
            }
        });

        timeEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timeEditText.setText(""); // Clear the text when touched
                return false;
            }
        });

        companionNameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                companionNameEditText.setText(""); // Clear the text when touched
                return false;
            }
        });

        extraInfoEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                extraInfoEditText.setText(""); // Clear the text when touched
                return false;
            }
        });
    }
}