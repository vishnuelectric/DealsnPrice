package com.dnp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.dealnprice.activity.DashboardActivity;
import com.dnp.data.HttpRequest;

import org.json.JSONObject;

public class AmountTask extends AsyncTask<String, Void, String>{
	Context cxt;
	String url;
	
	public AmountTask(Context cxt,String url){
		this.cxt=cxt;
		this.url=url;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(url);
		//UtilMethod.showToast("Amount Response Value is "+response, cxt);
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
		//UtilMethod.showToast("Response Result in data is "+result, cxt);
		if(result!=null){
			try{
			JSONObject object=new JSONObject(result);
			String message=object.getString("message");
			if(message.equals("1")){
				String amount=object.getString("amount");
				//StaticData.amount=amount;
				DashboardActivity.setAmount(amount);
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
