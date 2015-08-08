package com.dnp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.NotificationAdapter;
import com.dnp.asynctask.GetNotificationTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class NotificationFragment extends Fragment{
	View view;
	ListView notification_list;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	LinearLayout notification_on_layout,notification_off_layout;
	ImageView notification_on,notification_off;
	LinearLayout footer_layout;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	Dialog dialog;
	//private AnimationDrawable loadingViewAnim;
	ImageView loading_image;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_notifications, container, false);
		notification_list=(ListView) view.findViewById(R.id.notification_list);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		
		
		LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		//LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_inactive));
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile));
		notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.notification_active));
		notification_off_layout=(LinearLayout) view.findViewById(R.id.notification_off_layout);
		notification_on_layout=(LinearLayout) view.findViewById(R.id.notification_on_layout);
		notification_off=(ImageView) view.findViewById(R.id.notification_off);
		notification_on=(ImageView) view.findViewById(R.id.notification_on);
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.gray));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.black));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
		SharedPreferences shpf2=getActivity().getSharedPreferences("User_login",1);
		String notification_value=shpf2.getString("notification_value", null);
		if(UtilMethod.isStringNullOrBlank(notification_value)){
			notification_off.setVisibility(View.GONE);
			notification_on.setVisibility(View.VISIBLE);
		}
		else if(notification_value.equals("on")){
			notification_off.setVisibility(View.GONE);
			notification_on.setVisibility(View.VISIBLE);
		}
		else if(notification_value.equals("off")){
			notification_off.setVisibility(View.VISIBLE);
			notification_on.setVisibility(View.GONE);
		}
		
		notification_off_layout.setOnClickListener(notificationofflistener);
		notification_on_layout.setOnClickListener(notificationonlistener);
		
		setProgressDialog();
		new GetNotificationTask(getActivity(), new NotificationListener()).execute();
		return view;
	}
	
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
	
	OnClickListener notificationofflistener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			notification_off.setVisibility(View.VISIBLE);
			notification_on.setVisibility(View.GONE);
			SharedPreferences shpf=getActivity().getSharedPreferences("User_login", 1);
			Editor edt=shpf.edit();
			edt.putString("notification_value","off");
			edt.commit();
		}
	};
	
	OnClickListener notificationonlistener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			notification_off.setVisibility(View.GONE);
			notification_on.setVisibility(View.VISIBLE);
			SharedPreferences shpf1=getActivity().getSharedPreferences("User_login", 1);
			Editor edt1=shpf1.edit();
			edt1.putString("notification_value", "on");
			edt1.commit();
		}
	};
	
	public class NotificationListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			notification_off_layout.setOnClickListener(notificationofflistener);
			notification_on_layout.setOnClickListener(notificationonlistener);
			
			if(StaticData.notification_list.size()>0){
			//notification_list.setDivider(getResources().getColor(R.color.offer_text_color));
			notification_list.setAdapter(new NotificationAdapter(getActivity()));
			}
			else{
				AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage("No Notification Found!");
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
			}
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
	
	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
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
	OnClickListener shopEarnListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ShopEarnFragment();
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
	OnClickListener inviteEarnListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener dealpriceListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPDealCouponFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}; 
	OnClickListener couponListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new UnderDevelopmentFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
			
}
