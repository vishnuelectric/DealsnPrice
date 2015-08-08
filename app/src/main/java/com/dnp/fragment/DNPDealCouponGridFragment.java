package com.dnp.fragment;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.CouponAdapter;
import com.dnp.adapter.DNPStealDealAdapter;
import com.dnp.adapter.DemoPriceComparisonAdapter;
import com.dnp.adapter.MostViewedCouponAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;

public class DNPDealCouponGridFragment extends Fragment{
		View view;
		LinearLayout offer_layout,shopearn_layout,price_layout,referearn_layout,deal_layout; 
		TextView offer_text,shopearn_text,price_text,referearn_text,dealprice_text;
		Fragment fragment;
		FragmentManager fmanager;
		SharedPreferences shpf;
		FragmentTransaction ft;
		Dialog dialog;
		HorizontalScrollView horizontal_id;
		//ProgressDialog pdialog;
		LinearLayout footer_layout;
		ImageView loading_image;
		//private AnimationDrawable loadingViewAnim;
		Bundle bundle;
		String purpose;
		TextView category_header;
		GridView category_grid;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_deal_grid, container, false);
		
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		price_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		price_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		deal_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		category_header=(TextView) view.findViewById(R.id.category_header);
		category_grid=(GridView) view.findViewById(R.id.category_grid);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		shopearn_text.setText("Shop & Earn");
		price_text.setText("Price Comparison");
		referearn_text.setText("Refer & Earn");
		dealprice_text.setText("Deals & Coupons");
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		price_layout.setOnClickListener(priceComparisonListener);
		referearn_layout.setOnClickListener(referEarnListener);
		bundle=getArguments();
		purpose=bundle.getString("purpose");
		category_header.setText(purpose);
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
		deal_layout.setOnClickListener(dealListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		shpf=getActivity().getSharedPreferences("User_login", 1);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		
		if(purpose.equalsIgnoreCase("Steal Deals")){
			DNPStealDealAdapter sdadapter=new DNPStealDealAdapter(getActivity(), StaticData.steal_deal_list);
			category_grid.setAdapter(sdadapter);
			category_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment fragment=new GetDealCodeFragment();
					Bundle b=new Bundle();
					b.putInt("position",arg2);
					b.putString("purpose","steal");
					fragment.setArguments(b);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, fragment);
					ft.addToBackStack(null);
					ft.commit();
				}
			});
		}
		else if(purpose.equalsIgnoreCase("Most Viewed Deals")){
			DemoPriceComparisonAdapter dadapter=new DemoPriceComparisonAdapter(getActivity(), StaticData.deal_product_list);
			category_grid.setAdapter(dadapter);
			category_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment fragment=new GetDealCodeFragment();
					Bundle b=new Bundle();
					b.putInt("position",arg2);
					b.putString("purpose","viewed");
					fragment.setArguments(b);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, fragment);
					ft.addToBackStack(null);
					ft.commit();
				}
			});
		}
		else if(purpose.equalsIgnoreCase("Hot Coupons")){
			CouponAdapter coupon_adapter=new CouponAdapter(getActivity(), StaticData.hot_coupon_product_list,new CouponGridListener());
			category_grid.setAdapter(coupon_adapter);
		}
		else if(purpose.equalsIgnoreCase("Most Viewed Coupons")){
			MostViewedCouponAdapter mvcadapter=new MostViewedCouponAdapter(getActivity(), StaticData.most_viewed_coupon_list,new CouponGridListener());
			category_grid.setAdapter(mvcadapter);
		}
		else if(purpose.equalsIgnoreCase("Top Stores")){
			
		}
		
		
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
	}
	*/
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}
	
	public class CouponGridListener{
		public void onGetCouponCode(int position,String purpose1){
			Fragment fragment=new GetCouponCodeFragment();
			Bundle b=new Bundle();
			b.putInt("position", position);
			b.putString("purpose", purpose1);
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}
	
	
	OnClickListener notificationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
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

	OnClickListener dealListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPDealCouponFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener priceComparisonListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};


	OnClickListener referEarnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ReferEarnFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

}
