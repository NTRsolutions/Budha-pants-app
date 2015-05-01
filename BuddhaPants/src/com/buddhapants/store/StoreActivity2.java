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
import com.buddhapants.util.JsonParser;
import com.buddhapants.webservices.WSAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class StoreActivity2 extends Activity {
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
	Boolean isInternetPresent = false;
	private TextView noProductText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		pDialog = new ProgressDialog(this);
		listStoreModal = new ArrayList<ImageModal>();
		gridView = (GridView) findViewById(R.id.gridviewlist_store);
		noProductText = (TextView) findViewById(R.id.no_product);
		backButton = (ImageButton) findViewById(R.id.btn_back);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);

		setupButtonListener();

		new ExecuteStore().execute();

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

				Intent i = new Intent(StoreActivity2.this,
						ProductActivity2.class);
				i.putExtra("product_id", producId);
				i.putExtra("product_image", image);
				startActivity(i);
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
				Intent iAddToCart = new Intent(StoreActivity2.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
			}
		});

	}

	class GridViewAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;
		List<ImageModal> listStoreModal;
		DisplayImageOptions options;

		public GridViewAdapter(Context mContext, List<ImageModal> listStoreModal) {

			this.mContext = mContext;
			this.listStoreModal = listStoreModal;
			options = setupImageLoader();
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
			final ViewHolder holder;

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

			// if (getImage != null && !getImage.equals("")) {
			// Picasso.with(StoreActivity2.this)
			// .load(String.valueOf(getImage))
			// .error(R.drawable.ic_no_image).into(holder.imageView);
			// } else {
			// Picasso.with(StoreActivity2.this).load(R.drawable.ic_no_image)
			// .into(holder.imageView);
			//
			// }
			ImageLoader.getInstance().displayImage(getImage, holder.imageView,
					options);

			return convertView;

		}
	}

	class ViewHolder {
		TextView textView;
		ImageView imageView;
	}

	private DisplayImageOptions setupImageLoader() {
		return new DisplayImageOptions.Builder().cacheInMemory(true)
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.no_image_icon)
				.showImageOnFail(R.drawable.no_image_icon).cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1)).build();

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
			pDialog.dismiss();

			listStoreModal = JsonParser.getProductList(result);

			if (listStoreModal != null && listStoreModal.size() != 0) {
				gridadapter = new GridViewAdapter(StoreActivity2.this,
						listStoreModal);
				gridView.setAdapter(gridadapter);
				noProductText.setVisibility(View.GONE);
			} else {
				gridView.setVisibility(View.GONE);
			}
		}

	}
}
