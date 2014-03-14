package com.heig_vd.crowdanalyzer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.heig_vd.crowdanalyzer.sensors.DetectorListener;
import com.heig_vd.crowdanalyzer.sensors.ShakeDetector;


public class MainActivity extends Activity implements DetectorListener {
	private ShakeDetector sD;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sD = new ShakeDetector(this);
	}

	@Override
	public void onResume() {
	    super.onResume();
	    sD.registerListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    sD.unregisterListener(this);
	}

	@Override
	public void process(Object... data) {
		((TextView) findViewById(R.id.accX)).setText("X: " + data[0]);
		((TextView) findViewById(R.id.accY)).setText("Y: " + data[1]);
		((TextView) findViewById(R.id.accZ)).setText("Z: " + data[2]);
	}
}
