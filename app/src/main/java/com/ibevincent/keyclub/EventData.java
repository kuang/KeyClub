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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventData {

	 JSONObject event;
	 JSONObject date;
	 ArrayList<JSONObject> objectList = new ArrayList<JSONObject>();
	 String[] eventList;
	 JSONArray JSONEventList;
	 int limit; //change
	

	public EventData(){

	}

	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws MalformedURLException, IOException, JSONException{
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);	
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	  
	  
	public void populateEvents(String url) throws IOException, JSONException{
		JSONObject json = readJsonFromUrl(url);
		Date today = new Date();
		
		limit = json.length(); //How many objects
		
		for (int i = 0; i < limit; i++){
			
			event = json.getJSONObject(Integer.toString(i));
			date  = event.getJSONObject("date");
			int imonth = date.getInt("month") - 1;
			int iday = date.getInt("day");
			int iyear = date.getInt("year") - 1900;
			
			@SuppressWarnings("deprecation")
			Date theDay = new Date(iyear, imonth, iday);
			if (theDay.after(today)) {
				objectList.add(event);
			}
			else {
			}
	    }
		eventList = new String[objectList.size()];
		for(int i = 0; i < objectList.size(); i++){
			eventList[i] = objectList.get(i).getString("pretty_name");
		}
		
	}
	
	
	public String[] getEventsList(){ //Returns list of available events
		return eventList; 
	}
	
	public void toList(JSONObject events) throws JSONException {
	    JSONArray names = events.names();
	    JSONEventList = events.toJSONArray(names);
	}

//	public static void main(String[] args) throws IOException, JSONException {
//		
//		getInfo get = new getInfo();
//		System.out.println(Arrays.toString(get.getEventsList()));
//	}

}
