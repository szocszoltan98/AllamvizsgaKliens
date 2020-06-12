package com.example.socket_programing;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import java.io.PrintWriter;
import java.net.Socket;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;
public class MainActivity extends AppCompatActivity implements SensorEventListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;



    private SensorManager sensorManager;
    Sensor acelerometer;
    public TextView ipadress;
    public String ipAdress="192.168.1.2";
    public int port=100;
    public Socket client;
    private String msg,msg2,msg3,still="",unknown="",walk="",run="",bicycle="",vehicle="",foot="",tilt="";
    private EditText xValue,zValue,yValue;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiClient=new GoogleApiClient.Builder(MainActivity.this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();
        mApiClient.connect();
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        ipadress=findViewById(R.id.txt_ip);
        ipadress.setText("A te ip cimed:" + ip);
        //ipAdress=ip;
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        xValue=findViewById(R.id.txtXValue);
        yValue=findViewById(R.id.txtYValue);
        zValue=findViewById(R.id.txtZValue);
        acelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,acelerometer,SensorManager.SENSOR_DELAY_UI);
        Intent intent=new Intent(MainActivity.this,ActivityRecognizedService.class);


    }

    @Override
    public void onConnected( Bundle bundle) {
        Intent intent=new Intent(MainActivity.this,ActivityRecognizedService.class);
        PendingIntent pendingIntent=PendingIntent.getService(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient,1000,pendingIntent);
        bundle = getIntent().getExtras();
        if(bundle!=null)
        {

            msg = xValue.getText().toString();
            msg2 = yValue.getText().toString();
            msg3 = zValue.getText().toString();
            still = getIntent().getExtras().get("still").toString();
            unknown = getIntent().getExtras().get("unknown").toString();
            walk = getIntent().getExtras().get("walk").toString();
            run = getIntent().getExtras().get("run").toString();
            vehicle = getIntent().getExtras().get("vehicle").toString();
            bicycle = getIntent().getExtras().get("bicycle").toString();
            foot = getIntent().getExtras().get("foot").toString();
            tilt = getIntent().getExtras().get("tilt").toString();


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
                        printWriter.write("\n");
                        printWriter.write(still);
                        printWriter.write("\n");
                        printWriter.write(unknown);
                        printWriter.write("\n");
                        printWriter.write(bicycle);
                        printWriter.write("\n");
                        printWriter.write(run);
                        printWriter.write("\n");
                        printWriter.write(walk);
                        printWriter.write("\n");
                        printWriter.write(vehicle);
                        printWriter.write("\n");
                        printWriter.write(foot);
                        printWriter.write("\n");
                        printWriter.write(tilt);

                        printWriter.flush();
                        printWriter.close();


                        client.close();

                    } catch (Exception e) {
                        e.printStackTrace();


                    }

                }


            }).start();
            Toast.makeText(MainActivity.this, "Data Send Succesfuly", Toast.LENGTH_SHORT).show();


        }


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
        xValue.setText("Acelerometer Xvalue: " + event.values[0]);
        yValue.setText("Acelerometer Yvalue: " + event.values[1]);
        zValue.setText("Acelerometer Zvalue: " + event.values[2]);

    }
}
