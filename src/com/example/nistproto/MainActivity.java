package com.example.nistproto;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {


	  protected Button getInformationButton;
	  protected TextView daytimeProtocolTextView;
	 
	  protected class NISTCommunicationThread extends Thread {
	 
	    @Override
	    public void run() {
	      try {
	        Socket socket = new Socket (
	          Constants.NIST_SERVER_HOST,
	          Constants.NIST_SERVER_PORT
	        );
	        BufferedReader bufferedReader = utilities.getReader(socket);
	        bufferedReader.readLine();
	        final String daytimeProtocol = bufferedReader.readLine();
	        daytimeProtocolTextView.post(new Runnable() {
	          @Override
	          public void run() {
	            daytimeProtocolTextView.setText(daytimeProtocol);
	          }
	        });
		socket.close();
	      } catch (UnknownHostException unknownHostException) {
	        Log.e(Constants.TAG, "An exception has occurred: "+unknownHostException.getMessage());
		if (Constants.DEBUG) {
	          unknownHostException.printStackTrace();
	        }
	      } catch (IOException ioException) {
	        Log.e(Constants.TAG, "An exception has occurred: "+ioException.getMessage());
	        if (Constants.DEBUG) {
	          ioException.printStackTrace();
	        }
	      }
	    }
	  }
	 
	  protected ButtonClickListener buttonClickListener = new ButtonClickListener();
	 
	  protected class ButtonClickListener implements Button.OnClickListener {
	 
	    @Override
	    public void onClick(View view) {
	      new NISTCommunicationThread().start();
	    }
	  }
	 
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	 
	    daytimeProtocolTextView = (TextView)findViewById(R.id.textView1);
	 
	    getInformationButton = (Button)findViewById(R.id.Get);
	    getInformationButton.setOnClickListener(buttonClickListener);
	  }
	
}
