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
import android.widget.Toast;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.MissingCashbackAdapter;
import com.dnp.asynctask.GetMissingCashbackTask;
import com.dnp.asynctask.MissingCashBackTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class DNPMissingCashbackFragment extends Fragment{
	View view;
	LinearLayout overview_layout,order_status_layout,payout_status_layout,missing_cashback_layout;
	LinearLayout footer_layout,date_layout;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	TextView more_missing,shopping_layout,date;
	LinearLayout recharge_layout,bank_layout;
	ListView missing_list;
	Dialog dialog;
	ImageView loading_image;
	//AnimationDrawable loadingViewAnim;
	EditText offer_title,amount;
	Button missing_submit;
	String s_offer_title,s_amount,s_date;
	private int year;
	private int month;
	private int day;
	private TextView total_amount;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_missingcashback_shopping, container, false);

		total_amount=(TextView)view.findViewById(R.id.total_amount);
		total_amount.setText(StaticData.user_info.get(0).getTotal_amount());

		overview_layout=(LinearLayout) view.findViewById(R.id.overview_layout);
		order_status_layout=(LinearLayout) view.findViewById(R.id.order_status_layout);
		payout_status_layout=(LinearLayout) view.findViewById(R.id.payout_status_layout);
		missing_cashback_layout=(LinearLayout) view.findViewById(R.id.missing_cashback_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		recharge_layout=(LinearLayout) view.findViewById(R.id.recharge_layout);
		bank_layout=(LinearLayout) view.findViewById(R.id.bank_layout);
		shopping_layout=(TextView) view.findViewById(R.id.shopping_layout);
		missing_list=(ListView) view.findViewById(R.id.missing_list);
		offer_title=(EditText) view.findViewById(R.id.offer_title);
		amount=(EditText) view.findViewById(R.id.amount);
		date_layout=(LinearLayout) view.findViewById(R.id.date_layout);
		fmanager=getActivity().getSupportFragmentManager();
		missing_submit=(Button) view.findViewById(R.id.missing_submit);
		date=(TextView) view.findViewById(R.id.date);
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
		recharge_layout.setOnClickListener(rechargeListener);
		bank_layout.setOnClickListener(bankListener);
		overview_layout.setOnClickListener(overviewListener);
		payout_status_layout.setOnClickListener(payoutstatusListener);
		order_status_layout.setOnClickListener(orderstatusListener);
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

		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		shopping_layout.setOnClickListener(missingshoppingListener);
		date_layout.setOnClickListener(dateListener);
		if(UtilMethod.isNetworkAvailable(getActivity())){
			setProgressDialog();
			new GetMissingCashbackTask(getActivity(),new MissingCashbackListener()).execute();
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

	OnClickListener missingSubmitListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_date=date.getText().toString();
			s_amount=amount.getText().toString();
			s_offer_title=offer_title.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_date)){
				UtilMethod.showToast("Please Select Date", getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_amount)){
				UtilMethod.showToast("Please Enter Amount", getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_offer_title)){
				UtilMethod.showToast("Please Enter Offer Title", getActivity());
			}
			else{
				try{
					MultipartEntity multipart=new MultipartEntity();
					multipart.addPart("mis_start_date",new StringBody(s_date));
					multipart.addPart("orderid",new StringBody(""));
					multipart.addPart("amountval",new StringBody(s_amount));
					multipart.addPart("producttitle",new StringBody(s_offer_title));
					multipart.addPart("retailertitle",new StringBody(""));
					multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id",null)));
					multipart.addPart("extension",new StringBody("1"));
					multipart.addPart("mistype",new StringBody("1"));
					new MissingCashBackTask(getActivity(), multipart, new MissingCashbackListener()).execute();
				}
				catch(Exception e){

				}
			}
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




	public class MissingCashbackListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			MissingCashbackAdapter mcadapter=new MissingCashbackAdapter(getActivity());
			missing_list.setAdapter(mcadapter);
			mcadapter.notifyDataSetChanged();
			missing_submit.setOnClickListener(missingSubmitListener);
		}
		public void onSuccessRequest(String msg){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage(msg);
			adialog.setButton("OK",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					adialog.dismiss();
					date.setText("");
					amount.setText("");
					offer_title.setText("");
				}
			});
			adialog.show();
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
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
			}
		}
	}



	OnClickListener missingshoppingListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMissingShoppingCashbackFragment();
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
	OnClickListener bankListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(Double.parseDouble(total_amount.getText().toString())<250)
			{
				Toast.makeText(getActivity(), "You are requested to raise cash redemption request when you have minimum Rs 250 approved cash back in your wallet.", Toast.LENGTH_SHORT).show();
			}
			else
			{			
				fragment=new DNPMyAccountBankFragment();
				ft.replace(R.id.container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
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
}
