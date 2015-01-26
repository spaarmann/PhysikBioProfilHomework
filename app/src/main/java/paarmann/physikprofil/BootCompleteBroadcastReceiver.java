/*
 * Copyright (c) 2015  Sebastian Paarmann
 * Licensed under the MIT license, see the LICENSE file
 */

package paarmann.physikprofil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;

import java.util.Calendar;
import java.util.Set;

public class BootCompleteBroadcastReceiver extends BroadcastReceiver {

  public static final String TAG = "BootCompleteBroadcastReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    SharedPreferences prefs = context.getSharedPreferences(MainActivity.PREF_NAME, 0);

    Log.i(TAG, "Setting reminder alarms again...");

    Set<Reminder> setReminders = Reminder.loadSavedReminders(context);
    for (Reminder currentReminder : setReminders) {
      long when = currentReminder.getDate().getTime();
      Uri uri = currentReminder.toUri();

      Intent
          alarmIntent =
          new Intent(MainActivity.ACTION_REMIND, uri, context, ReminderBroadcastReceiver.class);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, when, pendingIntent);
      } else {
        alarmManager.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
      }
    }

    if (prefs.getBoolean(MainActivity.PREF_AUTOUPDATES, false)) {
      Log.i(TAG, "Setting automatic update alarms again...");

      Uri uriAfterSchool = Uri.fromParts("homeworkUpdate", "afterSchool", "");
      Uri uriAfternoon = Uri.fromParts("homeworkUpdate", "afternoon", "");

      Intent intentAfterSchool = new Intent(MainActivity.ACTION_UPDATEHOMEWORK, uriAfterSchool,
                                            context, AutomaticUpdateService.class);
      Intent intentAfternoon = new Intent(MainActivity.ACTION_UPDATEHOMEWORK, uriAfternoon,
                                          context, AutomaticUpdateService.class);

      PendingIntent piAfterSchool = PendingIntent.getService(context, 1, intentAfterSchool, 0);
      PendingIntent piAfternoon = PendingIntent.getService(context, 2, intentAfternoon, 0);

      Calendar afterSchool = Calendar.getInstance();
      afterSchool.set(Calendar.HOUR_OF_DAY, 14);
      afterSchool.set(Calendar.MINUTE, 15);
      Calendar afternoon = Calendar.getInstance();
      afternoon.set(Calendar.HOUR_OF_DAY, 17);
      afternoon.set(Calendar.MINUTE, 15);

      long oneDayMillis = 24 * 60 * 60 * 1000;

      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, afterSchool.getTimeInMillis(),
                                oneDayMillis, piAfterSchool);
      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, afternoon.getTimeInMillis(),
                                oneDayMillis, piAfternoon);
    }
  }

}
