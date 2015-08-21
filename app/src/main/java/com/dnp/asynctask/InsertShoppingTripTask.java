package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.VisitShopOfferFragment.VisitShopListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class InsertShoppingTripTask extends AsyncTask<String, Void, String>{
	ProgressDialog pdialog;
	Context cxt;
	MultipartEntity multipart;
	VisitShopListener vsListener;
	public InsertShoppingTripTask(Context cxt,MultipartEntity multipart,VisitShopListener vslistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.vsListener=vslistener;
		pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			
			String response=HttpRequest.post(WebService.INSERT_SHOPPING_TRIP_SERVICE, multipart);
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
		if(pdialog!=null && pdialog.isShowing()){
			pdialog.dismiss();
		}
		if(result!=null){
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("status");
			if(status.equals("true")){
				vsListener.onSuccess();
				}
			else{
				
			}
			}
			catch(Exception e){
				
			}
			
		}
		else{
			vsListener.onError("slow");
		}
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		UtilMethod.showLoading(pdialog, cxt);
	}
}
