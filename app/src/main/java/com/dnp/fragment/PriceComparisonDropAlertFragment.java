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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.AlertProductBean;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.data.UtilMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PriceComparisonDropAlertFragment extends Fragment{
	TextView product_name,product_price;//,seller,alternative;
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,couponprice_text;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	LinearLayout alert_layout,share_layout;
	ImageView product_image;
	DisplayImageOptions opt;
	ImageLoader image_load;
	EditText target_price,email,mobile;
	Button save_alert;
	TextView duration;
	String[] duration_a;
	LinearLayout duration_layout;
	String s_target_price,s_mobile,s_email,s_duration;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_pricecomparison_alert, container, false);
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
		alert_layout=(LinearLayout) view.findViewById(R.id.alert_layout);
		duration=(TextView) view.findViewById(R.id.duration);
		target_price=(EditText) view.findViewById(R.id.target_price);
		email=(EditText) view.findViewById(R.id.email);
		mobile=(EditText) view.findViewById(R.id.mobile);
		save_alert=(Button) view.findViewById(R.id.save_alert);
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		shopearn_text.setText("Shop & Earn");
		//seller=(TextView) view.findViewById(R.id.seller);
		//alternative=(TextView) view.findViewById(R.id.alternative);
		duration_a=getResources().getStringArray(R.array.duration_array);
		duration_layout=(LinearLayout) view.findViewById(R.id.duration_layout);
		inviteearn_text.setText("Price Comparison");
		dealprice_text.setText("Deals & Coupons");
		couponprice_text.setText("Refer & Earn");
		product_name=(TextView) view.findViewById(R.id.product_name);
		product_price=(TextView) view.findViewById(R.id.product_price);
		product_image=(ImageView) view.findViewById(R.id.product_image);
		share_layout=(LinearLayout) view.findViewById(R.id.share_layout);
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
		save_alert.setOnClickListener(savealertListener);
		//alert_layout.setOnClickListener(alertListener);
		share_layout.setOnClickListener(shareListener);
		product_price.setText(StaticData.pc_detail.get(0).getProduct_mrp());
		SharedPreferences shpf=getActivity().getSharedPreferences("User_login",1);
		String user_email=shpf.getString("user_email", null);
		String user_mobile=shpf.getString("user_mobile",null);
		mobile.setText(user_mobile);
		email.setText(user_email);
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
		boolean flag=false;
		for(int i=0;i<StaticData.alert_lproduct_list.size();i++){
			if(StaticData.alert_lproduct_list.get(i).getProduct_id().equals(StaticData.pc_detail.get(0).getProduct_id())){
				target_price.setText(StaticData.alert_lproduct_list.get(i).getAlert_amount());
				flag=true;
				break;
			}
			
		}
		if(!flag){
			target_price.setText(StaticData.pc_detail.get(0).getProduct_mrp());
		}
		
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.pc_detail.get(0).getImagepath()+StaticData.pc_detail.get(0).getProduct_image(), product_image, opt);	
		//	seller.setOnClickListener(sellerListener);
		//	alternative.setOnClickListener(alertnativeListener);
		duration_layout.setOnClickListener(durationListener);

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
	OnClickListener shareListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonShareFragment();
			onReplace(fragment);
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
	OnClickListener durationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			final Dialog dialog=new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_duration);
			ListView duration_list=(ListView) dialog.findViewById(R.id.duration_list);
			final String[] duration_array=new String[]{"1 day","2 days","3 days","4 days","5 days","6 days","7 days","8 days","9 days","10 days","11 days","12 days","13 days","14 days","15 days","16 days","17 days","18 days","19 days","20 days","21 days","22 days","23 days","24 days","25 days","26 days","27 days","28 days","29 days","30 days"};
			
		     ArrayAdapter<String> duration_adapter = new ArrayAdapter<String>(getActivity(), R.layout.signalline_textview_xml, R.id.single_textview, duration_array);
			duration_list.setAdapter(duration_adapter);
			//duration_list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, duration_array));
			duration_list.setOnItemClickListener(new OnItemClickListener() { 

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {					
					dialog.dismiss();
					duration.setText(duration_array[arg2]);
				}
			});
			dialog.show();
		}
	};
	OnClickListener savealertListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_target_price=target_price.getText().toString();
			s_mobile=mobile.getText().toString();
			s_duration=duration.getText().toString();
			s_email=email.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_target_price)){
				UtilMethod.showToast(getResources().getString(R.string.target_price_waring),getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_email)){
				UtilMethod.showToast(getResources().getString(R.string.email_warning), getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_mobile)){
				UtilMethod.showToast(getResources().getString(R.string.mobile_warning),getActivity());
			}
			else if(s_duration.equals(getResources().getString(R.string.duration))){
				UtilMethod.showToast(getResources().getString(R.string.select_duration_warning),getActivity());
			}
			else if(!UtilMethod.isValidEmail(s_email)){
				UtilMethod.showToast(getResources().getString(R.string.valid_email_warning), getActivity());
			}
			else if(s_mobile.length()>10 || s_mobile.length()<10){
				UtilMethod.showToast(getResources().getString(R.string.valid_number_warning), getActivity());
			}
			else{
				try{
					setProgressDialog();
					MultipartEntity multipart=new MultipartEntity();
					multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id",null)));
					multipart.addPart("urlcheck",new StringBody("1"));
					multipart.addPart("txtemail1", new StringBody(s_email));
					multipart.addPart("mobileval", new StringBody(s_mobile));
					multipart.addPart("txtprice", new StringBody(s_target_price));
					multipart.addPart("txtdays", new StringBody(s_duration));
					multipart.addPart("pdid", new StringBody(StaticData.pc_detail.get(0).getProduct_id()));
					multipart.addPart("type", new StringBody("mobile"));
					new ProductAlertTask(getActivity(), multipart, new PCAlertListener(),StaticData.pc_detail.get(0).getProduct_id(),s_target_price,s_duration).execute();

				}
				catch(Exception e){

				}
			}
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
		
		loadingViewAnim.start();
		dialog.setCancelable(false);
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

	public class PCAlertListener{
		public void onSuccess(String msg,final String id,final String tprice,final String pduration){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage(msg);
			adialog.setButton("OK",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					boolean flag=false;
					for(int i=0;i<StaticData.alert_lproduct_list.size();i++){
						if(StaticData.alert_lproduct_list.get(i).getProduct_id().equals(id)){
							AlertProductBean apbean=new AlertProductBean();
							apbean.setProduct_id(id);
							apbean.setAlert_amount(tprice);
							apbean.setAlert_duration(pduration);
							StaticData.alert_lproduct_list.set(i, apbean);
							flag=true;
						}
						
					}
					if(!flag){
					AlertProductBean apbean=new AlertProductBean();
					apbean.setProduct_id(id);
					apbean.setAlert_amount(tprice);
					apbean.setAlert_duration(pduration);
					StaticData.alert_lproduct_list.add(apbean);
					}
					getActivity().getSupportFragmentManager().popBackStack();
				}
			});
			adialog.show();
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
	}
}
