package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;

import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class InsertAppStatusTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	ProgressDialog pdialog;
	public InsertAppStatusTask(Context cxt,MultipartEntity multipart){
		this.cxt=cxt;
		this.multipart=multipart;
		pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			
			String response=HttpRequest.post(WebService.INSERT_APP_STATUS_SERVICE, multipart);
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
		UtilMethod.showLoading(pdialog, cxt);
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(pdialog!=null && pdialog.isShowing()){
			pdialog.dismiss();
		}
		if(result!=null){
			
		}
	}
	

}
