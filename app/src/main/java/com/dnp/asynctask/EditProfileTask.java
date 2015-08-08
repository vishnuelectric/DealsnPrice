package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.ProfileFragment.EditProfileListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class EditProfileTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	//ProgressDialog pdialog;
	EditProfileListener epListener;
	public EditProfileTask(Context cxt,MultipartEntity multipart,EditProfileListener eplistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.epListener=eplistener;
		//pdialog=new ProgressDialog(cxt);
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			
			String response=HttpRequest.post(WebService.UPDATE_PROFILE_SERVICE, multipart);
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
			String status=jobj.getString("loginstatus");
			if(status.equals("1")){
				epListener.onSuccess("Account Information Successfuly Updated");	
			}
			else {
				epListener.onError("Please Enter Correct Information");
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			epListener.onError("slow");
		}
	}
}
