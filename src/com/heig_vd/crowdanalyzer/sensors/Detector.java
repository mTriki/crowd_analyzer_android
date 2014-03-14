package com.heig_vd.crowdanalyzer.sensors;

import java.util.LinkedList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class Detector implements SensorEventListener {
	protected SensorManager sensMng;
	protected LinkedList<DetectorListener> listeners = new LinkedList<DetectorListener>();
	protected LinkedList<Sensor> sensors = new LinkedList<Sensor>();
	
	public Detector(Context context) {
		sensMng = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}
	
	public void registerListener(DetectorListener listener) {
		listeners.add(listener);
		
		if(listeners.size() == 1)
			startDetectors();
	}
	
	public void unregisterListener(DetectorListener listener) {
		listeners.remove(listener);
		
		if(listeners.isEmpty())
			stopDetectors();
	}
	
	protected void notifyListeners(Object ...data) {
		for(DetectorListener listener : listeners)
			listener.process(data);
	}
	
	protected void startDetectors() {
		for(Sensor sensor : sensors)
			sensMng.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void stopDetectors() {
		for(Sensor sensor : sensors)
			sensMng.unregisterListener(this, sensor);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
