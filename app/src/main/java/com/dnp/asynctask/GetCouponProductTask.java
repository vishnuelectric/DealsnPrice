package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.CouponBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;
import com.dnp.fragment.DNPCouponGridFragment.CouponGridListener;
import com.dnp.fragment.DNPDealCouponFragment.DealCouponListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetCouponProductTask extends AsyncTask<String, Void, String>{
	DealCouponListener dcListener;
	Context cxt;
	String url;
//	ProgressDialog pdialog;
	CouponGridListener cgListener;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
	sqlHelper sHeler;
	
	public GetCouponProductTask(Context cxt,String url,DealCouponListener dclistener){
		this.cxt=cxt;
		this.dcListener=dclistener;
		this.url=url;
	}
	public GetCouponProductTask(Context cxt,String url,CouponGridListener cglistener){
		this.url=url;
		this.cgListener=cglistener;
		this.cxt=cxt;
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
		
		if(result!=null){
			StaticData.coupon_product_list.clear();
			StaticData.coupon_store_list.clear();
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("check");
			sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
			sHeler.deleteCoupons();
			if(status.equals("1")){
				JSONArray jarray=jobj.getJSONArray("stroelist");
				for(int i=0;i<jarray.length();i++){
					JSONObject object=jarray.getJSONObject(i);
					CouponBean cbean=new CouponBean();
					cbean.setStore_id(object.getString("s_id"));
					cbean.setStore_name(object.getString("s_name"));
					StaticData.coupon_store_list.add(cbean);
				}
				JSONArray product_array=jobj.getJSONArray("product");
				for(int j=0;j<product_array.length();j++){
					JSONObject product_object=product_array.getJSONObject(j);
					CouponBean cbean1=new CouponBean();
					cbean1.setCategory_id(product_object.getString("c_id"));
					cbean1.setStore_image(product_object.getString("image"));
					cbean1.setCategory_name(product_object.getString("name"));
					cbean1.setStore_code(product_object.getString("code"));
					cbean1.setCoupon_home(product_object.getString("coupon_home"));
					cbean1.setCoupon_end(product_object.getString("coupon_end"));
					
					cbean1.setCoupon_store_url(product_object.getString("coupon_store_url"));
					sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					for(int i=0;i<StaticData.coupon_store_list.size();i++){
						if(StaticData.coupon_store_list.get(i).getStore_id().equals(product_object.getString("store"))){
							sHeler.Insert_CouponDetail(product_object.getString("c_id"), product_object.getString("linkvalue"), product_object.getString("name"), product_object.getString("code"), StaticData.coupon_store_list.get(i).getStore_name(), product_object.getString("image"), product_object.getString("coupon_end"), product_object.getString("coupon_home"), product_object.getString("coupon_store_url"));
							cbean1.setStore_name(StaticData.coupon_store_list.get(i).getStore_name());
							break;
						}
					}
					if(product_object.getString("coupon_home").equals("1")){
						StaticData.hot_coupon_product_list.add(cbean1);
					}
					else if(product_object.getString("coupon_home").equals("2")){
						StaticData.most_viewed_coupon_list.add(cbean1);
					}
					else{
					StaticData.coupon_product_list.add(cbean1);
					}
				}
				
				/*String multiurl=WebService.WEB_HOST_URL+"jsonproduct/deals?urlcheck=1";
				new GetDealStoreTask(cxt,multiurl,dcListener,pdialog).execute();*/
				//UtilMethod.showToast("Coupon List Size is "+StaticData.coupon_product_list.size(), cxt);
				if(dcListener!=null){
				dcListener.onSuccessCoupon();
				}
				else if(cgListener!=null){
					cgListener.onSuccess();
				}
				
				
			}
			else{
				/*String multiurl=WebService.WEB_HOST_URL+"jsonproduct/deals?urlcheck=1";
				new GetDealStoreTask(cxt,multiurl,dcListener,pdialog).execute();*/
				StaticData.hot_coupon_product_list.clear();
				StaticData.most_viewed_coupon_list.clear();
				StaticData.coupon_product_list.clear();
				if(dcListener!=null){
				dcListener.onSuccessCoupon();
				}
				else if(cgListener!=null){
					cgListener.onError("No Coupon Found");
				}
				
			}
			}
			catch(Exception e){
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
/*				UtilMethod.showToast("Exception is "+e, cxt);
				UtilMethod.showToast("Exception is "+e, cxt);
				UtilMethod.showToast("Exception is "+e, cxt);*/
				StaticData.hot_coupon_product_list.clear();
				StaticData.most_viewed_coupon_list.clear();
				StaticData.coupon_product_list.clear();
				if(dcListener!=null){
					dcListener.onSuccessCoupon();
				}
				else if(cgListener!=null){
					cgListener.onSuccess();
				}
			}
		}
		else{
			/*if(pdialog!=null && pdialog.isShowing()){
				pdialog.dismiss();
			}
			UtilMethod.showServerError(cxt);*/
			if(dcListener!=null){
			dcListener.onError();
			}
			else if(cgListener!=null){
				cgListener.onError("slow");
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
