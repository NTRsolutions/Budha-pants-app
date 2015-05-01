package com.buddhapants.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyConnection {

	public MyDatabase mydb;
	public SQLiteDatabase sqdb;
	String allcolumn[] = { MyDatabase.TCTITLE, MyDatabase.TCSIZE,
			MyDatabase.TCPRICE, MyDatabase.TTOTAL, MyDatabase.TCQTY,
			MyDatabase.TCIMAGE, MyDatabase.TKEY };
	String allcolumnRegister[] = { MyDatabase.TCFNAME, MyDatabase.TCLASTNAME,
			MyDatabase.TCEMAIL, MyDatabase.TCID };

	public MyConnection(Context ct) {
		try {
			mydb = new MyDatabase(ct);
			sqdb = mydb.getWritableDatabase();
			Log.e("ddddddddddddddddd", "Data Base and table created");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("eeeeeeeeeeeeeeeee", e.getMessage());
		}

	}

	public boolean insertData(String title, String size, int qty, String image,
			String price, String total, String id) {
		boolean flag = false;
		ContentValues cv = new ContentValues();
		try {
			cv.put(MyDatabase.TCTITLE, title);
			cv.put(MyDatabase.TCSIZE, size);
			cv.put(MyDatabase.TCPRICE, price);
			cv.put(MyDatabase.TTOTAL, total);
			cv.put(MyDatabase.TCQTY, qty);
			cv.put(MyDatabase.TCIMAGE, image);
			cv.put(MyDatabase.TKEY, id);

			long row = sqdb.insert(MyDatabase.TABLENAME, null, cv);
			Log.e("row----", "" + row);
			if (row > 0) {
				flag = true;
			} else {
				long rowChange = sqdb.update(MyDatabase.TABLENAME, cv,
						MyDatabase.TKEY + " = ?", new String[] { id });
				if (rowChange == 1) {
					flag = true;
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean insertDataRegister(String fname, String lname, String id,
			String email) {
		boolean flag = false;
		try {
			ContentValues cv = new ContentValues();
			cv.put(MyDatabase.TCFNAME, fname);
			cv.put(MyDatabase.TCLASTNAME, lname);
			cv.put(MyDatabase.TCEMAIL, email);
			cv.put(MyDatabase.TCID, id);

			long row = sqdb.insert(MyDatabase.TABLENAME_REGISTER, null, cv);
			Log.e("row----", "" + row);
			if (row > 0) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	// fetch

	public Cursor selectData() throws SQLException {
		// ArrayList<String> a = new ArrayList<String>();
		Cursor mCursor = sqdb.query(MyDatabase.TABLENAME, allcolumn, null,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor selectDataRegister() throws SQLException {
		// ArrayList<String> a = new ArrayList<String>();
		Cursor mCursor = sqdb.query(MyDatabase.TABLENAME_REGISTER,
				allcolumnRegister, null, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public boolean searchData(String email) {
		Log.e("email-------------", "" + email);
		boolean flag = false;
		Cursor cursor = sqdb.query(MyDatabase.TABLENAME_REGISTER,
				allcolumnRegister, "email = ?", new String[] { email }, null,
				null, null, null);

		// if (cursor != null) {
		// cursor.moveToFirst();
		// }
		Log.e("cursor.getCount()-------------", "" + cursor.getCount());
		if (cursor.getCount() != 0) {

			do {
				// String mString = cursor.getString(2).trim();
				// Log.e("mString-----", "" + mString);
				// if (mString != null) {
				flag = true;
				// } else {
				// flag = false;
				// }

			} while (cursor.moveToNext());

			cursor.close();

		} else {
			flag = false;
		}

		return flag;
	}

	public void Delete_Row(String string) {
		Log.e("string: ", "" + string);

		sqdb.delete(MyDatabase.TABLENAME, MyDatabase.TKEY + "=" + string, null);

	}

	public boolean insertDataID(String id, String email) {
		Log.e("id----", "" + id);
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			ContentValues cv = new ContentValues();

			cv.put(MyDatabase.TABLENAME_LOGIN_id, id);
			cv.put(MyDatabase.TABLENAME_LOGIN_email, email);

			long row = sqdb.insert(MyDatabase.TABLENAME_LOGIN, null, cv);
			Log.e("row----", "" + row);
			if (row > 0) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

}
