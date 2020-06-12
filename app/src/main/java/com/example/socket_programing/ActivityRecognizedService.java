package com.example.socket_programing;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;
import static java.security.AccessController.getContext;

public class ActivityRecognizedService extends IntentService {
    public String ipAdress=": 192.168.1.2";
    public int port=100;
    public Socket client;
    public int number,db=0;
    public String type;
    public String msg="",msg2="",msg3="",msg4="",msg5="",msg6="",msg7="",msg8="";
    public ActivityRecognizedService(){
        super("ActivityRecognizedService");
    }



    public ActivityRecognizedService(String name){
        super(name);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)){
            ActivityRecognitionResult result =ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivity(result.getProbableActivities());
        }
    }



    private void handleDetectedActivity(List<DetectedActivity> probableActivities)
    {
        Intent intent=new Intent(ActivityRecognizedService.this,MainActivity.class);
        intent.putExtra("vehicle",msg);
        intent.putExtra("bicycle",msg2);
        intent.putExtra("run",msg3);
        intent.putExtra("walk",msg4);
        intent.putExtra("still",msg5);
        intent.putExtra("unknown",msg6);
        intent.putExtra("foot",msg7);
        intent.putExtra("tilting",msg8);
        for(DetectedActivity activity: probableActivities)
        {


            switch(activity.getType()) {

                case DetectedActivity.IN_VEHICLE: {
                    number = activity.getConfidence();
                    msg="VEHICLE:" + number;
                    Log.d(TAG,"handleDetectedActivity:" + msg);
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Vehicle", Toast.LENGTH_SHORT).show();
                    }


                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    number = activity.getConfidence();
                    msg2="BICYCLE:" + number;
                    Log.d(TAG,"handleDetectedActivity:" + msg2);
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Bicycle", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
                case DetectedActivity.RUNNING: {
                    number = activity.getConfidence();
                    msg3="RUNNING:" + number;
                    Log.d(TAG,"handleDetectedActivity:" + msg3);
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Run", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
                case DetectedActivity.WALKING: {
                    number = activity.getConfidence();
                    msg4="WALKING:" + number;
                    Log.d(TAG,"handleDetectedActivity: " + msg4);
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Walk", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
                case DetectedActivity.STILL: {
                    number = activity.getConfidence();
                    msg5="STILL:" + number;
                    Log.d(TAG,"handleDetectedActivity: " + msg5);
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Still", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    number = activity.getConfidence();

                    msg6="UNKNOWN:" + number;
                    Log.d(TAG,"handleDetectedActivity:" + msg6);
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Unknown", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.d(TAG,"handleDetectedActivity: ON_FOOT:" + activity.getConfidence());
                    number = activity.getConfidence();
                    msg7="FOOT:" + number;
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Foot", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                case DetectedActivity.TILTING: {
                    Log.d(TAG,"handleDetectedActivity: TILTING:" + activity.getConfidence());
                    number = activity.getConfidence();
                    msg8="STILL:" + number;
                    if(number>75)
                    {
                        Toast.makeText(this, "Now Tilt", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }



            }
            intent.putExtra("vehicle",msg);
            intent.putExtra("bicycle",msg2);
            intent.putExtra("run",msg3);
            intent.putExtra("walk",msg4);
            intent.putExtra("still",msg5);
            intent.putExtra("unknown",msg6);
            intent.putExtra("foot",msg7);
            intent.putExtra("tilt",msg8);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);



        }


    }



}




