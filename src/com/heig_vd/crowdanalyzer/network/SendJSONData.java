package com.heig_vd.crowdanalyzer.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SendJSONData extends Request {
	private LinkedList<JSONObject> data;
	
	public SendJSONData() {
		this(null);
	}
	
	public SendJSONData(Collection<? extends JSONObject> collection) {
		super(Method.POST);
		
		if(collection == null)
			data = new LinkedList<JSONObject>();
		else
			data = new LinkedList<JSONObject>(collection);
	}
	
	public void addData(JSONObject data) {
		this.data.add(data);
	}
	
	public int sizeData() {
		return data.size();
	}

	@Override
	protected void setHeaders() {
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
	}

	@Override
	protected HttpEntity getEntity() {
		try {
			JSONObject json = new JSONObject();
			json.put("RequestInfos", getRequestInfos().toString());
			
			String dataString = "";
			for(JSONObject j : data)
				dataString += j.toString();
			
			json.put("Data", dataString);
			
			return new StringEntity(json.toString(), CHARSET_NAME_DATA);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void processResponse(HttpResponse response) {
        try {
			Log.d("HttpResponse", EntityUtils.toString(response.getEntity()));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
