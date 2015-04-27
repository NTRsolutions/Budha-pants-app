package com.buddhapants.store;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buddhapants.AppConstant;
import com.buddhapants.R;
import com.buddhapants.database.MyConnection;
import com.buddhapants.modal.ProductsModal;
import com.buddhapants.ui.AddToCartActivity;
import com.buddhapants.util.LogMessage;
import com.squareup.picasso.Picasso;

public class ProductActivity extends Activity {

	// CirclePageIndicator mIndicator;
	private ViewPager _mViewPager;
	LayoutInflater inflater;
	EditText edtQty;
	LayoutInflater infl;
	ImageButton backButton, imgaddtoCart, imgFacebook;
	List<ResolveInfo> activityList;
	TextView txtTitle, txt_product_title, txtProductDescription,
			txtProduct_detaoil, txtProductdetail, txt2, txtPrice,
			txtNotification;
	SpannableString spannableString;
	String key_id, JSONstr, strID, mStrtitle, variant, mString_imagespath,
			variantoption1, variantoption2, imageStr, numtest;
	ProgressDialog pDialog;
	JSONArray mJsonArray, mJson_Array, mJsonArrayImages, mJsonArrayImage;
	List<ProductsModal> listProductsModal;
	Element tagTitle, tagAbout, tagQuote, tagDetail, tagDetails;
	WebView webView;
	JSONObject jsonVariants;
	ArrayList<String> arrayListImages, arrayListData;
	ImageView imageView;
	MainPagerAdapter _adapter;
	Spinner spinner, SpinnerColor;
	ArrayAdapter<String> arrayAdapter, arrAdapterColor;
	List<String> arraySizes, arrayColor;
	MyConnection con;
	Button addToCart;
	int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		con = new MyConnection(this);
		_mViewPager = (ViewPager) findViewById(R.id.viewPager);
		_adapter = new MainPagerAdapter();
		infl = (LayoutInflater) getApplicationContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		Bundle bundle = getIntent().getExtras();

		key_id = bundle.getString("Key");
		strID = bundle.getString("ID");
		JSONstr = bundle.getString("JSON");
		imageStr = bundle.getString("IMAGE");
		Log.e("JSONstr----", "" + JSONstr);
		Log.e("key_id----", "" + key_id);
		Log.e("strID----", "" + strID);
		// Log.e("imageStr----", "" + imageStr);

		arrayListImages = new ArrayList<String>();
		arraySizes = new ArrayList<String>();
		arrayColor = new ArrayList<String>();

