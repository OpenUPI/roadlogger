package com.example.roadlogger;

import java.util.Timer;
import java.util.Vector;

import com.example.roadlogger.DbRoad.Road;

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
import android.os.Handler;
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
	TextView cek;
	double lat;
	double lng;
	long minTime;
	float minDistance;
	String locProvider;
	LocationManager locMgr;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	Handler mHandler = new Handler();
	
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
		
		
		timer5s();
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

	
	public void timer5s()
	{
		final DbRoad db = new DbRoad(this);
		db.open();
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	            // TODO Auto-generated method stub
	            while (true) {
	                try {
	                    Thread.sleep(10000);
	                    mHandler.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            // TODO Auto-generated method stub
	                            // Write your code here to update the UI.
	                        	Time now = new Time();
	                    		now.setToNow();
	                    		String a = ("" + now.hour);
	                    		String b = ("" + now.second);
	                        	db.insert(a, tvLat.toString(), tvLng.toString(), tvX.toString(), tvY.toString(), tvZ.toString());                        	
	                        	cek.setText(b);
	                        }
	                    });
	                } catch (Exception e) {
	                    // TODO: handle exception
	                }
	            }
	        }
	    }).start();
	}
	
	public void cek()
	{
		final DbRoad db = new DbRoad(this);
		db.open();
		Vector<Road> VecR= new Vector<Road>();
		VecR = db.getAllRoad();
		
		String kata="";
		for(int a=0;a<VecR.size();a++)
		{
			Road x = (Road)(VecR.elementAt(a));
			String waktu = x.waktu.toString();
			String lat = x.latitude.toString();
			String lan = x.longi.toString();
			String nx = x.nilai_x.toString();
			String ny = x.nilai_y.toString();
			String nz = x.nilai_z.toString();
			
			kata += waktu + "," + lat + "," + lan + "," + nx + "," + ny + "," + nz + "\n";
			
		}
		
		FileDumper fd = new FileDumper(this, "fileAku.txt");
		fd.printToFile(kata);
	}
}
