package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.ShopOfferBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.fragment.DNPShopDetailFragment.ShopDetailListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetShopDetailTask extends AsyncTask<String, Void, String>{
	public Context cxt;
	public String url;
	ShopDetailListener sdListener;
	//ProgressDialog pdialog;
	public GetShopDetailTask(Context cxt,String url,ShopDetailListener sdlistener){
		this.cxt=cxt;
		this.url=url;
		this.sdListener=sdlistener;
		//pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(url);
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
		/*if(pdialog!=null && pdialog.isShowing()){
			pdialog.dismiss();
		}*/
		if(result!=null){
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("check");
			StaticData.shop_offer_detail.clear();
			if(status.equals("1")){
				JSONArray jarray=jobj.getJSONArray("detaillist");
				for(int i=0;i<jarray.length();i++){
					JSONObject object=jarray.getJSONObject(i);
					ShopOfferBean sobean=new ShopOfferBean();
					sobean.setCategory_name(object.getString("name"));
					sobean.setCategory_refer(object.getString("refer"));
					sobean.setCategory_typeuse(object.getString("typeuse"));
					sobean.setCategory_value(object.getString("value"));
					sobean.setCategory_image(object.getString("image"));
					StaticData.shop_offer_detail.add(sobean);
				}
				sdListener.onSuccess();
			}
			else{
				sdListener.onError("No Detail Found!");
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			sdListener.onError("slow");
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	//	UtilMethod.showLoading(pdialog, cxt);
	}

}
