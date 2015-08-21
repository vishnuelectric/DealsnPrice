package com.dnp.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.ShopListAdapter;
import com.dnp.asynctask.GetShopListTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.APP_Constants;
import com.dnp.data.Downloader;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class ShopEarnFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,referearn_text;
	Fragment fragment;
	FragmentManager fm;
	SwipeRefreshLayout swipe;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	String user_id;
	SharedPreferences shpf;
	ListView shop_list;
	AutoCompleteTextView shop_search;
	Dialog dialog;
	//UiLifecycleHelper uiHelper;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	/*	uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_shopearn, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		shop_search=(AutoCompleteTextView) view.findViewById(R.id.shop_search);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		inviteearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		shop_list=(ListView) view.findViewById(R.id.offer_list);
		swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
		swipe.setOnRefreshListener(this);
		//swipe.setProgressBackgroundColor(android.R.color.darker_gray);

		//swipe.setProgressViewOffset(false,150,150);

		swipe.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_blue_bright,android.R.color.holo_green_dark);
		/*shopearn_text.setText("Shop & Earn");
		inviteearn_text.setText("Price Comparison");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");*/
		shop_search.setHintTextColor(getResources().getColor(R.color.search_text_color));
		//DashboardActivity.onCustomActionView();
		//--
		LinearLayout lin[]	=	new LinearLayout[]{offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout};
		DashboardActivity.actRef.selectTab(lin, APP_Constants.SHOP_EARN);
		//--
		
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
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		v1.setLayoutParams(param);
		footer_layout.addView(v1);

		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		fm=getFragmentManager();
		ft=fm.beginTransaction();
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		pricecomparison_layout.setOnClickListener(inviteEarnListener);
		referearn_layout.setOnClickListener(couponListener);
		dealcoupon_layout.setOnClickListener(dealpriceListener);
		shpf=getActivity().getSharedPreferences("User_login", 1);
		user_id=shpf.getString("user_id", null);
		try{
			if(StaticData.shop_offer_list.size() == 0 || StaticData.shop_offer_search.size() == 0 || StaticData.shop_search.size() ==0) {
				setProgressDialog();
				MultipartEntity multipart = new MultipartEntity();
				multipart.addPart("user_id", new StringBody(user_id));
				new GetShopListTask(getActivity(), multipart, new ShopEarnListener()).execute();
			}
			else
			{
				new ShopEarnListener().onSuccess();
			}
		}
		catch(Exception e){
e.printStackTrace();
		}
		return view;
	}

	@Override
	public void onRefresh() {
		swipe.setRefreshing(true);
		MultipartEntity multipart = new MultipartEntity();
		try {
			multipart.addPart("user_id", new StringBody(user_id));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		new GetShopListTask(getActivity(), multipart, new ShopEarnListener()).execute();
	}
	/*private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};*/

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
		dialog.setCancelable(true);
		dialog.show();
	}
	/*private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {


		if (state.isOpened()) {
			Request.newMeRequest(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user,
						Response response) {
					if(user!=null){

					}

					// callback after Graph API response with user
					// object

				}
			}).executeAsync();
		} else if (state.isClosed()) {

		}
	}*/
	/*	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", StaticData.application_list.get(position).getApp_name());
	//	params.putString("caption", "Build great social apps and get more installs.");
	//	params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		params.putString("link", "https://developers.facebook.com/android");
	//	params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(getActivity(), Session.getActiveSession(), params)).setOnCompleteListener(
		new OnCompleteListener() {


		@Override
		public void onComplete(Bundle values, FacebookException error) {
			// TODO Auto-generated method stub
			if (error == null) {
				// When the story is posted, echo the success
				// and the post Id.
				final String postId = values.getString("post_id");
				if (postId != null) { 
				Toast.makeText(getActivity(), "Posted story, id: " + postId, Toast.LENGTH_SHORT).show();
				} else {
				// User clicked the Cancel button
				Toast.makeText(getActivity().getApplicationContext(), "Publish cancelled", Toast.LENGTH_SHORT).show();
				}
				} else if (error instanceof FacebookOperationCanceledException) {
				// User clicked the "x" button
				Toast.makeText(getActivity().getApplicationContext(), "Publish cancelled", Toast.LENGTH_SHORT).show();
				} else {
				// Generic, ex: network error
				Toast.makeText(getActivity().getApplicationContext(), "Error posting story", Toast.LENGTH_SHORT).show();
				}
		}

		}).build();
		feedDialog.show();
		}*/
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
			fragment=new NotificationFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener shopEarnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
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
	public class ShopEarnListener{
		public void onSuccess(){
			swipe.setRefreshing(false);
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			final ShopListAdapter sladapter=new ShopListAdapter(getActivity(), new ShopEarnListener());
			shop_search.setThreshold(1);
			shop_list.setAdapter(sladapter);
			shop_search.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					StaticData.shop_offer_list.clear();
					//	UtilMethod.showToast("Text on Changed",getActivity());
					for(int i=0;i<StaticData.shop_offer_search.size();i++){
						if(StaticData.shop_offer_search.get(i).getShop_name().toLowerCase().startsWith(shop_search.getText().toString().toLowerCase())){
							StaticData.shop_offer_list.add(StaticData.shop_offer_search.get(i));
						}
					}
					ShopListAdapter sladapter=new ShopListAdapter(getActivity(), new ShopEarnListener());
					shop_search.setThreshold(1);
					shop_list.setAdapter(sladapter);
					sladapter.notifyDataSetChanged();

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					StaticData.shop_offer_list.clear();
					//	UtilMethod.showToast("After Text Changed",getActivity());
					for(int i=0;i<StaticData.shop_offer_search.size();i++){
						if(StaticData.shop_offer_search.get(i).getShop_name().toLowerCase().startsWith(shop_search.getText().toString().toLowerCase())){
							StaticData.shop_offer_list.add(StaticData.shop_offer_search.get(i));
						}
					}
					ShopListAdapter sladapter=new ShopListAdapter(getActivity(), new ShopEarnListener());
					shop_search.setThreshold(1);
					shop_list.setAdapter(sladapter);
					sladapter.notifyDataSetChanged();
				}
			});
			shop_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment f=new DNPShopDetailFragment();
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					Bundle b=new Bundle();
					b.putString("shop_offer_image", StaticData.shop_offer_list.get(arg2).getShop_image());
					b.putString("detaillink",StaticData.shop_offer_list.get(arg2).getShop_detaillink());
					b.putString("shop_url",StaticData.shop_offer_list.get(arg2).getShop_url());
					b.putString("cashback", StaticData.shop_offer_list.get(arg2).getShop_offer_name());
					b.putString("shop_name", StaticData.shop_offer_list.get(arg2).getShop_name());
					Log.e("SHOP "," SET NAME AS "+ StaticData.shop_offer_list.get(arg2).getShop_name());
					f.setArguments(b);
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, f);
					ft.addToBackStack(null);
					ft.commit();
				}
			});

		}
		public void onError(){
			swipe.setRefreshing(false);
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
		}
		public void onVisit(int position){
			Editor edt=shpf.edit();
			/*shpf=getActivity().getSharedPreferences("User_name", 1);
			edt=shpf.edit();
			edt.putInt("position",position);
			edt.commit();*/
			Fragment f=new DNPShopDetailFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			Bundle b=new Bundle();
			b.putString("shop_offer_image",StaticData.shop_offer_list.get(position).getShop_image());
			b.putString("detaillink",StaticData.shop_offer_list.get(position).getShop_detaillink());
			b.putString("shop_url",StaticData.shop_offer_list.get(position).getShop_url());
			b.putString("cashback",StaticData.shop_offer_list.get(position).getShop_offer_name());
			b.putString("shop_name", StaticData.shop_offer_list.get(position).getShop_name());
			Log.e("SHOP "," SET NAME AS "+ StaticData.shop_offer_list.get(position).getShop_name());
			f.setArguments(b);
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
		public void onUrl(int position){
			Fragment f=new OpenShopUrlFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			Bundle b1=new Bundle();
			b1.putString("shop_url",StaticData.shop_offer_list.get(position).getShop_url());
			f.setArguments(b1);
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
		}
		public void onShareFacebook(int position){
			if(isAppInstalled("com.facebook.katana")){

				ShareDialog shareDialog = new ShareDialog(getActivity());

				ShareLinkContent content = new ShareLinkContent.Builder()
				.setContentTitle("E-StateBook")
				.setContentDescription(
				"E-StateBook Buy & Rent Mobile Application. E-State is Mobile Application")
				.setContentUrl(Uri.parse(StaticData.shop_offer_list.get(position).getShop_image()))
				.build();

				shareDialog.show(getActivity(), content);


				String extStorageDirectory = Environment.getExternalStorageDirectory()
						.toString();
				File folder = new File(extStorageDirectory, "dnp_images");
				folder.mkdir();
				File file = new File(folder, "sharing_image.png");
				//File file1=new File(Company_constants.str_candidate_resume);
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//Log.v("File Path",Company_constants.str_candidate_resume);
				/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    			        StrictMode.setThreadPolicy(policy);*/ 
				Downloader.DownloadFile(StaticData.shop_offer_list.get(position).getShop_image(), file);
			//	FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
			//	.setLink("http://dealsnprice.com/").setPicture(StaticData.shop_offer_list.get(position).getShop_image()).setCaption(StaticData.shop_offer_list.get(position).getShop_offer_name()).setDescription(StaticData.shop_offer_list.get(position).getShop_url()).setFragment(getParentFragment()).build();
			//	uiHelper.trackPendingDialogCall(shareDialog.present());
			}
			else{
				UtilMethod.showToast("Please install Facebook App", getActivity());
			}
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
