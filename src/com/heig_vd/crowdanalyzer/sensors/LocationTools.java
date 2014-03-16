package com.heig_vd.crowdanalyzer.sensors;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

public class LocationTools {
	public static JSONObject getJSON(Location location) {
		try {
			JSONObject json = new JSONObject();
			json.put("Longitude", location.getLongitude());
			json.put("Latitude", location.getLatitude());
			json.put("Accuracy", location.getAccuracy());
			json.put("Altitude", location.getAltitude());
			
			return new JSONObject().put("Location", json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
