package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppointmentsAndReminders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_and_reminders);

        FloatingActionButton addAppointmentButton = findViewById(R.id.addAppointmentButton);
        addAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentsAndReminders.this, add_appointments_and_reminders.class);
                startActivity(intent);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointmentreminder");

        ListView listView = findViewById(R.id.appointmentsListView);
        List<Object> objects = new ArrayList<>();
        AppointmentReminderSort adapter = new AppointmentReminderSort(this, objects);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // since keys are stored as dates, iterate over dates and add them to objects list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String _date = snapshot.getKey();
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(_date);
                        objects.add(date); // Add  date
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // children of dates in DB are appointmentReminders, so iterate over them now and store in list
                    for (DataSnapshot appointmentReminderSnapshot : snapshot.getChildren()) {
                        AppointmentReminder appointment = appointmentReminderSnapshot.getValue(AppointmentReminder.class);
                        objects.add(appointment); // Add appointment/reminder
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE", " DB ERROR");
            }
        });

        listView.setAdapter(adapter);

    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(AppointmentsAndReminders.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}