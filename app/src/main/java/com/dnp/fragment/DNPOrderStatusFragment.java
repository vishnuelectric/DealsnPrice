package com.dnp.fragment;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.OrderStatusAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;

public class DNPOrderStatusFragment extends Fragment{
	View view;
	LinearLayout overview_layout,payoutstatus_layout,missingcashback_layout;
	LinearLayout footer_layout,recharge_layout,bank_layout;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	TextView shopping_layout,order_status;
	LinearLayout status_layout;
	ListView order_list;
	String[] status={"Confirmed","Rejected"};
	private TextView total_amount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_order_status, container, false);


		total_amount=(TextView)view.findViewById(R.id.total_amount);
		total_amount.setText(StaticData.user_info.get(0).getTotal_amount());

		overview_layout=(LinearLayout) view.findViewById(R.id.overview_layout);
		payoutstatus_layout=(LinearLayout) view.findViewById(R.id.payout_status_layout);
		missingcashback_layout=(LinearLayout) view.findViewById(R.id.missing_cashback_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		recharge_layout=(LinearLayout) view.findViewById(R.id.recharge_layout);
		bank_layout=(LinearLayout) view.findViewById(R.id.bank_layout);
		shopping_layout=(TextView) view.findViewById(R.id.shopping_layout);
		order_status=(TextView) view.findViewById(R.id.order_status);
		status_layout=(LinearLayout) view.findViewById(R.id.status_layout);
		order_list=(ListView) view.findViewById(R.id.order_list);
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

		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		recharge_layout.setOnClickListener(rechargeListener);
		bank_layout.setOnClickListener(bankListener);
		overview_layout.setOnClickListener(overviewListener);
		payoutstatus_layout.setOnClickListener(payoutstatusListener);
		missingcashback_layout.setOnClickListener(missingListener);
		status_layout.setOnClickListener(statusListener);
		shopping_layout.setOnClickListener(ordershoppingListener);

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

	OnClickListener ordershoppingListener= new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPOrderShoppingStatusFragment();
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

	OnClickListener statusListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.mytextview,status);

			final Dialog dialog=new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_status);
			ListView status_list=(ListView) dialog.findViewById(R.id.status_list);
			TextView cancel=(TextView) dialog.findViewById(R.id.cancel);
			status_list.setAdapter(adapter);
			status_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					StaticData.order_filter_list.clear();
					order_status.setText(status[arg2]);
					/*if(status[arg2].equals("Pending")){
						for(int i=0;i<StaticData.account_detail.size();i++){
							if((StaticData.account_detail.get(i).getCashback_status().equals("1") && StaticData.account_detail.get(i).getCashback_approve().equals("0"))){
								StaticData.order_filter_list.add(StaticData.account_detail.get(i));
							}
						}
					}
					else*/ 
					if(status[arg2].equals("Confirmed")){
						for(int i=0;i<StaticData.offer_user.size();i++){
							if(StaticData.offer_user.get(i).getConversion_status().equals("0")){
								StaticData.order_filter_list.add(StaticData.offer_user.get(i));
							}
						}
					}
					else if(status[arg2].equals("Rejected")){
						for(int i=0;i<StaticData.offer_user.size();i++){
							if(StaticData.offer_user.get(i).getConversion_status().equals("1")){
								StaticData.order_filter_list.add(StaticData.offer_user.get(i));
							}
						}
					}
					OrderStatusAdapter osadapter=new OrderStatusAdapter(getActivity());
					order_list.setAdapter(osadapter);
					osadapter.notifyDataSetChanged();

				}
			});
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.show();
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


	OnClickListener missingListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPMissingCashbackFragment();
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







}
