package com.dnp.fragment;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.PCSellerAdapter;
import com.dnp.adapter.PCSellerVariantAdapter;
import com.dnp.asynctask.PCDetailTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.Helper;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PriceComparisonSellerFragment extends Fragment{
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,couponprice_text;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	ImageView shop_image;
	LinearLayout visit_layout;
	GridView gridview;
	Bundle bundle;
	Helper helper;
	RelativeLayout sort_layout;
	String shop_offer_image,shop_url,detaillink;
	DisplayImageOptions opt;
	ImageLoader image_load;
	ListView seller_list;
	TextView product_name,product_price,seller,specification,alternative;
	ImageView product_image;
	LinearLayout alert_layout,share_layout;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	private static final int DATABASE_VERSION = 1;  
	private static final String DATABASE_NAME = "DealsnPrice";
	sqlHelper sHeler;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_pricecomparison_detail, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		inviteearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		couponprice_text=(TextView) view.findViewById(R.id.couponprice_text);
		seller_list=(ListView) view.findViewById(R.id.seller_list);
		product_name=(TextView) view.findViewById(R.id.product_name);
		product_price=(TextView) view.findViewById(R.id.product_price);
		product_image=(ImageView) view.findViewById(R.id.product_image);
		helper=new Helper();
		/*helper.getListViewSize(seller_list);*/
		sort_layout=(RelativeLayout) view.findViewById(R.id.sort_layout);
		alert_layout=(LinearLayout) view.findViewById(R.id.alert_layout);
		share_layout=(LinearLayout) view.findViewById(R.id.share_layout);
		seller=(TextView) view.findViewById(R.id.seller);
		specification=(TextView) view.findViewById(R.id.specification);
		alternative=(TextView) view.findViewById(R.id.alternative);
		shopearn_text.setText("Shop & Earn");
		inviteearn_text.setText("Price Comparison");
		dealprice_text.setText("Deals & Coupons");
		couponprice_text.setText("Refer & Earn");
		//DashboardActivity.onCustomActionView();

		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();

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
		alert_layout.setOnClickListener(alertListener);
		share_layout.setOnClickListener(shareListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		bundle=getArguments();
		if(UtilMethod.isNetworkAvailable(getActivity())){
			setProgressDialog();
			String url=WebService.WEB_HOST_URL+"productjsondetails/?urlcheck=1&type=1&pid="+bundle.getString("product_id")+"&userid="+getActivity().getSharedPreferences("User_login", 1).getString("user_id", null);
			new PCDetailTask(getActivity(), url, new PCSellerListener()).execute();
		}
		else{
			UtilMethod.showNetworkError(getActivity());
		}

		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		fm=getFragmentManager();
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		pricecomparison_layout.setOnClickListener(inviteEarnListener);
		referearn_layout.setOnClickListener(couponListener);
		dealcoupon_layout.setOnClickListener(dealpriceListener);

		return view;
	}
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
	OnClickListener alertListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonDropAlertFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener shareListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonShareFragment();
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
		ft.addToBackStack(null);
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
	public class PCSellerListener{
		public void onSuccess(){
			/*UtilMethod.showToast("hello this is done", getActivity());*/
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			product_name.setText(StaticData.pc_detail.get(0).getProduct_name());
			product_price.setText(StaticData.pc_detail.get(0).getProduct_mrp());
			opt=UniversalImageLoaderHelper.setImageOptions();

			image_load=ImageLoader.getInstance();
			//UtilMethod.showToast("Seller Lsit Size is "+StaticData.pc_detail.size(), getActivity());
			image_load.displayImage(StaticData.pc_detail.get(0).getImagepath()+StaticData.pc_detail.get(0).getProduct_image(), product_image, opt);
			seller_list.setAdapter(new PCSellerAdapter(getActivity(),new PCSellerListener()));

			specification.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fragment=new PriceComparisonSpecificationFragment();
					onReplace(fragment);
				}
			});
			alternative.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fragment=new PriceComparisonAlternativeFragment();
					onReplace(fragment);
				}
			});

			sort_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Dialog d1=new Dialog(getActivity());
					d1.requestWindowFeature(Window.FEATURE_NO_TITLE);
					d1.setContentView(R.layout.activity_sorting);
					ListView sort_option_list=(ListView) d1.findViewById(R.id.sort_option_list);
					TextView cancel=(TextView) d1.findViewById(R.id.sorting_cancel);
					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							d1.dismiss();
						}
					});
					String[] option_value={"Low to High","High to Low"};
					sort_option_list.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.signalline_textview_xml, R.id.single_textview, option_value));
					sort_option_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							if(arg2==0){
								d1.dismiss();
								StaticData.pc_variant_detail.clear();
								sHeler=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
								ArrayList<PriceComparisonBean> list=sHeler.getStoreProductwithAsc();
								seller_list.setAdapter(new PCSellerAdapter(getActivity(),new PCSellerListener()));
							}
							else if(arg2==1){
								d1.dismiss();
								StaticData.pc_variant_detail.clear();
								sHeler=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
								ArrayList<PriceComparisonBean> list=sHeler.getStoreProductwithDesc();
								seller_list.setAdapter(new PCSellerAdapter(getActivity(),new PCSellerListener()));
							}
						}
					});
					d1.show();

				}
			});

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
		public void onBuyNow(String url){
			Fragment fragment=new OpenShopUrlFragment();
			Bundle bb1=new Bundle();
			bb1.putString("shop_url",url);
			fragment.setArguments(bb1);
			onReplace(fragment);
		}
		public void onVariant(String store_image){
			StaticData.pc_full_variant.clear();
			for(int i=0;i<StaticData.pc_detail.size();i++){
				if(StaticData.pc_detail.get(i).getStore_image().equals(store_image)){
					StaticData.pc_full_variant.add(StaticData.pc_detail.get(i));
				}
			}
			Dialog dialog=new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_variant_list);
			ListView seller_variant_list=(ListView) dialog.findViewById(R.id.variant_list);
			seller_variant_list.setAdapter(new PCSellerVariantAdapter(getActivity(),dialog,new PCSellerListener()));

			dialog.show();
		}
		public void onDialogBuyNow(Dialog dialog,String url){
			dialog.dismiss();
			Fragment fragment=new OpenShopUrlFragment();
			Bundle bb1=new Bundle();
			bb1.putString("shop_url",url);
			fragment.setArguments(bb1);
			onReplace(fragment);
		}
	}
}
