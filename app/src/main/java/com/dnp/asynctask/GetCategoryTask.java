package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dnp.bean.CategoryBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.DNPDealCouponFragment.DealCouponListener;
import com.dnp.fragment.PriceComparisonFragment.PriceComparisonListener;

public class GetCategoryTask extends AsyncTask<String, Void, String>{
	Context cxt;
	//ProgressDialog pdialog;
	PriceComparisonListener pcListener;
	DealCouponListener dcListener;
	String multipartString;
	int value;
	public GetCategoryTask(Context cxt,PriceComparisonListener pclistener){
		this.cxt=cxt;
		this.pcListener=pclistener;
		//pdialog=new ProgressDialog(cxt);
		value=0;
	}
	public GetCategoryTask(Context cxt){
		this.cxt=cxt;
		value=0;
	}
	
	public GetCategoryTask(Context cxt,DealCouponListener dclistener){
		this.cxt=cxt;
		this.dcListener=dclistener;
		value=0;
	}
	
	public GetCategoryTask(Context cxt,PriceComparisonListener pclistener, String category_id){
		this.cxt=cxt;
		this.pcListener=pclistener;
		//pdialog=new ProgressDialog(cxt);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.CATEGORY_SERVICE);
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
			try
			{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("check");
			StaticData.category_list.clear();
			StaticData.category_fixed_list.clear();
			StaticData.category_deal_list.clear();
			if(status.equals("1")){
				JSONArray jarray=jobj.getJSONArray("list");
				boolean flag=false;
				boolean deal_flag=false;
				for(int i=0;i<jarray.length();i++){
					JSONObject object=jarray.getJSONObject(i);
					JSONArray child_array=object.getJSONArray("catchild");
					for(int j=0;j<child_array.length();j++){
						flag=false;
						deal_flag=false;
					JSONObject child_object=child_array.getJSONObject(j);
					CategoryBean cbean=new CategoryBean();
					cbean.setCategory_id(object.getString("catid"));
					cbean.setCategory_name(object.getString("catname"));
					cbean.setCategory_image(object.getString("catimage"));
					cbean.setLink(object.getString("link"));
					cbean.setLink_value(object.getString("linkvalue"));
					cbean.setSubcategory_link(child_object.getString("link"));
					cbean.setSubcategory_link_value(child_object.getString("linkvalue"));
					boolean category_flag=false;
					if(StaticData.category_fixed_list.size()>0){
					for(int l=0;l<StaticData.category_fixed_list.size();l++){
						if(object.getString("catname").equals(StaticData.category_fixed_list.get(l).getCategory_name())){
							category_flag=true;
							break;
						}
					}
					if(!category_flag && (child_object.getString("link").equals("coupon") || child_object.getString("link").equals("deals"))){
						cbean.setSubcategory_id(child_object.getString("catid"));
						cbean.setSubcategory_name(child_object.getString("catname"));
						StaticData.category_fixed_list.add(cbean);
						category_flag=false;
					}
					}
					if(StaticData.category_fixed_list.size()==0 && (child_object.getString("link").equals("coupon") || child_object.getString("link").equals("deals"))){
						cbean.setSubcategory_id(child_object.getString("catid"));
						cbean.setSubcategory_name(child_object.getString("catname"));
						StaticData.category_fixed_list.add(cbean);
					}
					
					
					if(StaticData.category_deal_list.size()>0){
						for(int u=0;u<StaticData.category_deal_list.size();u++){
							//if((StaticData.category_deal_list.get(u).getSubcategory_id().equals(child_object.getString("catid")) && !StaticData.category_deal_list.get(u).getCategory_name().equals(object.getString("catname")) && !StaticData.category_deal_list.get(u).getSubcategory_name().equals(child_object.getString("catname")) )&& ((child_object.getString("catname").equals("Deals")) || child_object.getString("catname").equals("Coupon"))){
							/*if((!StaticData.category_deal_list.get(u).getCategory_id().equals(object.getString("catid")) && !StaticData.category_deal_list.get(u).getSubcategory_id().equals(child_object.getString("catid"))) && ((!child_object.getString("catname").equals("Deals")) && !child_object.getString("catname").equals("Coupon"))){
								deal_flag=true;
								break;
							}*/
							if(((child_object.getString("catname").equals("Deals")) || child_object.getString("catname").equals("Coupon")) && (!StaticData.category_deal_list.get(u).getCategory_id().equals(object.getString("catid"))) && !StaticData.category_deal_list.get(u).getSubcategory_id().equals(child_object.getString("catid")) && !StaticData.category_deal_list.get(u).getCategory_id().equals(object.getString("catid")) && !StaticData.category_deal_list.get(u).getLink().equals(child_object.getString("link"))){
								cbean.setSubcategory_id(child_object.getString("catid"));
								cbean.setSubcategory_name(child_object.getString("catname"));
								StaticData.category_deal_list.add(cbean);
								break;
							}
								
		
						}
						/*if(!deal_flag){
							cbean.setSubcategory_id(child_object.getString("catid"));
							cbean.setSubcategory_name(child_object.getString("catname"));
							StaticData.category_deal_list.add(cbean);
						}*/
						
					}
					if(StaticData.category_deal_list.size()==0 && (child_object.getString("catname").equals("Deals") || child_object.getString("catname").equals("Coupon"))){
						cbean.setSubcategory_id(child_object.getString("catid"));
						cbean.setSubcategory_name(child_object.getString("catname"));
						StaticData.category_deal_list.add(cbean);
					}
					
					
					
					
					if(StaticData.category_list.size()>0){
					for(int k=0;k<StaticData.category_list.size();k++){
						if(StaticData.category_list.get(k).getSubcategory_id().equals(child_object.get("catid")) && StaticData.category_list.get(k).getSubcategory_name().equals(child_object.getString("catname"))){
							
							flag=true;
							break;
						}
					}
					if(flag==false && !child_object.getString("catname").equals("Coupon") && !child_object.getString("catname").equals("Deals") && !child_object.getString("catname").equals("Price Comparison")){
					cbean.setSubcategory_id(child_object.getString("catid"));
					cbean.setSubcategory_name(child_object.getString("catname"));
					StaticData.category_list.add(cbean);
					flag=false;
					}
					
					}
					
					else{
						if(!child_object.getString("catname").equals("Coupon") && !child_object.getString("catname").equals("Deals") && !child_object.getString("catname").equals("Price Comparison")){
						cbean.setSubcategory_id(child_object.getString("catid"));
						cbean.setSubcategory_name(child_object.getString("catname"));
						StaticData.category_list.add(cbean);
						}
						/*else{
							cbean.setSubcategory_id(child_object.getString("catid"));
							cbean.setSubcategory_name(child_object.getString("catname"));
							StaticData.category_deal_list.add(cbean);
						}*/
					}
					
				}
					
					
				}
				
				if(pcListener!=null){
					/*if(value==0){
						for(int i1=0;i1<StaticData.category_list.size();i1++){
						if(StaticData.category_list.get(i1).getSubcategory_name().equals("Price Comparison")){
							
						multipartString=WebService.WEB_HOST_URL+"jsonproduct/"+StaticData.category_list.get(i1).getLink()+"/?type="+StaticData.category_list.get(i1).getLink_value()+"&catid="+StaticData.category_list.get(i1).getSubcategory_id()+"&urlcheck=1";               
							break;	
						}
						}
						new GetPriceComparisonTask(cxt, multipartString,pcListener).execute();
					}*/
					pcListener.onSuccess();
				}
				else if(dcListener!=null){
					if(value==0){
						/*multipartString=WebService.WEB_HOST_URL+"jsonproduct/coupon?urlcheck=1";
						new GetCouponProductTask(cxt,multipartString,dcListener).execute();*/
						dcListener.onSuccessCoupon();
					}
				}
				//UtilMethod.showToast("List of Category Fixed is "+StaticData.category_fixed_list.size(), cxt);
				//UtilMethod.showToast("List of Category is "+StaticData.category_list.size(), cxt);
				
			}
			}
			catch(Exception e){
				if(value==0){
					if(pcListener!=null){
					pcListener.onSuccess();
					}
					else if(dcListener!=null){
						dcListener.onSuccess();
					}
				}
				/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
				//UtilMethod.showToast("Category Exception is "+e, cxt);
				//UtilMethod.showToast("List of Category is "+StaticData.category_list.size(), cxt);
				//UtilMethod.showToast("List of Category Fixed is "+StaticData.category_fixed_list.size(), cxt);
				//UtilMethod.showToast("List of Category Deal is "+StaticData.category_deal_list.size(), cxt);
				/*for(int i1=0;i1<StaticData.category_fixed_list.size();i1++){
					UtilMethod.showToast("Sub Category Name is "+StaticData.category_fixed_list.get(i1).getSubcategory_name()+" "+StaticData.category_fixed_list.get(i1).getSubcategory_id()+" "+StaticData.category_fixed_list.get(i1).getCategory_name()+" "+StaticData.category_fixed_list.get(i1).getSubcategory_link(), cxt);
				}*/
			}
		}
		else{
			/*if(pdialog!=null && pdialog.isShowing()){
				pdialog.dismiss();
			}*/
			UtilMethod.showServerError(cxt);
			if(pcListener!=null){
				pcListener.onError("slow");
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		/*UtilMethod.showLoading(pdialog, cxt);*/
	}
}
