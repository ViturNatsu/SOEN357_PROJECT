package com.example.soen357_project;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        MaterialButton getStartedButton = findViewById(R.id.GetStartedbtn);
        MaterialButton loginButton = findViewById(R.id.Loginbtn);

        // Set click listeners
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start registration activity
                startActivity(new Intent(MainActivity.this, register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start login activity
                startActivity(new Intent(MainActivity.this, LoginPage.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
