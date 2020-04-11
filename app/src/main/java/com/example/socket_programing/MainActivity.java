package com.example.socket_programing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements SensorEventListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;



    private SensorManager sensorManager;
    Sensor acelerometer;


    private EditText editText_ip,editText_port;
    public String ipAdress;
    public int port=0;
    public Socket client;
    private String msg,msg2,msg3;
    private EditText xValue,zValue,yValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiClient=new GoogleApiClient.Builder(MainActivity.this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();
        mApiClient.connect();



        editText_ip = (EditText)findViewById(R.id.ipadress);
        editText_port=(EditText)findViewById(R.id.port);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        xValue=findViewById(R.id.txtXValue);
        yValue=findViewById(R.id.txtYValue);
        zValue=findViewById(R.id.txtZValue);
        acelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,acelerometer,SensorManager.SENSOR_DELAY_UI);
        
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipAdress =editText_ip.getText().toString();
                port = Integer.valueOf(editText_port.getText().toString());
                msg=xValue.getText().toString();
                msg2=yValue.getText().toString();
                msg3=zValue.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                          try {
                              client = new Socket(ipAdress, port);
                              PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                              printWriter.write("\n");
                              printWriter.write(msg);
                              printWriter.write("\n");
                              printWriter.write(msg2);
                              printWriter.write("\n");
                              printWriter.write(msg3);
                              printWriter.flush();
                              printWriter.close();


                              client.close();

                          }catch(Exception e)
                          {
                              e.printStackTrace();

                          }

                      }


                }).start();
                Toast.makeText(MainActivity.this,"Data Send Succesfuly",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Intent intent=new Intent(MainActivity.this,ActivityRecognizedService.class);
        PendingIntent pendingIntent=PendingIntent.getService(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient,3000,pendingIntent);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient,5000,pendingIntent);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xValue.setText("Xvalue: " + event.values[0]);
        yValue.setText("Yvalue: " + event.values[1]);
        zValue.setText("Zvalue: " + event.values[2]);
    }
}
