//@igrir

package com.example.roadlogger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Environment;

public class FileDumper {
 
	/*
	 * Cara pakai FileDumper
	 * 
	 * 
	 * inisialisasi dulu
	 * 
	 * contoh:
	 * FileDumper fd = new FileDumper(this, "fileAku.txt"); 
	 * ----
	 * this: mengacu ke Activity saat itu.
	 * ---
	 * 
	 * Untuk write ke file
	 * 
	 * fd.printToFile("String yang dimasukkan");
	 * 
	 * String yang dimasukkan get aja satu persatu semuanya
	 * dengan pakai RelasiOpenHelper&DBRoad
	 * yang dibuat sama Yola 
	 * 
	 * */
	
	
	private String namaFile = ""; 
	private Activity activityInduk;
	
	/*
	 * Masukkan nama file disini ( di constructor)
	 * Misalnya "road.txt"
	 * */
	public FileDumper(Activity activityInduk, String namaFile) {
		// TODO Auto-generated constructor stub
		this.namaFile = namaFile;
		this.activityInduk = activityInduk;
		//tulis file ke SD Card
		
		
	}
	
	public void printToFile(String barisInput){
		String namaFileOutput = Environment.getExternalStorageDirectory()+this.namaFile;
		
		try {
			PrintWriter pw = new PrintWriter(namaFileOutput);
			pw.println(barisInput);
			pw.close();
			
			AlertDialog dialog = new AlertDialog.Builder(this.activityInduk).create();
			dialog.setMessage("Data selesai ditulis");
			dialog.show();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
