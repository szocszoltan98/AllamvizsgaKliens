package com.example.socket_programing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {




    private EditText editText_ip,editText_port,editText_mes;
    private String ipAdress;
    private int port=0;
    private Socket client;
    private String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_ip = (EditText)findViewById(R.id.ipadress);
        editText_port=(EditText)findViewById(R.id.port);
        editText_mes=(EditText)findViewById(R.id.message);


        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipAdress =editText_ip.getText().toString();
                port = Integer.valueOf(editText_port.getText().toString());
                msg=editText_mes.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                          try {
                              client = new Socket(ipAdress, port);
                              PrintWriter printWriter = new PrintWriter(client.getOutputStream());

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
                Toast.makeText(MainActivity.this,"Data Send Succesfuly",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
