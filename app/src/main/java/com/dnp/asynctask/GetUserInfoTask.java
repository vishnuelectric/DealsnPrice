package com.dnp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.dnp.bean.FavouriteBean;
import com.dnp.bean.MyAccountBean;
import com.dnp.bean.UserBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.fragment.BankTransferFragment.BankListener;
import com.dnp.fragment.DNPMyAccountFragment.MyAccountListener;
import com.dnp.fragment.FavouriteFragment.FavouriteListener;
import com.dnp.fragment.ProfileFragment.ProfileListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetUserInfoTask extends AsyncTask<String, Void, String>{
	Context cxt;
	ProfileListener pListener;
	//ProgressDialog pdialog;
	FavouriteListener fListener;
	BankListener bankListener;
	MyAccountListener maListener; 
	
	
	public GetUserInfoTask(Context cxt,ProfileListener plistener){
		this.cxt=cxt;
		this.pListener=plistener;
		//pdialog=new ProgressDialog(cxt);
	}
	public GetUserInfoTask(Context cxt,FavouriteListener flistener){
		this.cxt=cxt;
		this.fListener=flistener;
	//	pdialog=new ProgressDialog(cxt);
	}
	public GetUserInfoTask(Context cxt,BankListener banklistener){
		this.cxt=cxt;
		this.bankListener=banklistener;
	//	pdialog=new ProgressDialog(cxt);
	}
	
	public GetUserInfoTask(Context cxt,MyAccountListener malistener){
		this.cxt=cxt;
		this.maListener=malistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			String st=WebService.USER_INFO_SERVICE+cxt.getSharedPreferences("User_login",1).getString("user_id",null);
		String response=HttpRequest.post(WebService.USER_INFO_SERVICE+cxt.getSharedPreferences("User_login",1).getString("user_id",null));
			System.out.println("user info  "+response);
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
			if(status.equals("1")){
				StaticData.user_info.clear();
				StaticData.user_account.clear();
				StaticData.favourite_list.clear();
				StaticData.my_paid_detail.clear();
				StaticData.account_detail.clear();
				StaticData.order_filter_list.clear();
				StaticData.offer_user.clear();
				
				
				JSONObject object=jobj.getJSONObject("userlogininfo");
				UserBean ubean=new UserBean();
				ubean.setUser_id(object.getString("od_id"));
				ubean.setUser_name(object.getString("od_user_first_name")+" "+object.getString("od_user_last_name"));
				ubean.setUser_email(object.getString("od_user_email"));
				ubean.setUser_mobile(object.getString("od_user_mobile"));
				ubean.setUser_pass(object.getString("od_user_password"));
				ubean.setTotal_amount(jobj.getString("totalamount"));
				StaticData.user_info.add(ubean);
				/*JSONArray account_array=jobj.optJSONArray("accountinfo");*/
				if(jobj.optJSONObject("accountinfo")!=null){
				JSONObject account_object=jobj.optJSONObject("accountinfo");
				UserBean ubean1=new UserBean();
				ubean1.setUser_id(object.getString("od_id"));
				ubean1.setAccount_holder_name(account_object.getString("account_holder"));
				ubean1.setAccount_no(account_object.getString("account_no"));
				ubean1.setBank_name(account_object.getString("account_bank"));
				ubean1.setBranch_name(account_object.getString("account_branch"));
				ubean1.setIfsc_code(account_object.getString("account_ifsc"));
				ubean1.setPan_no(account_object.getString("account_pan"));
				ubean1.setTotal_amount(jobj.getString("totalamount"));
				StaticData.user_account.add(ubean1);
				}
				
				/*if(jobj.optJSONArray("fabmobile").equals("true")){*/
					JSONArray favourite_array=jobj.getJSONArray("fabmobile");
					for(int i=0;i<favourite_array.length();i++){
						JSONObject favourite_object=favourite_array.getJSONObject(i);
						FavouriteBean fbean=new FavouriteBean();
						fbean.setProduct_id(favourite_object.getString("pdid")); 
						fbean.setProduct_image(favourite_object.getString("pdimage"));
						fbean.setProduct_name(favourite_object.getString("pdname"));
						fbean.setProduct_price(favourite_object.getString("pdprice"));
						fbean.setProduct_type(favourite_object.getString("ptype"));
						StaticData.favourite_list.add(fbean);
				/*}*/
				}
					JSONArray favouritecom_array=jobj.getJSONArray("fabcomputers");
					for(int i=0;i<favouritecom_array.length();i++){
						JSONObject favourite_object=favouritecom_array.getJSONObject(i);
						FavouriteBean fbean=new FavouriteBean();
						fbean.setProduct_id(favourite_object.getString("pdid")); 
						fbean.setProduct_image(favourite_object.getString("pdimage"));
						fbean.setProduct_name(favourite_object.getString("pdname"));
						fbean.setProduct_price(favourite_object.getString("pdprice"));
						fbean.setProduct_type(favourite_object.getString("ptype"));
						StaticData.favourite_list.add(fbean);
				
				}
					JSONArray favouritecam_array=jobj.getJSONArray("fabcamera");
					for(int i=0;i<favouritecam_array.length();i++){
						JSONObject favourite_object=favouritecam_array.getJSONObject(i);
						FavouriteBean fbean=new FavouriteBean();
						fbean.setProduct_id(favourite_object.getString("pdid")); 
						fbean.setProduct_image(favourite_object.getString("pdimage"));
						fbean.setProduct_name(favourite_object.getString("pdname"));
						fbean.setProduct_price(favourite_object.getString("pdprice"));
						fbean.setProduct_type(favourite_object.getString("ptype"));
						StaticData.favourite_list.add(fbean);
				
				}
					JSONArray favouriteother_array=jobj.getJSONArray("productoth");
					for(int i=0;i<favouriteother_array.length();i++){
						JSONObject favourite_object=favouriteother_array.getJSONObject(i);
						FavouriteBean fbean=new FavouriteBean();
						fbean.setProduct_id(favourite_object.getString("pdid")); 
						fbean.setProduct_image(favourite_object.getString("pdimage"));
						fbean.setProduct_name(favourite_object.getString("pdname"));
						fbean.setProduct_price(favourite_object.getString("pdprice"));
						fbean.setProduct_type(favourite_object.getString("ptype"));
						StaticData.favourite_list.add(fbean);
				}
					
					
				JSONArray cashback_array=jobj.getJSONArray("cashback");
				for(int j=0;j<cashback_array.length();j++){
					JSONObject cashback_object=cashback_array.getJSONObject(j);
					MyAccountBean abean=new MyAccountBean();
					abean.setCashback_amount(cashback_object.getString("cashback_amount"));
					abean.setCashback_approve(cashback_object.getString("cashback_approve"));
					abean.setCashback_status(cashback_object.getString("cashback_status"));
					abean.setCashback_type(cashback_object.getString("cashback_type"));
					abean.setCashback_order(cashback_object.getString("cashback_order"));
					abean.setCashback_date(cashback_object.getString("cashback_date"));
					abean.setCashback_confirm_date(cashback_object.getString("cashback_confirm"));
					abean.setCashback_paid_date(cashback_object.getString("cashback_paid"));
					abean.setCashback_canclled_date(cashback_object.getString("cashback_canclled"));
					JSONObject publish_object=jobj.getJSONObject("publishcode");
					abean.setRetailer_name(publish_object.getString(cashback_object.getString("cashback_compaign")));
					//abean.setConversion_status(cashback_object.getString("conversion_status"));
					StaticData.account_detail.add(abean);
					StaticData.order_filter_list.add(abean);
				}
				JSONArray paid_array=jobj.getJSONArray("paiddetails");
				for(int k=0;k<paid_array.length();k++){
					JSONObject paid_object=paid_array.getJSONObject(k);
					MyAccountBean paid_bean=new MyAccountBean();
					paid_bean.setPaid_amount_value(paid_object.getString("amount_value"));
					paid_bean.setAmount_user_get(paid_object.getString("amount_user_get"));
					paid_bean.setPaid_amount_type(paid_object.getString("amount_type"));
					paid_bean.setPaid_amount_tax(paid_object.getString("amount_tax"));
					paid_bean.setPaid_amount_total(paid_object.getString("amount_total"));
					paid_bean.setPaid_amount_transaction(paid_object.getString("amount_transaction"));
					//paid_bean.setPaid_amount_transaction(paid_object.getString("amount_mop"));
					paid_bean.setPaid_amount_date(paid_object.getString("amount_date"));
					paid_bean.setPaid_amount_update(paid_object.getString("amount_update"));
					paid_bean.setPaid_amount_mop(paid_object.getString("amount_transaction"));
					paid_bean.setPaid_amount_paid_type(paid_object.getString("amount_paid_type"));
					paid_bean.setPaid_amount__paid_date(paid_object.getString("amount_paid_date"));
					
					StaticData.my_paid_detail.add(paid_bean);
				}
				
				JSONArray offer_array=jobj.getJSONArray("offeruser");
				for(int k=0;k<offer_array.length();k++){
					JSONObject offer_object=offer_array.getJSONObject(k);
					MyAccountBean offer_bean=new MyAccountBean();
					offer_bean.setConversion_status(offer_object.getString("conversion_status"));
					offer_bean.setConversion_date(offer_object.getString("conversion_date"));
					offer_bean.setOffername(offer_object.getString("offer_name"));
					offer_bean.setConversion_amount_user(offer_object.getString("conversion_amount_user"));

					StaticData.offer_user.add(offer_bean);
				}
				
				
				
				
				if(pListener!=null){
				pListener.onSuccess();
				}
				else if(fListener!=null){
					if(StaticData.favourite_list.size()>0){
					fListener.onSuccess();
					}
					else{
						fListener.onError("No Favourite Product!");
					}
				}
				else if(bankListener!=null){
					bankListener.onSuccess();
				}
				else if(maListener!=null){
					maListener.onSuccess();
				}
			}
			else{
				if(pListener!=null){
				pListener.onError("No User Found!");
				} 
				else if(fListener!=null){
					fListener.onError("No Favourite Product!");
				}
				else if(bankListener!=null){
					bankListener.onError("No User Found!");
				}
				else if(maListener!=null){
					maListener.onError("No User Found!");
				}
			}
			}
			catch(Exception e){
				if(pListener!=null){
				pListener.onSuccess();
				}
				else if(fListener!=null){
					fListener.onSuccess();
				}
				else if(bankListener!=null){
					bankListener.onSuccess();
				}
				else if(maListener!=null){
					maListener.onSuccess();
				}
			}
			
		}
		else{
			if(pListener!=null){
			pListener.onError("slow");
			}
			else if(fListener!=null){
				fListener.onError("slow");
			}
			else if(bankListener!=null){
				bankListener.onError("slow");
			}
			else if(maListener!=null){
				maListener.onError("slow");
			}
		}
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	//	UtilMethod.showLoading(pdialog, cxt);
	}

}
