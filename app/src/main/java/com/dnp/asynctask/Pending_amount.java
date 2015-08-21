package com.dnp.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.dealnprice.activity.Constant;
import com.dealnprice.activity.DashboardActivity;
import com.dnp.data.APP_Constants;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

import org.json.JSONObject;

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
						Log.e(" "," 000 Amount is 0");
						DashboardActivity.onCustomActionView(""+jobj.getString("amount"));
					}
					else
					{
						Log.e(" ","  Amount is "+jobj.getString("amount"));
						DashboardActivity.onCustomActionView(""+ Math.round(jobj.getDouble("amount")));
					}
					/**
					 * Code Below Shows Notification whenever ther's a change in users AMOUNT
					 */
					int finalAmount	=	(int) Math.round(jobj.getDouble("amount"));
					if(APP_Constants.AMOUNT_COUNTER > 0)
					{
						/*if(finalAmount != APP_Constants.AMOUNT)
						{
							/**
							 * Put Notifications Code here for Money Received

							if(DashboardActivity.actRef!=null)
							{
								DashboardActivity.actRef.showNotification(APP_Constants.MONEY_RECIEVED, "Dealsnprice");
							}
						}*/
					}
					if(finalAmount >= 0) //avoid -ve amounts
					{
						APP_Constants.AMOUNT	=	finalAmount;
						APP_Constants.AMOUNT_COUNTER++;
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
