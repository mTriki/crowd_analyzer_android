package com.heig_vd.crowdanalyzer.sensors;

import java.util.EventListener;

public interface ShakeDetectorListener extends EventListener {
	public void onShaked(float x, float y, float z);
}
