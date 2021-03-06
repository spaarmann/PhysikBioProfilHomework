/*
 * Copyright (c) 2016  Sebastian Paarmann
 * Licensed under the MIT license, see the LICENSE file
 */

package de.s_paarmann.homeworkapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import de.s_paarmann.homeworkapp.R;

public class NoUpdateDialog extends DialogFragment {

  public static final String TAG = "NoUpdateDialog";

  public NoUpdateDialog() {
    super();
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(R.string.noUpdateDialogText)
        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // Do nothing, just let the dialog close
          }
        });
    return builder.create();
  }
}
