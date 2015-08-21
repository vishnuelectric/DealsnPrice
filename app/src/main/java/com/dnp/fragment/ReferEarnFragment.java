package com.dnp.fragment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.FullContactListAdapter;
import com.dnp.asynctask.GetReferEarnTask;
import com.dnp.bean.ContactAdapter;
import com.dnp.bean.ContactBean;
import com.dnp.data.APP_Constants;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

public class ReferEarnFragment extends Fragment {
	String url;
	String user_id;
	SharedPreferences shpf;
	String usercode;
	LinearLayout sms_layout, whatsapp_layout, other_layout, gmail_layout;
	LinearLayout footer_layout, offer_layout, shopearn_layout,
	pricecomparison_layout, dealcoupon_layout, referearn_layout;
	TextView shopearn_text, inviteearn_text, dealprice_text, couponprice_text;
	Dialog dialog;
	FragmentTransaction ft;
	Fragment fragment;
	FragmentManager fm;
	ImageView loading_image;
	private AnimationDrawable loadingViewAnim;
	TextView copy_link, refer_link;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_referearn, container,
				false);

		offer_layout = (LinearLayout) view.findViewById(R.id.offer_layout);
		footer_layout = (LinearLayout) view.findViewById(R.id.footer_layout);
		shopearn_layout = (LinearLayout) view
				.findViewById(R.id.shopearn_layout);
		fm = getActivity().getSupportFragmentManager();
		ft = fm.beginTransaction();
		pricecomparison_layout = (LinearLayout) view
				.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout = (LinearLayout) view
				.findViewById(R.id.dealprice_layout);
		referearn_layout = (LinearLayout) view
				.findViewById(R.id.referearn_layout);
		shopearn_text = (TextView) view.findViewById(R.id.shopearn_text);
		inviteearn_text = (TextView) view
				.findViewById(R.id.pricecomparison_text);
		dealprice_text = (TextView) view.findViewById(R.id.dealprice_text);
		couponprice_text = (TextView) view.findViewById(R.id.referearn_text);
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
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		pricecomparison_layout.setOnClickListener(inviteEarnListener);
		referearn_layout.setOnClickListener(couponListener);
		dealcoupon_layout.setOnClickListener(dealpriceListener);
		//--
		LinearLayout lin[]	=	new LinearLayout[]{offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout};
		DashboardActivity.actRef.selectTab(lin, APP_Constants.REFER_EARN);
		//--

		shpf = getActivity().getSharedPreferences("User_login", 1);
		sms_layout = (LinearLayout) view.findViewById(R.id.sms_layout);
		whatsapp_layout = (LinearLayout) view
				.findViewById(R.id.whatsapp_layout);
		gmail_layout = (LinearLayout) view.findViewById(R.id.gmail_layout);
		other_layout = (LinearLayout) view.findViewById(R.id.other_layout);
		copy_link = (TextView) view.findViewById(R.id.copy_link);
		refer_link = (TextView) view.findViewById(R.id.refer_link);
		user_id = shpf.getString("user_id", "");
		usercode = shpf.getString("usercode", "");
		url = WebService.REFER_EARN_WEBSERVICE + user_id;
		if(StaticData.referearn_list.size() == 0) {
			setProgressDialog();
			new GetReferEarnTask(getActivity(), url, new ReferEarnListener())
					.execute();
		}
		else
		{
			new ReferEarnListener().onSuccess();

		}

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
	}
