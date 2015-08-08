package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.Payment_Transfer;
import com.dnp.asynctask.Pending_amount;
import com.dnp.asynctask.UpdateBankDetailTask;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class DNPMyAccountBankFragment extends Fragment{
	View view;
	LinearLayout overview_layout,payout_status_layout,order_status_layout,missing_cashback_layout;
	LinearLayout footer_layout;
	EditText account_holder_name,bank_name,branch_name,account_number,ifsc_code,pan_no,password;
	TextView save_change;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	String s_account_holdername,s_account_number,s_bank_name,s_branch_name,s_ifsc_code,s_pan_no,s_password;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_myaccount_bank, container, false);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		overview_layout=(LinearLayout) view.findViewById(R.id.overview_layout);
		payout_status_layout=(LinearLayout) view.findViewById(R.id.payout_status_layout);
		order_status_layout=(LinearLayout) view.findViewById(R.id.order_status_layout);
		missing_cashback_layout=(LinearLayout) view.findViewById(R.id.missing_cashback_layout);
		account_holder_name=(EditText) view.findViewById(R.id.account_holder_name);
		bank_name=(EditText) view.findViewById(R.id.bank_name);
		branch_name=(EditText) view.findViewById(R.id.branch_name);
		account_number=(EditText) view.findViewById(R.id.account_number);
		password=(EditText) view.findViewById(R.id.password);
		ifsc_code=(EditText) view.findViewById(R.id.ifsc_code);
		pan_no=(EditText) view.findViewById(R.id.pan_no);
		save_change=(TextView) view.findViewById(R.id.save_change);
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		save_change.setOnClickListener(saveListener);
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		overview_layout.setOnClickListener(overviewListener);
		missing_cashback_layout.setOnClickListener(missingcashbackListener);
		order_status_layout.setOnClickListener(orderstatusListener);
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.black));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		account_holder_name.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		account_number.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		bank_name.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		branch_name.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		pan_no.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		ifsc_code.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		if(StaticData.user_account.size()>0){
			account_holder_name.setEnabled(false);
			account_number.setEnabled(false);
			bank_name.setEnabled(false);
			branch_name.setEnabled(false);
			pan_no.setEnabled(false);
			ifsc_code.setEnabled(false);
			account_holder_name.setText(StaticData.user_account.get(0).getAccount_holder_name());
			account_number.setText(StaticData.user_account.get(0).getAccount_no());
			bank_name.setText(StaticData.user_account.get(0).getBank_name());
			branch_name.setText(StaticData.user_account.get(0).getBranch_name());
			pan_no.setText(StaticData.user_account.get(0).getPan_no());
			ifsc_code.setText(StaticData.user_account.get(0).getIfsc_code());
			save_change.setText("Transfer");
		}
		else
		{
			account_holder_name.setEnabled(true);
			account_number.setEnabled(true);
			bank_name.setEnabled(true);
			branch_name.setEnabled(true);
			pan_no.setEnabled(true);
			ifsc_code.setEnabled(true);
			save_change.setText("Update Detail");
		}
		
		return view;
	}
	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			ft.replace(R.id.container, fragment);
			ft.commit();
		}
	};
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener notificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener saveListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(StaticData.user_account.size()>0){
				Payment_Transfer pp=new Payment_Transfer(getActivity());
				pp.execute();
			}
			else
			{
				s_account_holdername=account_holder_name.getText().toString();
				s_account_number=account_number.getText().toString();
				s_bank_name=bank_name.getText().toString();
				s_branch_name=branch_name.getText().toString();
				s_ifsc_code=ifsc_code.getText().toString();
				s_pan_no=pan_no.getText().toString();
				s_password=password.getText().toString();
				if(UtilMethod.isStringNullOrBlank(s_account_holdername)){
					UtilMethod.showToast(getResources().getString(R.string.account_holder_name_warning), getActivity());
				}
				else if(UtilMethod.isStringNullOrBlank(s_bank_name)){
					UtilMethod.showToast(getResources().getString(R.string.bank_name_warning), getActivity());
				}
				else if(UtilMethod.isStringNullOrBlank(s_branch_name)){
					UtilMethod.showToast(getResources().getString(R.string.branch_name_warning),getActivity());
				}
				else if(UtilMethod.isStringNullOrBlank(s_account_number)){
					UtilMethod.showToast(getResources().getString(R.string.account_number_warning),getActivity());
				}
				else if(UtilMethod.isStringNullOrBlank(s_ifsc_code)){
					UtilMethod.showToast(getResources().getString(R.string.ifsc_code_warning), getActivity());
				}
				else if(UtilMethod.isStringNullOrBlank(s_password)){
					UtilMethod.showToast(getResources().getString(R.string.password_warning), getActivity());
				}
				else if(!getActivity().getSharedPreferences("User_login",1).getString("user_pass",null).equals(s_password)){
					UtilMethod.showToast("Incorrect Password",getActivity());
				}
				else{
					try{
						MultipartEntity multipart=new MultipartEntity();
						multipart.addPart("neft_acc_name", new StringBody(s_account_holdername));
						multipart.addPart("neft_bank", new StringBody(s_bank_name));
						multipart.addPart("neft_bank_branch",new StringBody(s_branch_name));
						multipart.addPart("neft_acc_number", new StringBody(s_account_number));
						multipart.addPart("neft_ifsc_code", new StringBody(s_ifsc_code));
						multipart.addPart("neft_ifsc_pan", new StringBody(s_pan_no));
						multipart.addPart("extension", new StringBody("1"));
						multipart.addPart("pass", new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_pass",null)));
						multipart.addPart("userid", new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id", null)));
						setProgressDialog();
						new UpdateBankDetailTask(getActivity(), multipart, new MyAccountBTListener()).execute();
						}
						catch(Exception e){
							
						}
				}
			}
			
			
		}
	};
	/*private void setProgressDialog(){
		
		dialog=new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading);
		LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
		loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
		loading_image.setBackgroundResource(R.anim.loading_animation);
		loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
		dialog.setCancelable(false);
		loadingViewAnim.start();
		
		dialog.show();
	}*/
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}
	OnClickListener overviewListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMyAccountFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener missingcashbackListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMissingCashbackFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener orderstatusListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPOrderStatusFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	public class MyAccountBTListener{
		public void onSuccess(String msg){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage(msg);
			adialog.setButton("OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					getActivity().getSupportFragmentManager().popBackStack();
				}
			});
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
				adialog.setButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
	
					}
				});	
				adialog.show();
			}
		}
	}
	
}
