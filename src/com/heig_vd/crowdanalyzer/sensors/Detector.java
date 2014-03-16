package com.heig_vd.crowdanalyzer.sensors;

import java.util.EventListener;
import java.util.LinkedList;

import android.content.Context;

public abstract class Detector implements DetectorConfig {
	protected Context context;
	protected LinkedList<EventListener> listeners = new LinkedList<EventListener>();
	
	public Detector(Context context) {
		this.context = context;
	}
	
	public void registerListener(EventListener listener) {
		listeners.add(listener);
		
		if(listeners.size() == 1)
			startDetector();
	}
	
	public void unregisterListener(EventListener listener) {
		listeners.remove(listener);
		
		if(listeners.isEmpty())
			stopDetector();
	}
	
	protected abstract void startDetector();
	
	protected abstract void stopDetector();
}
