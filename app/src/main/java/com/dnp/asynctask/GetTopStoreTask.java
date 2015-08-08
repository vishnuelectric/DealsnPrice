package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.StoreBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.fragment.DNPDealCouponFragment.DealCouponListener;

import android.content.Context;
import android.os.AsyncTask;

public class GetTopStoreTask extends AsyncTask<String, Void, String>{
	Context cxt;
	DealCouponListener dcListener;
	public GetTopStoreTask(Context cxt, DealCouponListener dclistener){
		this.cxt=cxt;
		this.dcListener=dclistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.TOP_STORE_WEBSERVICE);
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
			StaticData.top_store_list.clear();
			if(status.equals("1")){
				JSONArray jarray=jobj.getJSONArray("list");
				for(int i=0;i<jarray.length();i++){
					JSONObject object=jarray.getJSONObject(i);
					StoreBean sbean=new StoreBean();
					sbean.setCoupon_total(object.getInt("coupontotal"));
					sbean.setDeal_count(object.getInt("dealstotal"));
					sbean.setStore_url(object.getString("store_url"));
					sbean.setStore_image(object.getString("store_image"));
					sbean.setStore_id(object.getString("store_id"));
					sbean.setStore_description(object.getString("store_description"));
					StaticData.top_store_list.add(sbean);
				}
			}
			if(dcListener!=null){
				dcListener.onSuccessWithStore();
			}
			
			}
			catch(Exception e){
				if(dcListener!=null){
					dcListener.onSuccessWithStore();
				}
			}
		}
	}
}
