package com.heig_vd.crowdanalyzer;

import org.json.JSONObject;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.heig_vd.crowdanalyzer.network.SendJSONData;
import com.heig_vd.crowdanalyzer.sensors.AccelerometerTools;
import com.heig_vd.crowdanalyzer.sensors.LocationDetector;
import com.heig_vd.crowdanalyzer.sensors.LocationDetectorListener;
import com.heig_vd.crowdanalyzer.sensors.LocationTools;
import com.heig_vd.crowdanalyzer.sensors.ShakeDetector;
import com.heig_vd.crowdanalyzer.sensors.ShakeDetectorListener;


public class MainActivity extends FragmentActivity implements ShakeDetectorListener,
															  LocationDetectorListener {
	private ShakeDetector shakeD;
	private LocationDetector locatD;
	private GoogleMap map;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
		shakeD = new ShakeDetector(this);
		locatD = new LocationDetector(this);
	}

	@Override
	public void onResume() {
	    super.onResume();
	    shakeD.registerListener(this);
	    locatD.registerListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    unregisterListener();
	}
	
	@Override
	public void onStop() {
	    super.onStop();
	    unregisterListener();
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    unregisterListener();
	}
	
	private void unregisterListener() {
		shakeD.unregisterListener(this);
	    locatD.unregisterListener(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		String locMsg = "Lat: " + location.getLatitude() + 
						" - Long: " + location.getLongitude() + 
						" - Acc: " + location.getAccuracy();
		
		Log.d("Location", locMsg);
	}

	@Override
	public void onShaked(float x, float y, float z) {
		JSONObject loc = LocationTools.getJSON(map.getMyLocation());
		JSONObject acc = AccelerometerTools.getJSON(x, y, z);
		
		SendJSONData query = new SendJSONData();
		query.addData(loc);
		query.addData(acc);
		query.execute();
	}
}
