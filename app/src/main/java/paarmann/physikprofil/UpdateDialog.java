/*
 * Copyright (c) 2014  Sebastian Paarmann
 */

package paarmann.physikprofil;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class UpdateDialog extends DialogFragment {

  public static final String TAG = "UpdateDialog";

  private String newVersionName;

  public UpdateDialog() {
    super();
  }

  public UpdateDialog setVersionName(String newVersionName) {
    this.newVersionName = newVersionName;
    return this;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    String message = getResources().getString(R.string.updateDialogText);
    message = message.replace("$VERSIONNAME$", newVersionName);
    builder.setMessage(message).setPositiveButton("Ja!", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        ((MainActivity) getActivity()).update();
      }
    }).setNegativeButton("Nicht jetzt", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        Log.d(TAG, "Not updating after user input");
      }
    });
    return builder.create();
  }
}
