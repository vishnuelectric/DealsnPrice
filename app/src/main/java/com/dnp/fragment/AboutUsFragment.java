package com.dnp.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.LoginActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.GetAppUrlTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class AboutUsFragment extends Fragment{
	TextView copyright_text,all_right,app_detail,app_detail2;
	LinearLayout footer_layout;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
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
		View view=inflater.inflate(R.layout.activity_aboutus, container, false);
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
		app_detail2=(TextView) view.findViewById(R.id.app_detail2);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");

		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		//DashboardActivity.onCustomActionView();

		copyright_text=(TextView) view.findViewById(R.id.copyright_text);
		all_right=(TextView) view.findViewById(R.id.all_text);
		app_detail=(TextView) view.findViewById(R.id.app_detail);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		app_detail.setText("DealnPrice is India's leading Cashback, Price Comparison and Coupon website. We are passionate about offering cash backs on your online shopping across e-commerce players and finding Coupons that we think will catch your eyes. We compile Best Deals & Coupons across shopping sites so that you \" Spend Less & Save More \".");
		app_detail2.setText("There are times when you have already decided upon the product you want to buy but you are looking for the best price, we help you discover the best price through our price comparison service. ");
		copyright_text.setText("Copyright @2014 Dealsprice.com");
		all_right.setText("All rights reserved");
		/*ImageSpan is = new ImageSpan(getActivity(), R.drawable.settings_icon);
		app_detail.setSpan(is, 0, app_detail.getTe, 0);*/
		//DashboardActivity.onCustomActionView();

		LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile_active));
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

		v1.setLayoutParams(param);
		footer_layout.addView(v1);
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

	OnClickListener faqsListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
	OnClickListener contactListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new ContactUsFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener rateListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setProgressDialog();
			new GetAppUrlTask(getActivity(),new RateListener()).execute();
		}
	};

	OnClickListener offerListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			onReplace(fragment);
		}
	};
	OnClickListener profileListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			onReplace(fragment);

		}
	};
	OnClickListener notificationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			onReplace(fragment);

		}
	};
	OnClickListener favouriteListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			onReplace(fragment);
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

	public class RateListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
			//	loadingViewAnim.stop();
				dialog.dismiss();
			}
			Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(StaticData.our_app_list.get(0).getApp_url()));
			startActivity(intent);
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}

		}
	}

}
