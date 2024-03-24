package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AppointmentsAndReminders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_and_reminders);
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(AppointmentsAndReminders.this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AppointmentsAndReminders.this, Dashboard.class);
        startActivity(intent);
    }
}