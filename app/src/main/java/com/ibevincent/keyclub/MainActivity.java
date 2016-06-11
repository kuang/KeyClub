package com.ibevincent.keyclub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

	private EventData data;
	private RetrieveEventsTask retrieveTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		retrieveTask = new RetrieveEventsTask();
		retrieveTask.execute("https://script.google.com/macros/s/AKfycbxHk_GXziSAwSH6umVyz3LnnbgpkA9BnqvL2ILeFdhdUkLKobg/exec?next=asdasds");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			data = retrieveTask.get();
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		} catch (ExecutionException e) { 
			
			e.printStackTrace();
		}
		
		populateListView(data.getEventsList());
		registerClickCallback();
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
private void populateListView(String[] arr){
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, 
				R.layout.the_event, 
				arr);
		ListView list = (ListView) findViewById(R.id.eventsList);
		list.setAdapter(adapter);
	}

private void registerClickCallback() {
	ListView list = (ListView)findViewById(R.id.eventsList);
	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View viewClicked, int position,
				long id) {
			TextView textView = (TextView) viewClicked;
			String message = "You clicked #" + position + ", which is string: " + textView.getText().toString();
			Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
		}
		
	});
}

	

}
