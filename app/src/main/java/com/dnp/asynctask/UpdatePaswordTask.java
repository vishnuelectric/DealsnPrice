package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;
import com.dnp.fragment.ChangePasswordFragment.ChangePasswordListener;

import android.content.Context;
import android.os.AsyncTask;

public class UpdatePaswordTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	ChangePasswordListener cpListener;
	public UpdatePaswordTask(Context cxt,MultipartEntity multipart,ChangePasswordListener cplistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.cpListener=cplistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.UPDATE_PASSWORD_SERVICE, multipart);
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
			String message=jobj.getString("message");
			if(message.equals("1")){
					cpListener.onSuccess("Account Information Successfuly Updated");
			}
			else if(message.equals("2")){
				cpListener.onError("Please Enter Correct Current Password");
			}
			else{
				cpListener.onError("Please Enter Correct Information");
			}
			}
			catch(Exception e){
				
			}
		}
	}
	
}
