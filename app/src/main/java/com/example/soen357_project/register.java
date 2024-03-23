package com.example.soen357_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private EditText nameEditText, emailEditText,phoneEditText,AddressEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton,registerBack;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Initialize EditText fields
        nameEditText = findViewById(R.id.Namereg);
        phoneEditText = findViewById(R.id.Phonereg);
        AddressEditText = findViewById(R.id.AddressReg);
        emailEditText = findViewById(R.id.Emailreg);
        passwordEditText = findViewById(R.id.Passwordreg);
        confirmPasswordEditText = findViewById(R.id.CPasswordreg);

        // Initialize register button
        registerBack = findViewById(R.id.backButton);
        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the behavior when the back button is clicked

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton = findViewById(R.id.Regbtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = AddressEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Perform validation
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()|| address.isEmpty()) {
                    Toast.makeText(register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Account creation successful
                                    // Save user data to Firebase database
                                    String userId = mAuth.getCurrentUser().getUid();
                                    User user = new User(name, email, phone, address);
                                    usersRef.child(userId).setValue(user);

                                    Toast.makeText(register.this, "Account created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
