package com.dnp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.ShopDetailAdapter;
import com.dnp.asynctask.GetShopDetailTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.APP_Constants;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DNPShopDetailFragment extends Fragment{
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,couponprice_text;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	ImageView shop_image;
	LinearLayout visit_layout;
	GridView gridview;
	Bundle b;
	String shop_cashback;
	String shop_offer_image,shop_url,detaillink,shop_name;
	DisplayImageOptions opt;
	ImageLoader image_load;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	TextView cashback_detail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_shopoffer_detail, container, false);
		shop_image=(ImageView) view.findViewById(R.id.shop_image);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		cashback_detail=(TextView) view.findViewById(R.id.cashback_detail);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		inviteearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		couponprice_text=(TextView) view.findViewById(R.id.referearn_text);
		shopearn_text.setText("Shop & Earn");
		inviteearn_text.setText("Price Comparison");
		dealprice_text.setText("Deals & Coupons");
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		couponprice_text.setText("Refer & Earn");
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
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
		
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		fm=getFragmentManager();
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		pricecomparison_layout.setOnClickListener(inviteEarnListener);
		referearn_layout.setOnClickListener(couponListener);
		dealcoupon_layout.setOnClickListener(dealpriceListener);
		
		visit_layout=(LinearLayout) view.findViewById(R.id.visit_layout);
		gridview=(GridView) view.findViewById(R.id.gridview);
		b=getArguments();
		shop_offer_image=b.getString("shop_offer_image");
		shop_url=b.getString("shop_url");
		detaillink=b.getString("detaillink");
		shop_cashback=b.getString("cashback");
		shop_name=b.getString("shop_name");
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(shop_offer_image, shop_image, opt);
		cashback_detail.setText(shop_cashback);
		visit_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("OnClick ","SHOP NOW");
				if(!UtilMethod.isStringNullOrBlank(shop_url)){
				/*Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(shop_url));
				getActivity().startActivity(intent);*/

					//--
					Log.e("OnCLick "," "+shop_name);
					if(shop_name !=null && !shop_name.isEmpty())
					{
						Log.e(" ON CLICK "," Not Null");
						
						if(shop_name.equalsIgnoreCase("Flipkart") )
						{
							isNoPlayStore(APP_Constants.FLIPKART);
							return;
						}
						else if(shop_name.equalsIgnoreCase("Myntra")||shop_name.equalsIgnoreCase("Myntra123"))
						{
							isNoPlayStore(APP_Constants.MYNTRA);
							return;
						}
					}
					//--	
					
					
					
				Fragment f=new OpenShopUrlFragment();
				FragmentManager fm=getActivity().getSupportFragmentManager();
				FragmentTransaction ft=fm.beginTransaction();
				Bundle b=new Bundle();
				b.putString("shop_url", shop_url);
				f.setArguments(b);
				ft.replace(R.id.container, f);
				ft.addToBackStack(null);
				ft.commit();
				}
			}
		});
		String url=WebService.WEB_HOST_URL+"jsonproduct/storesdetails/?urlcheck=1&detaillink="+detaillink;
		setProgressDialog();
		new GetShopDetailTask(getActivity(), url,new ShopDetailListener()).execute();
		
		return view;
	}
	/**
	 * This method opens a given store url in chrome if available else in default browser
	 * @param url
	 */
	private void isNoPlayStore(String url)
	{
		Log.e("isNoPlayStore "," "+url);
		try {
		    Intent i = new Intent("android.intent.action.MAIN");
		    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
		    i.addCategory("android.intent.category.LAUNCHER");
		    i.setData(Uri.parse(url));
		    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    getActivity().getApplicationContext().startActivity(i);
		}
		catch(ActivityNotFoundException e) {
		    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    getActivity().getApplicationContext().startActivity(i);
		}
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
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
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
	OnClickListener notificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			ft.replace(R.id.container,fragment);
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
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
	
	public class ShopDetailListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			gridview.setAdapter(new ShopDetailAdapter(getActivity()));
			
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
