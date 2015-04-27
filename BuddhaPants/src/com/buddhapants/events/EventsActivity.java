package com.buddhapants.events;

import java.util.List;

import org.json.JSONObject;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.modal.PagesModal;
import com.buddhapants.store.StoreActivity;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.util.ConnectionDetector;
import com.buddhapants.webservices.WSAdapter;

public class EventsActivity extends Activity {
	ProgressDialog pDialog;
	PagesModal pagesModal;
	List<PagesModal> listPagesModals;

	ListView listView;
	AdapterList adapter1;
	ImageButton backButton, addtoCart;
	TextView txtHeaderTitle;
	Element tagText;
	WebView view;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);

		pDialog = new ProgressDialog(this);
		pagesModal = new PagesModal();
		new ExecuteEvents().execute();
		cd = new ConnectionDetector(getApplicationContext());
		// listView = (ListView) findViewById(R.id.listview_event);
		backButton = (ImageButton) findViewById(R.id.btn_back);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);
		txtHeaderTitle = (TextView) findViewById(R.id.txt_event_title);
		view = (WebView) findViewById(R.id.webView);
		checkInternet();
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		addtoCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddToCart = new Intent(EventsActivity.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
			}
		});

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
			showAlertDialog(EventsActivity.this, "No Internet Connection",
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

	class AdapterList extends BaseAdapter {

		private Context mContext;
		private int[] image;
		List<PagesModal> listPagesModals;

		public AdapterList(Context mContext, List<PagesModal> listPagesModals) {

			this.mContext = mContext;
			this.listPagesModals = listPagesModals;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listPagesModals.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listPagesModals.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewGroup rootview = (ViewGroup) layoutInflater.inflate(
					R.layout.event_listview_items, null);
			// ImageView imageView = (ImageView) rootview
			// .findViewById(R.id.img_events);
			// imageView.setImageResource(image[position]);
			TextView txt_address = (TextView) rootview
					.findViewById(R.id.txtAddress);
			txt_address.setText(Html.fromHtml(tagText.toString()));
			txt_address.setMovementMethod(LinkMovementMethod.getInstance());
			// txt_address.setText(spannableString);
			return rootview;
		}

	}

	class ExecuteEvents extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String response = WSAdapter.getJSONObject(AppConstant.EVENTS);
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
			pDialog.dismiss();
			// listPagesModals = updateUI(result);
			pagesModal = updateUI(result);
			// adapter1 = new AdapterList(EventsActivity.this, listPagesModals);
			// listView.setAdapter(adapter1);
		}
	}

	public PagesModal updateUI(String result) {
		// TODO Auto-generated method stub
		// List<PagesModal> listPagesModals = new ArrayList<PagesModal>();
		try {
			JSONObject mJsonObject = new JSONObject(result)
					.getJSONObject("page");
			Log.e("mJsonObject---", "" + mJsonObject);
			Log.e("mJsonObject.length()----------", "" + mJsonObject.length());

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
				// listPagesModals.add(pagesModal);
				txtHeaderTitle.setText(title);

				String data = "<DOCTYPE html><head><div style=\"text-align: center;\"><i><i></i></i></div>\r\n<div></div>\r\n<div style=\"text-align: center;\"><strong><br /><br /></strong></div>\r\n<div style=\"text-align: center;\"><strong><i></i></strong></div>\r\n<div><strong><i></i></strong></div>\r\n<div>\r\n<table width=\"100%\">\r\n<tbody>\r\n<tr>\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/greenfest_compact.jpg?1782\" /></td>\r\n<td><i><a href=\"http://greenfestivals.org\" target=\"_blank\">GREEN FESTIVAL</a><br /><i>September 25-27, 2015<br />Los Angeles, CA</i></i></td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/greenfest_compact.jpg?1782\" /></td>\r\n<td><i><a href=\"http://greenfestivals.org\" target=\"_blank\">GREEN FESTIVAL</a><br /></i><i>June 5-7, 2015<br />Washington, DC<br /></i></td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/greenfest_compact.jpg?1782\" /></td>\r\n<td>\r\n<p><i><a href=\"http://greenfestivals.org\" target=\"_blank\">GREEN FESTIVAL<br /></a></i><i>April 24-26, 2015</i></p>\r\n<p><i>New York City, NY</i></p>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/wanderlust_compact.jpg?616\" /></td>\r\n<td><i><a href=\"http://wanderlust.com\" target=\"_blank\">WANDERLUST FESTIVAL</a><br />FEBRUARY 26 – MARCH 1, 2015<br />North Shore, Oahu, Hawaii</i></td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/Unknown_compact.jpeg?1780\" /></td>\r\n<td>\r\n<p><i>Zen Cruise<br /></i><i>February 9-15, 2015</i></p>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/Unknown_compact.jpeg?1780\" /></td>\r\n<td>\r\n<p><i>Zen Cruise Gala<br /></i><i>February 7, 2015</i></p>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td> <img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/wanderlust_compact.jpg?616\" /></td>\r\n<td><i><a href=\"http://wanderlust.com\" target=\"_blank\">WANDERLUST FESTIVAL<br /></a>January 29-February 2, 2015<br />Lake Taupo, New Zealand </i></td>\r\n</tr>\r\n</tbody>\r\n</table>\r\n</div>\r\n<br /><br />\r\n<div style=\"text-align: center;\"><strong><i></i></strong></div>\r\n<div style=\"text-align: center;\"><strong><i></i></strong></div>\r\n<h3 style=\"text-align: center;\"><strong><i>PAST EVENTS 2014</i></strong><br /><br /></h3>\r\n<table width=\"100%\">\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center;\"><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/Yoga-Journal-Conference-New-York-City_d4593572-243f-4448-ba6b-21486e85eb04_small.jpg?1061\" style=\"display: block; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://www.yjevents.com/sf15/\" target=\"_blank\">YOGA JOURNAL LIVE</a><br /><i>JANUARY 16–20, 2014<br /></i>Hollywood, California<br /></i></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/wanderlust_compact.jpg?616\" style=\"display: block; float: none; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://wanderlust.com\" target=\"_blank\">WANDERLUST FESTIVAL<br /></a>November 6-9, 2014<br />Austin, Texas<br /></i></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center;\"> <img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/om_yoga_compact.jpeg?617\" style=\"display: block; margin-left: auto; margin-right: auto; float: none;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://www.omyogashow.com/london/home.php\" target=\"_blank\">OM YOGA SHOW<br /></a></i><i>London, England<br /></i><em>OCTOBER 24 – 26, 2014</em></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td style=\"text-align: left;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/wanderlust_compact.jpg?616\" style=\"display: block; float: none; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><i><a href=\"http://wanderlust.com\" target=\"_blank\">WANDERLUST FESTIVAL<br /></a>July 17- 20, 2014<br /></i></i>Squaw Valley, California</td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td style=\"text-align: left;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/wanderlust_compact.jpg?616\" style=\"display: block; float: none; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><i><a href=\"http://wanderlust.com\" target=\"_blank\">WANDERLUST FESTIVAL<br /></a>July 3- 6, 2014<br />Aspen, Colorado<br /></i></i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td style=\"text-align: left;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/wanderlust_compact.jpg?616\" style=\"display: block; float: none; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://wanderlust.com\" target=\"_blank\">WANDERLUST FESTIVAL</a><br />FEBRUARY 27 – MARCH 2, 2014<br />North Shore, Oahu, Hawaii</i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td style=\"text-align: center;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/bonnaroo2014logo_19eaa809-1848-47c2-aa25-ccc4aef8f1d5_small.jpg?619\" style=\"float: none;\" /></td>\r\n<td>\r\n<div style=\"text-align: left;\"><a href=\"https://www.facebook.com/media/set/?set=a.781350531897419.1073741861.544042932294848&amp;type=3\" target=\"_blank\">BONNAROO</a></div>\r\n<div style=\"text-align: left;\">JUNE 12-15, 2014<br />Manchester, Tennessee</div>\r\n</td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/logo23_small.jpg?614\" style=\"float: none;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://www.theyogaconference.com/toronto\" target=\"_blank\">YOGA CONFERENCE AND SHOW<br /></a>MARCH 27-30, 2014<br />Toronto, Ontario, CANADA</i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/newlife_small.jpg?620\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"https://www.facebook.com/media/set/?set=a.666948730004267.1073741848.544042932294848&amp;type=3\" target=\"_blank\">NEWLIFE EXPO</a><br />MARCH 8 – 9, 2014<br />Fort Lauderdale, Florida</i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/aurafest_small.jpg?622\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://www.auramusicfestival.com\" target=\"_blank\">AURA MUSIC FESTIVAL</a><br /></i><i>FEBRUARY 14-16, 2014<br /></i><i>Live Oak, Florida</i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/VIKASA_small.jpg?621\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://vikasaexpo.com\" target=\"_blank\">VIKASA EXPO</a><br /></i><i>JANUARY 25–26, 2014<br /></i><i>Orlando, Florida</i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td style=\"text-align: left;\"><img src=\"//cdn.shopify.com/s/files/1/0532/1481/files/Yoga-Journal-Conference-New-York-City_d4593572-243f-4448-ba6b-21486e85eb04_small.jpg?1061\" style=\"display: block; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://www.yjevents.com/sf15/\" target=\"_blank\">YOGA JOURNAL LIVE</a><br /></i><i>JANUARY 16–20, 2014<br /></i><i>San Francisco, California</i></td>\r\n</tr>\r\n<tr style=\"text-align: center;\">\r\n<td><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/2013_SE_ArriveThrive_Burst_small.png?610\" style=\"float: none;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://www.surfexpo.com/\" target=\"_blank\">SURF EXPO</a><br /></i><i>JANUARY 9–11, 2014<br /></i><i>Orlando, Florida</i></td>\r\n</tr>\r\n</tbody>\r\n</table>\r\n<h3 style=\"text-align: center;\"><span style=\"color: #000000;\"><strong><i><i><br /><br /></i></i></strong></span></h3>\r\n<div style=\"text-align: center;\"><strong><i>PAST EVENTS 2013</i></strong></div>\r\n<div style=\"text-align: center;\"><span style=\"color: #000000;\"> <strong><i><i><br /></i></i></strong></span></div>\r\n<div style=\"text-align: center;\">\r\n<table width=\"100%\">\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: left;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/1456687_665007520198388_1043128366_n_small.jpg?609\" style=\"float: none; display: block; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><i><a href=\"http://wynwoodcontainergarden.com\" target=\"_blank\">WYNWOOD CONTAINER GARDEN (ART BASEL)</a><br /></i><i>DECEMBER 2–10, 2013<br /></i><i>Wynwood, Miami, Florida</i></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: left;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/1813004843_mrf-logo_web_small.png?609\" style=\"float: none; display: block; margin-left: auto; margin-right: auto;\" /></td>\r\n<td style=\"text-align: left;\"><a href=\"http://www.rockersmovement.com/events_detail.php?aid=2\" target=\"_blank\"><i>MIAMI REGGAE FESTIVAL</i></a><i><br /></i><i>NOVEMBER 23, 2013<br />Coral Gables, Miami, Florida</i></td>\r\n</tr>\r\n<tr>\r\n<td></td>\r\n<td></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: left;\"><img alt=\"\" src=\"//cdn.shopify.com/s/files/1/0532/1481/files/Yoga-Journal-Conference-New-York-City_small.jpg?608\" style=\"float: none; display: block; margin-left: auto; margin-right: auto;\" /></td>\r\n<td>\r\n<div>\r\n<div style=\"text-align: left;\"><i><a href=\"http://www.yjevents.com/fl/\" target=\"_blank\">YOGA JOURNAL LIVE</a></i></div>\r\n<div style=\"text-align: left;\"><i></i></div>\r\n<div style=\"text-align: left;\"><i>NOVEMBER 1-3 2013<br /></i><i>Hollywood, Florida</i></div>\r\n</div>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td></td>\r\n<td><i></i></td>\r\n</tr>\r\n</tbody>\r\n</table>\r\n</div>\r\n<div style=\"text-align: center;\"> </div></body></html>";
				view.loadDataWithBaseURL(AppConstant.EVENTS, data, "text/html",
						"UTF-8", null);
				// view.loadData(body_html, "text/html", null);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return pagesModal;
	}
}
