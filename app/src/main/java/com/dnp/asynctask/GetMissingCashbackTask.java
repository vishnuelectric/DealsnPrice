package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.MissingAmountBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.fragment.DNPMissingCashbackFragment.MissingCashbackListener;

import android.content.Context;
import android.os.AsyncTask;

public class GetMissingCashbackTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MissingCashbackListener mcListener;
	
	public GetMissingCashbackTask(Context cxt,MissingCashbackListener mclistener){
		this.cxt=cxt;
		this.mcListener=mclistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.MISSING_CASHBACK_LIST_WEBSERVICE+cxt.getSharedPreferences("User_login", 1).getString("user_id", null));
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
			String status=jobj.getString("check");
			if(status.equals("1")){
				if(jobj.optJSONArray("offer")!=null){
					JSONArray offer_array=jobj.getJSONArray("offer");
					for(int i=0;i<offer_array.length();i++){
						JSONObject offer_object=offer_array.getJSONObject(i);
						MissingAmountBean mabean=new MissingAmountBean();
						mabean.setMissing_id(offer_object.getString("missing_id"));
						mabean.setMissing_date(offer_object.getString("missing_date"));
						mabean.setMissing_order(offer_object.getString("missing_order"));
						mabean.setMissing_amount(offer_object.getString("missing_amount"));
						mabean.setRetailer_name(offer_object.getString("missing_retailer"));
						mabean.setMissing_title(offer_object.getString("missing_title"));
						mabean.setMissing_status(offer_object.getString("missing_status"));
						mabean.setMissing_type(offer_object.getString("missing_type"));
						StaticData.missing_offer_detail.add(mabean);
					}
				}
				if(jobj.optJSONArray("order")!=null){
					JSONArray order_array=jobj.getJSONArray("order");
					for(int j=0;j<order_array.length();j++){
						JSONObject order_object=order_array.getJSONObject(j);
						MissingAmountBean mabean=new MissingAmountBean();
						mabean.setMissing_id(order_object.getString("missing_id"));
						mabean.setMissing_date(order_object.getString("missing_date"));
						mabean.setMissing_order(order_object.getString("missing_order"));
						mabean.setMissing_amount(order_object.getString("missing_amount"));
						mabean.setRetailer_name(order_object.getString("missing_retailer"));
						mabean.setMissing_title(order_object.getString("missing_title"));
						mabean.setMissing_status(order_object.getString("missing_status"));
						mabean.setMissing_type(order_object.getString("missing_type"));
						StaticData.missing_shop_detail.add(mabean);
					}
				}
				if(mcListener!=null){
					mcListener.onSuccess();
				}
				
			}
			
			}
			catch(Exception e){
				if(mcListener!=null){
					mcListener.onSuccess();
				}
			}
		}
	}
	
}
