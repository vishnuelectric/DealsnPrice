package com.dnp.asynctask;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.dnp.data.HttpRequest;
import com.dnp.data.Receiver.AppListener;

public class InstallAppTask extends AsyncTask<String, Void, String>{
	Context cxt;
	String multipart;
	AppListener aListener;
	public InstallAppTask(Context cxt,String url,AppListener alistener){
		this.cxt=cxt;
		this.multipart=url;
		this.aListener=alistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(multipart);
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
				aListener.onSuccess();
			}
			}
			catch(Exception e){
				 
			}
		}
	}

}
