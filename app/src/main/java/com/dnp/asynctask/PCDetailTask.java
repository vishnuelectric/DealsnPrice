package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.sqlHelper;
import com.dnp.fragment.PriceComparisonSellerFragment.PCSellerListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class PCDetailTask extends AsyncTask<String, Void, String>{
	Context cxt;
	String url;
	PCSellerListener pcsListener;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
	sqlHelper sHeler;
	
	
	//ProgressDialog pdialog;
	public PCDetailTask(Context cxt,String url,PCSellerListener pcslistener){
		this.cxt=cxt;
		this.url=url;
		this.pcsListener=pcslistener;
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
			JSONObject object=new JSONObject(result);
			String check=object.getString("check");
			if(check.equals("1")){
				StaticData.pc_detail.clear();
				StaticData.pc_specification.clear();
				StaticData.pc_alternatives.clear();
				StaticData.pc_variant_detail.clear();
				JSONObject product_detail=object.getJSONObject("pd_details");
				PriceComparisonBean pcbean=new PriceComparisonBean();
				pcbean.setProduct_id(product_detail.getString("product_id"));
				pcbean.setBrand_id(product_detail.getString("product_brand"));
				pcbean.setProduct_name(product_detail.getString("product_name"));
				pcbean.setProduct_description(product_detail.getString("product_description"));
				pcbean.setImagepath(object.getString("imagepath"));
				pcbean.setProduct_image(product_detail.getString("product_image"));
				pcbean.setProduct_mrp(product_detail.getString("product_mrp"));
				pcbean.setSelling_price(product_detail.getString("product_selling_price"));
				pcbean.setProduct_feature(product_detail.getString("product_feature"));
				pcbean.setFav_status(object.getInt("fav"));
				//StaticData.pc_detail.add(pcbean);
				
				JSONArray jarray=object.getJSONArray("storelist");
				for(int i=0;i<jarray.length();i++){
					JSONArray array=jarray.getJSONArray(i);
					for(int j=0;j<array.length();j++){ 
						JSONObject store_object=array.getJSONObject(j);
						PriceComparisonBean pcbean1=new PriceComparisonBean();
						pcbean1.setStore_price(store_object.getString("store_price"));
						pcbean1.setStore_color(store_object.getString("store_color"));
						pcbean1.setStore_offer(store_object.getString("store_offer"));
						pcbean1.setStore_shipping(store_object.getString("store_shipping"));
						pcbean1.setStore_code(store_object.getString("store_cod"));
						pcbean1.setStore_deal_id(store_object.getString("store_deal_id"));
						pcbean1.setStore_image(store_object.getString("store_image"));
						pcbean1.setStore_url(store_object.getString("storurl"));
						pcbean1.setStore_coupon_offer(store_object.getString("store_coupon_offer"));
						pcbean1.setStore_discount_type(store_object.getString("store_discount_type"));
						pcbean1.setStore_offer_amount(store_object.getString("store_offer_amount"));
						pcbean1.setProduct_id(product_detail.getString("product_id"));
						pcbean1.setBrand_id(product_detail.getString("product_brand"));
						pcbean1.setProduct_name(product_detail.getString("product_name"));
						pcbean1.setProduct_description(product_detail.getString("product_description"));
						pcbean1.setImagepath(object.getString("imagepath"));
						pcbean1.setProduct_image(product_detail.getString("product_image"));
						pcbean1.setProduct_mrp(product_detail.getString("product_mrp"));
						pcbean1.setSelling_price(product_detail.getString("product_selling_price"));
						pcbean1.setProduct_feature(product_detail.getString("product_feature"));
						int value=array.length()-1;
						pcbean1.setVariant_value(""+value);
						StaticData.pc_detail.add(pcbean1);
					}
					for(int j=0;j<1;j++){
						JSONObject store_object=array.getJSONObject(0);
						PriceComparisonBean pcbean2=new PriceComparisonBean();
						pcbean2.setStore_price(store_object.getString("store_price"));
						pcbean2.setStore_color(store_object.getString("store_color"));
						pcbean2.setStore_offer(store_object.getString("store_offer"));
						pcbean2.setStore_shipping(store_object.getString("store_shipping"));
						pcbean2.setStore_code(store_object.getString("store_cod"));
						pcbean2.setStore_deal_id(store_object.getString("store_deal_id"));
						pcbean2.setStore_image(store_object.getString("store_image"));
						pcbean2.setStore_url(store_object.getString("storurl"));
						pcbean2.setStore_coupon_offer(store_object.getString("store_coupon_offer"));
						pcbean2.setStore_discount_type(store_object.getString("store_discount_type"));
						pcbean2.setStore_offer_amount(store_object.getString("store_offer_amount"));
						pcbean2.setProduct_id(product_detail.getString("product_id"));
						pcbean2.setBrand_id(product_detail.getString("product_brand"));
						pcbean2.setProduct_name(product_detail.getString("product_name"));
						pcbean2.setProduct_description(product_detail.getString("product_description"));
						pcbean2.setImagepath(object.getString("imagepath"));
						pcbean2.setProduct_image(product_detail.getString("product_image"));
						pcbean2.setProduct_mrp(product_detail.getString("product_mrp"));
						pcbean2.setSelling_price(product_detail.getString("product_selling_price"));
						pcbean2.setProduct_feature(product_detail.getString("product_feature"));
						int value1=array.length()-1;
						pcbean2.setVariant_value(""+value1);
						StaticData.pc_variant_detail.add(pcbean2);
						sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
						sHeler.Insert_StoreDetail(store_object.getString("store_price"), store_object.getString("store_color"), store_object.getString("store_offer"), store_object.getString("store_shipping"), store_object.getString("store_cod"), store_object.getString("store_deal_id"), store_object.getString("store_image"), store_object.getString("storurl"), store_object.getString("store_coupon_offer"), store_object.getString("store_discount_type"), store_object.getString("store_offer_amount"), product_detail.getString("product_id"), product_detail.getString("product_brand"), product_detail.getString("product_name"), product_detail.getString("product_description"), object.getString("imagepath"), product_detail.getString("product_image"), product_detail.getString("product_mrp"), product_detail.getString("product_selling_price"), product_detail.getString("product_feature"), ""+value1, Double.valueOf(product_detail.getString("product_selling_price")));
					}
				}
				
				JSONArray specification_array=object.getJSONArray("specifications");
				for(int k=0;k<specification_array.length();k++){
					JSONArray s_array=specification_array.getJSONArray(k);
					for(int l=0;l<s_array.length();l++){
					JSONObject specification_object=s_array.getJSONObject(l);
					PriceComparisonBean pcbean2=new PriceComparisonBean();
					pcbean2.setSpecification_left(specification_object.getString("left"));
					pcbean2.setSpecification_right(specification_object.getString("right"));
					pcbean2.setProduct_id(product_detail.getString("product_id"));
					StaticData.pc_specification.add(pcbean2);
					}
				}
				sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
				sHeler.deleteAlternative_details();
				
				JSONArray alternative_array=object.getJSONArray("alternativelist");
				/*for(int m=0;m<alternative_array.length();m++){
					JSONArray alert_array=alternative_array.getJSONArray(m);*/
					for(int n=0;n<alternative_array.length();n++){
						JSONObject alertnative_object=alternative_array.getJSONObject(n);
						PriceComparisonBean pcbean3=new PriceComparisonBean();
						pcbean3.setProduct_id(alertnative_object.getString("product_id"));
						pcbean3.setFav_status(alertnative_object.getInt("fav"));
						//pcbean3.setBrand_id(alertnative_object.getString("product_brand"));
						pcbean3.setProduct_name(alertnative_object.getString("product_name"));
					//	pcbean3.setProduct_description(alertnative_object.getString("product_description"));
						pcbean3.setProduct_image(alertnative_object.getString("product_image"));
						pcbean3.setProduct_mrp(alertnative_object.getString("product_selling_price"));
						//pcbean3.setSelling_price(alertnative_object.getString("product_selling_price"));
					//	pcbean3.setProduct_feature(alertnative_object.getString("product_feature"));
						StaticData.pc_alternatives.add(pcbean3);
						sHeler=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
						sHeler.Insert_AlternativeDetail(alertnative_object.getString("product_id"), alertnative_object.getInt("fav"), alertnative_object.getString("product_name"), alertnative_object.getString("product_image"), alertnative_object.getString("product_selling_price"), Double.valueOf(alertnative_object.getString("product_selling_price")));
					}
				/*}*/
				pcsListener.onSuccess();
			}
			
			}
			catch(Exception e){
			//	UtilMethod.showToast("Exception is "+e, cxt);
				UtilMethod.showToast("Exception is "+e, cxt);
				pcsListener.onSuccess();
			}
		}
		else{
			pcsListener.onError("slow");
		}
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	//	UtilMethod.showLoading(pdialog, cxt);
	}

}
