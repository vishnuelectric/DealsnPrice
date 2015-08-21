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

public class LoginTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multiPart;
	//ProgressDialog pdialog;
	LoginListener lListener;
	String gcm_id;
	public LoginTask(Context cxt,MultipartEntity multipart,LoginListener llistener,String gccm_id){
		this.cxt=cxt;
		this.multiPart=multipart;
		this.lListener=llistener;
		this.gcm_id=gccm_id;
	//	pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.LOGIN_SERVICE, multiPart);
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
		/*UtilMethod.showToast("Result is "+result, cxt);*/
		if(result!=null){
			try{
			JSONObject obj=new JSONObject(result);
			String status=obj.getString("loginstatus");
				Log.e(" onLogin","return JSON "+status);
			if(status.equals("0")){
				SharedPreferences shpf=cxt.getSharedPreferences(Constant.pref_name, 1);
				Editor edt=shpf.edit();
				edt.putString(Constant.USER_ID, obj.getString("id"));
				edt.putString("user_name", obj.getString("name"));
				edt.putString("user_email", obj.getString("email"));
				edt.putString("user_mobile",obj.getString("mobile"));
				edt.putString("user_code", obj.getString("usercode"));
				Log.e(" "," USER CODE "+obj.getString("usercode"));
				edt.putString("pendingamount", obj.getString("pendingamount"));
				edt.commit();
				lListener.onSuccess();
				/*MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("user_id",new StringBody(obj.getString("id")));
				multipart.addPart("user_full_name",new StringBody(obj.getString("name")));
				multipart.addPart("user_email",new Stri,ngBody(obj.getString("email")));
				multipart.addPart("user_password",new StringBody("12345"));
				multipart.addPart("user_mobile",new StringBody(obj.getString("mobile")));
				multipart.addPart("gcm_id", new StringBody(gcm_id));*/
				//new LocalRegistrationTask(cxt, multipart, lListener).execute();
				/*lListener.onSuccess();*/
			}
			else if(status.equals("1")){
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
				lListener.onError("All field are mandatory");
			}
			else if(status.equals("2")){
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
				lListener.onError("Please Enter Valid Email ID");
			}
			else if(status.equals("3")){
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
				lListener.onError("Wrong Email ID or Password");
			}
			else if(status.equals("4")){
				
			}
			}
			catch(Exception e){
				lListener.onError("Exception is "+e);
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
		//UtilMethod.showLoading(pdialog, cxt);
	}

}
