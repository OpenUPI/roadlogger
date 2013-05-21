package com.example.roadlogger;

import java.util.Vector;

import com.example.roadlogger.DbRoad.Road;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbRoad{
	
	public static class Road{
		public int id;
		public String waktu;
		public String latitude;
		public String longi;
		public String nilai_x;
		public String nilai_y;
		public String nilai_z;
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
	
	public long insert(String waktu, String latitude, String longi,  String nilai_x,  String nilai_y,  String nilai_z){
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
			R.latitude = cur.getString(1);
			R.longi = cur.getString(2);
			R.nilai_x = cur.getString(3);
			R.nilai_y = cur.getString(4);
			R.nilai_z = cur.getString(5);
			R.waktu = cur.getString(6);
		
		}
		
		return R;
	}
	
	public Vector<Road> getAllRoad(){
		Cursor cur = null;
		
		//Vector Road yang akan dilemparkan hasil kembalian
		Vector<Road> VecR= new Vector<Road>();
		
		String[] COLS = new String[] {"ID", "LATITUDE", "LONGI", "NILAI_X", "NILAI_Y", "NILAI_Z", "WAKTU"};
		
		cur = db.query("ROADLOGGER", COLS, null, null, null, null, null);
		if(cur.getCount() > 0){
			
			//mendapatkan jumlah baris yang mau ditampilkan
			int jumlahBaris = cur.getCount();
			
			//posisikan cursor di paling atas jika punya banyak
			//baris agar bisa mengambil data dari pertama sampai akhir
			if (jumlahBaris > 0) {
				cur.moveToFirst();
			}
			
			for (int i = 0; i < jumlahBaris; i++) {
				
				Road R = new Road();
				R.id = cur.getInt(0);
				R.latitude = cur.getString(1);
				R.longi = cur.getString(2);
				R.nilai_x = cur.getString(3);
				R.nilai_y = cur.getString(4);
				R.nilai_z = cur.getString(5);
				R.waktu = cur.getString(6);
				
				//masukkan objek iterasi ini ke dalam vector
				VecR.add(R);
				
				//pindah ke baris berikutnya
				cur.moveToNext();
			}		
		}
		return VecR;
	}
	
	public void update(int id, String latitude , String longi, String nilai_x,  String nilai_y,  String nilai_z,  String waktu){
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
