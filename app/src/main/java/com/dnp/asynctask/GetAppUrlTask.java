package com.dnp.asynctask;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.dealnprice.activity.CommonUtilities;
import com.dealnprice.activity.Constant;
import com.dealnprice.activity.DashboardActivity.DashboardRateListener;
import com.dnp.bean.ApplicationBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.fragment.AboutUsFragment.RateListener;

public class GetAppUrlTask extends AsyncTask<String, Void, String>{
	Context cxt;
	RateListener rListener;
	//ProgressDialog pdialog;
	DashboardRateListener drListener;
	public GetAppUrlTask(Context cxt,RateListener rlistener){
		this.cxt=cxt;
		this.rListener=rlistener;
		//pdialog=new ProgressDialog(cxt);
	
	}
	public GetAppUrlTask(Context cxt,DashboardRateListener drlistener){
		this.cxt=cxt;
		this.drListener=drlistener;
		//pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.APP_URL_SERVICE);
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
			String status=jobj.getString("status");
			if(status.equals("true")){
				StaticData.our_app_list.clear();
				ApplicationBean abean=new ApplicationBean();
				abean.setApp_id(jobj.getString(Constant.APP_ID));
				abean.setApp_url(jobj.getString(Constant.APP_URL)+ "&" + Constant.USER_ID + "=" + cxt.getSharedPreferences(Constant.pref_name,1).getString(Constant.USER_ID, null) + "&" + "=" + CommonUtilities.getImei(cxt));
				StaticData.our_app_list.add(abean);
			}
			if(rListener!=null){
				rListener.onSuccess();
			}
			if(drListener!=null){
				drListener.onSuccess();
			}
		}
			catch(Exception e){
				
			}
		}
		else{
			if(rListener!=null){
				rListener.onError("slow");
			}
			else if(drListener!=null){
				drListener.onError("slow");
			}
		}
	}
}
