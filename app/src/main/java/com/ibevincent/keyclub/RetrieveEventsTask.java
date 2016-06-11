package com.ibevincent.keyclub;

import android.os.AsyncTask;

public class RetrieveEventsTask extends AsyncTask <String, Void, EventData> {
	
	private EventData data = new EventData();
	
	@Override
	protected EventData doInBackground(String... params) {
		try {
			data.populateEvents(params[0]);
			return data;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.err.println("IOException occured");
			e.printStackTrace(System.err);
		} 
		return null;
	}

}
