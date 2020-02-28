package com.example.androidtcp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);

        new Thread(

                ()->{
                    try {
                        socket = new Socket("10.0.2.2",5001);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        ).start();

        btn.setOnClickListener(
                (v)->{
                    new Thread(

                            ()->{

                                try {
                                    OutputStream os = socket.getOutputStream();
                                    OutputStreamWriter osw = new OutputStreamWriter(os);
                                    BufferedWriter write = new BufferedWriter(osw);
                                    write.write("hola desde adroid \n");
                                    write.flush();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                    ).start();

                }
        );


    }
}
