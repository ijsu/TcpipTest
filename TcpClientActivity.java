package com.example.studenttcpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TcpClientActivity extends Activity {

	Socket socket;
	BufferedReader br;
	TextView textIn;
	String rxmsg;
	Handler handler = new Handler();
	Runnable updateUI = new Runnable() {
		public void run() {
			textIn.append(rxmsg);
		}
	}; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textIn = (TextView) findViewById(R.id.txtout);
        OpenConnection();
    }
	
	public void OpenConnection() {
		new Thread(new Runnable(){
			public void run(){
				try {
					socket = new Socket("192.168.1.139", 5566);
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					rxmsg = "Initial Connection Successes!\n";
				} catch (Exception e) {
					rxmsg = "Initial Connection Fail!\n";
				}
				handler.post( updateUI );

				boolean running = true;
				while(running) {
					try {
						String str = br.readLine();
						if (str.indexOf("/quit") >= 0) {
								rxmsg = "Server Quit Connection!\n";
								running = false;
						} else 
							rxmsg = "Received from server: " + str + "\n";
					} catch (Exception e) {
						rxmsg = "Connection Exception!\n";
						running = false;
					}
					handler.post( updateUI );
				}
				try {
					socket.close();
				} catch (Exception e) {
				}
				
			}
		}).start();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
