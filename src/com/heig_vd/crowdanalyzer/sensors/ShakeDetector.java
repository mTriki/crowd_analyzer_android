package com.heig_vd.crowdanalyzer.sensors;

import java.util.EventListener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector extends Detector implements SensorEventListener {
	protected SensorManager sensMng;
	private Sensor acc;
	
	private float mAccel,
				  mAccelCurrent,
				  mAccelLast;
	
	public ShakeDetector(Context context) {
		super(context);
		sensMng = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		acc = sensMng.getDefaultSensor(SHAKE_TYPE_ACCELEROMETER);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
        float x = event.values[0],
          	  y = event.values[1],
          	  z = event.values[2];
          
		mAccelLast = mAccelCurrent;
		mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
		mAccel = mAccel * 0.9f + (mAccelCurrent - mAccelLast);
		if(mAccel >= SHAKE_DETECTION_SENSIBILITY)
			notifyListenersShaked(x, y, z);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	protected void startDetector() {
		sensMng.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void stopDetector() {
		sensMng.unregisterListener(this, acc);
	}
	
	protected void notifyListenersShaked(float x, float y, float z) {
		for(EventListener listener : listeners)
			((ShakeDetectorListener) listener).onShaked(x, y, z);
	}
}
