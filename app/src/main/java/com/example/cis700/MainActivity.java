package com.example.cis700;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor acclereometer,mGyro,mMagno,mTemp,mPressure,mHumid,mlight;
    TextView xval,yval,zval,gxval,gyval,gzval,mxval,myval,mzval,temp,pressure,light,humi;
    EditText subId;
    Button save,start;
    boolean pressed_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xval=(TextView)findViewById(R.id.x_val);
        yval=(TextView)findViewById(R.id.y_val);
        zval=(TextView)findViewById(R.id.z_val);
        gxval=(TextView)findViewById(R.id.gx_val);
        gyval=(TextView)findViewById(R.id.gy_val);
        gzval=(TextView)findViewById(R.id.gz_val);
        mxval=(TextView)findViewById(R.id.mx_val);
        myval=(TextView)findViewById(R.id.my_val);
        mzval=(TextView)findViewById(R.id.mz_val);
        temp=(TextView)findViewById(R.id.t_val);
        pressure=(TextView)findViewById(R.id.p_val);
        light=(TextView)findViewById(R.id.l_val);
        humi=(TextView)findViewById(R.id.h_val);
        subId=(EditText)findViewById(R.id.subID);
        save=(Button)findViewById(R.id.savebtn);
        start=(Button)findViewById(R.id.strbtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_flag=false;
                Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_flag=true;

            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acclereometer =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro =sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagno =sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mTemp =sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mlight =sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mPressure =sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mHumid =sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(acclereometer!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,acclereometer,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            xval.setText("sensor not available");
            yval.setText("sensor not available");
            zval.setText("sensor not available");

        }
        if(mMagno!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,mMagno,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            mxval.setText("sensor not available");
            myval.setText("sensor not available");
            mzval.setText("sensor not available");

        }
        if(mGyro!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,mGyro,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            gxval.setText("sensor not available");
            gyval.setText("sensor not available");
            gzval.setText("sensor not available");

        }
        if(mHumid!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,mHumid,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            humi.setText("sensor not available");

        }
        if(mlight!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,mlight,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            light.setText("sensor not available");

        }
        if(mPressure!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            pressure.setText("sensor not available");

        }
        if(mTemp!=null){
            Log.d(TAG, "onCreate: init sensor services");
            sensorManager.registerListener(MainActivity.this,mTemp,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: registered sensor services");
        }
        else{
            temp.setText("sensor not available");

        }



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor= event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            Log.d(TAG, "onSensorChanged: X:"+event.values[0]+" Y:"+event.values[1]+" Z:"+event.values[2]);
            xval.setText(""+event.values[0]);
            yval.setText(""+event.values[1]);
            zval.setText(""+event.values[2]);
            if(pressed_flag){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                String entry = format+","+xval.getText().toString() + "," + yval.getText().toString() + "," + zval.getText().toString() + "\n";
                if(subId.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter Subject ID", Toast.LENGTH_SHORT).show();
                }
                else{

                    try {

                        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                        String fileName = "output"+subId.getText().toString()+".csv";
                        String filePath = baseDir + File.separator + fileName;
                        //Boolean dirsMade = dir.mkdir();
                        //System.out.println(dirsMade);
                        //Log.v("Accel", dirsMade.toString());

                        File file = new File(filePath);
                        FileOutputStream f = new FileOutputStream(file, true);


                        try {
                            f.write(entry.getBytes());
                            f.flush();
                            f.close();
                            //Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
        if(sensor.getType()==Sensor.TYPE_GYROSCOPE){
            gxval.setText(""+event.values[0]);
            gyval.setText(""+event.values[1]);
            gzval.setText(""+event.values[2]);

        }
        if(sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            mxval.setText(""+event.values[0]);
            myval.setText(""+event.values[1]);
            mzval.setText(""+event.values[2]);


        }
        if(sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            temp.setText(""+event.values[0]);
        }
        if(sensor.getType()==Sensor.TYPE_LIGHT){
            light.setText(""+event.values[0]);
        }
        if(sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            humi.setText(""+event.values[0]);
        }
        if(sensor.getType()==Sensor.TYPE_PRESSURE){
            pressure.setText(""+event.values[0]);

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
