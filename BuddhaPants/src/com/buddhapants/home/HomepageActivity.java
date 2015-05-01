package com.buddhapants.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.modal.ImageModal;
import com.buddhapants.modal.PagesModal;
import com.buddhapants.store.ProductActivity2;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.ui.GridViewScrollable;
import com.buddhapants.util.ConnectionDetector;
import com.buddhapants.util.LogMessage;
import com.buddhapants.webservices.WSAdapter;
import com.squareup.picasso.Picasso;

public class HomepageActivity extends Activity {

	GridViewScrollable gridView;
	GridViewAdapter gridadapter;
	List<ImageModal> listStoreModal;

	ImageButton btn_nav, backButton, addtoCart;
	PagesModal pagesModal;
	TextView txtAbout, txtTitle, txtQuote;
	JSONArray jsonArray;
	ProgressDialog pDialog;
	Element tagTitle, tagAbout, tagQuote;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	ImageModal storeModal;

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

		gridView = (GridViewScrollable) findViewById(R.id.gridviewfeatured);
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
				// finish();

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

		setupButtonListener();
		initGridFeatureProduct();

	}

	private void setupButtonListener() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				final ImageModal storeModal = listStoreModal.get(position);
				String producId = storeModal.getProduct_id();
				String image = storeModal.getImage();

				Intent i = new Intent(HomepageActivity.this,
						ProductActivity2.class);
				i.putExtra("product_id", producId);
				i.putExtra("product_image", image);
				startActivity(i);
			}
		});
	}

	private void initGridFeatureProduct() {
		new ExecuteStore().execute();

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

	class ExecuteStore extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = WSAdapter.getJSONObject(AppConstant.STORE);
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

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			listStoreModal = updateUI(result);

			gridadapter = new GridViewAdapter(HomepageActivity.this,
					listStoreModal);
			gridView.setAdapter(gridadapter);
			pDialog.dismiss();
		}

		public List<ImageModal> updateUI(String result) {
			// TODO Auto-generated method stub
			List<ImageModal> listStoreModal = new ArrayList<ImageModal>();

			try {

				JSONObject mJsonObject = new JSONObject(result);
				JSONObject Image_scr = null;
				JSONArray mJsonArray = mJsonObject.getJSONArray("products");
				for (int i = 0; i < mJsonArray.length(); i++) {
					JSONObject jsonObject = mJsonArray.getJSONObject(i);

					ImageModal storeModal = new ImageModal();

					String title = jsonObject.getString("title").replaceAll(
							"[^\\x00-\\x7F]", "");
					String id = jsonObject.getString("id");
					if (jsonObject.has("image")) {
						Image_scr = jsonObject.getJSONObject("image");
						String src = Image_scr.getString("src");

						storeModal.setImage(src);

					}

					String p_id = Image_scr.getString("product_id");

					storeModal.setTitle(title);
					storeModal.setProduct_id(p_id);
					storeModal.setID(id);

					listStoreModal.add(storeModal);
					if (listStoreModal.size() == 5) {
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			return listStoreModal;
		}
	}

	public PagesModal updateUI(String result) {
		// TODO Auto-generated method stub

		try {
			JSONObject mJsonObject = new JSONObject(result)
					.getJSONObject("page");

			pagesModal = new PagesModal();

			String author = mJsonObject.getString("author");
			String body_html = mJsonObject.getString("body_html").replaceAll(
					"[^\\x00-\\x7F]", "");
			String created_at = mJsonObject.getString("created_at");
			String published_at = mJsonObject.getString("published_at");
			String template_suffix = mJsonObject.getString("template_suffix");
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

			Document doc = Jsoup.parse(body_html);
			tagTitle = doc.select("h1").first();
			String tagAbout = doc.select("p").first().text().toString();
			String tagQuote = doc.select("p").last().text().toString();

			txtTitle.setText(Html.fromHtml(tagTitle.toString()));
			txtAbout.setText(tagAbout);
			txtQuote.setText(tagQuote);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pagesModal;
	}

	class GridViewAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;
		List<ImageModal> listStoreModal;

		public GridViewAdapter(Context mContext, List<ImageModal> listStoreModal) {

			this.mContext = mContext;
			this.listStoreModal = listStoreModal;
			// Log.e("enter", "eneter");
			// Log.e("listStoreModal.size()--------", "" +
			// listStoreModal.size());

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listStoreModal.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			if (inflater == null) {
				inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.activity_gridview_items, null);

				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.txt_titles);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.gridview_images);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			storeModal = listStoreModal.get(position);
			String title = storeModal.getTitle();

			holder.textView.setText(title);
			String getImage = storeModal.getImage();

			if (getImage != null && !getImage.equals("")) {
				Picasso.with(HomepageActivity.this)
						.load(String.valueOf(getImage))
						.error(R.drawable.ic_no_image).into(holder.imageView);
			} else {
				Picasso.with(HomepageActivity.this)
						.load(R.drawable.ic_no_image).into(holder.imageView);

			}

			return convertView;

		}
	}

	class ViewHolder {
		TextView textView;
		ImageView imageView;
	}
}
