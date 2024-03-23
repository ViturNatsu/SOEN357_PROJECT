package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DogInfo extends AppCompatActivity {
    private TextView dogName,dogBreed,dogDoB,dogSex;
    private Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info);



        dogName = findViewById(R.id.dDogName);
        dogBreed = findViewById(R.id.dDogBreed);
        dogDoB = findViewById(R.id.dDogDoB);
        dogSex = findViewById(R.id.dDogGender);
        returnBtn = findViewById(R.id.backDogInfoBtn);

        getDogInfo();
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DogInfo.this, MyCompanions.class);
                startActivity(intent);
            }
        });

    }

    private void getDogInfo() {
        String selectedDog = getIntent().getStringExtra("dogName");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("My Pets:").child(selectedDog);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String breed = dataSnapshot.child("breed").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String dob = dataSnapshot.child("dofB").getValue(String.class);

                    // Set the fetched data to TextViews
                    dogName.setText(name);
                    dogBreed.setText(breed);
                    dogSex.setText(gender);
                    dogDoB.setText(dob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}