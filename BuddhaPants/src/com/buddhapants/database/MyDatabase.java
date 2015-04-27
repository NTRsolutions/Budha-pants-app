package com.buddhapants.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabase extends SQLiteOpenHelper {
	public static final String DBNAME = "buddhapants.db";
	public static final String TABLENAME = "products";
	public static final String TABLENAME_REGISTER = "register";
	public static final String TABLENAME_LOGIN = "login";
	public static final int VERSIONID = 1;

	public static String TCTITLE = "title";
	public static String TCSIZE = "size";
	public static String TCPRICE = "price";
	public static String TCQTY = "qty";
	public static String TKEY = "id";
	public static String TCIMAGE = "image";

	public static String TCFNAME = "first_name";
	public static String TCLASTNAME = "last_name";
	public static String TCEMAIL = "email";
	public static String TCID = "id";

	public static String TABLENAME_LOGIN_id = "_id";
	public static String TABLENAME_LOGIN_email = "_email";

	public static String QUERY = "create table " + TABLENAME + " ( " + TCTITLE
			+ " TEXT not null, " + TCSIZE + " TEXT not null, " + TCPRICE
			+ " varchar, " + TCQTY + " int," + TCIMAGE + " varchar," + TKEY
			+ " varchar)";

	public static String QUERY_REGISTER = "create table " + TABLENAME_REGISTER + " ( "
			+ TCFNAME + " TEXT not null, " + TCLASTNAME + " TEXT not null, "
			+ TCEMAIL + " varchar, " + TCID + " varchar)";

	public static String QUERY_LOGIN = "create table " + TABLENAME_LOGIN
			+ " ( " + TABLENAME_LOGIN_id + " int, " + TABLENAME_LOGIN_email
			+ " TEXT not null)";

	public MyDatabase(Context context) {
		super(context, DBNAME, null, VERSIONID);
		Log.e("dbbbbbbbbbbbbbbbb", "data base created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
			db.execSQL(QUERY);
			db.execSQL(QUERY_REGISTER);
			db.execSQL(QUERY_LOGIN);
			Log.e("Tableeeeeeeeeeeeeee", "table Created");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Tableeeeeeeeeeeeeee", e.getMessage());
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
