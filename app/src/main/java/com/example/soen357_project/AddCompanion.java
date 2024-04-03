package com.example.soen357_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCompanion extends AppCompatActivity {
    private Spinner DogBreeds,Days,Months,Years,Sex;
    private EditText dogName, dogChipNumber;
    private Button nextButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companion);

        dogName = findViewById(R.id.dogNameTxt);
        dogChipNumber = findViewById(R.id.dogChipNumber);
        nextButton = findViewById(R.id.nextBtn);
        DogBreeds = findViewById(R.id.breedSpinner);
        Days = findViewById(R.id.daySpinner);
        Months = findViewById(R.id.monthSpinner);
        Years = findViewById(R.id.yearSpinner);
        Sex = findViewById(R.id.genderSpinner);

        final ArrayAdapter<String> BreedAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        BreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        BreedAdapter.add("Dog Breed");
        BreedAdapter.add("German Shepherd");
        BreedAdapter.add("Bulldog");
        BreedAdapter.add("Labrador Retriever");
        BreedAdapter.add("Siberian Husky");
        BreedAdapter.add("Beagle");
        BreedAdapter.add("Alaskan Malamute");
        BreedAdapter.add("Poodle");

        DogBreeds.setAdapter(BreedAdapter);
        DogBreeds.setSelection(0);

        //-----------------------------------------
        // Set up an ArrayAdapter for the day Spinner
        final ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.add("DD");

        // Populate the day Spinner with days 1 through 31
        for (int i = 1; i <= 31; i++) {
            dayAdapter.add(String.valueOf(i));
        }

        Days.setAdapter(dayAdapter);
        Days.setSelection(0);

        //-----------------------------------------
        // Set up an ArrayAdapter for the day Spinner
        final ArrayAdapter<String> MonthAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        MonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthAdapter.add("MM");

        // Populate the day Spinner with days 1 through 31
        for (int i = 1; i <= 12; i++) {
            MonthAdapter.add(String.valueOf(i));
        }

        Months.setAdapter(MonthAdapter);
        Months.setSelection(0);

        //----------------------------------------------------
        // Set up an ArrayAdapter for the day Spinner
        final ArrayAdapter<String> YearAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearAdapter.add("YYYY");

        // Populate the day Spinner with days 1 through 31
        for (int i = 1900; i <= 2030; i++) {
            YearAdapter.add(String.valueOf(i));
        }

        Years.setAdapter(YearAdapter);
        Years.setSelection(0);

        //----------------------------------------------------
        // Set up an ArrayAdapter for the day Spinner
        final ArrayAdapter<String> SexAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        SexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SexAdapter.add("Gender");
        SexAdapter.add("Male");
        SexAdapter.add("Female");

        Sex.setAdapter(SexAdapter);
        Sex.setSelection(0);

        dogName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogName.setText(""); // Clear the text when touched
                return false;
            }
        });

        dogChipNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogChipNumber.setText(""); // Clear the text when touched
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pass info to AddCompanion2
                Intent intent = new Intent(AddCompanion.this, AddCompanion2.class);
                String dogNameString = dogName.getText().toString();
                String dogChipNumberString = dogChipNumber.getText().toString();
                String dogBreedString = DogBreeds.getSelectedItem().toString();
                String dayString = Days.getSelectedItem().toString();
                String monthString = Months.getSelectedItem().toString();
                String yearString = Years.getSelectedItem().toString();
                String sexString = Sex.getSelectedItem().toString();
                intent.putExtra("dogName", dogNameString);
                intent.putExtra("dogChipNumber", dogChipNumberString);
                intent.putExtra("dogBreed", dogBreedString);
                intent.putExtra("dogBirthDay", dayString);
                intent.putExtra("dogBirthMonth", monthString);
                intent.putExtra("dogBirthYear", yearString);
                intent.putExtra("dogSex", sexString);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MyCompanions.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(AddCompanion.this, MyCompanions.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}