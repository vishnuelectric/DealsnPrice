package com.dnp.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dnp.bean.ShopOfferBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.fragment.ShopEarnFragment.ShopEarnListener;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetShopListTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	//ProgressDialog pdialog;
	ShopEarnListener seListener;
	public GetShopListTask(Context cxt,MultipartEntity multipart,ShopEarnListener selistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.seListener=selistener;
		//pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			
			String response=HttpRequest.post(WebService.GET_SHOPPING_LIST_SERVICE);
			return response;
			}
			catch(Exception e){
				
			}
		return null;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	//	UtilMethod.showLoading(pdialog, cxt);
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
			JSONObject obj=new JSONObject(result);
			String status=obj.getString("check");
			if(status.equals("1")){
				StaticData.shop_offer_list.clear();
				StaticData.shop_offer_search.clear();
				JSONArray jarray=obj.getJSONArray("storelist");
				for(int i=0;i<jarray.length();i++){
					JSONObject jobj=jarray.getJSONObject(i);
					ShopOfferBean sobean=new ShopOfferBean();
					sobean.setShop_id(jobj.getString("id"));
					sobean.setShop_name(jobj.getString("name"));
					Log.e("GetShopList "," "+jobj.getString("name"));
					sobean.setShop_image(jobj.getString("image"));
					sobean.setShop_url(jobj.getString("storelink"));
					sobean.setShop_offer_name(jobj.getString("description"));
					/*sobean.setShop_offer_id(jobj.getString("offer_id"));*/
					//sobean.setShop_offer_name(jobj.getString("description"));
					sobean.setShop_detaillink(jobj.getString("detaillink"));
					StaticData.shop_offer_list.add(sobean);
					StaticData.shop_offer_search.add(sobean);
					StaticData.shop_search.add(jobj.getString("name"));
				}
				seListener.onSuccess(); 
			}
			else{
				
			}
			
			}
			catch(Exception e){
				//UtilMethod.showToast("Exception is "+e, cxt);
			}
		}
	}
	
}
