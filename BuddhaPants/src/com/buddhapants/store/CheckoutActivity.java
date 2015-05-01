package com.buddhapants.store;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.buddhapants.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class CheckoutActivity extends Activity {
	ImageButton buttonback;

	private Button btnPay;

	private Button buttonContinue;
	// set the environment for production/sandbox/no netowrk
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

	private static final String CONFIG_CLIENT_ID = "Ab_mDQ__q0Gsq9MWvT83n7ZhnJ9IHp2uVKe3xG9uxPWpADD1AS2oXliIZtVOQ4puolQY97Gok08ZC7AI";

	private static final int REQUEST_PAYPAL_PAYMENT = 1;

	double subTotal;

	private EditText editFirstName;
	private EditText editLastName;
	private EditText editCompany;
	private EditText editAddress;
	private EditText editSuite;
	private EditText editCity;
	private EditText editCountry;
	private EditText editState;
	private EditText editPostal;
	private EditText editNum;

	private String firstName, lastName, company, address, suite, city, country,
			state, postal, num;

	private static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT)
			.clientId(CONFIG_CLIENT_ID)
			// The following are only used in PayPalFuturePaymentActivity.
			.merchantName("Buddha Pants")
			.merchantPrivacyPolicyUri(
					Uri.parse("http://www.buddhapants.com/pages/about-buddha-pants"))
			.merchantUserAgreementUri(
					Uri.parse("http://www.buddhapants.com/pages/about-buddha-pants"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		subTotal = getIntent().getExtras().getDouble("subTotal");
		setContentView(R.layout.activity_checkout);
		buttonback = (ImageButton) findViewById(R.id.btn_back);
		buttonContinue = (Button) findViewById(R.id.btnContinue);
		editFirstName = (EditText) findViewById(R.id.firstName);
		editLastName = (EditText) findViewById(R.id.lastName);
		editCompany = (EditText) findViewById(R.id.company);
		editAddress = (EditText) findViewById(R.id.address);
		editSuite = (EditText) findViewById(R.id.suite);
		editCity = (EditText) findViewById(R.id.city);
		editCountry = (EditText) findViewById(R.id.country);
		editState = (EditText) findViewById(R.id.state);
		editPostal = (EditText) findViewById(R.id.postalCode);
		editNum = (EditText) findViewById(R.id.number);

		initPayPal();
		setupListener();

	}

	private void setupListener() {
		buttonback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		buttonContinue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Validate()) {
					PayPalPayment thingToBuy = new PayPalPayment(
							new BigDecimal(subTotal), "USD", "buddhapants.com",
							PayPalPayment.PAYMENT_INTENT_SALE);

					Intent intent = new Intent(CheckoutActivity.this,
							PaymentActivity.class);

					intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

					startActivityForResult(intent, REQUEST_PAYPAL_PAYMENT);

				}

			}
		});

	}

	protected boolean Validate() {
		firstName = editFirstName.getText().toString().trim();
		lastName = editLastName.getText().toString().trim();
		company = editCompany.getText().toString().trim();
		address = editAddress.getText().toString().trim();
		suite = editSuite.getText().toString().trim();
		city = editCity.getText().toString().trim();
		country = editCountry.getText().toString().trim();
		state = editState.getText().toString().trim();
		postal = editPostal.getText().toString().trim();
		num = editNum.getText().toString().trim();

		if (firstName.equals("")) {
			editFirstName.setError("can't be blank");
			return false;
		} else {
			editFirstName.setError(null);
		}
		if (lastName.equals("")) {
			editLastName.setError("can't be blank");
			return false;
		} else {
			editLastName.setError(null);
		}
		if (address.equals("")) {
			editAddress.setError("can't be blank");
			return false;
		} else {
			editAddress.setError(null);
		}
		if (city.equals("")) {
			editCity.setError("can't be blank");
			return false;
		} else {
			editCity.setError(null);
		}
		if (country.equals("")) {
			editCountry.setError("can't be blank");
			return false;
		} else {
			editCountry.setError(null);
		}
		if (state.equals("")) {
			editState.setError("can't be blank");
			return false;
		} else {
			editState.setError(null);
		}
		if (postal.equals("")) {
			editPostal.setError("can't be blank");
			return false;
		} else {
			editPostal.setError(null);
		}
		if (num.equals("")) {
			editNum.setError("can't be blank");
			return false;
		} else {
			editNum.setError(null);
		}

		return true;
	}

	private void initPayPal() {
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_PAYPAL_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data
						.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i("paymentExample", confirm.toJSONObject()
								.toString());

						JSONObject jsonObj = new JSONObject(confirm
								.toJSONObject().toString());

						String paymentId = jsonObj.getJSONObject("response")
								.getString("id");
						System.out.println("payment id:-==" + paymentId);
						Toast.makeText(getApplicationContext(), paymentId,
								Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						Log.e("paymentExample",
								"an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("paymentExample", "The user canceled.");
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i("paymentExample",
						"An invalid Payment was submitted. Please see the docs.");
			}
		}

	}

}
