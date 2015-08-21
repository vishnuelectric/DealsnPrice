package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.dnp.bean.NotificationBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.NotificationFragment.NotificationListener;

public class GetNotificationTask extends AsyncTask<String, Void, String>{
	Context cxt;
	NotificationListener nListener;
	public GetNotificationTask(Context cxt,NotificationListener nlistener){
		this.cxt=cxt;
		this.nListener=nlistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			
			String response=HttpRequest.post(WebService.GET_NOTIFICATION_SERVICE+"urlcheck=1&uniqueid=User");
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
			StaticData.notification_list.clear();
			if(status.equals("1")){
				JSONArray jarray=jobj.getJSONArray("list");
				for(int i=0;i<jarray.length();i++){
					JSONObject notification_object=jarray.getJSONObject(i);
					NotificationBean nbean=new NotificationBean();
					nbean.setNotification_id(notification_object.getString("nid"));
					nbean.setNotification_title(notification_object.getString("ntitle"));
					nbean.setNotification_description(notification_object.getString("ndesc"));
					nbean.setNotification_time(notification_object.getString("date"));
					StaticData.notification_list.add(nbean);
				}
				nListener.onSuccess();
			}
			else{
				
			}
			
			}
			catch(Exception e){
				//UtilMethod.showToast("Exception "+e, cxt);
				nListener.onSuccess();
			}
		}
	}
}