		if (arrayListImages.size() != 0) {
			arrayListImages.clear();
		}

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
		// arrayListData = new ArrayList<String>();
		// arrayListData = con.selectData();
		// a = Integer.parseInt(edtQty.getText().toString());

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
				Intent iAddToCart = new Intent(ProductActivity.this,
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
				boolean flag = con.insertData(mStrtitle, variantoption1, 0,
						imageStr, variant, null, strID);

				if (flag) {
					Toast.makeText(getApplicationContext(), "Added to Cart", 0)
							.show();
					Intent iAddToCart = new Intent(ProductActivity.this,
							AddToCartActivity.class);
					startActivity(iAddToCart);
				} else {
					// Toast.makeText(getApplicationContext(), "Data Not save",
					// 1)
					// .show();
				}
				addone();

			}
		});

		updateUI(JSONstr);
	}

	public void addone() {
		// TODO Auto-generated method stub
		txtNotification.setVisibility(View.VISIBLE);
		count++;
		Log.e("numtest", "" + count);
		txtNotification.setText(String.valueOf(count));
	}

	private void updateUI(String JSONstr) {
		// TODO Auto-generated method stub

		try {
			JSONObject mJsonObject = new JSONObject(JSONstr);
			mJsonArray = mJsonObject.getJSONArray("products");
			Log.e("JSONstr------------------", "" + JSONstr);

			for (int i = 0; i < mJsonArray.length(); i++) {
				JSONObject jsonObject = mJsonArray.getJSONObject(i);
				String id = jsonObject.getString("id");
				String body_html = jsonObject.getString("body_html")
						.replaceAll("[^\\x00-\\x7F]", "");

				if (strID.matches(id)) {
					if (jsonObject.has("images")) {
						mJsonArrayImages = jsonObject.getJSONArray("images");
						for (int z = 0; z < mJsonArrayImages.length(); z++) {
							JSONObject JsonObject = mJsonArrayImages
									.getJSONObject(z);
							mString_imagespath = JsonObject.getString("src");
							arrayListImages.add(mString_imagespath);
						}
					}
					mStrtitle = jsonObject.getString("title");
					mJson_Array = jsonObject.getJSONArray("variants");
					for (int j = 0; j < mJson_Array.length(); j++) {
						JSONObject json_Object = mJson_Array.getJSONObject(j);
						variant = json_Object.getString("price");

						variantoption1 = json_Object.getString("option1");
						if (variantoption1.contains("null")) {
							spinner.setVisibility(View.GONE);
						} else {
							spinner.setVisibility(View.VISIBLE);
							arraySizes.add(variantoption1);
						}
						variantoption2 = json_Object.getString("option2");
						if (variantoption2.contains("null")) {
							SpinnerColor.setVisibility(View.GONE);
						} else {
							SpinnerColor.setVisibility(View.VISIBLE);
							arrayColor.add(variantoption2);
						}

						// Log.e("variantoption1---", "" + variantoption1);
						// Log.e("variantoption2---", "" + variantoption2);
					}
					// mJsonArrayImage = jsonObject.getJSONArray("image");
					// for (int k = 0; k < mJsonArrayImage.length(); k++) {
					// JSONObject json_Object = mJson_Array.getJSONObject(k);
					// imageStr = json_Object.getString("src");
					// Log.e("imageStr__", ""+imageStr);
					//
					// }

					webView.loadDataWithBaseURL(AppConstant.STORE, body_html,
							"text/html", "UTF-8", null);

				}

			}

		} catch (Exception e) {
			// // TODO: handle exception
		}
		// Log.e("arrayListImages: ", "" + arrayListImages.size());
		// Log.e("arrayListImages full: ", "" + arrayListImages);

		txt_product_title.setText(mStrtitle);

		txtPrice.setText("$" + " " + variant);
		// String str = variant.toString().trim();
		// int n=Integer.parseInt(str);
		// int n1 =n+n;
		// Log.e("total price", "" +n1 );

		// arraySizes.add(variantoption1);

		arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.custom_spinner_item, arraySizes);
		arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_item);

		spinner.setAdapter(arrayAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String selected = parent.getItemAtPosition(0).toString();
				if (selected == null) {
//					Toast.makeText(getApplicationContext(), "NOT SELECTED", 0)
//							.show();
//				} else {
//					Toast.makeText(getApplicationContext(), "SELECTED", 0)
//							.show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				LogMessage.showDialog(ProductActivity.this, null,
						"Please Select a size", "OK");
			}
		});

		arrAdapterColor = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.custom_spinner_item, arrayColor);
		arrAdapterColor.setDropDownViewResource(R.layout.custom_spinner_item);
		SpinnerColor.setAdapter(arrAdapterColor);

		Log.e("spinner---", "" + spinner);

		if (arrayListImages.size() != 0) {

			for (int s = 0; s < arrayListImages.size(); s++) {
				LinearLayout v1 = (LinearLayout) infl.inflate(
						R.layout.custom_image, null);
				imageView = (ImageView) v1.findViewById(R.id.img1);
				Picasso.with(getApplicationContext())
						.load(arrayListImages.get(s)).into(imageView);

				// imageView.setImageResource(arrayListImages.get(s));

				addView(v1);

			}
			_mViewPager.setAdapter(_adapter);

		} else {
			
		}

	}

	public void addView(View newPage) {
		int pageIndex = _adapter.addView(newPage);

		_mViewPager.setCurrentItem(pageIndex, true);
	}

}
