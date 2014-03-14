package com.heig_vd.crowdanalyzer.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class ShakeDetector extends Detector {
	private float mAccel,
				  mAccelCurrent,
				  mAccelLast;
	
	public ShakeDetector(Context context) {
		super(context);	
		sensors.add(sensMng.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
        float x = event.values[0],
          	  y = event.values[1],
          	  z = event.values[2];
          
          mAccelLast = mAccelCurrent;
          mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
          mAccel = mAccel * 0.9f + (mAccelCurrent - mAccelLast);
          if(mAccel > 3){ 
          	Log.d("ShakeSensor", "X: " + x +
          						 "Y: " + y +
          						 "Z: " + z);
          	notifyListeners(x, y, z);
          }
	}
}
