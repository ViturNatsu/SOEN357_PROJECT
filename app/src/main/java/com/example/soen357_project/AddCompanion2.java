package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCompanion2 extends AppCompatActivity {

    private EditText dogVaccinationStatus, dogWeight, dogAllergies, dogMedication, dogVetName;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companion2);

        dogVaccinationStatus = findViewById(R.id.dogVaccinationStatus);
        dogWeight = findViewById(R.id.dogWeight);
        dogAllergies = findViewById(R.id.dogAllergies);
        dogMedication = findViewById(R.id.dogMedication);
        dogVetName = findViewById(R.id.dogVetName);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();

        // Retrieved from AddCompanion
        String dogName = intent.getStringExtra("dogName");
        String dogChipNumber = intent.getStringExtra("dogChipNumber");
        String dogBreed = intent.getStringExtra("dogBreed");
        String dogBirthDay = intent.getStringExtra("dogBirthDay");
        String dogBirthMonth = intent.getStringExtra("dogBirthMonth");
        String dogBirthYear = intent.getStringExtra("dogBirthYear");
        String dogSex = intent.getStringExtra("dogSex");

        clearOnTouch();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDogInfo(dogName, dogBreed, dogBirthDay, dogBirthMonth, dogBirthYear, dogSex, dogChipNumber,
                        dogVaccinationStatus.getText().toString(), dogWeight.getText().toString(),
                        dogAllergies.getText().toString(), dogMedication.getText().toString(), dogVetName.getText().toString());

                Intent intent = new Intent(AddCompanion2.this,MyCompanions.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void addDogInfo(String Name, String Breed, String Day, String Month, String Year, String Gender, String ChipNumber, String vaccinationStatus, String weight, String allergies, String medication, String vetName){
        String Age;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference addPetData = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("My Pets:").child(Name);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String currentYearString = yearFormat.format(new Date());
        int currentYear = Integer.parseInt(currentYearString);

        Calendar calendar = Calendar.getInstance();
        // Get the current month (zero-based index, so January is 0)
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (Integer.parseInt(Month) > currentMonth) {
            Age = "" + (currentYear - Integer.parseInt(Year));
        }

        else if (Integer.parseInt(Month) == currentMonth && Integer.parseInt(Day) < currentDay) {
            Age = "" + (currentYear - Integer.parseInt(Year));
        }

        else {
            Age =   "" + (currentYear - Integer.parseInt(Year) - 1);
        }

        DogData dogData = new DogData(Name,Age,Breed,"None",Day,Month,Year,Day + "/" + Month + "/"+ Year,Gender,ChipNumber, vaccinationStatus, weight, allergies, medication, vetName);
        addPetData.setValue(dogData);
    }


    void clearOnTouch() {
        dogVaccinationStatus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogVaccinationStatus.setText(""); // Clear the text when touched
                return false;
            }
        });

        dogWeight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogWeight.setText(""); // Clear the text when touched
                return false;
            }
        });

        dogAllergies.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogAllergies.setText(""); // Clear the text when touched
                return false;
            }
        });

        dogMedication.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogMedication.setText(""); // Clear the text when touched
                return false;
            }
        });

        dogVetName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogVetName.setText(""); // Clear the text when touched
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,AddCompanion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(AddCompanion2.this, AddCompanion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}