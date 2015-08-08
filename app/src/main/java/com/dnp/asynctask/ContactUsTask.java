package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.ContactUsFragment.ContactListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ContactUsTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	ContactListener cListener;
	//ProgressDialog pdialog;
	
	public ContactUsTask(Context cxt,MultipartEntity multipart,ContactListener clistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.cListener=clistener;
		/*pdialog=new ProgressDialog(cxt);*/
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.CONTACT_SERVICE, multipart);
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
	//	UtilMethod.showLoading(pdialog, cxt);
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
			if(status.equals("1")){
			cListener.onSuccess("Thank you for writing to us. We will revert you in the next 24 to 48 hours");	
			}
			else if(status.equals("2")){
				cListener.onError("Enter a valid Email Address");
			}
			else if(status.equals("3")){
				cListener.onError("Please Enter Correct Information");
			}
			
			}
			catch(Exception e){
				
			}
		}
		else{
			cListener.onError("slow");
		}
		
	}
}
