package com.dnp.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.SharedPreferences;
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
import com.dealnprice.activity.OfferShareFragment;
import com.dealnprice.activity.R;
import com.dnp.adapter.AppListDemoAdapter;
import com.dnp.asynctask.GetAppListTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.APP_Constants;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class OfferFragment extends Fragment{
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	LinearLayout footer_layout;
	FragmentManager fm;
	ListView offer_list;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	String user_id;
	Dialog dialog;
	ImageView loading_image;
	SharedPreferences shpf;
	//private AnimationDrawable loadingViewAnim;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.activity_offer, container, false);
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
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		offer_list=(ListView) view.findViewById(R.id.offer_list);
		//DashboardActivity.onCustomActionView();
		//--
		LinearLayout lin[]	=	new LinearLayout[]{offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout};
		DashboardActivity.actRef.selectTab(lin, APP_Constants.OFFERS);
		//--
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
	/*	shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");*/
		shpf=getActivity().getSharedPreferences("User_login", 1);
		user_id=shpf.getString("user_id",null);
		view.post(new Runnable() {

			@Override
			public void run() {
				
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		/*offer_layout.setOnClickListener(offerListener);*/
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
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
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		v1.setLayoutParams(param);
		footer_layout.addView(v1);

		callTask();//get offers list
		//view.setF

		fm=getActivity().getSupportFragmentManager();
		
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
		//dialog.setCancelable(false);
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
	
	private void callTask(){
		
		setProgressDialog();
		
		try {
			/*MultipartEntity multipart=new MultipartEntity();
			multipart.addPart("user_id", new StringBody(user_id));*/
			new GetAppListTask(getActivity(),new OfferListener()).execute();
		} catch (Exception e) {
			//UtilMethod.showToast("Exception is "+e, getActivity());
		}
		
		
	}
	
	OnShowListener show=new OnShowListener() {

		@Override
		public void onShow(DialogInterface dialog) {
			// TODO Auto-generated method stub
			
			//loading_image.post(new Starter());
		}
	};
	
/*	class Starter implements Runnable{

		@Override
		public void run() {
			
			setProgressDialog();
			
			try{
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("user_id", new StringBody(user_id));
				new GetAppListTask(getActivity(), multipart,new OfferListener()).execute();
			}
			catch(Exception e){

			}
		}
	}*/
	
	
	OnClickListener offerListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			fragment=new OfferFragment();
			onReplace(fragment);
		}
	};

	OnClickListener profileListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			fragment=new ProfileFragment();
			onReplace(fragment);
		}
	};

	OnClickListener favouriteListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			fragment=new FavouriteFragment();
			onReplace(fragment);
		}
	};

	OnClickListener homeListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			fragment=new OfferFragment();
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
			fragment=new ReferEarnFragment();
			onReplace(fragment);
		}
	};

	public void onReplace(Fragment fragment1){
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.addToBackStack(null);
		ft.commit();
	}

	public class OfferListener{
		public void onSuccess(){
			dialog.dismiss();
			offer_list.setAdapter(new AppListDemoAdapter(getActivity(),new OfferListener()));
			offer_list.setDivider(null);
			offer_list.setDividerHeight(12);
		}
		public void onError(String msg){
			dialog.dismiss();
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
						adialog.dismiss();
					}
				});
				adialog.show();
			}
		}
		
		/**
		 * OnClick Listener called from Adapter[Offers List] AppListDemoAdapter
		 * @param position
		 */
		public void onAppClick(int position){
			Fragment f = null;
			if(StaticData.application_list.get(position).getPurpose_id()==1){
				f=new OfferDetailFragment();
			}
			else if(StaticData.application_list.get(position).getPurpose_id()==2){
				f=new OfferShareFragment();
			}
			else if(StaticData.application_list.get(position).getPurpose_id()==3){
				f=new OfferShareFragment();
			}
			else if(StaticData.application_list.get(position).getPurpose_id()==4){
				f=new OfferShareFragment();
			}

			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			Bundle b=new Bundle();
			if(f!=null){
				b.putInt("position",position);
				f.setArguments(b);
				ft.replace(R.id.container, f);
				ft.commit();
			}
		}
	}
}
