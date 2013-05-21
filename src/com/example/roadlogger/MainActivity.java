package com.example.roadlogger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener, SensorEventListener{

	TextView tvLat;
	TextView tvLng;
	TextView tvStatus;
	TextView tvX;
	TextView tvY;
	TextView tvZ;
	double lat;
	double lng;
	long minTime;
	float minDistance;
	String locProvider;
	LocationManager locMgr;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//variabel-variabel tempat textarea 		
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvLat = (TextView) findViewById(R.id.tvLat);
		tvLng = (TextView) findViewById(R.id.tvLng);
		tvX = (TextView) findViewById(R.id.tvX);
		tvY = (TextView) findViewById(R.id.tvY);
		tvZ = (TextView) findViewById(R.id.tvZ);
		
		locMgr = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		
		tvStatus.setText("ambil lokasi terakhir berdasarkan network. Jangan jalan dulu, jalan baru kalau ketemu GPS");
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
		
		//sensor accelerometer
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){			
    		//device memiliki accelerometer,lanjutkan		   
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);	
	        mSensorManager.registerListener(this, mSensor, 
	    		                         SensorManager.SENSOR_DELAY_NORMAL);
		}else {
		  //tidak punya sensor accelerometer, tampilkan pesan error
		}
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
	    mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locMgr.removeUpdates(this);
	    mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		double ax=0,ay=0,az=0;
		// menangkap perubahan nilai sensor
		
		//if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
	            ax=event.values[0];
	            ay=event.values[1];
	            az=event.values[2];
	    }		
		tvX.setText("x : "+ax);
		tvY.setText("y : "+ay);
		tvZ.setText("z : "+az);
	}
	
}
