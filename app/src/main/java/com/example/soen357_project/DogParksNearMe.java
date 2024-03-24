package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;

public class DogParksNearMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_parks_near_me);
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(DogParksNearMe.this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DogParksNearMe.this, Dashboard.class);
        startActivity(intent);
    }
}