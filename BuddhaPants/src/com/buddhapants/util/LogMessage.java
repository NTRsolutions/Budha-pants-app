package com.buddhapants.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LogMessage {

	public static void showDialog(Context context, String title,
			String Message, String text) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title).setMessage(Message).setCancelable(false)
				.setPositiveButton(text, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		AlertDialog dialog = alertDialog.create();
		dialog.show();

	}

}
