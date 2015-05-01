package com.buddhapants.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buddhapants.R;
import com.buddhapants.database.MyConnection;
import com.buddhapants.store.CheckoutActivity;
import com.squareup.picasso.Picasso;

public class AddToCartActivity extends Activity {
	ListView listView;
	ListAdapter adapterlist;
	Button btnCheckout, btnRemove;
	EditText editText;
	MyConnection connection;
	ArrayList<String> arrayList;
	ArrayList<String> arrayList_title;
	ArrayList<String> arrayList_size;
	ArrayList<String> arrayList_price;
	ArrayList<String> arrayList_qty;
	ArrayList<String> arrayList_image;
	ArrayList<String> arrayList_id;
	private ArrayList<String> arrayList_total_price;
	ImageView imgProduct;
	TextView txtTitle, txtSize, txtPrice, txtNoData;
	EditText edtQuantity;
	Cursor mCursor;
	RelativeLayout relativeLayout_parent;
	double subtotal = 0;
	private TextView subtotalText;
	private int cartSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addtocart);
		connection = new MyConnection(this);
		listView = (ListView) findViewById(R.id.listview_cart_items);
		btnCheckout = (Button) findViewById(R.id.btnCheckOut);
		txtNoData = (TextView) findViewById(R.id.txt_no_item);
		subtotalText = (TextView) findViewById(R.id.txt_price);
		relativeLayout_parent = (RelativeLayout) findViewById(R.id.relative_parent);

		arrayList = new ArrayList<String>();
		arrayList_title = new ArrayList<String>();
		arrayList_size = new ArrayList<String>();
		arrayList_price = new ArrayList<String>();
		arrayList_total_price = new ArrayList<String>();
		arrayList_qty = new ArrayList<String>();
		arrayList_image = new ArrayList<String>();
		arrayList_id = new ArrayList<String>();

		initCartItem();

		setUpButtonListener();

		setUpTotalPrice();

	}

	private void initCartItem() {
		try {
			mCursor = connection.selectData();

			// mCursor.moveToFirst();
			if (mCursor.getCount() != 0) {

				arrayList_title.clear();
				do {

					arrayList_title.add(mCursor.getString(0).trim());
					arrayList_size.add(mCursor.getString(1).trim());
					arrayList_price.add(mCursor.getString(2).trim());
					arrayList_total_price.add(mCursor.getString(3).trim());
					arrayList_qty.add(mCursor.getString(4).trim());
					arrayList_image.add(mCursor.getString(5).trim());
					arrayList_id.add(mCursor.getString(6).trim());

				} while (mCursor.moveToNext());

				mCursor.close();
				// connection.close();

				adapterlist = new ListAdapter(arrayList_title, arrayList_size,
						arrayList_price, arrayList_total_price, arrayList_qty,
						arrayList_image, arrayList_id, arrayList);
				listView.setAdapter(adapterlist);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpButtonListener() {
		relativeLayout_parent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnCheckout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cartSize > 0) {
					Intent icheckout = new Intent(AddToCartActivity.this,
							CheckoutActivity.class);
					icheckout.putExtra("subTotal", subtotal);
					startActivity(icheckout);
					finish();
				} else {
					Toast.makeText(AddToCartActivity.this, "Cart is Empty",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void setUpTotalPrice() {
		for (int i = 0; i < arrayList_total_price.size(); i++) {
			subtotal = subtotal
					+ Double.parseDouble(arrayList_total_price.get(i));
		}
		cartSize = arrayList_price.size();
		subtotalText.setText("$" + subtotal);

	}

	class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		ArrayList<String> arrayList;
		ArrayList<String> arrayList_title;
		ArrayList<String> arrayList_size;
		ArrayList<String> arrayList_price;
		ArrayList<String> arrayList_total_price;
		ArrayList<String> arrayList_qty;
		ArrayList<String> arrayList_image;
		ArrayList<String> arrayList_id;

		public ListAdapter(ArrayList<String> arrayList_title,
				ArrayList<String> arrayList_size,
				ArrayList<String> arrayList_price,
				ArrayList<String> arrayList_total_price,
				ArrayList<String> arrayList_qty,
				ArrayList<String> arrayList_image,
				ArrayList<String> arrayList_id, ArrayList<String> arrayList) {
			super();
			this.arrayList_title = arrayList_title;
			this.arrayList_size = arrayList_size;
			this.arrayList_price = arrayList_price;
			this.arrayList_total_price = arrayList_total_price;
			this.arrayList_qty = arrayList_qty;
			this.arrayList_image = arrayList_image;
			this.arrayList_id = arrayList_id;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList_title.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			if (inflater == null) {
				inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			}
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.cart_items, null);

				holder = new ViewHolder();
				holder.imgProduct = (ImageView) convertView
						.findViewById(R.id.img_selected_cart);
				holder.txtTitle = (TextView) convertView
						.findViewById(R.id.txt_cart_title);
				holder.txtPrice = (TextView) convertView
						.findViewById(R.id.txt_cart_price);
				// holder.txtTotalPrice = (TextView) convertView
				// .findViewById(R.id.txt_cart_total_price);
				holder.txtQuant = (TextView) convertView
						.findViewById(R.id.txt_cart_quantity);
				holder.txtSize = (TextView) convertView
						.findViewById(R.id.txt_cart_item_size);
				holder.btnRemove = (Button) convertView
						.findViewById(R.id.buttonRemove);
				// edtQuantity = (EditText) rootview.findViewById(R.id.edtQty);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txtTitle.setText(arrayList_title.get(position));
			holder.txtPrice.setText("$" + arrayList_total_price.get(position));
			// holder.txtTotalPrice.setText("Total Price : "
			// + arrayList_total_price.get(position));
			holder.txtSize.setText("(" + arrayList_size.get(position) + ")");
			holder.txtQuant.setText("Qty : " + arrayList_qty.get(position));
			Picasso.with(getApplicationContext())
					.load(arrayList_image.get(position))
					.into(holder.imgProduct);
			// edtQuantity.setText(arrayList_qty.get(position));

			holder.btnRemove.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("arrayList_id: ", "" + arrayList_id.get(position));
					connection.Delete_Row(" " + arrayList_id.get(position));

					arrayList_title.remove(arrayList_title.get(position));
					removeFromSubTotal(arrayList_total_price.get(position));
					notifyDataSetChanged();
				}
			});

			return convertView;

		}

		protected void removeFromSubTotal(String amtD) {
			subtotal = subtotal - Double.parseDouble(amtD);
			cartSize = cartSize - 1;
			if (subtotal <= 0.0) {
				subtotalText.setText("$" + 0.0);
			} else {
				subtotalText.setText("$" + subtotal);

			}

		}
	}

	class ViewHolder {
		TextView txtTitle, txtPrice, txtSize, txtQuant, txtTotalPrice;
		ImageView imgProduct;
		Button btnRemove;
	}

}