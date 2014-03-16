package com.heig_vd.crowdanalyzer.sensors;

import org.json.JSONException;
import org.json.JSONObject;

public class AccelerometerTools {
	public static JSONObject getJSON(float x, float y, float z) {
		try {
			JSONObject json = new JSONObject();
			json.put("X", x);
			json.put("Y", y);
			json.put("Z", z);
			
			return new JSONObject().put("Accelerometer", json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
