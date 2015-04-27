package com.buddhapants.loginregister;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.database.MyConnection;
import com.buddhapants.home.HomepageActivity;
import com.buddhapants.util.LogMessage;
import com.buddhapants.webservices.WSAdapter;

public class LoginActivity extends Activity {
	Button btn_login, btn_register;
	EditText edtEmail, edtPwd;
	MyConnection connection;
	String strEmail, strPwd, email, _email;
	ProgressDialog pDialog;
	JSONArray jsonArray;
	int id;
	TextView txtForgotpwd;
	SharedPreferences sharedPreferences;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		connection = new MyConnection(this);
		pDialog = new ProgressDialog(this);
		sharedPreferences = getSharedPreferences(AppConstant.Buddhapants,
				Context.MODE_PRIVATE);
		edtEmail = (EditText) findViewById(R.id.edt_username);
		edtPwd = (EditText) findViewById(R.id.edt_passworrd);
		btn_login = (Button) findViewById(R.id.btn_logIn);
		btn_register = (Button) findViewById(R.id.btnRegister);
		txtForgotpwd = (TextView) findViewById(R.id.txt_forgotPwd);
		email = sharedPreferences.getString(AppConstant.EMAIL, "");
		btn_register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iRegister = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(iRegister);
				finish();
			}
		});
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strEmail = edtEmail.getText().toString();
				strPwd = edtPwd.getText().toString();
				boolean receive = validation(strEmail, strPwd);
				if (receive) {
					sharedPrefernces();
					new ExecuteLogin().execute(strEmail, strPwd);
				}
			}
		});

		txtForgotpwd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iForgot = new Intent(LoginActivity.this,
						ForgotPassword.class);
				startActivity(iForgot);
			}
		});

	}

	private boolean validation(String username, String password) {
		// TODO Auto-generated method stub
		String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";

		if (username.matches("")) {
			LogMessage.showDialog(LoginActivity.this, null,
					"Please enter your email address", "OK");
			return false;
		}
		if (!username.matches(emailPattern)) {
			LogMessage.showDialog(LoginActivity.this, null,
					"Please enter valid email address.", "OK");
			return false;
		}
		if (password.matches("")) {
			LogMessage.showDialog(LoginActivity.this, null,
					"Password should be atleast 5 characters", "OK");
			return false;
		}

		return true;
	}

	public static String postSession(String email, String pwd) {
		String url = AppConstant.LOGIN + "?query=" + email;
		String result_session = WSAdapter.getJSONObject(url);
		Log.e("result_session--", "" + result_session);
		return result_session;

	}

	class ExecuteLogin extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String URL = postSession(params[0], params[1]);
			Log.e("URL--", "" + URL);
			return URL;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();

			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();

			updateSession(result);

		}

	}

	public void updateSession(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject mJsonObject = new JSONObject(result);
			jsonArray = mJsonObject.getJSONArray("customers");
			if (jsonArray.length() == 0) {
				LogMessage.showDialog(LoginActivity.this, null,
						"Invalid login credentials.", "OK");
			} else {

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					_email = jsonObject.getString("email");
					if (_email.matches(strEmail)) {
						Toast.makeText(getApplicationContext(), "Logged in as"+_email, 0).show();
						Intent iForm = new Intent(LoginActivity.this,
								HomepageActivity.class);
						startActivity(iForm);
						finish();
					}
					else {
						Toast.makeText(getApplicationContext(), "error"+_email, 0).show();
					}
					id = jsonObject.getInt("id");
					Log.e("id-----", "" + id);
					Log.e("_email-----", "" + _email);
				}
				boolean receive = connection.insertDataID("" + id, _email);
				Log.e("strEmail---", "" + strEmail);
				Log.e("receive-----", "" + receive);
				if (receive) {
					// Intent iForm = new Intent(LoginActivity.this,
					// HomepageActivity.class);
					// startActivity(iForm);
					// finish();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void sharedPrefernces() {
		editor = sharedPreferences.edit();
		editor.putString(AppConstant.EMAIL, strEmail);
		editor.commit();

	}

}
