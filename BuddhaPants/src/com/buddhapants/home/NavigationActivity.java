package com.buddhapants.home;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buddhapants.R;
import com.buddhapants.about.AboutActivity;
import com.buddhapants.contactus.ContactActivity;
import com.buddhapants.events.EventsActivity;
import com.buddhapants.getinvolved.GetInvolvedActivity;
import com.buddhapants.retailers.RetailersActivity;
import com.buddhapants.store.StoreActivity;
import com.buddhapants.wholesale.WholesaleActivity;

public class NavigationActivity extends Activity implements OnClickListener {
	ListAdapter adapter1;
	ListView listView;
	ArrayList<String> arrayListItems;
	ImageButton imgTwitter, imgFacebook, imgPinterest, imgTumblr, imgInstagram;
	TextView txtLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		listView = (ListView) findViewById(R.id.listview_navigation);
		imgFacebook = (ImageButton) findViewById(R.id.img_facebook);
		imgTumblr = (ImageButton) findViewById(R.id.img_tumblr);
		imgTwitter = (ImageButton) findViewById(R.id.img_twitter);
		imgPinterest = (ImageButton) findViewById(R.id.img_pinterest);
		imgInstagram = (ImageButton) findViewById(R.id.img_instagram);
//		txtLogout = (TextView) findViewById(R.id.txt_Logout);
		arrayListItems = new ArrayList<String>();
		getArrayListItems();
		adapter1 = new ListAdapter(arrayListItems, getApplicationContext());
		listView.setAdapter(adapter1);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String string = arrayListItems.get(position);

				if (string.matches("HOME")) {
					Intent iHome = new Intent(NavigationActivity.this,
							HomepageActivity.class);
					startActivity(iHome);
				}

				else if (string.matches("RETAILERS")) {
					Intent iretailer = new Intent(NavigationActivity.this,
							RetailersActivity.class);
					startActivity(iretailer);

				} else if (string.matches("ABOUT")) {
					Intent iAbout = new Intent(NavigationActivity.this,
							AboutActivity.class);
					startActivity(iAbout);
				} else if (string.matches("WHOLESALE")) {
					Intent iWholesale = new Intent(NavigationActivity.this,
							WholesaleActivity.class);
					startActivity(iWholesale);
				} else if (string.matches("CONTACT US!")) {
					Intent iContact = new Intent(NavigationActivity.this,
							ContactActivity.class);
					startActivity(iContact);
				} else if (string.matches("GET INVOLVED")) {
					Intent iInvolved = new Intent(NavigationActivity.this,
							GetInvolvedActivity.class);
					startActivity(iInvolved);
				} else if (string.matches("STORE")) {
					Intent iStore = new Intent(NavigationActivity.this,
							StoreActivity.class);
					startActivity(iStore);
				} else if (string.matches("EVENTS")) {
					Intent iEVENT = new Intent(NavigationActivity.this,
							EventsActivity.class);
					startActivity(iEVENT);
				}

				else {
					Toast.makeText(getApplicationContext(),
							"Under developement:" + string, Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		imgFacebook.setOnClickListener(this);
		imgInstagram.setOnClickListener(this);
		imgPinterest.setOnClickListener(this);
		imgTumblr.setOnClickListener(this);
		imgTwitter.setOnClickListener(this);
		//txtLogout.setOnClickListener(this);

	}

	class ListAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		ArrayList<String> arrList = new ArrayList<String>();
		Context context;

		public ListAdapter(ArrayList<String> arrList, Context context) {
			super();
			this.arrList = arrList;
			this.context = context;
			Log.e("arrList----", ""+arrList.size());
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewGroup rootview = (ViewGroup) layoutInflater.inflate(
					R.layout.activity_listview_items, null);

			TextView txt_items = (TextView) rootview
					.findViewById(R.id.txtItems);
			txt_items.setText(arrList.get(position));
			return rootview;
		}

	}

	void getArrayListItems() {
		// TODO Auto-generated method stub
		arrayListItems.add("HOME");
		arrayListItems.add("STORE");
		//arrayListItems.add("ART PANTS");
		arrayListItems.add("EVENTS");
		arrayListItems.add("RETAILERS");
		arrayListItems.add("ABOUT");
		arrayListItems.add("CONTACT US!");
		arrayListItems.add("GET INVOLVED");
		//arrayListItems.add("GIVEAWAYS");
		arrayListItems.add("WHOLESALE");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int Id = v.getId();
		switch (Id) {
		case R.id.img_facebook:
			Intent intentFB = new Intent();
			intentFB.setAction(Intent.ACTION_VIEW);
			intentFB.addCategory(Intent.CATEGORY_BROWSABLE);
			intentFB.setData(Uri
					.parse("https://www.facebook.com/wear.buddhapants"));
			startActivity(intentFB);
			break;
		case R.id.img_tumblr:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse("http://buddhapants.tumblr.com"));
			startActivity(intent);
			break;

		case R.id.img_twitter:
			Intent intent1 = new Intent();
			intent1.setAction(Intent.ACTION_VIEW);
			intent1.addCategory(Intent.CATEGORY_BROWSABLE);
			intent1.setData(Uri.parse("https://twitter.com/buddha_pants"));
			startActivity(intent1);
			break;
		case R.id.img_pinterest:
			Intent intentPin = new Intent();
			intentPin.setAction(Intent.ACTION_VIEW);
			intentPin.addCategory(Intent.CATEGORY_BROWSABLE);
			intentPin.setData(Uri
					.parse("https://www.pinterest.com/wearbuddhapants/"));
			startActivity(intentPin);
			break;
		case R.id.img_instagram:
			Intent intentInsta = new Intent();
			intentInsta.setAction(Intent.ACTION_VIEW);
			intentInsta.addCategory(Intent.CATEGORY_BROWSABLE);
			intentInsta
					.setData(Uri.parse("https://instagram.com/buddha_pants"));
			startActivity(intentInsta);
			break;

//		case R.id.txt_Logout:
//			// Intent iLogout = new Intent(NavigationActivity.this,
//			// LoginActivity.class);
//			// startActivity(iLogout);
//			// overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//			// finish();
//			finish();

		default:
			break;
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
	}

}
