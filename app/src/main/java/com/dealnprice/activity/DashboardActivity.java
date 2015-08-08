package com.dealnprice.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dnp.adapter.NavDrawerListAdapter;
import com.dnp.asynctask.GetAppUrlTask;
import com.dnp.asynctask.LogoutTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.NavDrawerItem;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.fragment.ContactUsFragment;
import com.dnp.fragment.DNPDealCouponFragment;
import com.dnp.fragment.DNPMyAccountFragment;
import com.dnp.fragment.OfferFragment;
import com.dnp.fragment.PriceComparisonFragment;
import com.dnp.fragment.RechargeFragment;
import com.dnp.fragment.ReferEarnFragment;
import com.dnp.fragment.ShopEarnFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;


public class DashboardActivity extends ActionBarActivity{
	private static DrawerLayout mDrawerLayout;
	
	private static ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	public static Context cxt;
	SharedPreferences shpf;
	private static RelativeLayout drawer_layout;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public static TextView user_name,user_email;
	NavDrawerListAdapter adapter;
	static Fragment f;
	public static ActionBar abar;
	ArrayList<NavDrawerItem> navDrawerItems;
	static FragmentManager fmanager;
	static FragmentTransaction ft;
	Dialog dialog,dialog1;
	static TextView amount;
	ImageView loading_image;
	private GoogleApiClient mGoogleApiClient;
	private AnimationDrawable loadingViewAnim;
	private int selected_tab_bg	=	0;
	private int unselected_tab_bg	=	0;
	private Resources mResources	=	null;
	public static DashboardActivity actRef	=	null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		mResources	= getResources();
		actRef		=	this;
		selected_tab_bg	=	mResources.getColor(R.color.active_stripe_tab_bg);
		unselected_tab_bg	=	mResources.getColor(R.color.tab_header_bg);
		getSupportActionBar().setDisplayShowCustomEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(new ColorDrawable(mResources.getColor(android.R.color.transparent)));
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		user_name=(TextView) findViewById(R.id.user_name);
		user_email=(TextView) findViewById(R.id.user_email);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		drawer_layout=(RelativeLayout) findViewById(R.id.left_drawer_layout);
		navMenuTitles=getResources().getStringArray(R.array.list_drawer_nav);
		navMenuIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);
		fmanager=getSupportFragmentManager();
		navDrawerItems = new ArrayList<NavDrawerItem>();
		abar=getSupportActionBar();
		fmanager=getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		shpf=getSharedPreferences(Constant.pref_name, 1);
		cxt=this;
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[14], navMenuIcons.getResourceId(0, -1)));

		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setDivider(new ColorDrawable(cxt.getResources().getColor(R.color.side_line_color)));
		mDrawerList.setDividerHeight(4);
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.color.header_bg_color,//active_stripe_tab_bg, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);

				// calling onPrepareOptionsMenu() to show action bar icons
				//invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				//getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				//invalidateOptionsMenu();
			}
		};
		//getSupportActionBar().setIcon(R.drawable.menu_btn);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		//First Launch
		if (savedInstanceState == null) {
			displayView(1);

		}

		Pending_amount pp= new Pending_amount(DashboardActivity.this);
		pp.execute();


	}
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	public void displayView(int position){


		switch(position){
		case 1: 
			f=new OfferFragment();	
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.commit();
			break;
		case 2:
			f=new ShopEarnFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 3:
			f=new ReferEarnFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 5:
			f=new PriceComparisonFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 6:
			f=new DNPDealCouponFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 8:
			f=new RechargeFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 9:
			/*f=new BankTransferFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();*/
			f=new DNPMyAccountFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 10:
			f=new DNPMyAccountFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 11:
			/*f=new TakeATourFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();*/
			startActivity(new Intent(this,TakeATourActivity.class));
			break;
		case 12:
			f=new ContactUsFragment();
			Bundle b=new Bundle();
			b.putString("purpose","main");
			f.setArguments(b);
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;
		case 13:
			new GetAppUrlTask(DashboardActivity.this, new DashboardRateListener()).execute();	

			break;
		case 14:
			final Dialog dialog=new Dialog(DashboardActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_logout);
			Button ok=(Button) dialog.findViewById(R.id.ok);
			Button cancel=(Button) dialog.findViewById(R.id.cancel);
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					setProgressDialog();
					new LogoutTask(DashboardActivity.this, new DashBoardListener()).execute();
				}
			});
			dialog.setCancelable(false);
			dialog.show();

			break;
		default: 
			break;
		}
		mDrawerLayout.closeDrawer(drawer_layout);
	}




	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		int size=getSupportFragmentManager().getBackStackEntryCount();
		if(size>0){
			mDrawerLayout.closeDrawer(drawer_layout);
			/*setDisableTitle();*/
			/*abar.setDisplayHomeAsUpEnabled(false);
			abar.setDisplayShowCustomEnabled(true);
			abar.setDisplayShowHomeEnabled(false);
			abar.setDisplayUseLogoEnabled(false);
			onCustomActionView();*/
			/*displayView(1);*/
		}
		else{

			/*finish();*/
			displayView(1);

		}

	}

	public class DashboardRateListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(StaticData.our_app_list.get(0).getApp_url()));
			startActivity(intent);
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(cxt);
			}
		}
	}

	private void setProgressDialog(){
		dialog1=new Dialog(DashboardActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.activity_loading_progressbar);
		dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		/*@SuppressWarnings("unused")
		LinearLayout loadinglayout=(LinearLayout) dialog1.findViewById(R.id.LinearLayout1);
		loading_image=(ImageView) dialog1.findViewById(R.id.imageView111);
		loading_image.setBackgroundResource(R.anim.loading_animation);
		loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
		loadingViewAnim.start();*/
		dialog1.show();
	}

	public static void setImage(){
		SharedPreferences sh=cxt.getSharedPreferences("User_login", 1);
		user_name.setText(sh.getString("user_name", null));
		user_email.setText(sh.getString("user_email", null));
	}
	public static void setAmount(String amount1){
		amount.setText(amount1);
	}

	public static void onCustomActionView(String Amount){

		/*setDisableTitle();*/
		/*actionBar.setDisplayHomeAsUpEnabled(true);*/

		LayoutInflater inflater=LayoutInflater.from(cxt);
		View customview=inflater.inflate(R.layout.activity_header_view,null);

		ImageView title=(ImageView) customview.findViewById(R.id.title);
		final ImageView menu_btn=(ImageView) customview.findViewById(R.id.menu);
		TextView amount=(TextView) customview.findViewById(R.id.amount_text);
		ImageView add_btn=(ImageView) customview.findViewById(R.id.add_btn);
		ImageView refresh=(ImageView) customview.findViewById(R.id.refresh);
		ImageView searchImageView=(ImageView) customview.findViewById(R.id.search);
		LinearLayout earn_layout=(LinearLayout)customview.findViewById(R.id.earn_layout);
		abar.setBackgroundDrawable(cxt.getResources().getDrawable(R.color.header_bg_color));

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cxt);
		amount.setText(Amount);
		earn_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				f=new DNPMyAccountFragment();
				ft=fmanager.beginTransaction();
				ft.replace(R.id.container, f);
				ft.addToBackStack(null);
				ft.commit();				
			}
		});
		setDisableTitle();
		abar.setCustomView(customview);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowHomeEnabled(false);
		abar.setDisplayHomeAsUpEnabled(false);
		abar.setDisplayUseLogoEnabled(false);
		/*refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayView(1);
			}
		});*/
		add_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*f=new PostFeedFragment();
				ft=fmanager.beginTransaction();
				ft.replace(R.id.container, f);
				ft.addToBackStack(null);
				ft.commit();*/
			}
		});

		menu_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mDrawerLayout.isDrawerOpen(drawer_layout)) {
					mDrawerLayout.closeDrawer(drawer_layout);
					menu_btn.setImageResource(R.drawable.list);
				}
				else{
					mDrawerLayout.openDrawer(drawer_layout);
					menu_btn.setImageResource(R.drawable.list_icon);
				}

			}
		});
		/*menu_btn.setOnClickListener(menuListener);*/
		/*ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP );*/

	}


	public static void onCustomView(int position,String purpose1){

		/*setDisableTitle();*/
		/*actionBar.setDisplayHomeAsUpEnabled(true);*/

		LayoutInflater inflater=LayoutInflater.from(cxt);
		View customview=inflater.inflate(R.layout.activity_header_view,null);

		ImageView title=(ImageView) customview.findViewById(R.id.title);
		ImageView menu_btn=(ImageView) customview.findViewById(R.id.menu);
		ImageView add_btn=(ImageView) customview.findViewById(R.id.add_btn);
		ImageView refresh=(ImageView) customview.findViewById(R.id.refresh);
		ImageView searchImageView=(ImageView) customview.findViewById(R.id.search);
		LinearLayout earn_layout=(LinearLayout) customview.findViewById(R.id.earn_layout);
		LinearLayout code_layout=(LinearLayout) customview.findViewById(R.id.code_layout);
		code_layout.setVisibility(View.VISIBLE);
		TextView code=(TextView) customview.findViewById(R.id.code);
		if(purpose1.equals("hot")){
			code.setText(StaticData.hot_coupon_product_list.get(position).getStore_code());
		}
		else if(purpose1.equals("viewed")){
			code.setText(StaticData.coupon_product_list.get(position).getStore_code());	
		}
		Button copy=(Button) customview.findViewById(R.id.copy);
		earn_layout.setVisibility(View.GONE);
		title.setVisibility(View.GONE);
		abar.setBackgroundDrawable(cxt.getResources().getDrawable(R.color.header_bg_color));
		/*title.setText("Dashboard");*/
		setDisableTitle();
		abar.setCustomView(customview);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowHomeEnabled(false);
		abar.setDisplayHomeAsUpEnabled(false);
		abar.setDisplayUseLogoEnabled(false);
		/*refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				displayView(1);
			}
		});*/
		copy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});


		add_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*f=new PostFeedFragment();
				ft=fmanager.beginTransaction();
				ft.replace(R.id.container, f);
				ft.addToBackStack(null);
				ft.commit();*/
			}
		});

		menu_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mDrawerLayout.isDrawerOpen(drawer_layout)) {
					mDrawerLayout.closeDrawer(drawer_layout);

				}
				else{
					mDrawerLayout.openDrawer(drawer_layout);
				}

			}
		});
		/*menu_btn.setOnClickListener(menuListener);*/
		/*ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP );*/

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//onCustomActionView();
	}
	OnClickListener menuListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mDrawerLayout.isDrawerOpen(drawer_layout)) {
				mDrawerLayout.closeDrawer(drawer_layout);
			}
			else{
				mDrawerLayout.openDrawer(drawer_layout);
			}
		}
	};
	public static void setDisableTitle(){
		abar.setDisplayShowTitleEnabled(false);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setImage();
		onCustomActionView("0");
		Pending_amount pp= new Pending_amount(DashboardActivity.this);
		pp.execute();
		//onCustomActionView();
	}
	OnClickListener addbtnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*f=new PostFeedFragment();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();*/
		}
	};

	public class DashBoardListener{
		public void onSuccess(){
			if(dialog1!=null && dialog1.isShowing()){
				dialog1.dismiss();
			}
			/*if(getSharedPreferences("User_login",1).getString("type",null).equals("gplus")){*/
			if(LoginActivity.mGoogleApiClient!=null){
				LoginActivity.flag1=1;
				if (LoginActivity.mGoogleApiClient.isConnected()) {
					UtilMethod.showToast("Disconnected Value", cxt);
					Plus.AccountApi.clearDefaultAccount(LoginActivity.mGoogleApiClient);
					//      LoginOptionFragment.mGoogleApiClient.connect();
					LoginActivity.mGoogleApiClient.disconnect();
					LoginActivity.mGoogleApiClient.connect();
					// LoginActivity.mGoogleApiClient.connect(); 
				}
				/*}*/

			}
			/*if(Session.getActiveSession()!=null){
				Session.getActiveSession().closeAndClearTokenInformation();
				Session.getActiveSession().close();
				Session.setActiveSession(null);
			}
			if(Session.getActiveSession()!=null){
				Session session = Session.getActiveSession();
				session.closeAndClearTokenInformation();
			}*/

			SharedPreferences shpf=cxt.getSharedPreferences(Constant.pref_name,Context.MODE_PRIVATE);
			Editor edt=shpf.edit();
			edt.clear();
			edt.commit();
			Intent intent=new Intent(DashboardActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
		public void onError(String msg){
			if(dialog1!=null && dialog1.isShowing()){
				dialog1.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(DashboardActivity.this);
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(DashboardActivity.this).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
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
	/**
	 * This method called from various fragments when a Tab is Selected changes the respective color of TAB
	 * Selected TAB Color: active_stripe_tab_bg
	 * UnSelected TAB Color: tab_header_bg

	 * @param index
	 */
	public void selectTab(LinearLayout[] lin , int index)
	{
		if(lin==null)return;
		int length	=	lin.length;
		for(int i=0;i<length;i++)
		{
			if(i   ==	index)
				lin[index].setBackgroundColor(selected_tab_bg);
			else
				lin[i].setBackgroundColor(unselected_tab_bg);
		}
	}

	public DashboardActivity getInstance()
	{
		if(actRef!=null)
			return actRef;
		actRef	=	this;
		return actRef;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		actRef	=	null;
	}
}
