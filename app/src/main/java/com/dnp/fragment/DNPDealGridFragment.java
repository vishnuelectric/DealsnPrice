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
import com.dnp.adapter.DealGridAdapter;
import com.dnp.asynctask.GetDealStoreTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

public class DNPDealGridFragment extends Fragment{
	View view;
	TextView coupons_text,header_text;
	GridView deal_grid;
	LinearLayout offer_layout,shopearn_layout,price_layout,referearn_layout,deal_layout; 
	TextView offer_text,shopearn_text,price_text,referearn_text,dealprice_text;
	Fragment fragment;
	FragmentManager fmanager;

	FragmentTransaction ft;
	SharedPreferences shpf;
	String category_name;
	//ProgressDialog pdialog;
	LinearLayout footer_layout;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	HorizontalScrollView horizontal_id;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_inner_deal, container, false);
		coupons_text=(TextView) view.findViewById(R.id.coupons_id);
		deal_grid=(GridView) view.findViewById(R.id.deal_grid);
		header_text=(TextView) view.findViewById(R.id.header_id);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		deal_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		deal_layout.setOnClickListener(dealListener);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		price_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		price_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		shopearn_text.setText("Shop & Earn");
		//DashboardActivity.onCustomActionView();

		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();price_text.setText("Price & Comparison");
		referearn_text.setText("Refer & Earn");
		dealprice_text.setText("Deal & Coupon");
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		price_layout.setOnClickListener(priceComparisonListener);
		referearn_layout.setOnClickListener(referEarnListener);
		//pdialog=new ProgressDialog(getActivity());
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
		shpf=getActivity().getSharedPreferences("User_login",1);
		category_name=shpf.getString("category_name",null);
		String url;
		if(category_name.equalsIgnoreCase("all")){
			url=WebService.WEB_HOST_URL+"jsonproduct/deals?urlcheck=1";
		}
		else{
			url=WebService.WEB_HOST_URL+"jsonproduct/deals?type=0&catid="+shpf.getString("subcategory_id", null)+"&urlcheck=1";
		}
		setProgressDialog();
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		new GetDealStoreTask(getActivity(), url, new DealGridListener()).execute();

		return view;
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
	public class DealGridListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			deal_grid.setAdapter(new DealGridAdapter(getActivity(), StaticData.deal_product_list));
			deal_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment f=new GetDealCodeFragment();
					Bundle b1=new Bundle();
					b1.putInt("position", arg2);
					f.setArguments(b1);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.addToBackStack(null);
					ft.replace(R.id.container, f);
					ft.commit();
				}
			});
		}
		public void onError(String msg){
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}
			else{
				/*UtilMethod.showToast(msg, getActivity());*/
				
			}
			//--
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			//--
		}
	}

}