*/
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {

			}
		});
		dialog.show();
	}
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
	OnClickListener offerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new OfferFragment();
			onReplace(fragment);
		}
	};

	public void onReplace(Fragment fragment1) {
		ft = fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}

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
	OnClickListener shopEarnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new ShopEarnFragment();
			onReplace(fragment);
		}
	};
	OnClickListener inviteEarnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			/* fragment=new InviteEarnFragment(); */
			fragment = new PriceComparisonFragment();
			onReplace(fragment);
		}
	};
	OnClickListener dealpriceListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new DNPDealCouponFragment();
			onReplace(fragment);
		}
	};
	OnClickListener couponListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment = new ReferEarnFragment();
			onReplace(fragment);
		}
	};

	OnClickListener otherShareListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			/*String text = "Download the dealsnprice app & signup using my referral code "
					+ usercode
					+ " & get ready to earn upto Rs. 500 on monthly basis. Also get upto 25% cashback on all your online purchases. Download Now - "
					+ WebService.PLAY_STORE_URL;
			*/
			String text = "Download dealsnprice app, use my referral code "
					+ usercode
					+ " to earn Rs.500 monthly & 25% cashback on all online purchases."
					+ WebService.PLAY_STORE_URL;
			sendIntent.putExtra(Intent.EXTRA_TEXT, text);
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}
	};

	public void readContacts() {
		ContentResolver cr = getActivity().getContentResolver();
		String id;
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, Phone.DISPLAY_NAME + " ASC");
		Cursor cur1 = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
		Log.v("Contact no", "is " + cur.getCount());

		if (cur.getCount() > 0) {
			if (cur.moveToFirst()) {
				do {

					ContactAdapter pcu = new ContactAdapter();
					id = cur.getString(cur
							.getColumnIndex(ContactsContract.Contacts._ID));
					String hasphonenumber = cur
							.getString(cur
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					/* pcu.setId(id); */
					if (Integer.parseInt(hasphonenumber) > 0) {

						String name = cur
								.getString(cur
										.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						String capital_name = name.toUpperCase();
						Cursor curc = cr
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
										+ " = ?", new String[] { id },
										null);
						if (curc != null) {
							if (curc.getCount() > 0) {
								if (curc.moveToFirst()) {
									/* do{ */
									String phone = curc
											.getString(curc
													.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
									ContactBean cbean1 = new ContactBean();
									cbean1.setContact_name(name);
									cbean1.setContact_number(phone);
									StaticData.contact_list.add(cbean1);
								}

							}/* while(curc.moveToNext()); */

						}
						// int phone=
						// cur.getInt(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						Log.v("Name", name);
						// pcu.setName(capital_name);
						// pcu.setPhone(phone);
						// cadapter.add(pcu);

					}
				} while (cur.moveToNext());

			}
		}
		// Collections.sort(cadapter);
		/* return cadapter; */
	}

	OnClickListener smsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			try {
				final Dialog dialog1 = new Dialog(getActivity(),
						android.R.style.Theme_Light);
				dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog1.setContentView(R.layout.activity_contactlist);
				ListView contact_list = (ListView) dialog1
						.findViewById(R.id.contact_list);
				// Button sending_message=(Button)
				// dialog1.findViewById(R.id.sending_message);
				readContacts();
				contact_list.setAdapter(new FullContactListAdapter(getActivity()));
				/*
				 * sending_message.setOnClickListener(new OnClickListener() {
				 * 
				 * @Override public void onClick(View v) { // TODO Auto-generated
				 * method stub dialog1.dismiss(); } });
				 */
				contact_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							final int arg2, long arg3) {
						// TODO Auto-generated method stub
						dialog1.dismiss();
						final Dialog dialog2 = new Dialog(getActivity());
						dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog2.setContentView(R.layout.activity_share_sms);
						Button yes = (Button) dialog2.findViewById(R.id.yes);
						Button no = (Button) dialog2.findViewById(R.id.no);
						yes.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog2.dismiss();
								SmsManager sm = SmsManager.getDefault();
								String text = "Download the dealsnprice app & signup using my referral code "
										+ usercode
										+ " & earn upto Rs. 2500 monthly!"
										+ WebService.PLAY_STORE_URL;
								sm.sendTextMessage(StaticData.contact_list
										.get(arg2).getContact_number(), null, text,
										null, null);
							}
						});
						no.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog2.dismiss();
							}
						});
						dialog2.show();
					}
				});
				dialog1.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	OnClickListener whatsappListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			if (isAppInstalled("com.whatsapp")) {
				PackageManager pm = getActivity().getPackageManager();
				try {

					Intent waIntent = new Intent(Intent.ACTION_SEND);
					// waIntent.setType("text/plain");
					// waIntent.setType("*/*");

					String text = "Download the dealsnprice app & signup using my referral code "
							+ usercode
							+ " & get ready to earn upto Rs. 500 on monthly basis. Also get upto 25% cashback on all your online purchases. Download Now - "
							+ WebService.PLAY_STORE_URL;
					// String text =
					// "Would you like to earn more money?So, Please install this app and follow the instructions and link is "+WebService.APPLICATION_URL;

					PackageInfo info = pm.getPackageInfo("com.whatsapp",
							PackageManager.GET_META_DATA);
					// Check if package exists or not. If not then code
					// in catch block will be called
					waIntent.setPackage("com.whatsapp");
					waIntent.putExtra(Intent.EXTRA_TEXT, text);
					waIntent.setType("text/plain");

					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
					StrictMode.setThreadPolicy(policy);

					getActivity().startActivity(
							Intent.createChooser(waIntent, "Share image via:"));
					// cxt.startActivity(waIntent);

				} catch (NameNotFoundException e) {

				}
			} else {
				UtilMethod.showToast("Please first install WhatsApp",
						getActivity());
			}

		}
	};
	OnClickListener gmailListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			// emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailTo});
			// emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
			String text = "Hi There,"
					+

					"\nHere is some great value up for grab! Download the Dealsnprice app & sign up using my referral code "
					+ usercode
					+ " and get ready to earn cashback."
					+

					"\nYou can earn upto 25% on all your online purchases & upto Rs. 500 on monthly basis by utility downloading applications."
					+

					"Download the Dealsnprice App from "
					+ WebService.PLAY_STORE_URL +

					"\n Cheers," + "\n" + shpf.getString("user_name", "")
					+ "\n" + shpf.getString("user_email", "");
			;
			emailIntent.putExtra(Intent.EXTRA_TEXT, text);
			// / use below 2 commented lines if need to use BCC an CC feature in
			// email
			// emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
			// emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
			// use below 3 commented lines if need to send attachment
			// emailIntent .setType("image/jpeg");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT,
					"Deals N Price - Earn Cashback");
			// emailIntent .putExtra(Intent.EXTRA_STREAM,
			// Uri.parse("file://sdcard/captureimage.png"));

			// need this to prompts email client only
			emailIntent.setType("message/rfc822");

			startActivity(Intent.createChooser(emailIntent,
					"Select an Email Client:"));

		}
	};
	OnClickListener copyLinkListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ClipboardManager clipboard = (ClipboardManager) getActivity()
					.getSystemService(getActivity().CLIPBOARD_SERVICE);
			String text = "Download the dealsnprice app & signup using my referral code "
					+ usercode
					+ " & get ready to earn upto Rs. 500 on monthly basis. Also get upto 25% cashback on all your online purchases. Download Now - "
					+ WebService.PLAY_STORE_URL;
			ClipData clip = ClipData.newPlainText("label", text);
			clipboard.setPrimaryClip(clip);
			Toast.makeText(getActivity(), "Copied Text", Toast.LENGTH_SHORT)
			.show();
		}
	};

	public class ReferEarnListener {
		public void onSuccess() {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}

			if (!UtilMethod.isStringNullOrBlank(getActivity()
					.getSharedPreferences("User_login", 1).getString(
							"user_code", null))) {
				refer_link.setText(getActivity().getSharedPreferences(
						"User_login", 1).getString("user_code", null));
			} else {

			}

			copy_link.setOnClickListener(copyLinkListener);
			other_layout.setOnClickListener(otherShareListener);
			sms_layout.setOnClickListener(smsListener);
			whatsapp_layout.setOnClickListener(whatsappListener);
			gmail_layout.setOnClickListener(gmailListener);

		}

		public void onError() {

		}
	}

	private boolean isAppInstalled(String packageName) {
		PackageManager pm = getActivity().getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

}
