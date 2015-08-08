package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dealnprice.activity.ForgotPasswordActivity.ForgotPasswordListener;
import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ForgotPasswordTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	//ProgressDialog pdialog;
	ForgotPasswordListener fpListener;
	public ForgotPasswordTask(Context cxt,MultipartEntity multipart,ForgotPasswordListener fplistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.fpListener=fplistener;
		//pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			
			String response=HttpRequest.post(WebService.FORGOT_PASSWORD_SERVICE, multipart);
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
		//UtilMethod.showLoading(pdialog, cxt);
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
			String status=jobj.getString("message");
			if(status.equals("1")){
				fpListener.onSuccess("Your password has been sent to your email");
			}
			else{
				fpListener.onError("Unregistered Email ID");
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			fpListener.onError("slow");
		}
	}
}
