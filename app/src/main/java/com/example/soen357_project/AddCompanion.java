package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Date;

public class AddCompanion extends AppCompatActivity {
    private Spinner DogBreeds,Days,Months,Years,Sex;
    private EditText dogName,dogVet;
    private Button saveButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_companion);

        dogName = findViewById(R.id.dogNameTxt);
        dogVet = findViewById(R.id.dogVetTxt);
        saveButton = findViewById(R.id.saveBtn);
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

        dogVet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dogVet.setText(""); // Clear the text when touched
                return false;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context applicationContext = getApplicationContext();
                if (dogName.getText().toString().equals("NAME") || dogName.getText().toString().trim().isEmpty() ||
                        DogBreeds.getSelectedItem().toString().equals("Dog Breed") ||
                        Days.getSelectedItem().toString().equals("DD") ||
                        Months.getSelectedItem().toString().equals("MM") ||
                        Years.getSelectedItem().toString().equals("YYYY") ||
                        Sex.getSelectedItem().toString().equals("Gender") ||
                        dogVet.getText().toString().equals("NAME") ||
                        dogVet.getText().toString().trim().isEmpty()) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show();
                    String myPetName = dogName.getText().toString();
                    String myPetBreed = DogBreeds.getSelectedItem().toString();
                    String myPetDay = Days.getSelectedItem().toString();
                    String myPetMonth = Months.getSelectedItem().toString();
                    String myPetYear = Years.getSelectedItem().toString();
                    String myPetGender = Sex.getSelectedItem().toString();
                    String myPetVet = dogVet.getText().toString();
                    addDogInfo(myPetName, myPetBreed, myPetDay, myPetMonth, myPetYear, myPetGender, myPetVet);
                    Intent intent = new Intent(AddCompanion.this, MyCompanions.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addDogInfo(String Name, String Breed, String Day, String Month, String Year, String Gender, String Vet){
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

        DogData dogData = new DogData(Name,Age,Breed,"None",Day,Month,Year,Day + "/" + Month + "/"+ Year,Gender,Vet);
        addPetData.setValue(dogData);
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(AddCompanion.this, MyCompanions.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCompanion.this, MyCompanions.class);
        startActivity(intent);
    }
}