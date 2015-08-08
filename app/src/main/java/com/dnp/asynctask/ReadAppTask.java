package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.dnp.bean.ApplicationBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.Receiver;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.OfferDetailFragment.OfferDetailListener;
import com.dnp.fragment.OfferFragment.OfferListener;

public class ReadAppTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	//ProgressDialog pdialog;
	OfferDetailListener odListener;
	String url;
	int position;
	public ReadAppTask(Context cxt,MultipartEntity multipart,String url,OfferDetailListener odlistener,int position){
		this.cxt=cxt;
		this.multipart=multipart;
		this.odListener=odlistener;
		this.url=url;
		this.position=position;
		//pdialog=new ProgressDialog(cxt);
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.READ_APP_SERVICE, multipart);
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
		if(result!=null){
			try{
			JSONObject obj=new JSONObject(result);
			/*String status=obj.getString("status");
			if(status.equals("true")){
				String message=obj.getString("message");
				//rlistener.onSuccess();
			}
			else{
				String message=obj.getString("message");
				//rlistener.onError(message);
			}*/
			/*ApplicationBean abean=new ApplicationBean();
			abean.setPackage_name(StaticData.application_list.get(position).getPackage_name());
			abean.setApp_id(StaticData.application_list.get(position).getApp_id());	
			Receiver.createAppPreference(abean, cxt, cxt.getSharedPreferences("User_login",1).getString("user_id",null));
			Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			cxt.startActivity(intent);*/
			}
			catch(Exception e){
				
			}
		}
		else{
			odListener.onError("slow");
		}
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	//	UtilMethod.showLoading(pdialog, cxt);
	}

}
