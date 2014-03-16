package com.heig_vd.crowdanalyzer.sensors;

import android.hardware.Sensor;

interface DetectorConfig {
	public static final int LOCATION_INTERVAL_MS = 2000,
							LOCATION_INTERVAL_M = 0;
	
	public static final int SHAKE_DETECTION_SENSIBILITY = 4,
							SHAKE_TYPE_ACCELEROMETER = Sensor.TYPE_LINEAR_ACCELERATION;
}
