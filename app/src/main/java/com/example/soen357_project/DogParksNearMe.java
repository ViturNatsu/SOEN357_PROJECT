package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DogParksNearMe extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_parks_near_me);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), ""); // Removed API Key for safety purposes, will restrict and add later
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Static Montreal
        fetchDogParks(45.49719, -73.57907);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng montreal = new LatLng(45.49719, -73.57907);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(montreal, 15));
    }

    // REST API GET Request to retrieve dog parks near location provided with a radius of 10000
    private void fetchDogParks(double latitude, double longitude) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = ""; // Removed API Key for safety purposes, will restrict and add later
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=10000&type=park&keyword=dog&key=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                Log.d("F", "fetchDogParks: FAIL");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    // use runOnUiThread as specified in the Android documentation
                    runOnUiThread(() -> {
                        List<DogPark> parkNames = parseParkNamesFromResponse(responseData);
                        updateListView(parkNames);
                    });
                }
            }
        });
    }

    private List<DogPark> parseParkNamesFromResponse(String response) {
        List<DogPark> parkNames = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            Log.d("API Status", status);
            JSONArray results = jsonObject.getJSONArray("results");
            Log.d("tempCheck", results.toString());

            for (int i = 0; i < results.length(); i++) {
                JSONObject park = results.getJSONObject(i);
                String name = park.getString("name");
                String address = park.getString("vicinity");
                parkNames.add(new DogPark(name, address));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parkNames;
    }
    private void updateListView(List<DogPark> parkNames) {
        ListView listView = findViewById(R.id.dogParkListView);
        DogParkAdapter adapter = new DogParkAdapter(this, parkNames);
        listView.setAdapter(adapter);
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(DogParksNearMe.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DogParksNearMe.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}