package com.dnp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.PayoutStatusAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;

public class DNPPayoutStatusFragment extends Fragment{
	View view;
	LinearLayout overview_layout,order_status_layout,payout_status_layout,missing_cashback_layout;
	LinearLayout footer_layout;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	ListView payoutstatus_list;
	private TextView total_amount;
	LinearLayout recharge_layout, bank_layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_payoutstatus, container, false);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();

		total_amount=(TextView)view.findViewById(R.id.total_amount);
		total_amount.setText(StaticData.user_info.get(0).getTotal_amount());
		recharge_layout = (LinearLayout) view
				.findViewById(R.id.recharge_layout);
		bank_layout = (LinearLayout) view.findViewById(R.id.bank_layout);
		overview_layout=(LinearLayout) view.findViewById(R.id.overview_layout);
		order_status_layout=(LinearLayout) view.findViewById(R.id.order_status_layout);
		payout_status_layout=(LinearLayout) view.findViewById(R.id.payout_status_layout);
		missing_cashback_layout=(LinearLayout) view.findViewById(R.id.missing_cashback_layout);
		payoutstatus_list=(ListView) view.findViewById(R.id.payout_list);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);

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
		overview_layout.setOnClickListener(overviewListener);
		missing_cashback_layout.setOnClickListener(missingcashbackListener);
		order_status_layout.setOnClickListener(orderstatusListener);
		bank_layout.setOnClickListener(bankListener);
		recharge_layout.setOnClickListener(rechargeListener);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		payoutstatus_list.setAdapter(new PayoutStatusAdapter(getActivity()));

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
			ft.replace(R.id.container, fragment);
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

	OnClickListener rechargeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new DNPMyAccountRechargeFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};


	public class PayoutStatusListener{
		public void onSuccess(){

		}
		public void onError(){

		}
	}
}
