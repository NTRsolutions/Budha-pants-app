package com.buddhapants.store;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.database.MyConnection;
import com.buddhapants.modal.ProductsModal;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.util.JsonParser;
import com.buddhapants.util.LogMessage;
import com.buddhapants.webservices.WSAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ProductActivity2 extends FragmentActivity {

	// CirclePageIndicator mIndicator;

	EditText edtQty;
	ImageButton backButton, imgaddtoCart, imgFacebook;
	TextView txtTitle, txt_product_title, txtProductDescription,
			txtProduct_detaoil, txtProductdetail, txt2, txtPrice,
			txtNotification;
	SpannableString spannableString;
	String productKey, JSONstr, strID, mStrtitle, variant, mString_imagespath,
			variantoption1, variantoption2, imageStr, numtest;
	Button addToCart;
	ImageView imageView;
	ProgressDialog pDialog;

	List<ProductsModal> listProductsModal;

	WebView webView;
	Spinner spinner, SpinnerColor;
	ArrayAdapter<String> arrayAdapter, arrAdapterColor;
	MyConnection con;

	Element tagTitle, tagAbout, tagQuote, tagDetail, tagDetails;

	String title, price, htmlContent, image;
	List<String> arraySizes, arrayColor, arrayListImages;
	ArrayList<String> arrayListData;

	String valueSizeFromSpinner, valueColorFromSpinner;
	int quantity;
	ViewPager _mViewPager;
	ScreenSlidePagerAdapter mPagerAdapter;

	static int cartSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		con = new MyConnection(this);
		_mViewPager = (ViewPager) findViewById(R.id.viewPager);

		productKey = getIntent().getStringExtra("product_id");
		arrayListImages = new ArrayList<String>();
		arraySizes = new ArrayList<String>();
		arrayColor = new ArrayList<String>();

		spinner = (Spinner) findViewById(R.id.spinnerSize);
		SpinnerColor = (Spinner) findViewById(R.id.spinnerColor);
		backButton = (ImageButton) findViewById(R.id.btn_back);
		imgaddtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);
		imgFacebook = (ImageButton) findViewById(R.id.imgbtn_facebook);
		txtTitle = (TextView) findViewById(R.id.textViewTitle);
		txt_product_title = (TextView) findViewById(R.id.txt_product_title);
		txtPrice = (TextView) findViewById(R.id.txt_product_price);
		edtQty = (EditText) findViewById(R.id.edtQty);
		webView = (WebView) findViewById(R.id.webView);
		imageView = (ImageView) findViewById(R.id.img1);
		addToCart = (Button) findViewById(R.id.btnAddToCart);
		txtNotification = (TextView) findViewById(R.id.txt_notification);

		setupButtonListener();

		getProductDetailtask();

		// updateUI(JSONstr);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Cursor curser = con.selectData();
		cartSize = curser.getCount();

		if (cartSize > 0) {
			txtNotification.setVisibility(View.VISIBLE);
			txtNotification.setText(String.valueOf(cartSize));
		} else {
			txtNotification.setVisibility(View.GONE);
		}

	}

	private DisplayImageOptions setupImageLoader() {
		return new DisplayImageOptions.Builder().cacheInMemory(true)
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.no_image_icon)
				.showImageOnFail(R.drawable.no_image_icon).cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1)).build();

	}

	private void setupButtonListener() {

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		imgaddtoCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddToCart = new Intent(ProductActivity2.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
				txtNotification.setVisibility(View.GONE);
			}
		});
		imgFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(getApplicationContext(), "FACEBOOK",
				// Toast.LENGTH_SHORT).show();
				// Intent intent = new
				// Intent("android.intent.category.LAUNCHER");
				// intent.setClassName("com.facebook.katana",
				// "com.facebook.katana.LoginActivity");
				// startActivity(intent);

				// ShareLinkContent content = new ShareLinkContent.Builder()
				// .setContentUrl(
				// Uri.parse("https://developers.facebook.com"))
				// .build();
			}
		});
		addToCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validate()) {

					DecimalFormat df = new DecimalFormat("#.00");
					double priceValue = Double.parseDouble(price);
					double subtotal_price = quantity * priceValue;

					boolean flag = con.insertData(title, valueSizeFromSpinner,
							valueColorFromSpinner, quantity, image,
							df.format(priceValue), df.format(subtotal_price),
							productKey);

					if (flag) {
						addone();
						Toast.makeText(getApplicationContext(),
								"Added to Cart", 0).show();
						Intent iAddToCart = new Intent(ProductActivity2.this,
								AddToCartActivity.class);
						startActivity(iAddToCart);
					} else {
						// Toast.makeText(getApplicationContext(),
						// "Data Not save",
						// 1)
						// .show();
					}
				}

			}
		});

	}

	protected boolean validate() {
		String quantityString = edtQty.getText().toString();
		if (valueSizeFromSpinner.toLowerCase(Locale.getDefault()).equals(
				"select size")) {
			Toast.makeText(ProductActivity2.this, "Please select size ",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (arrayColor != null && arrayColor.size() > 1) {
			if (valueColorFromSpinner.toLowerCase(Locale.getDefault()).equals(
					"select color")) {
				Toast.makeText(ProductActivity2.this, "Please color size ",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		} else {
			valueColorFromSpinner="";
		}
		if (quantityString.trim().equals("")) {
			Toast.makeText(ProductActivity2.this, "Please enter quantity ",
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			quantity = Integer.parseInt(quantityString);
		}

		return true;
	}

	private void getProductDetailtask() {

		new ExecuteProductDetailTask().execute(productKey);

	}

	public void addone() {
		Cursor curser = con.selectData();
		cartSize = curser.getCount();

		if (cartSize > 0) {
			txtNotification.setVisibility(View.VISIBLE);
			txtNotification.setText(String.valueOf(cartSize));
		} else {
			txtNotification.setVisibility(View.GONE);
		}
		// txtNotification.setVisibility(View.VISIBLE);
		// cartSize = cartSize + 1;
		// txtNotification.setText(String.valueOf(cartSize));
	}

	class ExecuteProductDetailTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			String url = AppConstant.PRODUCT_DETAIL + params[0] + ".json";
			String response = WSAdapter.getJSONObject(url);
			// String response = WSAdapter.getJSONObject(AppConstant.STORE);
			return response;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ProductActivity2.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			pDialog.dismiss();
			if (result != null) {

				parseResponse(result);
				// updateUI(result);
			}

		}

	}

	public void parseResponse(String result) {

		try {
			JSONObject jsonMainObject = new JSONObject(result);
			JSONObject jsonProduct = jsonMainObject.getJSONObject("product");
			String id = jsonProduct.getString("id");
			title = jsonProduct.getString("title");
			htmlContent = jsonProduct.getString("body_html").replaceAll(
					"[^\\x00-\\x7F]", "");

			JSONArray jsonVarianArrary = jsonProduct.getJSONArray("variants");
			JSONArray jsonImagesArrary = jsonProduct.getJSONArray("images");

			arraySizes = JsonParser.getVariantSize(jsonVarianArrary);
			arrayColor = JsonParser.getVariantColor(jsonVarianArrary);
			price = JsonParser.getPrice(jsonVarianArrary);
			arrayListImages = JsonParser.getGalleryImages(jsonImagesArrary);

			if (jsonProduct.has("image")) {
				JSONObject jImageObject = jsonProduct.getJSONObject("image");
				image = jImageObject.getString("src");
			} else {
				image = "";
			}
			if (htmlContent.equals("null")) {
				htmlContent = "";
			}

			setupSpinner();
			setupViewPager();
			setUpOtherUi();

		} catch (Exception e) {
			Log.e("Exception", "" + e.getCause());
		}

	}

	private void setUpOtherUi() {
		txt_product_title.setText(title);

		txtPrice.setText("$" + " " + price);
		webView.loadDataWithBaseURL(AppConstant.STORE, htmlContent,
				"text/html", "UTF-8", null);

	}

	private void setupViewPager() {

		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		_mViewPager.setAdapter(mPagerAdapter);

	}

	private void setupSpinner() {

		if (arraySizes != null && arraySizes.size() != 0) {
			arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.custom_spinner_item, arraySizes);
			arrayAdapter
					.setDropDownViewResource(R.layout.custom_spinner_item_dropdown);

			spinner.setAdapter(arrayAdapter);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					String selected = parent.getItemAtPosition(position)
							.toString();
					valueSizeFromSpinner = selected;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		} else {
			spinner.setVisibility(View.GONE);
		}

		if (arrayColor != null && arrayColor.size() != 1) {
			arrAdapterColor = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.custom_spinner_item, arrayColor);
			arrAdapterColor
					.setDropDownViewResource(R.layout.custom_spinner_item_dropdown);
			SpinnerColor.setAdapter(arrAdapterColor);
			SpinnerColor
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							String selected = parent
									.getItemAtPosition(position).toString();
							valueColorFromSpinner = selected;
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {

						}
					});
		} else {
			SpinnerColor.setVisibility(View.GONE);
		}

	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		DisplayImageOptions options;

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
			options = setupImageLoader();
		}

		@Override
		public Fragment getItem(int position) {

			return ImageHolderSlideFragment.create(position,
					arrayListImages.get(position), options);
		}

		@Override
		public int getCount() {
			return arrayListImages.size();
		}

	}
}
