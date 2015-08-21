package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dealnprice.activity.LoginActivity.LoginListener;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LocalRegistrationTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	LoginListener lListener;
	//ProgressDialog pdialog;
	public LocalRegistrationTask(Context cxt,MultipartEntity multipart,LoginListener llistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.lListener=llistener;
		//this.pdialog=pdialog;
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.LOCAL_REGISTRATION_SERVICE, multipart);
		return response;
		}
		catch(Exception e){
			
		}
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		/*if(pdialog!=null && pdialog.isShowing()){
			pdialog.dismiss();
		}*/
		if(result!=null){
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("status");
			if(status.equals("true")){
				lListener.onSuccess();
			}
			else{
				
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			lListener.onError("slow");
		}
		
	}
}
