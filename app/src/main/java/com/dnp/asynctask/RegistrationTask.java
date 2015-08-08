package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dealnprice.activity.RegistrationActivity.RegistrationListener;
import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class RegistrationTask extends AsyncTask<String, Void, String>{
	//ProgressDialog pdialog;
	MultipartEntity multipart;
	RegistrationListener rListener;
	Context cxt;
	public RegistrationTask(Context cxt,MultipartEntity multipart,RegistrationListener rlistener){
		this.multipart=multipart;
		this.cxt=cxt;
		this.rListener=rlistener;
		//pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.REGISTRATION_SERVICE, multipart);
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
		/*if(pdialog!=null && pdialog.isShowing()){
			pdialog.dismiss();
		}*/
	//	UtilMethod.showToast("REsponse Registration is "+result, cxt);
		if(!UtilMethod.isStringNullOrBlank(result)){
			try{
			JSONObject obj=new JSONObject(result);
			String status=obj.getString("loginstatus");
			if(status!=null && status.equals("1")){
				/*SharedPreferences shpf=cxt.getSharedPreferences("User_login", 1);
				Editor edt=shpf.edit();
				edt.putString("user_id",obj.getString("id"));
				edt.putString("user_name",obj.getString("name"));
				edt.putString("user_email",obj.getString("email"));
				edt.commit();*/
				rListener.onSuccess("Successfully Registered Done");
			}
			else if(status!=null && status.equals("3")){
				rListener.onError("Email Already Exists!");
			}
			else if(status!=null && status.equals("4")){
				rListener.onError("All fields are required");
			}
			else{
				//String msg=obj.getString("message");
				//rListener.onError(msg);
			}
			}
			catch(Exception e){
				rListener.onError("Email Already Exists!");
			}
		}
		else{
			rListener.onError("Email Already Exists!");
		}
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//UtilMethod.showLoading(pdialog, cxt);
	}
}
