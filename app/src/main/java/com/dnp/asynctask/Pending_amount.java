package com.dnp.asynctask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

import com.dealnprice.activity.Constant;
import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

public class Pending_amount extends AsyncTask<String, Void, String> {

	private Activity cxt;
	SharedPreferences shpf;


	public Pending_amount(Activity cxt){
		this.cxt=cxt;
		shpf=cxt.getSharedPreferences(Constant.pref_name, 1);
	}

	@Override
	protected String doInBackground(String... params) {

		try{
			String response=HttpRequest.post(WebService.PENDING_AMOUNT_SERVICE + shpf.getString("user_id", ""));
			return response;
		}
		catch(Exception e){

		}
		return null;
	}


	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(result!=null){
			try{
				JSONObject jobj=new JSONObject(result);
				String status=jobj.getString("status");
				if(status.equals("1"))
				{
					if(jobj.getString("amount").equals("0"))
					{
						DashboardActivity.onCustomActionView(""+jobj.getString("amount"));
					}
					else
					{
						DashboardActivity.onCustomActionView(""+ Math.round(jobj.getDouble("amount")));
					}
					
					//((TextView)cxt.findViewById(R.id.amount_text)).setText(""+Math.round(jobj.getDouble("amount")));

				}

			}
			catch(Exception e){
				DashboardActivity.onCustomActionView("0");

			}
		}
	}



}
