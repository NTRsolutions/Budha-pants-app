package com.buddhapants.contactus;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.modal.PagesModal;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.util.ConnectionDetector;
import com.buddhapants.webservices.WSAdapter;

public class ContactActivity extends Activity implements OnClickListener {
	ImageView imgFacebook, imgInstagram, imgPinterest;
	TextView txt_address, txt_clicktoOpen, txt_orderInquery, txtTitle;
	SpannableString spannableString, spannableString2, spannableString3;
	ImageButton backButton, addtoCart;
	ProgressDialog pDialog;
	PagesModal pagesModal;
	Element tagDesc, tagAdd, tagLink;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_us);
		txt_address = (TextView) findViewById(R.id.txt_contact_address);
		txt_clicktoOpen = (TextView) findViewById(R.id.txt_clickabletext);
		txt_orderInquery = (TextView) findViewById(R.id.txt_order_inquery);
		imgFacebook = (ImageView) findViewById(R.id.img_facebook);
		imgInstagram = (ImageView) findViewById(R.id.img_instagram);
		imgPinterest = (ImageView) findViewById(R.id.img_pinterest);
		backButton = (ImageButton) findViewById(R.id.btn_back_contact);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);

		spannableString = new SpannableString("BUDDHA PANTS\n"
				+ "P.O. Box 370903 Miami, Florida 33137\n"
				+ "Hello@BuddhaPants.com\n" + "1-855-5-BUDDHA (528-3342)\n");
		spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, 0);
		spannableString.setSpan(new UnderlineSpan(), 88, 96, 0);

		ClickableSpan clickableSpan = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				// We display a Toast. You could do anything you want here.
				// Toast.makeText(ContactActivity.this, "Clicked",
				// Toast.LENGTH_SHORT).show();
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				String aEmailList[] = { "Hello@buddhapants.com" };
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						aEmailList);
				emailIntent.setType("plain/text");
				startActivity(emailIntent);

			}
		};

		spannableString.setSpan(clickableSpan, 50, 71, 0);
		spannableString
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						50, 71, 0);

		spannableString2 = new SpannableString("Click here to apply to" + " "
				+ "Join the team!");

//		ClickableSpan clickableSpan2 = new ClickableSpan() {
//
//			@Override
//			public void onClick(View widget) {
//				// TODO Auto-generated method stub
//
//				// Intent iInvolved = new Intent(ContactActivity.this,
//				// GetInvolvedActivity.class);
//				// startActivity(iInvolved);
//				
//			}
//		};

		spannableString2.setSpan(new URLSpan(
				"http://www.buddhapantsmiami.com/pages/get-involved"),
				23, 37, 0);
		spannableString2
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						23, 37, 0);

		spannableString3 = new SpannableString("Order Inquiries" + " "
				+ "Sales@BuddhaPants.com");
		ClickableSpan clickableSpan3 = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				// TODO Auto-generated method stub

				Intent emailIntent1 = new Intent(
						android.content.Intent.ACTION_SEND);
				String aEmailList[] = { "Sales@BuddhaPants.com" };
				emailIntent1.putExtra(android.content.Intent.EXTRA_EMAIL,
						aEmailList);
				emailIntent1.setType("plain/text");
				startActivity(emailIntent1);
			}
		};

		spannableString3.setSpan(clickableSpan3, 16, 37, 0);
		spannableString3.setSpan(new ForegroundColorSpan(Color.BLACK), 16, 37,
				0);

		txt_address.setMovementMethod(LinkMovementMethod.getInstance());
		txt_address.setText(spannableString);

		txt_clicktoOpen.setMovementMethod(LinkMovementMethod.getInstance());
		txt_clicktoOpen.setText(spannableString2);

		txt_orderInquery.setMovementMethod(LinkMovementMethod.getInstance());
		txt_orderInquery.setText(spannableString3);

		imgFacebook.setOnClickListener(this);
		imgInstagram.setOnClickListener(this);
		imgPinterest.setOnClickListener(this);
		backButton.setOnClickListener(this);
		addtoCart.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int ID = v.getId();
		switch (ID) {
		case R.id.img_instagram:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse("https://instagram.com/buddha_pants"));
			startActivity(intent);
			break;
		case R.id.img_pinterest:
			Intent intentPin = new Intent();
			intentPin.setAction(Intent.ACTION_VIEW);
			intentPin.addCategory(Intent.CATEGORY_BROWSABLE);
			intentPin.setData(Uri
					.parse("https://www.pinterest.com/wearbuddhapants/"));
			startActivity(intentPin);
			break;
		case R.id.img_facebook:
			Intent intentFB = new Intent();
			intentFB.setAction(Intent.ACTION_VIEW);
			intentFB.addCategory(Intent.CATEGORY_BROWSABLE);
			intentFB.setData(Uri
					.parse("https://www.facebook.com/wear.buddhapants"));
			startActivity(intentFB);
			break;

		case R.id.btn_back_contact:
			onBackPressed();
			break;
		case R.id.btn_addTo_cart:
			Intent iAddToCart = new Intent(ContactActivity.this,
					AddToCartActivity.class);
			startActivity(iAddToCart);

		default:
			break;
		}

	}

	public void checkInternet() {
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
			showAlertDialog(ContactActivity.this, "No Internet Connection",
					"You don't have internet connection.", false);
		}
	}

	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	class Executecontact extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String response = WSAdapter.getJSONObject(AppConstant.CONTACT);
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
		}

	}

	public PagesModal updateUI(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject mJsonObject = new JSONObject(result)
					.getJSONObject("page");
			Log.e("mJsonObject---", "" + mJsonObject);

			for (int i = 0; i < mJsonObject.length(); i++) {
				pagesModal = new PagesModal();

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

				txtTitle.setText(title);

				Document document = Jsoup.parse(body_html);

				tagDesc = document.select("p").first();

				tagAdd = document.select("div").last();
				tagLink = tagAdd.nextElementSibling();
				Log.e("tagLink---", "" + tagLink);

				txt_address.setText(Html.fromHtml(tagDesc.toString()),
						BufferType.SPANNABLE);
				// txt_address.setMovementMethod(LinkMovementMethod.getInstance());
				txt_address.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent emailIntent = new Intent(
								android.content.Intent.ACTION_SEND);
						String aEmailList[] = { "Hello@buddhapants.com" };
						emailIntent.putExtra(
								android.content.Intent.EXTRA_EMAIL, aEmailList);
						emailIntent.setType("plain/text");
						startActivity(emailIntent);
					}
				});

				txt_clicktoOpen.setText(Html.fromHtml(tagAdd.toString()),
						BufferType.SPANNABLE);
				txt_clicktoOpen.setMovementMethod(LinkMovementMethod
						.getInstance());

				// txt_orderInquery.setText(Html.fromHtml(tagLink.toString()));
				// txt_orderInquery.setOnClickListener(new
				// View.OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// Intent emailIntent1 = new Intent(
				// android.content.Intent.ACTION_SEND);
				// String aEmailList[] = { "Sales@BuddhaPants.com" };
				// emailIntent1.putExtra(
				// android.content.Intent.EXTRA_EMAIL, aEmailList);
				// emailIntent1.setType("plain/text");
				// startActivity(emailIntent1);
				// }
				// });

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pagesModal;

	}

}
