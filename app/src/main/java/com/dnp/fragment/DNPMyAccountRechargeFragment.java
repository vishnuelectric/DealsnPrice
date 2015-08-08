package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.UtilMethod;

public class DNPMyAccountRechargeFragment extends Fragment{
	View view;
	LinearLayout overview_layout,order_status_layout,payout_status_layout,missing_cashback_layout,footer_layout;
	EditText mobile,amount;
	TextView operator,regional;
	LinearLayout operator_layout,regional_layout;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	TextView recharge;
	String circle_text,regional_text,s_mobile,s_amount;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =inflater.inflate(R.layout.activity_myaccount_recharge, container, false);
		overview_layout=(LinearLayout) view.findViewById(R.id.overview_layout);
		order_status_layout=(LinearLayout) view.findViewById(R.id.order_status_layout);
		payout_status_layout=(LinearLayout) view.findViewById(R.id.payout_status_layout);
		missing_cashback_layout=(LinearLayout) view.findViewById(R.id.missing_cashback_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		mobile=(EditText) view.findViewById(R.id.mobile);
		amount=(EditText) view.findViewById(R.id.amount);
		operator=(TextView) view.findViewById(R.id.operator);
		regional=(TextView) view.findViewById(R.id.regional);
		operator_layout=(LinearLayout) view.findViewById(R.id.operator_layout);
		regional_layout=(LinearLayout) view.findViewById(R.id.regional_layout);
		recharge=(TextView) view.findViewById(R.id.recharge);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		overview_layout.setOnClickListener(overviewListener);
		payout_status_layout.setOnClickListener(payoutstatusListener);
		missing_cashback_layout.setOnClickListener(missingcashbackListener);
		order_status_layout.setOnClickListener(orderstatusListener);
			
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
		favourite_layout.setOnClickListener(favoriteListener);
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
		
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
	
		
		return view;
	}
	
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
	OnClickListener favoriteListener=new OnClickListener() {
		
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
	OnClickListener payoutstatusListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPPayoutStatusFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};	
	OnClickListener missingcashbackListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMissingCashbackFragment();
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
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
			
		}
	};
	OnClickListener banktransferListener=new OnClickListener() {
		
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
			s_amount=amount.getText().toString();
			s_mobile=mobile.getText().toString();
			circle_text=operator.getText().toString();
			regional_text=regional.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_mobile)){
				UtilMethod.showToast(getResources().getString(R.string.mobile_warning), getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(circle_text) || circle_text.equalsIgnoreCase("circle")){
				UtilMethod.showToast(getResources().getString(R.string.circle), getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(regional_text) || regional_text.equalsIgnoreCase("Operator")){
				UtilMethod.showToast(getResources().getString(R.string.regional_warning), getActivity());
			}
			else if(UtilMethod.isStringNullOrBlank(s_amount)){
				UtilMethod.showToast(getResources().getString(R.string.amount_warning),getActivity());
			}
			else{
				MultipartEntity multipart=new MultipartEntity();
			}
		}
	};
	
}
