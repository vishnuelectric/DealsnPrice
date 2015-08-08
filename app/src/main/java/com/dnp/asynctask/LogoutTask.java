package com.dnp.asynctask;

import org.json.JSONObject;

import com.dealnprice.activity.DashboardActivity.DashBoardListener;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

import android.content.Context;
import android.os.AsyncTask;

public class LogoutTask extends AsyncTask<String, Void, String>{
	Context cxt;
	DashBoardListener dListener;
	public LogoutTask(Context cxt,DashBoardListener dlistener){
		this.cxt=cxt;
		this.dListener=dlistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.LOGOUT_WEBSERVICE+"gcmid="+cxt.getSharedPreferences("User_login", 1).getString("gcm_id", null)+"&gcmtype=");
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
			String status=jobj.getString("loginstatus");
			if(status.equals("1")){
				dListener.onSuccess();
			}
			else{
				//dListener.onError("Some Internal Issues");
				dListener.onSuccess();
			}
			}
			catch(Exception e){
				dListener.onSuccess();
			}
		}
		else{
			dListener.onError("slow");
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
}
