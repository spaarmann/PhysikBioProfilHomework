/*
 * Copyright (c) 2015  Sebastian Paarmann
 * Licensed under the MIT license, see the LICENSE file
 */

package de.s_paarmann.homeworkapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Simple {@code ArrayAdapter} for a list of Reminders. Mostly for use by {@link ManageRemindersActivity}.
 */
public class ReminderArrayAdapter extends ArrayAdapter<Reminder> {

  public static final String TAG = "ReminderArrayAdapter";

  private List<Reminder> objects;
  private Context context;

  public ReminderArrayAdapter(Context context, List<Reminder> objects) {
    super(context, R.layout.reminder_list_item, objects);
    this.objects = objects;
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View root = inflater.inflate(R.layout.reminder_list_item, parent, false);

    TextView title = (TextView) root.findViewById(R.id.reminderTitle);
    TextView date = (TextView) root.findViewById(R.id.reminderDate);

    String strDate = objects.get(position).getDate("dd.MM.yyyy HH:mm");

    title.setText(objects.get(position).getTitle());
    date.setText(strDate);

    return root;
  }
}
