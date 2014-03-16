package com.heig_vd.crowdanalyzer.sensors;

import java.util.EventListener;

import android.location.Location;

public interface LocationDetectorListener extends EventListener {
	public void onLocationChanged(Location location);
}
