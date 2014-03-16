package com.heig_vd.crowdanalyzer.sensors;

import java.util.EventListener;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class LocationDetector extends Detector implements LocationListener {
	private LocationManager locatMng;

	public LocationDetector(Context context) {
		super(context);
		locatMng = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void onLocationChanged(Location location) {
		if(location == null)
			Log.d("NULL", "JE SUIS NULLLLL");
		notifyListenersLocationChanged(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Provider disabled", provider);
		
		if(provider.equals(LocationManager.GPS_PROVIDER)) {
			locatMng.removeUpdates(this);
			locatMng.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL_MS, LOCATION_INTERVAL_M, this);
			Log.d("Provider enabled", LocationManager.NETWORK_PROVIDER);
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		if(provider.equals(LocationManager.GPS_PROVIDER)) {
			locatMng.removeUpdates(this);
			locatMng.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL_MS, LOCATION_INTERVAL_M, this);
			Log.d("Provider disabled", LocationManager.NETWORK_PROVIDER);
		}
		
		Log.d("Provider enabled", provider);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String newStatus = "";
		switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				newStatus = "OUT_OF_SERVICE";
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				newStatus = "TEMPORARILY_UNAVAILABLE";
				break;
			case LocationProvider.AVAILABLE:
				newStatus = "AVAILABLE";
				break;
		}
		
		Log.d("Status Changed", provider + ": " + newStatus);
	}
	
	@Override
	protected void startDetector() {
		if (locatMng.isProviderEnabled(LocationManager.GPS_PROVIDER))
			locatMng.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL_MS, LOCATION_INTERVAL_M, this);

		locatMng.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL_MS, LOCATION_INTERVAL_M, this);
	}

	@Override
	protected void stopDetector() {
		locatMng.removeUpdates(this);
	}
	
	protected void notifyListenersLocationChanged(Location location) {
		for(EventListener listener : listeners)
			((LocationDetectorListener) listener).onLocationChanged(location);
	}
}
