package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;
import com.dnp.fragment.DNPMissingCashbackFragment.MissingCashbackListener;
import com.dnp.fragment.DNPMissingShoppingCashbackFragment.MissingCashBackListener;

import android.content.Context;
import android.os.AsyncTask;

public class MissingCashBackTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	MissingCashBackListener mcbListener;
	MissingCashbackListener mcbListener1;
	
	public MissingCashBackTask(Context cxt,MultipartEntity multipart,MissingCashBackListener mcblistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.mcbListener=mcblistener;
		
	}
	
	public MissingCashBackTask(Context cxt,MultipartEntity multipart,MissingCashbackListener mcblistener1){
		this.cxt=cxt;
		this.multipart=multipart;
		this.mcbListener1=mcblistener1;
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.MISSING_CASHBACK_WEBSERVICE, multipart);
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
				
				if(mcbListener!=null){
				mcbListener.onSuccess();
				}
				else if(mcbListener1!=null){
					mcbListener1.onSuccessRequest("Request Sent Successfully");
				}
			}
			else{
				if(mcbListener!=null){
				mcbListener.onError("");
				}
				else if(mcbListener1!=null){
					mcbListener1.onError("Some Internal ");
				}
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			if(mcbListener!=null){
			mcbListener.onError("slow");
			}
			else if(mcbListener1!=null){
				mcbListener1.onError("slow");
			}
		}
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
