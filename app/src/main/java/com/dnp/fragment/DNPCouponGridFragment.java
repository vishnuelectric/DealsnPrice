package com.dnp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.CouponGridAdapter;
import com.dnp.asynctask.GetCouponProductTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

public class DNPCouponGridFragment extends Fragment{
	View view;
	TextView header_text,deal_text;
	GridView coupon_grid;
	LinearLayout offer_layout,shopearn_layout,price_layout,referearn_layout,dealscoupon_layout; 
	TextView offer_text,shopearn_text,price_text,referearn_text,dealprice_text;
	Fragment fragment;
	FragmentManager fmanager;
	SharedPreferences shpf;
	String category_name;
	String url;
	FragmentTransaction ft;
	ProgressDialog pdialog;
	LinearLayout footer_layout;
	Dialog dialog;
	ImageView loading_image;
//	private AnimationDrawable loadingViewAnim;
	HorizontalScrollView horizontal_id;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_inner_coupon, container, false);
		coupon_grid=(GridView) view.findViewById(R.id.coupon_grid);
		header_text=(TextView) view.findViewById(R.id.header_id);
		deal_text=(TextView) view.findViewById(R.id.deal_id);

		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		price_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		dealscoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		price_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		shopearn_text.setText("Shop & Earn");
		price_text.setText("Price Comparison");
		referearn_text.setText("Refer & Earn");
		dealprice_text.setText("Deals & Coupons");
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		price_layout.setOnClickListener(priceComparisonListener);
		referearn_layout.setOnClickListener(referEarnListener);
		pdialog=new ProgressDialog(getActivity());
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
		dealscoupon_layout.setOnClickListener(dealsListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		shpf=getActivity().getSharedPreferences("User_login",1);
		category_name=shpf.getString("category_name",null);
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		if(category_name.equals("all")){
			url=WebService.WEB_HOST_URL+"jsonproduct/coupon?urlcheck=1";
		}
		else{
			url=WebService.WEB_HOST_URL+"jsonproduct/coupon/?type=0&catid="+shpf.getString("subcategory_id", null)+"&urlcheck=1";
		}
		if(!UtilMethod.isStringNullOrBlank(url)){
			setProgressDialog();
			new GetCouponProductTask(getActivity(), url,new CouponGridListener()).execute();
		}
		v1.setLayoutParams(param);
		footer_layout.addView(v1);

		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		
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
	OnClickListener dealsListener=new OnClickListener() {
		
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
	
	public class CouponGridListener{
		public void onSuccess(){
			if(dialog!=null && pdialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			coupon_grid.setAdapter(new CouponGridAdapter(getActivity(), StaticData.coupon_product_list));
			coupon_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment fragment=new GetCouponCodeFragment();
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					Bundle b=new Bundle();
					b.putInt("position",arg2);
					fragment.setArguments(b);
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.addToBackStack(null);
					ft.replace(R.id.container, fragment);
					ft.commit();
				}
			});
			
		}
		public void onError(String msg){
			if(dialog!=null && pdialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
			UtilMethod.showServerError(getActivity());	
			}
			else{
			//UtilMethod.showToast(msg, getActivity());
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
	}

}
