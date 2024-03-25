package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AppointmentsAndReminders extends AppCompatActivity {

    private FloatingActionButton addAppointmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_and_reminders);

        addAppointmentButton = findViewById(R.id.addAppointmentButton);
        addAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentsAndReminders.this, add_appointments_and_reminders.class);
                startActivity(intent);
            }
        });
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(AppointmentsAndReminders.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}