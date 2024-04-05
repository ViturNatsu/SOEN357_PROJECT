package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointmentreminder");
                DatabaseReference reference = databaseReference.child(dateEditText.getText().toString());
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateEditText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppointmentReminder newAppointmentReminder = new AppointmentReminder(date, timeEditText.getText().toString(), companionNameEditText.getText().toString(), extraInfoEditText.getText().toString());
                reference.push().setValue(newAppointmentReminder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(add_appointments_and_reminders.this, "Success!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(add_appointments_and_reminders.this,AppointmentsAndReminders.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });

            }
        });

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