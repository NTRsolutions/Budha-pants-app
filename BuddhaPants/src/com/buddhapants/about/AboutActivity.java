package com.buddhapants.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.buddhapants.R;
import com.buddhapants.ui.AddToCartActivity;

public class AboutActivity extends Activity {
	ImageButton backButton, addtoCart;
	ImageView imgArrow;
	TextView txtRachael_website, txt_caroline_website, txt_claudio_website,
			txtRachel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		backButton = (ImageButton) findViewById(R.id.btn_back);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);
		imgArrow = (ImageView) findViewById(R.id.img_arrow);
		txtRachael_website = (TextView) findViewById(R.id.txt_visit);
		txt_caroline_website = (TextView) findViewById(R.id.txt_visit_caroline);
		txt_claudio_website = (TextView) findViewById(R.id.txt_visit_claudio);
		txtRachel = (TextView) findViewById(R.id.txt_name);
		txtRachael_website.setMovementMethod(LinkMovementMethod.getInstance());
		txt_caroline_website
				.setMovementMethod(LinkMovementMethod.getInstance());
		txt_claudio_website.setMovementMethod(LinkMovementMethod.getInstance());
		txtRachel.setMovementMethod(LinkMovementMethod.getInstance());

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
				imgArrow.setVisibility(View.GONE);

				Intent iAddToCart = new Intent(AboutActivity.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
			}
		});

		txtRachel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				String aEmailList[] = { "rachel@buddhpants.com" };
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						aEmailList);
				emailIntent.setType("plain/text");
				startActivity(emailIntent);

			}
		});

	}
}
