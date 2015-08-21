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
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.PCAlternativeAdapter;
import com.dnp.asynctask.FavouriteProductTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.data.UtilMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PriceComparisonAlternativeFragment extends Fragment{
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,couponprice_text;
	Fragment fragment;
	RelativeLayout sort_layout;
	FragmentManager fm;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	ImageView shop_image;
	DisplayImageOptions opt;
	ImageLoader image_load;
	GridView alternative_list;
	TextView product_name,product_price,seller,specification,alternative;
	ImageView product_image;
	LinearLayout alert_layout,share_layout;
	private Dialog dialog;
	//private AnimationDrawable loadingViewAnim;
	private ImageView loading_image;
	TextView sort;
	ImageView sort_image;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_pricecomparison_alternate, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		inviteearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		couponprice_text=(TextView) view.findViewById(R.id.referearn_text);
		product_name=(TextView) view.findViewById(R.id.product_name);
		product_price=(TextView) view.findViewById(R.id.product_price);
		sort=(TextView) view.findViewById(R.id.sort);
		sort_image=(ImageView) view.findViewById(R.id.sort_image);
		product_image=(ImageView) view.findViewById(R.id.product_image);
		sort_layout=(RelativeLayout) view.findViewById(R.id.sort_layout);
		alert_layout=(LinearLayout) view.findViewById(R.id.alert_layout);
		share_layout=(LinearLayout) view.findViewById(R.id.share_layout);
		alternative_list=(GridView) view.findViewById(R.id.alternative_list);
		seller=(TextView) view.findViewById(R.id.seller);
		specification=(TextView) view.findViewById(R.id.specification);
		alternative=(TextView) view.findViewById(R.id.alternative);
		shopearn_text.setText("Shop & Earn");
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
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
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		seller.setOnClickListener(sellerListener);
		specification.setOnClickListener(specificationListener);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
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
		product_name.setText(StaticData.pc_detail.get(0).getProduct_name());
		product_price.setText(StaticData.pc_detail.get(0).getProduct_mrp());
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.pc_detail.get(0).getImagepath()+StaticData.pc_detail.get(0).getProduct_image(), product_image, opt);
		//UtilMethod.showToast("Alternative List Size is "+StaticData.pc_alternatives.size(),getActivity());
		alternative_list.setAdapter(new PCAlternativeAdapter(getActivity(),new PCAListener()));
		/*Collections.sort(list, comparator);*/
		return view;
	}
	OnClickListener sellerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new PriceComparisonSellerFragment();
			Bundle bun=new Bundle();
			bun.putString("product_id",StaticData.pc_detail.get(0).getProduct_id());
			f.setArguments(bun);
			onReplace(f);
		}
	};
	OnClickListener specificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new PriceComparisonSpecificationFragment();
			onReplace(f);
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
/*	
	private void setProgressDialog(){
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
	
	public void onReplace(Fragment fragment1){
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
	public class PCAListener{
		public void onSuccessViewDetail(String product_id){
			Fragment fragment=new PriceComparisonSellerFragment();
			Bundle b=new Bundle();
			b.putString("product_id", product_id);
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.addToBackStack(null);
			ft.replace(R.id.container, fragment);
			ft.commit();
		}
		public void onError(String msg){
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
		public void onAddFavouriteProduct(String product_id1,int position){
			try{
			
			MultipartEntity multipart=new MultipartEntity();
			multipart.addPart("pdid", new StringBody(product_id1));
			SharedPreferences shpf=getActivity().getSharedPreferences("User_login", 1);
			multipart.addPart("pdty", new StringBody(shpf.getString("category_name", null).toLowerCase()));
			multipart.addPart("userid", new StringBody(getActivity().getSharedPreferences("User_login", 1).getString("user_id", null)));
			multipart.addPart("extension", new StringBody("1"));
			setProgressDialog();
			new FavouriteProductTask(getActivity(), multipart, new PCAListener(),product_id1,position).execute();
			}
			catch(Exception e){
				
			}
		}
		public void onfavSuccess(final int position){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage("Add Favourite Product Successfully!");
			adialog.setButton("OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					PriceComparisonBean pcbean=new PriceComparisonBean();
					pcbean.setProduct_id(StaticData.pc_alternatives.get(position).getProduct_id());
					pcbean.setProduct_name(StaticData.pc_alternatives.get(position).getProduct_name());
					pcbean.setProduct_image(StaticData.pc_alternatives.get(position).getProduct_image());
					pcbean.setProduct_mrp(StaticData.pc_alternatives.get(position).getProduct_mrp());
					pcbean.setFav_status(1);
					StaticData.pc_alternatives.set(position, pcbean);
					alternative_list.setAdapter(new PCAlternativeAdapter(getActivity(),new PCAListener()));
					
				}
			});
			adialog.show();
		}
		
	}
}
