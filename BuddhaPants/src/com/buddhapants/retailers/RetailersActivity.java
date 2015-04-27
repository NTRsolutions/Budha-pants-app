package com.buddhapants.retailers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.buddhapants.R;
import com.buddhapants.modal.PagesModal;
import com.buddhapants.ui.AddToCartActivity;

public class RetailersActivity extends Activity {
	TextView txt_calforniaaddress, txt_canadaAddress, txt_floridaAddress,
			txt_we, txt_pulse, txt_ambu, txt_hawaii, txt_maine, txt_seattle;
	SpannableString spannableString, spannableString2, spannableString3,
			spannableString4, spannableString5, spannableString6,
			spannableString7, spannableString8, spannableString9;
	ImageButton backButton, addtoCart;
	ProgressDialog pDialog;
	PagesModal pagesModal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retailers);
		spannableString = new SpannableString("Mount Madonna Center\n"
				+ "445 Summit Road\n" + "Watsonville, CA 95076\n"
				+ "mountmadonna.org\n" + "408.846.4064 \n");
		spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, 0);
		spannableString.setSpan(new URLSpan("http:\\mountmadonna.org"), 59, 76,
				0);
		spannableString
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						59, 76, 0);

		spannableString.setSpan(new UnderlineSpan(), 76, 89, 0);

		spannableString2 = new SpannableString("Precious Earth\n"
				+ "1213 15th Ave\n" + "Regina, SK S4P 0Y8\n"
				+ "preciousearth.ca\n" + "306.205.6959\n");
		spannableString2.setSpan(new StyleSpan(Typeface.BOLD), 0, 14, 0);
		spannableString2.setSpan(new URLSpan("http://preciousearth.ca"), 47,
				64, 0);
		spannableString2
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						47, 64, 0);

		spannableString2.setSpan(new UnderlineSpan(), 64, 77, 0);

		spannableString3 = new SpannableString("DK's Style Hut\n"
				+ "8347 Overseas Hwy.\n" + "Marathon, Florida 33050\n"
				+ "DKsstylehut.com\n" + "305.440.2738\n");
		spannableString3.setSpan(new StyleSpan(Typeface.BOLD), 0, 14, 0);
		spannableString3.setSpan(new URLSpan("http://dksstylehut.com/"), 58,
				73, 0);
		spannableString3
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						58, 73, 0);
		spannableString3.setSpan(new UnderlineSpan(), 73, 86, 0);

		spannableString4 = new SpannableString("138 W. Granada Blvd\n"
				+ "Ormond Beach, FL 32174\n" + "weareyoga.com\n"
				+ "386.677.9642\n");
		spannableString4.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, 0);
		spannableString4.setSpan(new URLSpan("http://www.weareyoga.com/"), 43,
				57, 0);
		spannableString4
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						43, 57, 0);
		spannableString4.setSpan(new UnderlineSpan(), 57, 69, 0);

		spannableString5 = new SpannableString("3447 NE 163rd Street\n"
				+ "North Miami Beach, FL 33160\n" + "Pulse163.com\n"
				+ "305.904.6444\n");
		spannableString5.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, 0);
		spannableString5
				.setSpan(new URLSpan("http://pulse163.com/"), 49, 61, 0);
		spannableString5
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						49, 61, 0);
		spannableString5.setSpan(new UnderlineSpan(), 61, 74, 0);

		spannableString6 = new SpannableString(
				"5400 South Seas Plantation Road\n" + "Captiva, FL 33924\n"
						+ "AmbuYoga.com\n" + "239.314.9642\n");

		spannableString6.setSpan(new StyleSpan(Typeface.BOLD), 0, 31, 0);
		spannableString6.setSpan(new URLSpan("http://ambuyoga.com"), 49, 62, 0);
		spannableString6
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						49, 62, 0);
		spannableString6.setSpan(new UnderlineSpan(), 62, 75, 0);

		spannableString7 = new SpannableString("Noelani Studios\n"
				+ "66-437 Kamehameha Highway\n" + "Haleiwa, HI 96712\n"
				+ "Noelanistudios.com\n" + "808.389.3709 \n");

		spannableString7.setSpan(new StyleSpan(Typeface.BOLD), 0, 14, 0);
		spannableString7.setSpan(new URLSpan("http://www.noelanistudios.com/"),
				59, 78, 0);
		spannableString7
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						59, 78, 0);
		spannableString7.setSpan(new UnderlineSpan(), 78, 91, 0);

		spannableString8 = new SpannableString("Niraj Yoga\n"
				+ "648 Congress Street\n" + "Portland, Maine\n"
				+ "207-318-1940\n");

		spannableString8.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, 0);
		spannableString8.setSpan(new UnderlineSpan(), 47, 60, 0);

		spannableString9 = new SpannableString("Center for Yoga & Health\n"
				+ "5340 Ballard Ave NW\n" + "Seattle, Washington 98107\n"
				+ "KulaMovement.com\n" + "206.972.2999\n");

		spannableString9.setSpan(new StyleSpan(Typeface.BOLD), 0, 23, 0);
		spannableString9.setSpan(new URLSpan("http://www.kulamovement.com"),
				70, 87, 0);
		spannableString9
				.setSpan(new ForegroundColorSpan(Color.parseColor("#8D081B")),
						70, 87, 0);
		spannableString9.setSpan(new UnderlineSpan(), 87, 100, 0);

		txt_calforniaaddress = (TextView) findViewById(R.id.txt_address);
		txt_canadaAddress = (TextView) findViewById(R.id.txt_address_canada);
		txt_floridaAddress = (TextView) findViewById(R.id.txt_address_florida);
		txt_we = (TextView) findViewById(R.id.txt_address_we);
		txt_pulse = (TextView) findViewById(R.id.txt_address_pulse);
		txt_ambu = (TextView) findViewById(R.id.txt_address_ambu);
		txt_hawaii = (TextView) findViewById(R.id.txt_address_hawaii);
		txt_maine = (TextView) findViewById(R.id.txt_address_maine);
		txt_seattle = (TextView) findViewById(R.id.txt_address_seattle);

		txt_calforniaaddress
				.setMovementMethod(LinkMovementMethod.getInstance());
		txt_calforniaaddress.setText(spannableString);

		txt_canadaAddress.setMovementMethod(LinkMovementMethod.getInstance());
		txt_canadaAddress.setText(spannableString2);

		txt_floridaAddress.setMovementMethod(LinkMovementMethod.getInstance());
		txt_floridaAddress.setText(spannableString3);

		txt_we.setMovementMethod(LinkMovementMethod.getInstance());
		txt_we.setText(spannableString4);

		txt_pulse.setMovementMethod(LinkMovementMethod.getInstance());
		txt_pulse.setText(spannableString5);

		txt_ambu.setMovementMethod(LinkMovementMethod.getInstance());
		txt_ambu.setText(spannableString6);

		txt_hawaii.setMovementMethod(LinkMovementMethod.getInstance());
		txt_hawaii.setText(spannableString7);

		txt_maine.setMovementMethod(LinkMovementMethod.getInstance());
		txt_maine.setText(spannableString8);

		txt_seattle.setMovementMethod(LinkMovementMethod.getInstance());
		txt_seattle.setText(spannableString9);

		backButton = (ImageButton) findViewById(R.id.btn_back);
		addtoCart = (ImageButton) findViewById(R.id.btn_addTo_cart);
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
				Intent iAddToCart = new Intent(RetailersActivity.this,
						AddToCartActivity.class);
				startActivity(iAddToCart);
			}
		});

	}
}
