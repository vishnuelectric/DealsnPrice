package com.dnp.fragment;

import java.util.Calendar;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.MissingCashbackShoppingAdapter;
import com.dnp.asynctask.MissingCashBackTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class DNPMissingShoppingCashbackFragment extends Fragment{
	View view;
	LinearLayout overview_layout,payoutstatus_layout,orderstatus_layout,recharge_layout,bank_layout;
	TextView total_amount,offer_layout,date;
	EditText order_id,transaction_amount,product_title,retailer_name;
	String s_order_id,s_transaction_amount,s_product_title,s_retailer_name,s_date;
	LinearLayout footer_layout;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	Button missing_submit;
	private int year;
	private int month;
	private int day;
	Dialog dialog;
	ImageView loading_image;
	//AnimationDrawable loadingViewAnim;
	ListView missing_list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_missingcashback, container, false);
		overview_layout=(LinearLayout) view.findViewById(R.id.overview_layout);
		payoutstatus_layout=(LinearLayout) view.findViewById(R.id.payout_status_layout);
		orderstatus_layout=(LinearLayout) view.findViewById(R.id.order_status_layout);
		bank_layout=(LinearLayout) view.findViewById(R.id.bank_layout);
		recharge_layout=(LinearLayout) view.findViewById(R.id.recharge_layout);
		order_id=(EditText) view.findViewById(R.id.order_id);
		date=(TextView) view.findViewById(R.id.date);
		transaction_amount=(EditText) view.findViewById(R.id.transaction_amount);
		product_title=(EditText) view.findViewById(R.id.product_title);
		retailer_name=(EditText) view.findViewById(R.id.retailer_name);
		missing_submit=(Button) view.findViewById(R.id.missing_submit);
		offer_layout=(TextView) view.findViewById(R.id.offer_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		missing_list=(ListView) view.findViewById(R.id.missing_list);
		total_amount=(TextView)view.findViewById(R.id.total_amount);
		total_amount.setText(StaticData.user_info.get(0).getTotal_amount());
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
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
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		//profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile_active));
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.black));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		offer_layout.setOnClickListener(offermissingListener);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		bank_layout.setOnClickListener(bankListener);
		recharge_layout.setOnClickListener(rechargeListener);
		orderstatus_layout.setOnClickListener(orderstatusListener);
		payoutstatus_layout.setOnClickListener(payoutstatusListener);
		overview_layout.setOnClickListener(overviewListener);
		date.setOnClickListener(dateListener);
		missing_submit.setOnClickListener(submitListener);
		MissingCashbackShoppingAdapter mcsadapter=new MissingCashbackShoppingAdapter(getActivity());
		missing_list.setAdapter(mcsadapter);
		mcsadapter.notifyDataSetChanged();
		
		return view;
	}
	
	OnClickListener offermissingListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMissingCashbackFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	
	OnClickListener dateListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DialogFragment dfragment=new DateFragmen();
			dfragment.show(getFragmentManager(), "datePicker");
		}
	};
	
	OnClickListener submitListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			s_order_id=order_id.getText().toString();
			s_product_title=product_title.getText().toString();
			s_retailer_name=retailer_name.getText().toString();
			s_transaction_amount=transaction_amount.getText().toString();
			s_date=date.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_date)){
				UtilMethod.showToast("Please Select Date", getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_order_id)){
				UtilMethod.showToast("Please Enter Order ID", getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_product_title)){
				UtilMethod.showToast("Please Enter Product Title", getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_retailer_name)){
				UtilMethod.showToast("Please Enter Retailer Name", getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_transaction_amount)){
				UtilMethod.showToast("Please Enter Transaction Amount", getActivity());
			}
			else{
				try{
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("mis_start_date",new StringBody(s_date));
				multipart.addPart("orderid",new StringBody(s_order_id));
				multipart.addPart("amountval",new StringBody(s_transaction_amount));
				multipart.addPart("producttitle",new StringBody(s_product_title));
				multipart.addPart("retailertitle",new StringBody(s_retailer_name));
				multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id", null)));
				multipart.addPart("extension",new StringBody("1"));
				setProgressDialog();
				new MissingCashBackTask(getActivity(), multipart, new MissingCashBackListener()).execute();
				
				}
				catch(Exception e){
					
				}
			}
		}
	};
/*	
	private void setProgressDialog(){
		
		dialog=new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading);
		LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
		loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
		dialog.setCancelable(false);
		loading_image.setBackgroundResource(R.anim.loading_animation);
		loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
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
	public class MissingCashBackListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage("Your Request Sent Successfully");
			adialog.setButton("OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					order_id.setText("");
					date.setText("");
					product_title.setText("");
					retailer_name.setText("");
					transaction_amount.setText("");
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
				adialog.setTitle("Warning");
				adialog.setMessage("Your request has not sent due to some issues");
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
	}
	
	
	
	
	OnClickListener orderstatusListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPOrderStatusFragment();
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
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	OnClickListener payoutstatusListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPPayoutStatusFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
			
		}
	};
	OnClickListener overviewListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMyAccountFragment();
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
	OnClickListener bankListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMyAccountBankFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	OnClickListener rechargeListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMyAccountRechargeFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	public class DateFragmen extends DialogFragment implements DatePickerDialog.OnDateSetListener{

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			year  = year;
			month = monthOfYear;
			day   = dayOfMonth;

			// Show selected date
			date.setText(new StringBuilder().append(year).append("-").append(month + 1)
					.append("-").append(day)
					.append(" "));

		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH); 

			DatePickerDialog dialog=new DatePickerDialog(getActivity(), this, year, month, day);
			dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
			// Create a new instance of DatePickerDialog and return it
			return dialog;
		}
	}
	
	
}
