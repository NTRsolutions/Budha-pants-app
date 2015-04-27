package com.buddhapants.wholesale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.buddhapants.R;
import com.buddhapants.ui.AddToCartActivity;

public class WholesaleActivity extends Activity {
	ImageButton backButton, addtoCart;
	TextView txtName, txtEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wholesale);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);
		backButton = (ImageButton) findViewById(R.id.btn_back);
		// txtName = (TextView) findViewById(R.id.txt_name);
		// txtEmail = (TextView) findViewById(R.id.txt_email);
		//
		// String strName = "Name*";
		// String strEmail = "Email*";
		// // String strColor = "*";
		//
		//
		// SpannableStringBuilder builder = new SpannableStringBuilder();
		// builder.append(strName);
		//
		// int istart = builder.length();
		// // builder.append(strColor);
		// int iend = builder.length();
		//
		// builder.setSpan(new ForegroundColorSpan(color.color_login), istart,
		// iend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//
		// SpannableStringBuilder builder2 = new SpannableStringBuilder();
		// builder2.append(strEmail);
		//
		//
		// int start = builder.length();
		// // builder2.append(strColor);
		// int end = builder.length();
		//
		// builder2.setSpan(new ForegroundColorSpan(color.color_login), start,
		// end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//
		// txtName.setText(builder);
		// txtEmail.setText(builder2);

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
				Intent iAddToCart = new Intent(WholesaleActivity.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
			}
		});
	}

}
