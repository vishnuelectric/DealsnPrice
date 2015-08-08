package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.bean.UserBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.BankTransferFragment.BankTransferListener;
import com.dnp.fragment.DNPMyAccountBankFragment.MyAccountBTListener;
import com.dnp.fragment.PaymentSettingFragment.PaymentListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UpdateBankDetailTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	PaymentListener pListener;
	MyAccountBTListener mabtListener;
	//ProgressDialog pdialog;
	BankTransferListener btListener;
	public UpdateBankDetailTask(Context cxt,MultipartEntity multipart,PaymentListener plistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.pListener=plistener;
		//pdialog=new ProgressDialog(cxt);
	}
	public UpdateBankDetailTask(Context cxt,MultipartEntity multipart,BankTransferListener btlistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.btListener=btlistener;
		//pdialog=new ProgressDialog(cxt);
	}
	public UpdateBankDetailTask(Context cxt,MultipartEntity multipart,MyAccountBTListener mabtlistener){
		this.cxt=cxt;
		this.multipart=multipart;
		this.mabtListener=mabtlistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.ACCOUNT_DETAIL_SERVICE, multipart);
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
			//UtilMethod.showToast("Result is "+result, cxt);
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("status");
			StaticData.user_account.clear();
			UserBean ubean=new UserBean();
			ubean.setAccount_holder_name(jobj.getString("account_holder_name"));
			ubean.setAccount_no(jobj.getString("account_number"));
			ubean.setBank_name(jobj.getString("bank_name"));
			ubean.setBranch_name(jobj.getString("branch_name"));
			ubean.setIfsc_code(jobj.getString("ifsc_code")); 
			ubean.setPan_no(jobj.getString("pan_no"));
			StaticData.user_account.add(ubean);
			if(status.equals("1")){
				
				if(pListener!=null){
				pListener.onSuccess("Account Information Successfuly Updated.");
				}
				else if(btListener!=null){
					btListener.onSuccess("Account Information Successfuly Updated.");
				}
				else if(mabtListener!=null){
					mabtListener.onSuccess("Account Information Successfuly Updated.");
				}
				
				
			}
			else if(status.equals("5")){
				if(pListener!=null){
				pListener.onError("Please Enter Correct Current Password.");
				}
				else if(btListener!=null){
					btListener.onError("Please Enter Correct Current Password.");
				}
				else if(mabtListener!=null){
					mabtListener.onError("Please Enter Correct Current Password.");
				}
			}
			
			else if(status.equals("6")){
				if(pListener!=null){
				pListener.onError("User Id Not Valid.");
				}
				else if(btListener!=null){
					btListener.onError("User ID not Valid");
				}
				else if(mabtListener!=null){
					mabtListener.onError("User ID not Valid");
				}
			}
			else{
				if(pListener!=null){
				pListener.onError("Please Enter Correct Information.");
				}
				else if(btListener!=null){
					btListener.onError("Please Enter Correct Information.");
				}
				else if(mabtListener!=null){
					mabtListener.onError("Please Enter Correct Information.");
				}
			}
			
			
			
			}
			catch(Exception e){
				
			}
		}
		else{
			if(pListener!=null){
			pListener.onError("slow");
			}
			else if(btListener!=null){
				btListener.onError("slow");
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
