package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.DealBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;
import com.dnp.fragment.DNPDealCouponFragment.DealCouponListener;
import com.dnp.fragment.DNPDealGridFragment.DealGridListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetDealStoreTask extends AsyncTask<String, Void, String>{
	Context cxt;
	DealCouponListener dcListener;
	String url;
	int value;
	DealGridListener dgListener;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
	sqlHelper sHeler;
	
	public GetDealStoreTask(Context cxt,String url,DealCouponListener dclistener){
		this.cxt=cxt;
		this.url=url;
		this.dcListener=dclistener;
		value=0;
	}
	
	public GetDealStoreTask(Context cxt,String url,DealCouponListener dclistener,int value){
		this.cxt=cxt;
		this.url=url;
		this.dcListener=dclistener;
		this.value=value;
	}
	public GetDealStoreTask(Context cxt,String url,DealGridListener dglistener){
		this.cxt=cxt;
		this.url=url;
		this.dgListener=dglistener;
		value=0;
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
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("check");
			StaticData.deal_store_list.clear();
			StaticData.deal_product_list.clear();
			if(status.equals("1")){
				sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
				sHeler.deleteDeals();
				JSONArray jarray=jobj.getJSONArray("stroelist");
				for(int i=0;i<jarray.length();i++){
					JSONObject object=jarray.getJSONObject(i);
					DealBean dbean=new DealBean();
					dbean.setStore_id(object.getString("s_id"));
					dbean.setStore_name(object.getString("s_name"));
					StaticData.deal_store_list.add(dbean);
				}
				
				JSONArray product_array=jobj.getJSONArray("product");
				for(int j=0;j<product_array.length();j++){
					JSONObject product_object=product_array.getJSONObject(j);
					DealBean dbean1=new DealBean();
					dbean1.setCategory_id(product_object.getString("c_id"));
					dbean1.setCategory_name(product_object.getString("name"));
					dbean1.setStore_code(product_object.getString("code"));
					dbean1.setStore_image(product_object.getString("storeimage"));
					dbean1.setCategory_image(product_object.getString("image"));
					dbean1.setDeals_home(product_object.getString("deals_home"));
					dbean1.setDeals_mrp(product_object.getString("deals_mrp"));
					dbean1.setDeals_end(product_object.getString("deals_end"));
					dbean1.setDeals_selling(product_object.getString("deals_selling"));
					dbean1.setDeals_store_url(product_object.getString("deals_store_url"));
					sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					for(int i=0;i<StaticData.deal_store_list.size();i++){
						if(StaticData.deal_store_list.get(i).getStore_id().equals(product_object.getString("store"))){
					sHeler.Insert_DealDetail(product_object.getString("c_id"), product_object.getString("linkvalue"), product_object.getString("name"), product_object.getString("code"), StaticData.deal_store_list.get(i).getStore_name(), StaticData.deal_store_list.get(i).getStore_name(), product_object.getString("image"), product_object.getString("storeimage"), product_object.getString("deals_end"),product_object.getString("deals_home"), product_object.getString("deals_mrp"), product_object.getString("deals_selling"), product_object.getString("deals_store_url"));
					dbean1.setStore_name(StaticData.deal_store_list.get(i).getStore_name());
					break;
						}
					}
					if(product_object.getString("deals_home").equals("1")){
						StaticData.steal_deal_list.add(dbean1);
					}
					else if(product_object.getString("deals_home").equals("2")){
						StaticData.most_viewed_deal_list.add(dbean1);
					}
					else{
					StaticData.deal_product_list.add(dbean1);
					}
				}
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
/*				if(dcListener!=null){
*/				if(dcListener!=null){
				dcListener.onSuccessAll();
				}
				else if(dgListener!=null){
					dgListener.onSuccess();
				}
				/*}*/
				
			}
			else{
				StaticData.steal_deal_list.clear();
				StaticData.most_viewed_deal_list.clear();
				StaticData.deal_product_list.clear();
				if(dcListener!=null){
					dcListener.onSuccessAll();
				}
				else if(dgListener!=null){
					dgListener.onSuccess();
				}
			}
			}
			catch(Exception e){
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
				StaticData.steal_deal_list.clear();
				StaticData.most_viewed_deal_list.clear();
				StaticData.deal_product_list.clear(); 
				
				if(dcListener!=null){
					//UtilMethod.showToast("Deal List Size is "+StaticData.deal_product_list.size(), cxt);
					dcListener.onSuccessAll();
					
				}
				else if(dgListener!=null){
					dgListener.onSuccess();
				}
				
				/*UtilMethod.showToast("Deal Exception is "+e, cxt);
				dcListener.onError();*/
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
