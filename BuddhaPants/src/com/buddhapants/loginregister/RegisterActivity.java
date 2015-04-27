package com.buddhapants.loginregister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.database.MyConnection;
import com.buddhapants.home.HomepageActivity;
import com.buddhapants.modal.RegisterModal;
import com.buddhapants.util.LogMessage;

public class RegisterActivity extends Activity {
	Button btnCreate;
	EditText edtFname, edtLname, edtEmail, edtPassword;
	String first_name, last_name, email, pwd;
	ProgressDialog pDialog;
	RegisterModal registerModal;
	MyConnection connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		pDialog = new ProgressDialog(this);
		connection = new MyConnection(this);
		btnCreate = (Button) findViewById(R.id.btn_Create);
		edtFname = (EditText) findViewById(R.id.edt_firstname);
		edtLname = (EditText) findViewById(R.id.edt_lastname);
		edtEmail = (EditText) findViewById(R.id.edt_email);
		edtPassword = (EditText) findViewById(R.id.edt_pwd_register);
		btnCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				first_name = edtFname.getText().toString().trim();
				last_name = edtLname.getText().toString();
				email = edtEmail.getText().toString();
				pwd = edtPassword.getText().toString().trim();
				boolean receive = validatation_method();
				if (receive) {
					new ExecuteRegister().execute(AppConstant.REGISTER);
				}
				// Intent iCreate = new Intent(RegisterActivity.this,
				// HomepageActivity.class);
				// startActivity(iCreate);
				// overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// finish();

			}
		});
	}

	private boolean validatation_method() {
		// TODO Auto-generated method stub
		String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
		if (edtFname.getText().toString().equals("")) {
			LogMessage.showDialog(RegisterActivity.this, null,
					"Please enter your first name", "OK");
			return false;
		}
		if (edtLname.getText().toString().equals("")) {
			LogMessage.showDialog(RegisterActivity.this, null,
					"Please enter your last name ", "OK");
			return false;
		}
		if (!edtEmail.getText().toString().matches(emailPattern)) {
			LogMessage.showDialog(RegisterActivity.this, null,
					"Please enter valid email address", "OK");
			return false;
		}
		if (!isValidPassword(pwd)) {
			LogMessage.showDialog(RegisterActivity.this, null,
					"Password should be atleast 5 characters", "OK");
			return false;
		}
		return true;
	}

	private boolean isValidPassword(String pwd2) {
		// TODO Auto-generated method stub
		if (edtPassword != null
				&& edtPassword.getText().toString().trim().length() >= 5) {
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	public static String POST(String url, RegisterModal registerModal) {
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			DefaultHttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			@SuppressWarnings("unused")
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
					AppConstant.API_KEY, AppConstant.PASSWORD);
			AuthScope authScope = new AuthScope(AuthScope.ANY_HOST,
					AuthScope.ANY_PORT);
			httpclient.getCredentialsProvider().setCredentials(authScope,
					credentials);

			String json = "";
			// List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			JSONObject json_Object_ = new JSONObject();
			json_Object_.accumulate("address1", "123 Oak St");
			json_Object_.accumulate("city", "Ottawa");
			json_Object_.accumulate("province", "ON");
			json_Object_.accumulate("phone", "555-1212");
			json_Object_.accumulate("zip", "123 ABC");
			json_Object_.accumulate("last_name", registerModal.getLname());
			json_Object_.accumulate("first_name", registerModal.getFname());
			json_Object_.accumulate("country", "CA");
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(json_Object_);

			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("first_name", registerModal.getFname());
			jsonObject.accumulate("last_name", registerModal.getLname());
			jsonObject.accumulate("email", registerModal.getEmail());
			jsonObject.accumulate("password", registerModal.getPwd());
			jsonObject.accumulate("password_confirmation",
					registerModal.getPwd());
			jsonObject.accumulate("send_email_welcome", true);
			jsonObject.accumulate("addresses", jsonArray);

			JSONObject json_Object = new JSONObject();
			json_Object.accumulate("customer", jsonObject);

			// 3. build jsonObject

			// 4. convert JSONObject to JSON to String
			json = json_Object.toString();
			Log.e("json to string", "" + json);

			// ** Alternative way to convert Person object to JSON string usin
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		return result;
	}

	class ExecuteRegister extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			registerModal = new RegisterModal();
			registerModal.setFname(first_name);
			registerModal.setLname(last_name);
			registerModal.setEmail(email);
			registerModal.setPwd(pwd);

			return POST(params[0], registerModal);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();

			pDialog.setMessage("Loading..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("result---", "" + result);
			pDialog.dismiss();
			if (result.contains("errors")) {
				LogMessage.showDialog(RegisterActivity.this, null,
						"Email has already been taken", "OK");
			} else {

				registerModal = updateUI(result);
			}
			Log.e("result----------", "" + result);
		}
	}

	public RegisterModal updateUI(String result) {
		// TODO Auto-generated method stub
		try {
			registerModal = new RegisterModal();
			JSONObject mJsonObject = new JSONObject(result)
					.getJSONObject("customer");
			int src = mJsonObject.getInt("id");
			String mStringFirst = mJsonObject.getString("first_name");
			String mStringlast_name = mJsonObject.getString("last_name");
			String mStringemail = mJsonObject.getString("email");
			boolean receive = connection.insertDataRegister(mStringFirst,
					mStringlast_name, "" + src, mStringemail);
			if (receive) {
				Intent i = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(i);
				finish();
			}

			// person.get

		} catch (Exception e) {
			// TODO: handle exception
		}
		return registerModal;

	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

}
