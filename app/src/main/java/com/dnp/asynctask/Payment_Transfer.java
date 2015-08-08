package com.dnp.asynctask;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dealnprice.activity.Constant;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

public class Payment_Transfer extends AsyncTask<String, Void, String> {

	Context cxt;
	SharedPreferences shpf;
	//ProgressDialog pDialog;

	public Payment_Transfer(Context cxt){
		this.cxt=cxt;
		shpf=cxt.getSharedPreferences(Constant.pref_name, 1);
		//pDialog=new ProgressDialog(cxt);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//pDialog.show(cxt, "Tranfering Payment..","Transfer Payment");
		//pDialog.setCancelable(false);
	}

	@Override
	protected String doInBackground(String... arg0) {

		try{
			Log.e("user id" , WebService.PAYMENT_TRANSFER_SERVICE + shpf.getString("user_id", ""));
			String response=HttpRequest.post(WebService.PAYMENT_TRANSFER_SERVICE + shpf.getString("user_id", ""));

			Log.e("user id response" , response);
			return response;
		}
		catch(Exception e){

		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//pDialog.dismiss();
		Log.e("user id response" , result);
		if(result!=null){
			//UtilMethod.showToast("Result is "+result, cxt);
			//pDialog.dismiss();
			//pDialog.cancel();
			try{
				JSONObject jobj=new JSONObject(result);
				String message=jobj.getString("message");
				if(message.equals("1"))
				{
					Toast.makeText(cxt, "Congratulations! Your redemption request has been received. It will be processed in the next 3 to 5 business days.", Toast.LENGTH_LONG).show();
				}
				else if(message.equals("2"))
				{
					Toast.makeText(cxt, "You are requested to raise cash redemption request when you have minimum Rs 250 approved cash back in your wallet..", Toast.LENGTH_LONG).show();
				}
				else if(message.equals("3"))
				{
					Toast.makeText(cxt, "Not Valid User", Toast.LENGTH_LONG).show();
				}
				else
				{

				}
			}
			catch(Exception e){

			}
		}
	}


}
