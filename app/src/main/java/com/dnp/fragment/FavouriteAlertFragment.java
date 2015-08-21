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
import com.dnp.data.UtilMethod;

public class FavouriteAlertFragment extends Fragment{
	EditText target_price,email,mobile;
	TextView duration;
	Button save_alert;
	SharedPreferences shpf;
	String s_target_price,s_email,s_mobile,s_duration;
	LinearLayout footer_layout;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	LinearLayout duration_layout;
	Bundle b;
	String product_id;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_favourite_alert, container, false);
		target_price=(EditText) view.findViewById(R.id.target_price);
		email=(EditText) view.findViewById(R.id.email);
		mobile=(EditText) view.findViewById(R.id.mobile);
		duration=(TextView) view.findViewById(R.id.duration);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		save_alert=(Button) view.findViewById(R.id.save_alert);
		duration_layout=(LinearLayout) view.findViewById(R.id.duration_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");
		//DashboardActivity.onCustomActionView();

		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();

		b=getArguments();
		product_id=b.getString("product_id");

		shpf=getActivity().getSharedPreferences("User_login", 1);
		LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile));
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.black));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.black));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_inactive));
		favourite_icon.setImageDrawable(getResources().getDrawable(R.drawable.favorite_active));
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		duration_layout.setOnClickListener(durationListener);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);

		return view;
	}

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
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
	OnClickListener offerListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new OfferFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	}; 
	OnClickListener profileListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new ProfileFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new FavouriteFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener notificationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment f=new NotificationFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener durationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Dialog dialog=new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_duration);
			ListView duration_list=(ListView) dialog.findViewById(R.id.duration_list);
			final String[] duration_array=getResources().getStringArray(R.array.duration_array);
			ArrayAdapter<String> duration_adapter = new ArrayAdapter<String>(getActivity(), R.layout.signalline_textview_xml, R.id.single_textview, duration_array);
			duration_list.setAdapter(duration_adapter);
			duration_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					duration.setText(duration_array[arg2]);
				}
			});
		}
	};
	OnClickListener alertListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_target_price=target_price.getText().toString();
			s_email=email.getText().toString();
			s_mobile=mobile.getText().toString();
			s_duration=duration.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_target_price)){
				UtilMethod.showToast(getResources().getString(R.string.target_price_waring),getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_email)){
				UtilMethod.showToast(getResources().getString(R.string.email_warning),getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_mobile)){
				UtilMethod.showToast(getResources().getString(R.string.mobile_warning),getActivity());
			}
			else if(s_duration.equals("Duration")){
				UtilMethod.showToast(getResources().getString(R.string.select_duration_warning), getActivity());
			}
			else if(!UtilMethod.isValidEmail(s_email)){
				UtilMethod.showToast(getResources().getString(R.string.valid_email_warning), getActivity());
			}
			else if(s_mobile.length()>10 || s_mobile.length()<0){
				UtilMethod.showToast(getResources().getString(R.string.valid_mobile_warning), getActivity());
			}
			else{
				try{
					MultipartEntity multipart=new MultipartEntity();
					multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id",null)));
					multipart.addPart("urlcheck",new StringBody("1"));
					multipart.addPart("txtemail1", new StringBody(s_email));
					multipart.addPart("mobileval", new StringBody(s_mobile));
					multipart.addPart("txtprice",new StringBody(s_target_price));
					multipart.addPart("txtdays",new StringBody(s_duration));
					multipart.addPart("pdid",new StringBody(product_id));
					multipart.addPart("type", new StringBody("1"));
					setProgressDialog();
					new ProductAlertTask(getActivity(), multipart, new FAlertListener(),product_id,s_target_price,s_duration).execute();
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

	public class FAlertListener{

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
