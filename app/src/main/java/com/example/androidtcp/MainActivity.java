package com.example.androidtcp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    Socket socket;
    BufferedWriter writer;
    boolean isUp = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);

        new Thread(

                ()->{
                    try {
                        socket = new Socket("10.0.2.2",5000);
                        //escritor
                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        writer = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        ).start();

        btn.setOnTouchListener(
                (v, event)->{

                    switch(event.getAction()){

                        case MotionEvent.ACTION_DOWN:
                            isUp=false;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            btn.setText("MOVE");
                            break;

                        case MotionEvent.ACTION_UP:
                            isUp=true;
                            break;
                    }

                    return true;
                }

        );

        new Thread(
                ()->{
                    while(true){

                        while(isUp){}

                        try {
                            Thread.sleep(300);
                            writer.write("UP\n");
                            writer.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }
        ).start();
    }
}
