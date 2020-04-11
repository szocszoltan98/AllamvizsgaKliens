package com.example.socket_programing;

import android.app.IntentService;
import android.content.Intent;
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

public class ActivityRecognizedService extends IntentService {
    public String ipAdress=": 192.168.1.3";
    public int port=100;
    public Socket client;
    public int number;
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
        for(DetectedActivity activity: probableActivities)
        {
            Log.d(TAG,"***************************************************************************" ) ;
            switch(activity.getType()) {

                case DetectedActivity.IN_VEHICLE: {
                    number = activity.getConfidence();
                    final String msg="handleDetectedActivity: IN_VEHICLE:" + number;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client = new Socket(ipAdress, port);
                                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                                printWriter.write("\n");
                                printWriter.write(msg);
                                printWriter.flush();
                                printWriter.close();
                                client.close();

                            }catch(Exception e)
                            {
                                e.printStackTrace();

                            }

                        }


                    }).start();
                    Log.d(TAG,"handleDetectedActivity: IN_VEHICLE:" + activity.getConfidence());
                    Toast.makeText(this, "Now Vehicle", Toast.LENGTH_SHORT).show();


                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    number = activity.getConfidence();
                    final String msg2="handleDetectedActivity: ON_BICYCLE:" + number;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client = new Socket(ipAdress, port);
                                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                                printWriter.write("\n");
                                printWriter.write(msg2);
                                printWriter.flush();
                                printWriter.close();
                                client.close();

                            }catch(Exception e)
                            {
                                e.printStackTrace();

                            }

                        }


                    }).start();
                    Log.d(TAG,"handleDetectedActivity: ON_BICYCLE:" + activity.getConfidence());
                    Toast.makeText(this, "Now Bicycle", Toast.LENGTH_SHORT).show();

                    break;
                }
                /*case DetectedActivity.ON_FOOT: {
                    Log.d(TAG,"handleDetectedActivity: ON_FOOT:" + activity.getConfidence());
                    Toast.makeText(this, "Now Foot", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                case DetectedActivity.RUNNING: {
                    number = activity.getConfidence();
                    final String msg3="handleDetectedActivity:  RUNNING:" + number;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client = new Socket(ipAdress, port);
                                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
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

                    Log.d(TAG,"handleDetectedActivity: RUNNING:" + activity.getConfidence());
                    Toast.makeText(this, "Now Run", Toast.LENGTH_SHORT).show();

                    break;
                }
                case DetectedActivity.WALKING: {
                    number = activity.getConfidence();
                    final String msg4="handleDetectedActivity: WALKING:" + number;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client = new Socket(ipAdress, port);
                                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                                printWriter.write("\n");
                                printWriter.write(msg4);
                                printWriter.flush();
                                printWriter.close();
                                client.close();

                            }catch(Exception e)
                            {
                                e.printStackTrace();

                            }

                        }


                    }).start();
                    Log.d(TAG,"handleDetectedActivity: WALKING:" + activity.getConfidence());
                    Toast.makeText(this, "Now Walk", Toast.LENGTH_SHORT).show();

                    break;
                }
                case DetectedActivity.STILL: {
                    number = activity.getConfidence();
                    final String msg5="handleDetectedActivity:  STILL:" + number;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client = new Socket(ipAdress, port);
                                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                                printWriter.write("\n");
                                printWriter.write(msg5);
                                printWriter.flush();
                                printWriter.close();
                                client.close();

                            }catch(Exception e)
                            {
                                e.printStackTrace();

                            }

                        }


                    }).start();
                    Log.d(TAG,"handleDetectedActivity: STILL:" + activity.getConfidence());
                    Toast.makeText(this, "Now Still", Toast.LENGTH_SHORT).show();

                    break;
                }

                /*case DetectedActivity.TILTING: {
                    Log.d(TAG,"handleDetectedActivity: TILTING:" + activity.getConfidence());
                    Toast.makeText(this, "Now Tilt", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                case DetectedActivity.UNKNOWN: {
                    number = activity.getConfidence();
                    final String msg6="handleDetectedActivity: UNKNOWN:" + number;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client = new Socket(ipAdress, port);
                                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                                printWriter.write("\n");
                                printWriter.write(msg6);
                                printWriter.flush();
                                printWriter.close();
                                client.close();

                            }catch(Exception e)
                            {
                                e.printStackTrace();

                            }

                        }


                    }).start();
                    Log.d(TAG,"handleDetectedActivity: UNKNOWN:" + activity.getConfidence());
                    Toast.makeText(this, "Now Unknown", Toast.LENGTH_SHORT).show();

                    break;
                }


            }



        }
    }

}




