package com.dnp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.PCSpecificationAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PriceComparisonSpecificationFragment extends Fragment{
	View view;
	TextView product_name,product_price,seller,alternative;
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,couponprice_text;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	ListView specification_list;
	ImageView product_image;
	DisplayImageOptions opt;
	ImageLoader image_load;
	LinearLayout alert_layout,share_layout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_pc_specification, container, false);
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
		shopearn_text.setText("Shop & Earn");
		seller=(TextView) view.findViewById(R.id.seller);
		alternative=(TextView) view.findViewById(R.id.alternative);
		alert_layout=(LinearLayout) view.findViewById(R.id.alert_layout);
		share_layout=(LinearLayout) view.findViewById(R.id.share_layout);
		specification_list=(ListView) view.findViewById(R.id.specification_list);
		inviteearn_text.setText("Price Comparison");
		dealprice_text.setText("Deals & Coupons");
		couponprice_text.setText("Refer & Earn");
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		product_name=(TextView) view.findViewById(R.id.product_name);
		product_price=(TextView) view.findViewById(R.id.product_price);
		product_image=(ImageView) view.findViewById(R.id.product_image);
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
		specification_list.setAdapter(new PCSpecificationAdapter(getActivity())	);
		seller.setOnClickListener(sellerListener);
		alternative.setOnClickListener(alertnativeListener);
		return view;
	}
	OnClickListener alertListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonDropAlertFragment();
			onReplace(fragment);
		}
	};
	OnClickListener shareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonShareFragment();
			onReplace(fragment);
		}
	};
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
	OnClickListener alertnativeListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new PriceComparisonAlternativeFragment();
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
	
	public void onReplace(Fragment fragment1){
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
}
