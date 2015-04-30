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
		setContentView(R.layout.activity_checkout);
		buttonback = (ImageButton) findViewById(R.id.btn_back);
		buttonContinue = (Button) findViewById(R.id.btnContinue);
		subTotal = getIntent().getExtras().getDouble("subTotal");
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

				PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(
						subTotal), "USD", "buddhapants.com",
						PayPalPayment.PAYMENT_INTENT_SALE);

				Intent intent = new Intent(CheckoutActivity.this,
						PaymentActivity.class);

				intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

				startActivityForResult(intent, REQUEST_PAYPAL_PAYMENT);
			}
		});

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
