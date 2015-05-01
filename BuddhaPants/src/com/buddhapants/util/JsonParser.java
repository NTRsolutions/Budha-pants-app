package com.buddhapants.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.buddhapants.modal.ImageModal;

public class JsonParser {

	public static List<ImageModal> getProductList(String result) {

		List<ImageModal> listStoreModal = new ArrayList<ImageModal>();

		try {

			JSONObject jsonMainObject = new JSONObject(result);
			JSONArray jsonProductArray = jsonMainObject
					.getJSONArray("products");
			// Log.e("mJsonArray", "" + mJsonArray);
			// Log.e("mJsonArray", "" + mJsonArray.length());
			for (int i = 0; i < jsonProductArray.length(); i++) {
				JSONObject jsonInnerObject = jsonProductArray.getJSONObject(i);

				ImageModal storeModal = new ImageModal();

				String title = jsonInnerObject.getString("title");
				String productId = jsonInnerObject.getString("id");
				storeModal.setTitle(title);
				storeModal.setProduct_id(productId);
				// Log.e("id---------", "" + id.length());

				// Log.e("id---", "" + id);
				if (jsonInnerObject.has("image")) {
					JSONObject jsonImageObject = jsonInnerObject
							.getJSONObject("image");
					String imageId = jsonImageObject.getString("id");
					String imageSrc = jsonImageObject.getString("src");

					storeModal.setID(imageId);
					storeModal.setImage(imageSrc);

				}

				listStoreModal.add(storeModal);

			}
		} catch (Exception e) {
			Log.e("Error", "getProductList-->" + e.getCause());
		}

		return listStoreModal;

	}

	public static List<String> getVariantSize(JSONArray jsonVarianArrary)
			throws JSONException {

		List<String> resultList = new ArrayList<String>();
		resultList.add("Select size");
		for (int i = 0; i < jsonVarianArrary.length(); i++) {

			JSONObject innerObject = jsonVarianArrary.getJSONObject(i);
			resultList.add(innerObject.getString("option1"));
		}

		return resultList;
	}

	public static List<String> getVariantColor(JSONArray jsonVarianArrary)
			throws JSONException {

		List<String> resultList = new ArrayList<String>();
		resultList.add("Select color");
		for (int i = 0; i < jsonVarianArrary.length(); i++) {

			JSONObject innerObject = jsonVarianArrary.getJSONObject(i);
			if (!innerObject.getString("option2").equals("null"))
				resultList.add(innerObject.getString("option2"));

		}

		return resultList;
	}

	public static List<String> getGalleryImages(JSONArray jsonVarianArrary)
			throws JSONException {

		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < jsonVarianArrary.length(); i++) {

			JSONObject innerObject = jsonVarianArrary.getJSONObject(i);
			resultList.add(innerObject.getString("src"));
		}

		return resultList;
	}

	public static String getPrice(JSONArray jsonVarianArrary)
			throws JSONException {

		String price = null;
		JSONObject innerObject = null;
		if (jsonVarianArrary.length() != 0) {
			innerObject = jsonVarianArrary.getJSONObject(0);
			price = innerObject.getString("price");
		} else {
			price = "0";
		}

		return price;

	}
}
