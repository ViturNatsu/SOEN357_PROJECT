package com.example.soen357_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.MyViewHolder> {
    Context context;
    ArrayList<DogData> list;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public DogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dog_entry,parent,false);
        return new DogAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DogAdapter.MyViewHolder holder, int position) {
        //DogData dogs = list.get(position);
        holder.name.setText(list.get(position).getName());
        holder.age.setText(list.get(position).getAge() + " YRS OLD");
        holder.breed.setText(list.get(position).getBreed());
        holder.condition.setText("Condition: " + list.get(position).getCondition());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position); // Notify the listener about the item click
                }
            }
        });
    }

    public DogAdapter(Context context, ArrayList<DogData> list) {
        this.context = context;
        this.list = list;
    }

    // Define an interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,age,breed,condition;
        ImageView dog_imsge;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dog_imsge = itemView.findViewById(R.id.imageViewDog);
            name = itemView.findViewById(R.id.textViewName);
            age = itemView.findViewById(R.id.textViewAge);
            breed = itemView.findViewById(R.id.textViewBreed);
            condition = itemView.findViewById(R.id.textViewHealthCondition);
        }
    }
}
