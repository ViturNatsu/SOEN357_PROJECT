package com.example.soen357_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DogParkAdapter extends ArrayAdapter<DogPark> {
    public DogParkAdapter(Context context, List<DogPark> dogParks) {
        super(context, 0, dogParks);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DogPark dogPark = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_dog_park, parent, false);
        }

        TextView parkName = view.findViewById(R.id.park_name);
        TextView parkAddress = view.findViewById(R.id.park_address);

        parkName.setText(dogPark.getName());
        parkAddress.setText(dogPark.getAddress());

        // return view
        return view;
    }
}
