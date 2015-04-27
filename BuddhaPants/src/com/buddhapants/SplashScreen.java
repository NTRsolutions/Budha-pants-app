package com.buddhapants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.buddhapants.home.HomepageActivity;
import com.buddhapants.loginregister.LoginActivity;

public class SplashScreen extends Activity {
	SharedPreferences sharedPreferences;
	Editor editor;
	private static int SPLASH_TIME_OUT = 10;
	String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sharedPreferences = getSharedPreferences(AppConstant.Buddhapants,
				Context.MODE_PRIVATE);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer.
			 */

			@Override
			public void run() {

				// This method will be executed once the timer is over
				// Start your app main activity

				if (sharedPreferences != null
						&& sharedPreferences.contains(AppConstant.EMAIL)) {
					email = sharedPreferences.getString(AppConstant.EMAIL, "");

					Intent i = new Intent(SplashScreen.this,
							HomepageActivity.class);
					startActivity(i);
					finish();
				}

				else {
					Intent iLog = new Intent(SplashScreen.this,
							LoginActivity.class);
					startActivity(iLog);
					finish();
				}

			}
		}, SPLASH_TIME_OUT);
	}
}
