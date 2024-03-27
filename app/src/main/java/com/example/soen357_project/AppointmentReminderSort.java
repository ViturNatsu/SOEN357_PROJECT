package com.example.soen357_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

// Custom ListView Adapter based on official Android documentation
public class AppointmentReminderSort extends BaseAdapter {
    private List<Object> objects;
    private Context context;

    public AppointmentReminderSort(Context context, List<Object> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // get the type of the object
    public int getType(int position) {
        Object object = getItem(position);
        // if object is a Date, return 0 (for view)
        if (object instanceof Date) {
            return 0;
        // if object is an AppointmentReminder, return 1 (for view)
        } else if (object instanceof AppointmentReminder) {
            return 1;
        }
        return -1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // find out if current is a date or appointment/reminder
        int type = getType(position);

        // If date
        if (type == 0) {
            // If date, use date xml layout for date
            view = layoutInflater.inflate(R.layout.list_date, parent, false);

            Date date = (Date) getItem(position);

            TextView dateTextView = view.findViewById(R.id.date);
            dateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(date));

        // If appointment/reminder
        } else if (type == 1) {
            // If Appointment/Reminder, use appointment_reminder xml layout
            view = layoutInflater.inflate(R.layout.list_appointment_reminder, parent, false);

            AppointmentReminder appointmentReminder = (AppointmentReminder) getItem(position);


            TextView time = view.findViewById(R.id.appointmentTime);
            TextView dogName = view.findViewById(R.id.dogName);
            TextView description = view.findViewById(R.id.appointmentDescription);

            time.setText(appointmentReminder.getTime());
            dogName.setText(appointmentReminder.getDogName());
            description.setText(appointmentReminder.getDescription());
        }

        return view;
    }

}