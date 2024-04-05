package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthRecords extends AppCompatActivity {

    private TextView dogName, vaccinationStatus, weight, allergies, medication, chipNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_records);

        dogName = findViewById(R.id.HR_Label);
        vaccinationStatus = findViewById(R.id.vax_status);
        weight = findViewById(R.id.weight);
        allergies = findViewById(R.id.allergies);
        medication = findViewById(R.id.medication);
        chipNumber = findViewById(R.id.chip_number);

        // get DogName from DogInfo
        Intent intent = getIntent();
        String dogNameString = intent.getStringExtra("dogName");
        dogName.setText(dogNameString);

        // Retrieve relevant dog data from Firebase DB based on dogName
        retrieveDogData(dogNameString);
    }

    void retrieveDogData(String dogName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference retrieveDogData = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("My Pets:").child(dogName);

        retrieveDogData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DogData dogData = snapshot.getValue(DogData.class);
                try {
                    vaccinationStatus.setText(dogData.getVaccinationStatus());
                    weight.setText(dogData.getWeight());
                    allergies.setText(dogData.getAllergies());
                    medication.setText(dogData.getMedication());
                    chipNumber.setText(dogData.getChipNumber());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(HealthRecords.this, DogInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}