package com.dnp.asynctask;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import com.dealnprice.activity.Constant;
import com.dealnprice.activity.LoginActivity.LoginListener;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

public class RegistrationWithSocialTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	LoginListener lListener;
	
	public RegistrationWithSocialTask(Context cxt,MultipartEntity multipart,LoginListener llistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.lListener=llistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.REGISTRATION_SOCIAL_SERVICE, multipart);
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
			Log.e("Response Login.. ", jobj+"");
			String status=jobj.getString("loginstatus");
			if(status.equals("1")){
				SharedPreferences shpf=cxt.getSharedPreferences(Constant.pref_name,1);
				Editor edt=shpf.edit();
				edt.putString("user_id",jobj.getString("id"));
				edt.putString("user_name", jobj.getString("name"));
				edt.putString("user_email", jobj.getString("email"));
				edt.putString("user_mobile",jobj.getString("mobile"));
				edt.putString("user_code", jobj.getString("usercode"));
				edt.commit();
				lListener.onSuccesswithSocial();
				
			}
			else{
				SharedPreferences shpf=cxt.getSharedPreferences(Constant.pref_name,1);
				Editor edt=shpf.edit();
				edt.putString("user_id",jobj.getString("id"));
				edt.putString("user_name", jobj.getString("name"));
				edt.putString("user_email", jobj.getString("email"));
				edt.putString("user_mobile",jobj.getString("mobile"));
				edt.putString("user_code", jobj.getString("usercode"));
				edt.commit();
				lListener.onSuccesswithSocial();
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			lListener.onError("slow");
		}
		
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
