package com.buddhapants.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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

}
