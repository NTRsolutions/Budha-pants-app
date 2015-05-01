package com.buddhapants.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.util.Log;

import com.buddhapants.AppConstant;
import com.buddhapants.modal.RegisterModal;

public class WSAdapter {

	public static String getJSONObject(String url) {

		// Making HTTP request
		String json = "";
		InputStream is = null;
		try {
			HttpParams httpParams = new BasicHttpParams();

			int some_reasonable_timeout = (int) (30 * 1000);
			HttpConnectionParams.setConnectionTimeout(httpParams,
					some_reasonable_timeout);
			HttpConnectionParams.setSoTimeout(httpParams,
					some_reasonable_timeout);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
					AppConstant.API_KEY, AppConstant.PASSWORD);
			AuthScope authScope = new AuthScope(AuthScope.ANY_HOST,
					AuthScope.ANY_PORT);
			httpclient.getCredentialsProvider().setCredentials(authScope,
					credentials);

			HttpGet httpGet = new HttpGet(url);

			httpGet.addHeader("Content-Type", "application/json");
			httpGet.addHeader("Accept", "application/json");
			HttpResponse response = null;
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Exception=======", "" + e.getMessage());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "");
			}
			is.close();
			// .d("value response", sb.toString());
			json = sb.toString();
			// Log.d("String Response", sb.toString());
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		// Log.e("jsonjnbhjn=======", "" + json.toString());
		// try {
		// jObj = new JSONObject(json);
		// } catch (JSONException e) {
		// Log.e("JSON Parser", "Error parsing data " + e.toString());
		// }

		// try parse the string to a JSON object

		// return JSON String
		return json;

	}

	// public static String postJSONObject(String url, RegisterModal
	// registerModal) {
	// HttpResponse response;
	// String result = "";
	// try {
	// Log.e("String Response?????", "enter this method");
	// // defaultHttpClient
	// HttpParams httpParams = new BasicHttpParams();
	// int some_reasonable_timeout = (int) (70 * 1000);
	// HttpConnectionParams.setConnectionTimeout(httpParams,
	// some_reasonable_timeout);
	// HttpConnectionParams.setSoTimeout(httpParams,
	// some_reasonable_timeout);
	// DefaultHttpClient httpclient = new DefaultHttpClient();
	// @SuppressWarnings("unused")
	// UsernamePasswordCredentials credentials = new
	// UsernamePasswordCredentials(
	// AppConstant.API_KEY, AppConstant.PASSWORD);
	// AuthScope authScope = new AuthScope(AuthScope.ANY_HOST,
	// AuthScope.ANY_PORT);
	// httpclient.getCredentialsProvider().setCredentials(authScope,
	// credentials);
	// String json = "";
	// StringEntity se = new StringEntity(json);
	// HttpPost httpPost = new HttpPost(url);
	// httpPost.addHeader("Content-Type", "application/json");
	// httpPost.addHeader("Accept", "application/json");
	// httpPost.setEntity(se);
	// // HttpResponse response = null;
	// response = httpclient.execute(httpPost);
	//
	// HttpEntity entity = response.getEntity();
	// is = entity.getContent();
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// is, "iso-8859-1"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "");
	// }
	// is.close();
	//
	// // Log.d("value response", sb.toString());
	// json = sb.toString();
	//
	// } catch (Exception e) {
	// Log.e("Buffer Error", "Error converting result " + e.toString());
	// }
	// // try parse the string to a JSON object
	// // return JSON String
	// return result;
	//
	// }

}
