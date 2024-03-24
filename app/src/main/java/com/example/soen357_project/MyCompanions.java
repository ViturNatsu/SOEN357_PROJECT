package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MyCompanions extends AppCompatActivity {

    private FloatingActionButton addPetBtn;
    private RecyclerView recyclerView;
    private FirebaseUser user;
    ArrayList<DogData> list;
    DatabaseReference databaseReference;
    DogAdapter mydogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_companions);

        recyclerView = findViewById(R.id.recycleViewList);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mydogAdapter = new DogAdapter(this,list);
        recyclerView.setAdapter(mydogAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addPetBtn = findViewById(R.id.addPetbtn);
        addPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCompanions.this, AddCompanion.class);
                startActivity(intent);

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("My Pets:");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        DogData dog = dataSnapshot.getValue(DogData.class);
                        if (dog != null) {
                            list.add(dog);
                        } else {
                            Log.e("DataRetrieval", "Failed to parse DogData from dataSnapshot");
                        }
                    }
                    if (!list.isEmpty()) {
//                        test.setText(list.get(0).getName());
                        mydogAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("DataRetrieval", "List is empty after data retrieval");
                    }
                } else {
                    Log.e("DataRetrieval", "Snapshot does not exist or has no children");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DataRetrieval", "Database error: " + error.getMessage());
            }
        });

        mydogAdapter.setOnItemClickListener(new DogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DogData clickedDog = list.get(position);
                Intent intent = new Intent(MyCompanions.this, DogInfo.class);
                intent.putExtra("dogName", clickedDog.getName()); // Pass data to the next activity
                startActivity(intent);
            }
        });
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(MyCompanions.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyCompanions.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}