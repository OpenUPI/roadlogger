package com.example.roadlogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbRoad{
	
	public static class Road{
		public int id;
		public String waktu;
		public float latitude;
		public float longi;
		public float nilai_x;
		public float nilai_y;
		public float nilai_z;
			
	}
	
	private SQLiteDatabase db;
	private final Context con;
	private final RelasiOpenHelper dbHelper;
	
	public DbRoad(Context c){
		
		con = c;
		dbHelper = new RelasiOpenHelper(con,"", null, 0);
	}
	
	public void open(){
		db = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		
		db.close();
	}
	
	public long insert(String waktu, float latitude, float longi,  float nilai_x,  float nilai_y,  float nilai_z){
		ContentValues newValues = new ContentValues();
		newValues.put("LATITUDE", latitude);
		newValues.put("LONGI", longi);
		newValues.put("NILAI_X", nilai_x);
		newValues.put("NILAI_Y", nilai_y);
		newValues.put("NILAI_Z", nilai_z);
		newValues.put("WAKTU", waktu);
		
		return db.insert("ROADLOGGER", null, newValues);
	}
	
	
	public Road getRoad(){
		Cursor cur = null;
		Road R = new Road();

		String[] COLS = new String[] {"ID", "LATITUDE", "LONGI", "NILAI_X", "NILAI_Y", "NILAI_Z", "WAKTU"};
		
		cur = db.query("ROADLOGGER", COLS, null, null, null, null, null);
		if(cur.getCount() > 0){
			cur.moveToFirst();
			R.id = cur.getInt(0);
			R.latitude = cur.getFloat(1);
			R.longi = cur.getFloat(2);
			R.nilai_x = cur.getFloat(3);
			R.nilai_y = cur.getFloat(4);
			R.nilai_z = cur.getFloat(5);
			R.waktu = cur.getString(6);
		
		}
		
		return R;
	}
	
	public void update(int id, float latitude , float longi, float nilai_x,  float nilai_y,  float nilai_z,  String waktu){
		ContentValues newValues = new ContentValues();
		newValues.put("ID", id);
		newValues.put("LATITUDE", latitude);
		newValues.put("LONGI", longi);
		newValues.put("NILAI_X", nilai_x);
		newValues.put("NILAI_Y", nilai_y);
		newValues.put("NILAI_Z", nilai_z);
		newValues.put("WAKTU", waktu);
		
		db.update("ROADLOGGER", newValues, "ID = " +id, null);
	}
	
	public void delete(int id){
		db.delete("ROADLOGGER", "ID = " +id, null);
	}
}
