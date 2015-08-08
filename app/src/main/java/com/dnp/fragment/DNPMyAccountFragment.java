package com.dnp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dealnprice.activity.R;
import com.dnp.asynctask.GetUserInfoTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class DNPMyAccountFragment extends Fragment {
	View view;
	LinearLayout overview_layout, order_status_layout, payout_status_layout,
	missing_cashback_layout;
	LinearLayout footer_layout;
	LinearLayout recharge_layout, bank_layout;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	ImageView loading_image;
	Dialog dialog;
	//AnimationDrawable loadingViewAnim;
	TextView pending_own, pending_referral, approval_own, approval_referral,
	total_pending_cashback, total_approval_cashback,
	requested_cashback, paid_cashback,paid__rejected_cashback;
	float pending_own_amount = 0, pending_referral_amount = 0,
			approved_own_amount = 0, approved_referral_amount = 0,
			total_pending_amount = 0, total_approved_amount = 0,
			requested_cashback_amount = 0, paid_cashback_amount = 0,rejected_amount = 0;
	private TextView total_amount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_myaccount, container, false);
		order_status_layout = (LinearLayout) view
				.findViewById(R.id.order_status_layout);
		payout_status_layout = (LinearLayout) view
				.findViewById(R.id.payout_status_layout);
		missing_cashback_layout = (LinearLayout) view
				.findViewById(R.id.missing_cashback_layout);
		footer_layout = (LinearLayout) view.findViewById(R.id.footer_layout);
		recharge_layout = (LinearLayout) view
				.findViewById(R.id.recharge_layout);
		bank_layout = (LinearLayout) view.findViewById(R.id.bank_layout);
		pending_own = (TextView) view.findViewById(R.id.pending_own);
		pending_referral = (TextView) view.findViewById(R.id.pending_referal);
		approval_own = (TextView) view.findViewById(R.id.approved_own);
		approval_referral = (TextView) view.findViewById(R.id.approved_referal);
		total_pending_cashback = (TextView) view
				.findViewById(R.id.total_pending_cashback);
		total_approval_cashback = (TextView) view
				.findViewById(R.id.total_approved_cashback);
		requested_cashback = (TextView) view
				.findViewById(R.id.requested_cashback);
		paid_cashback = (TextView) view.findViewById(R.id.paid_cashback);
		paid__rejected_cashback=(TextView)view.findViewById(R.id.paid__rejected_cashback);
		total_amount = (TextView) view.findViewById(R.id.total_amount);
		fmanager = getActivity().getSupportFragmentManager();
		ft = fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();

		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();

		LayoutInflater inflater1 = LayoutInflater.from(getActivity());
		View v1 = inflater1.inflate(R.layout.activity_footer, null);
		LinearLayout home_layout = (LinearLayout) v1
				.findViewById(R.id.home_layout);
		LinearLayout profile_layout = (LinearLayout) v1
				.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout = (LinearLayout) v1
				.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout = (LinearLayout) v1
				.findViewById(R.id.notification_layout);
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		ImageView home_icon = (ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon = (ImageView) v1
				.findViewById(R.id.notification_icon);
		ImageView profile_icon = (ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon = (ImageView) v1
				.findViewById(R.id.favourite_icon);
		// profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile_active));
		TextView home_text = (TextView) v1.findViewById(R.id.home_text);
		TextView notification_text = (TextView) v1
				.findViewById(R.id.notification_text);
		TextView profile_text = (TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text = (TextView) v1
				.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.black));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		bank_layout.setOnClickListener(bankListener);
		recharge_layout.setOnClickListener(rechargeListener);
		order_status_layout.setOnClickListener(orderstatusListener);
		payout_status_layout.setOnClickListener(payoutstatusListener);
		missing_cashback_layout.setOnClickListener(missingListener);
		setProgressDialog();
		new GetUserInfoTask(getActivity(), new MyAccountListener()).execute();

		return view;
	}

	/*private void setProgressDialog() {

		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading);
		LinearLayout loadinglayout = (LinearLayout) dialog
				.findViewById(R.id.LinearLayout1);
		loading_image = (ImageView) dialog.findViewById(R.id.imageView111);
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

	OnClickListener orderstatusListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new DNPOrderStatusFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};

	OnClickListener offerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new OfferFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener payoutstatusListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new DNPPayoutStatusFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};
	OnClickListener missingListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new DNPMissingCashbackFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener favouriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new FavouriteFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};
	OnClickListener profileListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new ProfileFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};
	OnClickListener notificationListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new NotificationFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};
	OnClickListener bankListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if(Double.parseDouble(total_amount.getText().toString())<250)
			{
				Toast.makeText(getActivity(), "You are requested to raise cash redemption request when you have minimum Rs 250 approved cash back in your wallet.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				fragment = new DNPMyAccountBankFragment();
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

	public class MyAccountListener {
		public void onSuccess() {
			try {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}

				pending_own_amount=0;
				pending_referral_amount=0;
				approved_own_amount=0;
				approved_referral_amount=0;
				paid_cashback_amount=0;
				requested_cashback_amount=0;
				rejected_amount=0;


				for (int i = 0; i < StaticData.account_detail.size(); i++) {
					if (StaticData.account_detail.get(i).getCashback_type()
							.equals("Order")
							&& (StaticData.account_detail.get(i)
									.getCashback_status().equals("1") && StaticData.account_detail
									.get(i).getCashback_approve().equals("0"))) {
						if (!UtilMethod
								.isStringNullOrBlank(StaticData.account_detail.get(
										i).getCashback_amount())) {
							pending_own_amount += Float
									.valueOf(StaticData.account_detail.get(i)
											.getCashback_amount());
						}
					} else if (!StaticData.account_detail.get(i).getCashback_type()
							.equals("Order")
							&& (StaticData.account_detail.get(i)
									.getCashback_status().equals("1") && StaticData.account_detail
									.get(i).getCashback_approve().equals("0"))) {
						if (!UtilMethod
								.isStringNullOrBlank(StaticData.account_detail.get(
										i).getCashback_amount())) {
							pending_referral_amount += Float
									.valueOf(StaticData.account_detail.get(i)
											.getCashback_amount());
						}
					} else if (StaticData.account_detail.get(i).getCashback_type()
							.equals("Order")
							&& StaticData.account_detail.get(i)
							.getCashback_status().equals("2")
							&& StaticData.account_detail.get(i)
							.getCashback_approve().equals("0")) {
						if (!UtilMethod
								.isStringNullOrBlank(StaticData.account_detail.get(
										i).getCashback_amount())) {
							approved_own_amount += Float
									.valueOf(StaticData.account_detail.get(i)
											.getCashback_amount());
						}
					} else if (!StaticData.account_detail.get(i).getCashback_type()
							.equals("Order")
							&& StaticData.account_detail.get(i)
							.getCashback_status().equals("2")) {
						if (!UtilMethod
								.isStringNullOrBlank(StaticData.account_detail.get(
										i).getCashback_amount())) {
							approved_referral_amount += Float
									.valueOf(StaticData.account_detail.get(i)
											.getCashback_amount());
						}

					}
					if(Integer.parseInt(StaticData.account_detail.get(i)
							.getCashback_status())>2)
					{
						if (!UtilMethod
								.isStringNullOrBlank(StaticData.account_detail.get(
										i).getCashback_amount())) {
							rejected_amount += Float
									.valueOf(StaticData.account_detail.get(i)
											.getCashback_amount());
						}
					}


				}

				for (int j = 0; j < StaticData.my_paid_detail.size(); j++) {
					if (!UtilMethod.isStringNullOrBlank(StaticData.my_paid_detail
							.get(j).getPaid_amount_value())) {
						if(StaticData.my_paid_detail.get(j).getPaid_amount_type().equals("1"))
						{
							paid_cashback_amount += Float
									.valueOf(StaticData.my_paid_detail.get(j)
											.getPaid_amount_value());
						}


					}

				}
				for (int j = 0; j < StaticData.my_paid_detail.size(); j++) {
					if (!UtilMethod.isStringNullOrBlank(StaticData.my_paid_detail
							.get(j).getPaid_amount_value()) 
							&& StaticData.my_paid_detail.get(j).getPaid_amount_type().equals("2")) {
						requested_cashback_amount += Float
								.valueOf(StaticData.my_paid_detail.get(j)
										.getPaid_amount_value());
					}

				}
				if (pending_own_amount != 0) {
					pending_own.setText("" + Math.round(pending_own_amount));
				} else {
					pending_own.setText("0.0");
				}
				if (pending_referral_amount != 0) {
					pending_referral.setText("" + Math.round(pending_referral_amount));
				} else {
					pending_referral.setText("0.0");
				}
				if (approved_own_amount != 0) {
					approval_own.setText("" + Math.round(approved_own_amount));
				} else {
					approval_own.setText("0.0");
				}
				if (approved_referral_amount != 0) {
					approval_referral.setText("" + Math.round(approved_referral_amount));
				} else {
					approval_referral.setText("0.0");
				}



				if (requested_cashback_amount != 0) {
					requested_cashback.setText("" + Math.round(requested_cashback_amount));
				} else {
					requested_cashback.setText("0.0");
				}
				if (paid_cashback_amount != 0) {
					paid_cashback.setText("" + Math.round(paid_cashback_amount));
				} else {
					paid_cashback.setText("0.0");
				}


				total_pending_amount = Math.round(pending_own_amount) + Math.round(pending_referral_amount);
				total_approved_amount = Math.round(approved_own_amount)	+ Math.round(approved_referral_amount);
				if (total_pending_amount != 0) {
					total_pending_cashback.setText("" + Math.round(total_pending_amount));
				} else {
					total_pending_cashback.setText("0.0");
				}
				if (total_approved_amount != 0) {
					total_approval_cashback.setText("" + Math.round(total_approved_amount));
				} else {
					total_approval_cashback.setText("0.0");
				}
				total_amount.setText(""+Math.round(Double.parseDouble(StaticData.user_info.get(0).getTotal_amount())));
				paid__rejected_cashback.setText(""+Math.round(rejected_amount));

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		public void onError(String msg) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (msg.equals("slow")) {
				UtilMethod.showServerError(getActivity());
			} else {
				final AlertDialog adialog = new AlertDialog.Builder(
						getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
				adialog.setButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
						getActivity().getSupportFragmentManager()
						.popBackStack();
					}
				});
				adialog.show();
			}
		}
	}
}
