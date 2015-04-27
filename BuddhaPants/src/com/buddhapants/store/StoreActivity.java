package com.buddhapants.store;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.modal.ImageModal;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.util.ConnectionDetector;
import com.buddhapants.util.LogMessage;
import com.buddhapants.webservices.WSAdapter;
import com.squareup.picasso.Picasso;

public class StoreActivity extends Activity {
	GridView gridView;
	GridViewAdapter gridadapter;

	ImageButton backButton, addtoCart;
	String strimage, strtext, p_id;
	ProgressDialog pDialog;
	ImageModal storeModal;
	List<ImageModal> listStoreModal;

	JSONArray mJsonArray;
	JSONObject Image_scr;
	String stringJSon;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		pDialog = new ProgressDialog(this);
		cd = new ConnectionDetector(getApplicationContext());
		new ExecuteStore().execute();
		listStoreModal = new ArrayList<ImageModal>();
		gridView = (GridView) findViewById(R.id.gridviewlist_store);
		backButton = (ImageButton) findViewById(R.id.btn_back);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);
		checkInternet();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				pDialog.setMessage("Loading...");
				pDialog.show();

				new Handler().postDelayed(new Runnable() {

					/*
					 * Showing splash screen with a timer. This will be useful
					 * when you want to show case your app logo / company
					 */

					@Override
					public void run() {
						// This method will be executed once the timer is over
						// Start your app main activity
						pDialog.dismiss();
						final ImageModal storeModal = listStoreModal
								.get(position);
						String Product_id = storeModal.getProduct_id();
						String ID = storeModal.getID();
						String CoverImage = storeModal.getImage();
						Bundle b = new Bundle();
						b.putString("Key", Product_id);

						Intent i = new Intent(StoreActivity.this,
								ProductActivity.class);
						i.putExtras(b);
						i.putExtra("JSON", stringJSon);
						i.putExtra("ID", ID);
						i.putExtra("IMAGE", CoverImage);
						startActivity(i);
					}
				}, 1200);

			}
		});

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
				Intent iAddToCart = new Intent(StoreActivity.this,
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
			LogMessage.showDialog(StoreActivity.this, "No Internet Connection",
					"You don't have internet connection.", "OK");
		}
	}

	class GridViewAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;
		List<ImageModal> listStoreModal;

		public GridViewAdapter(Context mContext, List<ImageModal> listStoreModal) {

			this.mContext = mContext;

			this.listStoreModal = listStoreModal;
//			Log.e("enter", "eneter");
//			Log.e("listStoreModal.size()--------", "" + listStoreModal.size());

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listStoreModal.size();

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
			// String uri = "@drawable/ic_no_image.png";
			// BitmapFactory.Options options = new BitmapFactory.Options();
			//
			// // downsizing image as it throws OutOfMemory Exception for
			// // larger
			// // images
			// options.inSampleSize = 1;
			//
			// Bitmap bitmap = BitmapFactory.decodeFile(title, options);
			// Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100,
			// false);

			Picasso.with(getApplicationContext()).load("" + getImage)
					.into(holder.imageView);

			return convertView;

		}
	}

	class ViewHolder {
		TextView textView;
		ImageView imageView;
	}

	class ExecuteStore extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = WSAdapter.getJSONObject(AppConstant.STORE);
			Log.e("response", "" + response);
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

			Log.e("result", "" + result);
			stringJSon = result;
			Log.e("stringJSon", "" + stringJSon);
			listStoreModal = updateUI(result);

			gridadapter = new GridViewAdapter(StoreActivity.this,
					listStoreModal);
			gridView.setAdapter(gridadapter);
			pDialog.dismiss();
		}

		public List<ImageModal> updateUI(String result) {
			// TODO Auto-generated method stub
			List<ImageModal> listStoreModal = new ArrayList<ImageModal>();

			try {

				JSONObject mJsonObject = new JSONObject(result);
				mJsonArray = mJsonObject.getJSONArray("products");
//				Log.e("mJsonArray", "" + mJsonArray);
//				Log.e("mJsonArray", "" + mJsonArray.length());
				for (int i = 0; i < mJsonArray.length(); i++) {
					JSONObject jsonObject = mJsonArray.getJSONObject(i);

					storeModal = new ImageModal();

					String title = jsonObject.getString("title").replaceAll(
							"[^\\x00-\\x7F]", "");
					String id = jsonObject.getString("id");
//					Log.e("id---------", "" + id.length());

//					Log.e("id---", "" + id);
					if (jsonObject.has("image")) {
						Image_scr = jsonObject.getJSONObject("image");
						String src = Image_scr.getString("src");
						
							storeModal.setImage(src);
						
						

					} else {
//						 String url =
//						 "https://cdn.shopify.com/s/files/1/0532/1481/products/plaid3.jpg?v=1424223031";
//						 storeModal.setImage(url);
						// String uri = "@drawable/ic_no_image.png";
						//
						// int imageResource = getResources().getIdentifier(uri,
						// null, getPackageName());
						//
						// Drawable res = getResources()
						// .getDrawable(imageResource);
						// storeModal.setImage(uri);
					}

					p_id = Image_scr.getString("product_id");
					Log.e("p_id", "" + p_id);

					storeModal.setTitle(title);
					storeModal.setProduct_id(p_id);
					storeModal.setID(id);

					listStoreModal.add(storeModal);

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			return listStoreModal;
		}
	}
}
