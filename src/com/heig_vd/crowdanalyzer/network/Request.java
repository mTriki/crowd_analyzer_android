package com.heig_vd.crowdanalyzer.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Request implements NetworkConfig, Runnable {
	protected static int id = NO_ID_ASSIGNED;
	protected HttpClient client = new DefaultHttpClient();
	protected HttpRequestBase request;
	protected Method method;
	private Thread thread = new Thread(this);
	
	public Request(Method method) {
		this.method = method;
	}

	protected void build(Method method) {
		this.method = method;
		
		switch (method) {
			case GET: request = new HttpGet(SERVER_URL);
					  break;
				  
			case POST: request = new HttpPost(SERVER_URL);
					   ((HttpPost) request).setEntity(getEntity());
					   break;
					   
			case PUT: request = new HttpPut(SERVER_URL);
					  ((HttpPut) request).setEntity(getEntity());
					  break;
				
			case DELETE: request = new HttpDelete(SERVER_URL);
						 break;
				
			default: break;
		}
	}
	
	@Override
	public void run() {
		build(method);
		setHeaders();
		
		try {
            processResponse(client.execute(request));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected HttpEntity getEntity() {
		try {
			return new StringEntity("", CHARSET_NAME_DATA);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void execute() {
		thread.start();
	}
	
	protected JSONObject getRequestInfos() {
		try {
			return new JSONObject().put("Id", Request.id)
								   .put("Date", new Date().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected abstract void setHeaders();
	protected abstract void processResponse(HttpResponse response);
}
