package com.example.roadlogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RelasiOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "dbRoadLogger";
	protected static final String TABLE_CREATE_1 = "CREATE TABLE DATA_ROADLOGGER (ID INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"LATITUDE FLOAT, LONGI TEXT, NILAI_X FLOAT, NILAI_Y FLOAT, NILAI_Z FLOAT, WAKTU STRING);";
	
	
	public RelasiOpenHelper(Context context, String name, CursorFactory factory, int version){
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_1);
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		
		db.execSQL("DROP TABLE IF EXISTS DATA_ROADLOGGER");
		onCreate(db);
	}

}
