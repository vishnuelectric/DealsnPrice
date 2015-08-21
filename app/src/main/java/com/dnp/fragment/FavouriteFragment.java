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
import com.dnp.adapter.FavouriteAdapter;
import com.dnp.asynctask.GetUserInfoTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.asynctask.RemoveFavouriteTask;
import com.dnp.bean.AlertProductBean;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class FavouriteFragment extends Fragment{
	View view;
	ListView favourite_list;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	LinearLayout footer_layout;
	HorizontalScrollView horizontal_id;
	FragmentTransaction ft;
	Fragment fragment;
	FragmentManager fm;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	String s_target_price;
	String s_email, s_mobile, s_duration;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_favourite, container, false);
		favourite_list=(ListView) view.findViewById(R.id.favourite_list);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");
		fm=getFragmentManager();
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
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
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_inactive));
		favourite_icon.setImageDrawable(getResources().getDrawable(R.drawable.favorite_active));
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.gray));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.black));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		
		if(UtilMethod.isNetworkAvailable(getActivity())){
			setProgressDialog();
		new GetUserInfoTask(getActivity(), new FavouriteListener()).execute();
		}
		else{
			UtilMethod.showServerError(getActivity());
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
	}*/
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}
	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			onReplace(fragment);
		}
	};
	
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			onReplace(fragment);
		}
	};
	
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			onReplace(fragment);
		}
	};
	
	OnClickListener homeListener=new OnClickListener() {
		
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
			onReplace(fragment);
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
	
	
	public class FavouriteListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
		//	UtilMethod.showToast("Favourite Size is "+StaticData.favourite_list.size(), getActivity());
			if(StaticData.favourite_list.size()>0){
			favourite_list.setAdapter(new FavouriteAdapter(getActivity(),new FavouriteListener()));
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage("No Favourite Product Found!");
				adialog.setCancelable(false);
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
						getActivity().getSupportFragmentManager().popBackStack();
					}
				});
				adialog.show();
			}
		}
		public void onFixAlert(String msg,final String id1,final String price,final String duration){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage(msg);
			adialog.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					boolean flag=false;
					for(int i=0;i<StaticData.alert_lproduct_list.size();i++){
						if(StaticData.alert_lproduct_list.get(i).getProduct_id().equals(id1)){
							AlertProductBean apbean=new AlertProductBean();
							apbean.setProduct_id(id1);
							apbean.setAlert_amount(price);
							apbean.setAlert_duration(duration);
							StaticData.alert_lproduct_list.set(i, apbean);
							flag=true;
						}
						
					}
					if(!flag){
					AlertProductBean apbean=new AlertProductBean();
					apbean.setProduct_id(id1);
					apbean.setAlert_amount(price);
					apbean.setAlert_duration(duration);
					StaticData.alert_lproduct_list.add(apbean);
					}
				}
			});
			adialog.show();
		}
		
		public void onRemoveProduct(final int position){
			try{
			MultipartEntity multipart=new MultipartEntity();
			multipart.addPart("extension", new StringBody("1"));
			multipart.addPart("type",new StringBody(StaticData.favourite_list.get(position).getProduct_type()));
			multipart.addPart("pid", new StringBody(StaticData.favourite_list.get(position).getProduct_id()));
			multipart.addPart("userid", new StringBody(getActivity().getSharedPreferences("User_login", 1).getString("user_id", null)));
			setProgressDialog();
			new RemoveFavouriteTask(getActivity(),multipart,new FavouriteListener(),position).execute();
			}
			catch(Exception e){
				
			}
		}
		
		public void onSuccessRemoveProduct(int position){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			StaticData.favourite_list.remove(position);
			FavouriteAdapter adapter=new FavouriteAdapter(getActivity(), new FavouriteListener());
			favourite_list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
		
		public void onSetAlert(final int position){
			/*Fragment fragment=new FavouriteAlertFragment();
			FragmentManager fm=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			Bundle b=new Bundle();
			b.putString("product_id",product_id);
			fragment.setArguments(b);
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();*/
			final Dialog dialog1=new Dialog(getActivity());
			dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog1.setContentView(R.layout.activity_favourite_alert);
			LinearLayout duration_layout=(LinearLayout) dialog1.findViewById(R.id.duration_layout);
			final EditText target_price=(EditText) dialog1.findViewById(R.id.target_price);
			final EditText email=(EditText) dialog1.findViewById(R.id.email);
			final EditText mobile=(EditText) dialog1.findViewById(R.id.mobile);
			final TextView duration=(TextView) dialog1.findViewById(R.id.duration);
			final Button alert_me=(Button) dialog1.findViewById(R.id.alert_me);
			target_price.setHintTextColor(getResources().getColor(R.color.black));
			email.setHintTextColor(getResources().getColor(R.color.black));
			mobile.setHintTextColor(getResources().getColor(R.color.black));
			
			SharedPreferences shpf=getActivity().getSharedPreferences("User_login",1);
			String user_email=shpf.getString("user_email",null);
			String user_mobile=shpf.getString("user_mobile", null);
			email.setText(user_email);
			mobile.setText(user_mobile);
			boolean flag=false;
			for(int i=0;i<StaticData.alert_lproduct_list.size();i++){
				if(StaticData.alert_lproduct_list.get(i).getProduct_id().equals(StaticData.favourite_list.get(position).getProduct_id())){
					target_price.setText(StaticData.alert_lproduct_list.get(i).getAlert_amount());
					flag=true;
					break;
				}
				
			}
			if(!flag){
				target_price.setText(StaticData.favourite_list.get(position).getProduct_price());
			}
			
			
			duration_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					final Dialog dialog2=new Dialog(getActivity());
					dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog2.setContentView(R.layout.activity_duration);
					ListView duration_list=(ListView) dialog2.findViewById(R.id.duration_list);
					final String[] duration_array=new String[]{"1 day","2 days","3 days","4 days","5 days","6 days","7 days","8 days","9 days","10 days","11 days","12 days","13 days","14 days","15 days","16 days","17 days","18 days","19 days","20 days","21 days","22 days","23 days","24 days","25 days","26 days","27 days","28 days","29 days","30 days"};
					
				     ArrayAdapter<String> duration_adapter = new ArrayAdapter<String>(getActivity(), R.layout.signalline_textview_xml, R.id.single_textview, duration_array);
					duration_list.setAdapter(duration_adapter);
					//duration_list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, duration_array));
					duration_list.setOnItemClickListener(new OnItemClickListener() { 

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {					
							dialog2.dismiss();
							duration.setText(duration_array[arg2]);
						}
					});
					dialog2.show();
				
				}
			});
			
			alert_me.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					s_target_price=target_price.getText().toString();
					s_email=email.getText().toString();
					s_mobile=mobile.getText().toString();
					s_duration=duration.getText().toString();
					if(UtilMethod.isStringNullOrBlank(s_target_price)){
						UtilMethod.showToast("Please Enter Target Price", getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_email)){
						UtilMethod.showToast("Please Enter Email Address", getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_mobile)){
						UtilMethod.showToast("Please Enter Mobile Number", getActivity());
					}
					else if(UtilMethod.isStringNullOrBlank(s_duration)){
						UtilMethod.showToast("Please Select Duration", getActivity());
					}
					else if(s_duration.equals("Duration")){
						UtilMethod.showToast(getResources().getString(R.string.select_duration_warning), getActivity());
					}
					else{
						try{
							dialog1.dismiss();
							setProgressDialog();
							MultipartEntity multipart=new MultipartEntity();
							multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id",null)));
							multipart.addPart("urlcheck",new StringBody("1"));
							multipart.addPart("txtemail1", new StringBody(s_email));
							multipart.addPart("mobileval", new StringBody(s_mobile));
							multipart.addPart("txtprice", new StringBody(s_target_price));
							multipart.addPart("txtdays", new StringBody(s_duration));
							multipart.addPart("pdid", new StringBody(StaticData.favourite_list.get(position).getProduct_id()));
							multipart.addPart("type", new StringBody("mobile"));
							new ProductAlertTask(getActivity(), multipart, new FavouriteListener(),StaticData.favourite_list.get(position).getProduct_id(),s_target_price,s_duration).execute();

						}
						catch(Exception e){

						}
					}
				
				//UtilMethod.showToast("Under Development",getActivity());	
				}
			});
			
			dialog1.show();
			
		}
	}
}
