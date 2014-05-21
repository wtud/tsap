package com.example.xmlrpctest;

import java.net.MalformedURLException;
import java.net.URL;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	
	private void addText(String text)
	{
		TextView v = (TextView)findViewById(R.id.main_text_box);
		text = v.getText() + text + "\n"; 
        v.setText(text);
	}
	
	private void callRemoteFunctions()
	{
		XMLRPCCallback listener = new XMLRPCCallback() {
			@Override
		    public void onResponse(long id, Object result) {
		        Log.i("XMLRPC", ""+(Integer)result);
		    }
			@Override
		    public void onError(long id, XMLRPCException error) {
				Log.w("XMLRPC", error.getMessage());
				Log.w("XMLRPC", "Error!");
		        // Handling any error in the library
		    }
			@Override
			public void onServerError(long id, XMLRPCServerException error) {
				Log.w("XMLRPC", "ServerError!");
				// TODO Auto-generated method stub
				
			}
		};

		XMLRPCClient client = null;
		try {
			client = new XMLRPCClient(new URL("http://192.168.1.112:8000/RPC2"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addText("Malformed URI!");
			return;
		}
		
		addText(client.toString());
		

		client.callAsync(listener, "pow", 2, 3);
		client.callAsync(listener, "add", 2, 3);
		client.callAsync(listener, "div", 18, 3);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callRemoteFunctions();		
			}
		});
    }
}
