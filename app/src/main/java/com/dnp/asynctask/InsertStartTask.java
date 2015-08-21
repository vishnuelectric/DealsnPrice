package com.dnp.asynctask;

import org.json.JSONObject;

import com.dealnprice.activity.OfferShareFragment.OfferShareListener;
import com.dnp.data.HttpRequest;

import android.content.Context;
import android.os.AsyncTask;

public class InsertStartTask extends AsyncTask<String, Void, String>{
	String url;
	Context cxt;
	OfferShareListener osListener;
	public InsertStartTask(Context cxt,String url){
		this.cxt=cxt;
		this.url=url;
		
	}
	
	public InsertStartTask(Context cxt,String url,OfferShareListener oslistener){
		this.cxt=cxt;
		this.url=url;
		this.osListener=oslistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(url);
		return response;
		
		}
		catch(Exception e){
			
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null){
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("status");
			if(status.equals("1")){
				if(osListener!=null){
					osListener.onSuccess();
				}
			}
			
			}
			catch(Exception e){
				
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
