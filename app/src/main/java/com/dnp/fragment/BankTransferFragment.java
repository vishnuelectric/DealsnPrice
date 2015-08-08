package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.LoginActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.GetUserInfoTask;
import com.dnp.asynctask.Payment_Transfer;
import com.dnp.asynctask.Pending_amount;
import com.dnp.asynctask.UpdateBankDetailTask;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class BankTransferFragment extends Fragment{
	EditText account_holder_name,bank_name,branch_name,account_number,ifsc_code,pan_no;
	TextView save_change;
	String s_account_holder_name,s_bank_name,s_branch_name,s_account_number,s_ifsc_code,s_pan_no;
	TextView personal_info,change_password;
	String user_id;
	SharedPreferences shpf;
	LinearLayout footer_layout;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	Dialog dialog;
	ImageView loading_image;
//	private AnimationDrawable loadingViewAnim;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_bank_transfer, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();

		account_holder_name=(EditText) view.findViewById(R.id.account_holder_name);
		bank_name=(EditText) view.findViewById(R.id.bank_name);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		branch_name=(EditText) view.findViewById(R.id.branch_name);
		account_number=(EditText) view.findViewById(R.id.account_number);
		ifsc_code=(EditText) view.findViewById(R.id.ifsc_code);
		pan_no=(EditText) view.findViewById(R.id.pan_no);
		/*personal_info=(TextView) view.findViewById(R.id.personal_info);
		change_password=(TextView) view.findViewById(R.id.change_password);*/
		save_change=(TextView) view.findViewById(R.id.save_change);

		/*personal_info.setOnClickListener(personalinfoListener);
		change_password.setOnClickListener(changepasswordListener);*/
		shpf=getActivity().getSharedPreferences("User_login", 1);
		LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile));
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.black));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		user_id=shpf.getString("user_id",null);


		if(StaticData.user_account.size()>0){
			if(user_id.equals(StaticData.user_account.get(0).getUser_id())){
				account_holder_name.setText(StaticData.user_account.get(0).getAccount_holder_name());
				account_number.setText(StaticData.user_account.get(0).getAccount_no());
				bank_name.setText(StaticData.user_account.get(0).getBank_name());
				branch_name.setText(StaticData.user_account.get(0).getBranch_name());
				ifsc_code.setText(StaticData.user_account.get(0).getIfsc_code());
				pan_no.setText(StaticData.user_account.get(0).getPan_no());
				account_holder_name.setEnabled(false);
				account_number.setEnabled(false);
				bank_name.setEnabled(false);
				branch_name.setEnabled(false);
				ifsc_code.setEnabled(false);
				pan_no.setEnabled(false);
				save_change.setText("Transfer");
			}
			else
			{
				account_holder_name.setEnabled(true);
				account_number.setEnabled(true);
				bank_name.setEnabled(true);
				branch_name.setEnabled(true);
				ifsc_code.setEnabled(true);
				pan_no.setEnabled(true);
				save_change.setText("Update Detail");
			}
		}
		else{
			setProgressDialog();
			new GetUserInfoTask(getActivity(),new BankListener()).execute();
		}

		save_change.setOnClickListener(updateListener);

		return view;
	}

	OnClickListener shopEarnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ShopEarnFragment();
			onReplace(fragment);
		}
	};
	OnClickListener inviteEarnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*fragment=new InviteEarnFragment();*/
			fragment=new PriceComparisonFragment();
			onReplace(fragment);
		}
	};
	OnClickListener dealpriceListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPDealCouponFragment();
			onReplace(fragment);
		}
	};
	OnClickListener couponListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ReferEarnFragment();
			onReplace(fragment);
		}
	};

	public void onReplace(Fragment fragment1){
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
	OnClickListener offerListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new OfferFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	}; 
	OnClickListener profileListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new ProfileFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new FavouriteFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener notificationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new NotificationFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener personalinfoListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new ProfileFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener changepasswordListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new ChangePasswordFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener updateListener=new OnClickListener() {

		@Override
		public void onClick(View v) {

			if(StaticData.user_account.size()>0){
				if(user_id.equals(StaticData.user_account.get(0).getUser_id())){
					Payment_Transfer pp=new Payment_Transfer(getActivity());
					pp.execute();
				}
				else
				{
					s_account_holder_name=account_holder_name.getText().toString();
					s_account_number=account_number.getText().toString();
					s_bank_name=bank_name.getText().toString();
					s_branch_name=branch_name.getText().toString();
					s_ifsc_code=ifsc_code.getText().toString();
					s_pan_no=pan_no.getText().toString();
					if(UtilMethod.isStringNullOrBlank(s_account_holder_name)){
						UtilMethod.showToast(getResources().getString(R.string.account_holder_name_warning), getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_bank_name)){
						UtilMethod.showToast(getResources().getString(R.string.bank_name_warning),getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_branch_name)){
						UtilMethod.showToast(getResources().getString(R.string.branch_name_warning),getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_account_number)){
						UtilMethod.showToast(getResources().getString(R.string.account_number_warning), getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_ifsc_code)){
						UtilMethod.showToast(getResources().getString(R.string.ifsc_code_warning),getActivity());
					}
					else{
						try{
							MultipartEntity multipart=new MultipartEntity();
							multipart.addPart("neft_acc_name", new StringBody(s_account_holder_name));
							multipart.addPart("neft_bank", new StringBody(s_bank_name));
							multipart.addPart("neft_bank_branch",new StringBody(s_branch_name));
							multipart.addPart("neft_acc_number", new StringBody(s_account_number));
							multipart.addPart("neft_ifsc_code", new StringBody(s_ifsc_code));
							multipart.addPart("neft_ifsc_pan", new StringBody(s_pan_no));
							multipart.addPart("extension", new StringBody("1"));
							multipart.addPart("pass", new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_pass",null)));
							multipart.addPart("userid", new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id", null)));
							setProgressDialog();
							new UpdateBankDetailTask(getActivity(), multipart, new BankTransferListener()).execute();
						}
						catch(Exception e){

						}
					}
				}
			}


		}
	};


/*	private void setProgressDialog(){

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

	public class BankTransferListener{
		public void onSuccess(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
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
					Payment_Transfer pp=new Payment_Transfer(getActivity());
					pp.execute();
					getActivity().getSupportFragmentManager().popBackStack();
				}
			});
			adialog.show();
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
				adialog.setButton("OK",new DialogInterface.OnClickListener() {

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
	public class BankListener{
		public void onSuccess(){
		//	loadingViewAnim.stop();
			dialog.dismiss();
			if(StaticData.user_account.size()>0){
				if(user_id.equals(StaticData.user_account.get(0).getUser_id())){
					account_holder_name.setText(StaticData.user_account.get(0).getAccount_holder_name());
					account_number.setText(StaticData.user_account.get(0).getAccount_no());
					bank_name.setText(StaticData.user_account.get(0).getBank_name());
					branch_name.setText(StaticData.user_account.get(0).getBranch_name());
					ifsc_code.setText(StaticData.user_account.get(0).getIfsc_code());
					pan_no.setText(StaticData.user_account.get(0).getPan_no());
				}
			}
		}
		public void onError(String msg){
			//loadingViewAnim.stop();
			dialog.dismiss();
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}
			else{
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
				adialog.show();
			}
		}
	}
}
