package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class Dashboard extends AppCompatActivity {

    private TextView UserWelcomed;
    private Button MyCompBtn,AppointBtn,NearMebtn,ProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        MyCompBtn = findViewById(R.id.myCompbtn);
        AppointBtn = findViewById(R.id.appointbtn);
        NearMebtn = findViewById(R.id.nearMebtn);
        ProfileBtn = findViewById(R.id.profilebtn);

        MyCompBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MyCompanions.class);
                startActivity(intent);

            }
        });

        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);

            }
        });
    }
}