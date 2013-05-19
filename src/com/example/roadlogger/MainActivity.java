package com.example.roadlogger;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener{

	TextView tvLat;
	TextView tvLng;
	TextView tvStatus;
	double lat;
	double lng;
	long minTime;
	float minDistance;
	String locProvider;
	LocationManager locMgr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//variabel-variabel tempat textarea 
		
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvLat = (TextView) findViewById(R.id.tvLat);
		tvLng = (TextView) findViewById(R.id.tvLng);
		
		locMgr = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		
		tvStatus.setText("ambil lokasi terakhir berdasarkan network");
		locProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locMgr.getLastKnownLocation(locProvider);
		
		lat = lastKnownLocation.getLatitude();
		lng = lastKnownLocation.getLongitude();
		
		tvLat.setText(String.valueOf(lat));
		tvLng.setText(String.valueOf(lng));
		
		Criteria cr = new Criteria();
		cr.setAccuracy(Criteria.ACCURACY_FINE);
		locProvider = locMgr.getBestProvider(cr, false);
		
		minTime = 5 * 1000;
		
		minDistance = 1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		lat = location.getLatitude();
		lng = location.getLongitude();
		tvLat.setText(String.valueOf(lat));
		tvLng.setText(String.valueOf(lng));
		
		Time now = new Time();
		now.setToNow();
		
		tvStatus.setText("Direfresh berdasarkan: " + locProvider + " Waktu: " + now.hour + ":" + now.minute +":"+ now.second);
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locMgr.requestLocationUpdates(locProvider, minTime, minDistance, this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locMgr.removeUpdates(this);
	}
	
}
