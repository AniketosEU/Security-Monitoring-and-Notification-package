package org.example.mqtt;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigFragment extends Fragment implements OnClickListener{

	
	Button connectButton = null;
	Button disconnectButton = null;
	private final String TAG = "Config_fragment";

	EditText address = null;
	
	static final int DISCONNECT = 0;
	static final int CONNECT = 1;
	
    // input is true if connected and false if disconnected
    public void setConnectButtons(boolean connect){
		connectButton.setEnabled(!connect);
		disconnectButton.setEnabled(connect);
		address.setEnabled(!connect);
    }

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.conf, container, false);
        setupView(rootView);
        Log.d(TAG, "On Create View");
        return rootView;
    }
	
	   public void setupView(View rootView)
	    {
	    	
	    	
	    	Activity parent = getActivity();
			MqttApplication appHandler = (MqttApplication) parent.getApplication();
	    	
	    	connectButton = (Button) rootView.findViewById(R.id.connectButton);
	    	connectButton.setOnClickListener(this);
	    	
	    	disconnectButton = (Button) rootView.findViewById(R.id.disconnectButton);
	    	disconnectButton.setOnClickListener(this);
	    	
	    	address = (EditText) rootView.findViewById(R.id.url_value);
	    	address.setText(appHandler.getAddress());
	    	

			this.setConnectButtons(appHandler.isConnection());
	    	
	    }
	   
		public void onClick(View v) {
			if(v == connectButton)
			{	

				connect();
			}
			
			if(v == disconnectButton)
			{
				disconnect();
			}
			

		}
		

		
		public void connect()
		{
			MainActivity activeHandler = (MainActivity) getActivity();
			MqttApplication appHandler = (MqttApplication) activeHandler.getApplication();
			appHandler.setAddress(address.getText().toString());
			
			activeHandler.connect();
		}
		private void toast(String message)
		{
			Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
		}
		
		
		private void disconnect()
		{
			MainActivity activeHandler = (MainActivity) getActivity();
			activeHandler.sendMessageToMQTTservice(MQTTSubscriberService.MSG_DISCONNECT);
		}
	

	
}
