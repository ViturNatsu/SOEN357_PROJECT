package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    private TextView title;
    private ImageView logo;
    private Button MyCompBtn,AppointBtn,NearMebtn,ProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        MyCompBtn = findViewById(R.id.myCompbtn);
        AppointBtn = findViewById(R.id.appointbtn);
        NearMebtn = findViewById(R.id.nearMebtn);
        ProfileBtn = findViewById(R.id.profilebtn);

        /*
         * Go to My Companions Activity
         */
        MyCompBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MyCompanions.class);
                startActivity(intent);

            }
        });

        /*
         * Go to Appointments and Reminders Activity
         */
        AppointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AppointmentsAndReminders.class);
                startActivity(intent);

            }
        });

        /*
         * Go to Dog Parks Near Me Activity
         */
        NearMebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, DogParksNearMe.class);
                startActivity(intent);

            }
        });

        /*
         * Go to Profile Activity
         */
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}