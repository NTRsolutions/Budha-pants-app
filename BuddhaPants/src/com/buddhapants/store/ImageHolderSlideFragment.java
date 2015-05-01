package com.buddhapants.store;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buddhapants.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageHolderSlideFragment extends Fragment {

	public static String TAG = "ImageHolderSlideFragment";
	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE = "page";
	public static final String ARG_URL = "url";

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;
	private String imageUrl;
	static DisplayImageOptions options;

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	public static ImageHolderSlideFragment create(int pageNumber, String url,
			DisplayImageOptions options2) {
		ImageHolderSlideFragment fragment = new ImageHolderSlideFragment();
		options = options2;
		Bundle args = new Bundle();

		args.putInt(ARG_PAGE, pageNumber);
		args.putString(ARG_URL, url);

		fragment.setArguments(args);
		return fragment;
	}

	public ImageHolderSlideFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		imageUrl = getArguments().getString(ARG_URL);

	}

	/*
	 * 
	 * id_name id_week rating_bar id_text_desc
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_image_viewer, container, false);

		final ImageView imageView = (ImageView) rootView
				.findViewById(R.id.image_viewer_id);
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options);

		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}
}
