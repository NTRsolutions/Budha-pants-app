package com.buddhapants.home;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.modal.PagesModal;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.util.ConnectionDetector;
import com.buddhapants.util.LogMessage;
import com.buddhapants.webservices.WSAdapter;

public class HomepageActivity extends Activity {
	ImageButton btn_nav, backButton, addtoCart;
	PagesModal pagesModal;
	TextView txtAbout, txtTitle, txtQuote;
	JSONArray jsonArray;
	ProgressDialog pDialog;
	Element tagTitle, tagAbout, tagQuote;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		pagesModal = new PagesModal();
		cd = new ConnectionDetector(getApplicationContext());
		btn_nav = (ImageButton) findViewById(R.id.btn_navigation_drawer);
		txtAbout = (TextView) findViewById(R.id.txt_about);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		txtQuote = (TextView) findViewById(R.id.txt_quote);
		backButton = (ImageButton) findViewById(R.id.btn_back);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);

		checkInternet();
		pDialog = new ProgressDialog(this);
		new ExecutePages().execute();
		btn_nav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomepageActivity.this,
						NavigationActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				//finish();

			}
		});

		addtoCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddToCart = new Intent(HomepageActivity.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
			}
		});

		// txtTitle.setText(Html
		// .fromHtml("<h1 style=\"text-align: center;\"><span style=\"color: #444444;\">Pants that make you dance!</span></h1>"));

	}

	private void checkInternet() {
		// TODO Auto-generated method stub
		isInternetPresent = cd.isConnectingToInternet();

		// check for Internet status
		if (isInternetPresent) {
			// Internet Connection is Present
			// make HTTP requests
			// showAlertDialog(HomepageActivity.this, "Internet Connection",
			// "You have internet connection", true);
		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			LogMessage.showDialog(HomepageActivity.this,
					"No Internet Connection",
					"You don't have internet connection.", "OK");
		}
	}

	class ExecutePages extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String response = WSAdapter.getJSONObject(AppConstant.PAGES);

			// Log.e("response------", "" + response);

			return response;
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

			pagesModal = updateUI(result);
			pDialog.dismiss();
			// Log.e("result:", "" + result);
			// Log.e("pagesModal:", "" + pagesModal);
		}

	}

	public PagesModal updateUI(String result) {
		// TODO Auto-generated method stub
		Log.e("result:update ui", "" + result);

		try {
			JSONObject mJsonObject = new JSONObject(result)
					.getJSONObject("page");
			Log.e("mJsonObject---", "" + mJsonObject);
			// jsonArray = mJsonObject.getJSONArray("page");
			// Log.e("jsonArray", "" + jsonArray);
			//
			// Log.e("jsonArray", "" + jsonArray.length());

			for (int i = 0; i < mJsonObject.length(); i++) {
				pagesModal = new PagesModal();

				// JSONObject jsonObject = jsonArray.getJSONObject(i);
				// Log.e("jsonObject---", ""+jsonObject);
				String author = mJsonObject.getString("author");
				String body_html = mJsonObject.getString("body_html")
						.replaceAll("[^\\x00-\\x7F]", "");
				String created_at = mJsonObject.getString("created_at");
				String published_at = mJsonObject.getString("published_at");
				String template_suffix = mJsonObject
						.getString("template_suffix");
				String title = mJsonObject.getString("title");
				String handle = mJsonObject.getString("handle");
				String updated_at = mJsonObject.getString("updated_at");
				long id = mJsonObject.getLong("id");
				long shop_id = mJsonObject.getLong("shop_id");

				// txtAbout.setText(Html.fromHtml(body_html));
				// Log.e("txtAbout----------", "" + txtAbout);

				pagesModal.setAuthor(author);
				pagesModal.setBody_html(body_html);
				pagesModal.setCreated_at(created_at);
				pagesModal.setHandle(handle);
				pagesModal.setId(id);
				pagesModal.setPublished_at(published_at);
				pagesModal.setTemplate_suffix(template_suffix);
				pagesModal.setTitle(title);
				pagesModal.setUpdated_at(updated_at);
				pagesModal.setShop_id(shop_id);

				Document doc = Jsoup.parse(body_html);
				tagTitle = doc.select("h1").first();
				tagAbout = doc.select("p").first();
				tagQuote = doc.select("p").last();

				// Log.e("tag------------", "" + tagTitle);
				// Log.e("tag1------------", "" + tagAbout);
				// Log.e("tagQuote------------", "" + tagQuote);

				txtTitle.setText(Html.fromHtml(tagTitle.toString()));
				txtAbout.setText(Html.fromHtml(tagAbout.toString()));
				txtQuote.setText(Html.fromHtml(tagQuote.toString()));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pagesModal;
	}
}
