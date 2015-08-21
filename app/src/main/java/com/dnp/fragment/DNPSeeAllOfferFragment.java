package com.dnp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.CouponAdapter;
import com.dnp.adapter.DealGridAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.sqlHelper;

public class DNPSeeAllOfferFragment extends Fragment{
	LinearLayout offer_layout,shopearn_layout,price_layout,referearn_layout,deal_layout; 
	TextView offer_text,shopearn_text,price_text,referearn_text,dealprice_text;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	LinearLayout footer_layout;
	TextView category_header;
	GridView category_grid;
	SharedPreferences shpf;
	Bundle b;
	String store_name,purpose;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
    sqlHelper sHelper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_deal_grid, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		price_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		price_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		deal_layout=(LinearLayout) view.findViewById(R.id.deal_layout);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		category_header=(TextView) view.findViewById(R.id.category_header);
		category_grid=(GridView) view.findViewById(R.id.category_grid);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		shopearn_text.setText("Shop & Earn");
		price_text.setText("Price Comparison");
		referearn_text.setText("Refer & Earn");
		dealprice_text.setText("Deals & Coupons");
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
		b=getArguments();
		sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
		store_name=b.getString("store_name");
		purpose=b.getString("purpose");
		category_header.setText(store_name.toUpperCase()+" OFFERS");
		if(purpose.equals("coupon")){
		category_grid.setAdapter(new CouponAdapter(getActivity(), sHelper.getAllCouponValuewithStore(store_name), new SeeCouponListener()));
		}
		else if(purpose.equals("deals"))
		category_grid.setAdapter(new DealGridAdapter(getActivity(), sHelper.getAllDealValuewithStore(store_name)));
		
		return view;
	}
	
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

	public class SeeCouponListener{
		public void onGetCode(int position,String home){
			Fragment fragment=new GetCouponCodeFragment();
			Bundle b=new Bundle();
			b.putInt("position", position);
			b.putString("purpose", home);
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
			
		}
		
	}
	
	
}
