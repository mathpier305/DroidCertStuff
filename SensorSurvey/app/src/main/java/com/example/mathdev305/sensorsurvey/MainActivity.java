package com.example.mathdev305.sensorsurvey;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    //individual light and proximity sensors.
    private Sensor mSensorproximity;
    private Sensor mSensorLight;
    private Sensor mSensorHumidity;

    //TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorHumidity;
    private ImageView mProximityImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mProximityImageView = findViewById(R.id.proximityImage);
        mTextSensorHumidity = findViewById(R.id.label_humidity);

        mSensorproximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if(mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }

        if(mSensorproximity == null){
            mTextSensorProximity.setText(sensor_error);
        }

        if(mSensorHumidity == null) {
            mTextSensorHumidity.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mSensorproximity != null){
            mSensorManager.registerListener(this, mSensorproximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(mSensorHumidity != null){
            mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue));
                getWindow().getDecorView().setBackgroundColor(Color.parseColor(String.format("#%06d", Math.round(currentValue))));
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity, currentValue));
                if(currentValue < 4){
                    mProximityImageView.setBackgroundResource(R.drawable.big_rectangle);
                }else{
                    mProximityImageView.setBackgroundResource(R.drawable.stretch_rectangle);
                }
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(getResources().getString(R.string.label_humidity, currentValue));
                break;
            default:
                break;

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
