package com.dnp.fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.EditProfileTask;
import com.dnp.asynctask.GetUserInfoTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class ProfileFragment extends Fragment{
	EditText first_name, email, password, mobile;
	String s_first_name,s_email,s_password,s_mobile;
	TextView save_change,change_password,payment_setting;
	LinearLayout footer_layout;
	String user_id;
	SharedPreferences shpf;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	String regexStr = "^[0-9]$";
	String text_regexStr = "^[a-zA-Z ]+$";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_personal_information, container, false);
		
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
		
		first_name=(EditText) view.findViewById(R.id.first_name);
		first_name.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		email=(EditText) view.findViewById(R.id.email);
		email.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		password=(EditText) view.findViewById(R.id.password);
		save_change=(TextView) view.findViewById(R.id.save_change);
		mobile=(EditText) view.findViewById(R.id.mobile);
		change_password=(TextView) view.findViewById(R.id.change_password);
		change_password.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
		payment_setting=(TextView) view.findViewById(R.id.payment_setting);
		payment_setting.setOnClickListener(paymentSettingListener);
		change_password.setOnClickListener(changePasswordListener);
		save_change.setOnClickListener(updateListener);
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
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile_active));
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.gray));
		profile_text.setTextColor(getResources().getColor(R.color.black));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_inactive));
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		password.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		mobile.setHintTextColor(getResources().getColor(R.color.placeholder_color));
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		if(UtilMethod.isNetworkAvailable(getActivity())){
		setProgressDialog();
		new GetUserInfoTask(getActivity(),new ProfileListener()).execute();
		}
		else{
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Warning");
			adialog.setMessage("Please check Internet Correction.Please try again");
			adialog.setCancelable(false);
			adialog.setButton("OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					//getActivity().getSupportFragmentManager().popBackStack();
				}
			});
			adialog.show(); 
		}
		
		if(StaticData.user_info.size()>0){
			if(getActivity().getSharedPreferences("User_login", 1).getString("user_id", null).equals(StaticData.user_info.get(0).getUser_id())){
				if(!UtilMethod.isStringNullOrBlank(StaticData.user_info.get(0).getUser_name())){
				first_name.setText(StaticData.user_info.get(0).getUser_name());
				}
				else{
					first_name.setHint("First Name");
				}
				email.setText(StaticData.user_info.get(0).getUser_email());
				mobile.setText(StaticData.user_info.get(0).getUser_mobile());
			}
		}
		
		return view;
	}
	
	
/*	private void setProgressDialog(){
		 
		dialog=new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading);
		dialog.setCancelable(false);
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
	
	
	public class ProfileListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(StaticData.user_info.size()>0){
				if(getActivity().getSharedPreferences("User_login", 1).getString("user_id", null).equals(StaticData.user_info.get(0).getUser_id())){
					if(!UtilMethod.isStringNullOrBlank(StaticData.user_info.get(0).getUser_name())){
					first_name.setText(StaticData.user_info.get(0).getUser_name());
					}
					else{ 	
						first_name.setHint("First Name");
					}
					email.setText(StaticData.user_info.get(0).getUser_email());
					mobile.setText(StaticData.user_info.get(0).getUser_mobile());
					DashboardActivity.setImage();
					/*if(getActivity().getSharedPreferences("User_login", 1).getString("user_pass", null)!=null){
					password.setText(getActivity().getSharedPreferences("User_login", 1).getString("user_pass", null));
					}*/
					//UtilMethod.showToast("Password is "+md5(StaticData.user_info.get(0).getUser_pass()), getActivity());
				}
			}
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage("Please check internet Connection.Please try again.");
				adialog.setButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						getActivity().getSupportFragmentManager().popBackStack();
					}
				});
				adialog.show();
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
		public String md5(String s) {
		    try {
		        // Create MD5 Hash
		        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
		        digest.update(s.getBytes());
		        byte messageDigest[] = digest.digest();

		        // Create Hex String
		        StringBuffer hexString = new StringBuffer();
		        for (int i=0; i<messageDigest.length; i++)
		            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		        return hexString.toString();

		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		    }
		    return "";
		}
	}
	
	
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new ProfileFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener notificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new NotificationFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.addToBackStack(null);
			ft.replace(R.id.container,fragment);
			ft.commit();
		}
	};
	
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new FavouriteFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.addToBackStack(null);
			ft.replace(R.id.container,fragment);
			
			ft.commit();
		}
	};
	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new OfferFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	OnClickListener paymentSettingListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new PaymentSettingFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener changePasswordListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new ChangePasswordFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.commit();
		}
	};
	OnClickListener updateListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_first_name=first_name.getText().toString();
			s_email=email.getText().toString();
			s_mobile=mobile.getText().toString();
			s_password=password.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_first_name)){

				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.first_name_warning));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						first_name.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(first_name, InputMethodManager.SHOW_IMPLICIT);


					}
				});
				adialog.show();
			
				
			}
			else if(s_first_name.matches(text_regexStr)==false){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.first_name_validation));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						first_name.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(first_name, InputMethodManager.SHOW_IMPLICIT);
						/*first_name.setSelection(s_first_name.length()-1);*/
					}
				});
				adialog.show();
			}
			else if(UtilMethod.isStringNullOrBlank(s_email)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.email_warning));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						email.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
				//UtilMethod.showAlert(getResources().getString(R.string.email_warning),getActivity());
			}
			else if(!UtilMethod.isValidEmail(s_email)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.email_validation));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						/*email.setSelection(s_email.length());*/
						email.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
				//UtilMethod.showAlert(getResources().getString(R.string.email_validation), getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_mobile)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.mobile_warning));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						/*mobile.setSelection(0);*/
						mobile.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(mobile, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
				
				//UtilMethod.showAlert(getResources().getString(R.string.mobile_warning),getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_password)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.password_warning));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						password.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
				
				//UtilMethod.showAlert(getResources().getString(R.string.password_warning),getActivity());
			}
			else if(s_mobile.length()<10 || s_mobile.length()>10 || !android.util.Patterns.PHONE.matcher(s_mobile).matches()){
				
				//UtilMethod.showToast("Length of mobile is "+s_mobile.length()+" & expression=="+s_mobile.matches(regexStr), getActivity());
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.mobile_number_valid_warning));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						/*mobile.setSelection(s_mobile.length()-1);*/
						mobile.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(mobile, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
				
				//UtilMethod.showAlert(getResources().getString(R.string.mobile_number_valid_warning), getActivity());
			}
			else if(!s_password.equals(getActivity().getSharedPreferences("User_login", 1).getString("user_pass", null))){
				//UtilMethod.showAlert("Incorrect Password",getActivity());
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage("Incorrect Password");
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						password.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
				
			}
			else{
				try{
				setProgressDialog();
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("txtemailsignname",new StringBody(s_first_name));
				multipart.addPart("mobileval",new StringBody(s_mobile));
				multipart.addPart("passworduser",new StringBody(s_password));
				multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id", null)));
				multipart.addPart("extension",new StringBody("1"));
				new EditProfileTask(getActivity(), multipart,new EditProfileListener()).execute();
				}catch(Exception e){
					
				}
			}
		}
	};
	
	public class EditProfileListener{
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
					password.setText("");
					SharedPreferences shpf=getActivity().getSharedPreferences("User_login",1);
					Editor edt=shpf.edit();
					edt.putString("user_name",s_first_name);
					edt.commit();
					DashboardActivity.setImage();
					/*getActivity().getSupportFragmentManager().popBackStack();*/
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
						getActivity().getSupportFragmentManager().popBackStack();
					}
				});
				adialog.show();		
			}
		}
	}
}
