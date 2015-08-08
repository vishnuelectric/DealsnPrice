package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.ContactUsTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

public class ContactUsFragment extends Fragment{
	ImageView facebook_image, gplus_image,twitter_image;
	EditText name,email,message,mobile;
	LinearLayout submit_layout;
	TextView address_line1,contact,deals_email;
	String s_name,s_email,s_message;
	SharedPreferences shpf;
	TextView copyright_text,all_right_text;
	LinearLayout footer_layout;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	Dialog dialog;
	ImageView loading_image;
	Bundle b;
	LinearLayout faqs_layout,aboutus_layout;
	//AnimationDrawable loadingViewAnim;
	String text_regexStr = "^[a-zA-Z ]+$";
	String s_mobile;
	ScrollView scrooling;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_contactus, container, false);
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
		faqs_layout=(LinearLayout) view.findViewById(R.id.faq_layout);
		aboutus_layout=(LinearLayout) view.findViewById(R.id.aboutus_layout);
		scrooling=(ScrollView) view.findViewById(R.id.scrooling);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		b=getArguments();
		if(b.getString("purpose").equals("ques")){
			view.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					scrooling.fullScroll(ScrollView.FOCUS_DOWN);
				}
			});
		}
		else if(b.getString("purpose").equals("main")){
			view.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					scrooling.fullScroll(ScrollView.FOCUS_UP);
				}
			});
		}
		else if(!UtilMethod.isStringNullOrBlank(b.getString("purpose"))){
			view.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					scrooling.fullScroll(ScrollView.FOCUS_UP);
				}
			});
		}
		faqs_layout.setOnClickListener(faqsListener);
		facebook_image=(ImageView) view.findViewById(R.id.facebook_id);
		
		gplus_image=(ImageView) view.findViewById(R.id.gplus_id);
		shpf=getActivity().getSharedPreferences("User_login",1);
		address_line1=(TextView) view.findViewById(R.id.address_line1);
		contact=(TextView) view.findViewById(R.id.contact);
		deals_email=(TextView) view.findViewById(R.id.deals);
		twitter_image=(ImageView) view.findViewById(R.id.t_id);
		name=(EditText) view.findViewById(R.id.name);
		mobile=(EditText) view.findViewById(R.id.mobile);
		email=(EditText) view.findViewById(R.id.email);
		copyright_text=(TextView) view.findViewById(R.id.copyright_text);
		all_right_text=(TextView) view.findViewById(R.id.all_text);
		message=(EditText) view.findViewById(R.id.message);
		submit_layout=(LinearLayout) view.findViewById(R.id.submit_layout);
		name.setText(shpf.getString("user_name", null));
		mobile.setText(shpf.getString("user_mobile", null));
		email.setText(shpf.getString("user_email", null));
		
		address_line1.setText("Ground Floor, 15A/38, Sarawati Marg, Karol Bagh, Delhi- 11005");
		contact.setText("Contact: +91-11-45085508");
		deals_email.setText("help@dealsprice.com");
		copyright_text.setText("Copyright @2013-2015 Dealsnprice.com");
		all_right_text.setText("All rights reserved");
		submit_layout.setOnClickListener(submitListener);
		aboutus_layout.setOnClickListener(aboutusListener);
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
		facebook_image.setOnClickListener(facebookListener);
		twitter_image.setOnClickListener(twitterListener);
		gplus_image.setOnClickListener(googleListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		message.requestFocus();
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(message, 0);
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		
		
		return view;
	}
	OnClickListener faqsListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new FAQFragment();
			FragmentManager fm=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}; 
	
	
	OnClickListener facebookListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new BackUpOpenShopUrlFragment();
			Bundle b=new Bundle();
			b.putString("shop_url", WebService.DNP_FACEBOOK_PAGE);
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	OnClickListener twitterListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new BackUpOpenShopUrlFragment();
			Bundle b=new Bundle();
			b.putString("shop_url", WebService.DNP_TWITTER_PAGE);
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	OnClickListener googleListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new BackUpOpenShopUrlFragment();
			Bundle b=new Bundle();
			b.putString("shop_url", WebService.DNP_GOOGLE_PLUS_PAGE);
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	
	OnClickListener aboutusListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new AboutUsFragment();
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
	
	OnClickListener submitListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_name=name.getText().toString();
			s_email=email.getText().toString();
			s_message=message.getText().toString();
			s_mobile=mobile.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_name) && UtilMethod.isStringNullOrBlank(s_email) && UtilMethod.isStringNullOrBlank(s_message)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage("Please Enter Name");
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						name.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);
						/*first_name.setSelection(s_first_name.length()-1);*/
					}
				});
				adialog.show();
			}
			else if(UtilMethod.isStringNullOrBlank(s_name)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage("Please Enter Name");
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						name.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);
						/*first_name.setSelection(s_first_name.length()-1);*/
					}
				});
				adialog.show();
			}
			else if(s_name.matches(text_regexStr)==false){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.first_name_validation));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						name.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);
						/*first_name.setSelection(s_first_name.length()-1);*/
					}
				});
				adialog.show();
			}
			else if(!UtilMethod.isValidEmail(s_email)){
				UtilMethod.showToast(getActivity().getResources().getString(R.string.valid_email), getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_message)){
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(getResources().getString(R.string.mobile_number_valid_warning));
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						/*mobile.setSelection(s_mobile.length()-1);*/
						message.requestFocus();
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(message, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				adialog.show();
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
			
			
			else{
				try{
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("txtuser",new StringBody(s_name));
				multipart.addPart("txtemail1", new StringBody(s_email));
				multipart.addPart("txtmessage",new StringBody(s_message));
				multipart.addPart("user_id",new StringBody(getActivity().getSharedPreferences("User_login", 1).getString("user_id",null)));
				multipart.addPart("value",new StringBody("1"));
				multipart.addPart("extension",new StringBody("1"));
				setProgressDialog();
				new ContactUsTask(getActivity(),multipart,new ContactListener()).execute();
				}
				catch(Exception e){
					
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
	
	public class ContactListener{
		public void onSuccess(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
				}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage(msg);
			adialog.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					/*name.setText("");
					email.setText("");*/
					message.setText("");
					//getActivity().getSupportFragmentManager().popBackStack();
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
	
	
}
