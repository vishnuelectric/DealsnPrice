package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.FavouriteAlertFragment.FAlertListener;
import com.dnp.fragment.FavouriteFragment.FavouriteListener;
import com.dnp.fragment.PriceComparisonDropAlertFragment.PCAlertListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ProductAlertTask extends AsyncTask<String, Void, String>{
	PCAlertListener pcaListener;
	Context cxt;
	MultipartEntity multipart;
	FavouriteListener flistener;
	String product_id;
	String target_price;
	String product_duration;
	//ProgressDialog pdialog;
	FAlertListener faListener;
	public ProductAlertTask(Context cxt,MultipartEntity multipart,PCAlertListener pcalistener,String product_id,String target_price,String product_duration){
		this.pcaListener=pcalistener;
		this.cxt=cxt;
		this.multipart=multipart;
		this.product_duration=product_duration;
		this.product_id=product_id;
		this.target_price=target_price;
	//	pdialog=new ProgressDialog(cxt);
	}
	public ProductAlertTask(Context cxt,MultipartEntity multipart,FAlertListener falistener,String product_id,String target_price,String product_duration){
		this.faListener=falistener;
		this.cxt=cxt;
		this.multipart=multipart;
		this.product_duration=product_duration;
		this.product_id=product_id;
		this.target_price=target_price;
	//	pdialog=new ProgressDialog(cxt);
	}
	public ProductAlertTask(Context cxt,MultipartEntity multipart,FavouriteListener flistener,String product_id,String target_price,String product_duration){
		this.flistener=flistener;
		this.cxt=cxt;
		this.multipart=multipart;
		this.product_duration=product_duration;
		this.product_id=product_id;
		this.target_price=target_price;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.PRODUCT_ALERT_SERVICE, multipart);
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
			String status=jobj.getString("status");
			if(status.equals("1")){
				if(pcaListener!=null){
				pcaListener.onSuccess("Alert Created Successfully",product_id,target_price,product_duration);
				}
				else if(faListener!=null){
					faListener.onSuccess("Alert Created Successfully",product_id,target_price,product_duration);
				}
				else if(flistener!=null){
					flistener.onFixAlert("Alert Created Successfully",product_id,target_price,product_duration);
				}
				
			}
			else if(status.equals("2")){
				if(pcaListener!=null){
				pcaListener.onError("Please Enter Valid Email ID");
				}
				else if(faListener!=null){
					faListener.onError("Please Enter Valid Email ID");
				}
				else if(flistener!=null){
					flistener.onError("Please Enter Valid Email ID");
				}
			}
			else if(status.equals("3")){
				if(pcaListener!=null){
				pcaListener.onError("Please Enter Required Parameter");
				}
				else if(faListener!=null){
					faListener.onError("Please Enter Required Parameter");
				}
			}
			else if(status.equals("4")){
				if(pcaListener!=null){
				pcaListener.onError("Internal Problem");
				}
				else if(faListener!=null){
					faListener.onError("Internal Problem");
				}
			}
			else if(status.equals("4")){
				if(pcaListener!=null){
				pcaListener.onError("Please ReLogin");
				}
				else if(faListener!=null){
					faListener.onError("Please ReLogin");
				}
			}
			}
			catch(Exception e){
				
			}
		}
		else{
			if(pcaListener!=null){
			pcaListener.onError("slow");
			}
			else if(faListener!=null){
				faListener.onError("slow");
			}
		}
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	//	UtilMethod.showLoading(pdialog, cxt);
	}

}
