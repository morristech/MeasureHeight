package com.zzy.smartweight.camera;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.zzy.smartweight.Common;


public class CustomSensor
{
	private SensorManager sm;
	private android.hardware.Sensor accelerometer;
	private android.hardware.Sensor magnetic;
	private MySensorEventListener mySensorEventListener;

	private float[] accelerometerValues = new float[3];
	private float[] magneticFieldValues = new float[3];
	private float[] orientationValues = new float[3];

	private ValueListen valueListen;

	public CustomSensor(Context context)
	{
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);
		magnetic = sm.getDefaultSensor(android.hardware.Sensor.TYPE_MAGNETIC_FIELD);
		mySensorEventListener = new MySensorEventListener();
    }

    public void start()
	{
		sm.registerListener(mySensorEventListener, accelerometer, android.hardware.Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(mySensorEventListener, magnetic, android.hardware.Sensor.TYPE_MAGNETIC_FIELD);
	}

	public void stop()
	{
		sm.unregisterListener(mySensorEventListener);
	}

	public float[] getOrientation()
	{
		return orientationValues;
	}

	private void calculateOrientation() {
		float[] R = new float[9];
		SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
		SensorManager.getOrientation(R, orientationValues);
		orientationValues[0] = (float) Math.toDegrees(orientationValues[0]);
		orientationValues[1] = (float) Math.toDegrees(orientationValues[1]);
		orientationValues[2] = (float) Math.toDegrees(orientationValues[2]);

		if(valueListen !=null)
		{
			valueListen.getValues(orientationValues);
		}
	}

	private class MySensorEventListener implements SensorEventListener {
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) {
				accelerometerValues = event.values;
			}
			if (event.sensor.getType() == android.hardware.Sensor.TYPE_MAGNETIC_FIELD) {
				magneticFieldValues = event.values;
			}
			calculateOrientation();
		}

		@Override
		public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

	}

	public void setValueListen(ValueListen valueListen)
	{
		this.valueListen = valueListen;
	}

	public interface ValueListen
	{
		public void getValues(float[] values);
	}

}
