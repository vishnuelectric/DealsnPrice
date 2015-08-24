package com.dealnprice.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.dnp.bean.NavDrawerItem;
import com.dnp.data.APP_Constants;
import com.dnp.data.DBHELPER;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.fragment.ContactUsFragment;
import com.dnp.fragment.DNPMyAccountFragment;
import com.dnp.fragment.OfferFragment;
import com.dnp.recharge.RechargeFragment;
import com.dnp.fragment.ReferEarnFragment;
import com.dnp.fragment.RetryFragment;
import com.dnp.fragment.ShopEarnFragment;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends ActionBarActivity implements RetryFragment.OnFragmentInteractionListener{
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
    public static TextView user_name, user_email;
    NavDrawerListAdapter adapter;
    static Fragment f;
    public static ActionBar abar;
    ArrayList<NavDrawerItem> navDrawerItems;
    static FragmentManager fmanager;
    static FragmentTransaction ft;
    Toolbar toolbar;
    Dialog dialog, dialog1;
    static TextView amount;
    private GoogleApiClient mGoogleApiClient;
    private AnimationDrawable loadingViewAnim;
    private int selected_tab_bg = 0;
    private int unselected_tab_bg = 0;
    private Resources mResources = null;
    public static DashboardActivity actRef = null;
    private NotificationManager notifMgr = null;
    public DBHELPER sqLiteHelper    =   null;

    GoogleApiClient.ConnectionCallbacks googleOnConnectionCallBack = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnectionSuspended(int connectionSuspendedReason) {


        }

        @Override
        public void onConnected(Bundle arg0) {


        }
    };

    GoogleApiClient.OnConnectionFailedListener googleOnConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Intent intent=new Intent(this,UptoService.class);
        startService(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mResources = getResources();
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(googleOnConnectionCallBack)
                .addOnConnectionFailedListener(googleOnConnectionFailedListener)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .build();
        actRef = this;
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
        String message = null;

        for (int i=0; i<rs.size(); i++) {
            ActivityManager.RunningServiceInfo
                    rsi = rs.get(i);
            Log.i("Service", "Process " + rsi.process + " with component " + rsi.service.getClassName());
            message =message+rsi.process ;
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        selected_tab_bg = mResources.getColor(R.color.active_stripe_tab_bg);
        unselected_tab_bg = mResources.getColor(R.color.tab_header_bg);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayout v = (LinearLayout)findViewById(R.id.earn_layout);
        android.app.ActionBar.LayoutParams params = new android.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //toolbar.addView(v,params);

        drawer_layout = (RelativeLayout) findViewById(R.id.left_drawer_layout);
        navMenuTitles = getResources().getStringArray(R.array.list_drawer_nav);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        fmanager = getSupportFragmentManager();
        navDrawerItems = new ArrayList<NavDrawerItem>();
        setSupportActionBar(toolbar);
        abar = getSupportActionBar();

        fmanager = getSupportFragmentManager();
        ft = fmanager.beginTransaction();
        shpf = getSharedPreferences(Constant.pref_name, 1);
        cxt = this;
        v.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                fmanager.beginTransaction().replace(R.id.container,new DNPMyAccountFragment()).commit();
            }
        });
        sqLiteHelper=  new DBHELPER(this);
        sqLiteHelper.getWritableDatabase();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(2, -1)));
        //Uncomment code below to show show shopping services:Price Comparison and Deals & Coupons

		/*navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(4, -1)));*/

       // navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(0, -1)));
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(5, -1)));
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(6, -1)));
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
               //active_stripe_tab_bg, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
System.out.println("drawer closed");
                // calling onPrepareOptionsMenu() to show action bar icons
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                System.out.println("drawer opened");
                //getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                //invalidateOptionsMenu();
            }
        };
        //getSupportActionBar().setIcon(R.drawable.menu_btn);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        abar.setBackgroundDrawable(cxt.getResources().getDrawable(R.color.header_bg_color));
        abar.setDisplayShowCustomEnabled(false);
        abar.setDisplayShowHomeEnabled(true);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        abar.setDisplayUseLogoEnabled(true);
       abar.setLogo(R.drawable.logo);
        //First Launch
        if (savedInstanceState == null) {
            displayView(1);

        }



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public void onFragmentInteraction() {
        fmanager.beginTransaction().replace(R.id.container,new OfferFragment()).commit();

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

    public void displayView(int position) {
        switch (position) {
            case 1:
                f = new OfferFragment();
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
                break;
            case 2:
                f = new ShopEarnFragment();
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 3:
                f = new ReferEarnFragment();
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.addToBackStack(null);
                ft.commit();
                break;
		/*case 5:
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
			break;*/
            case 5:
                f = new RechargeFragment();
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 6:
			/*f=new BankTransferFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();*/
                f = new DNPMyAccountFragment();
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 7:
                f = new DNPMyAccountFragment();
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 8:
                startActivity(new Intent(this, TakeATourActivity.class));
                break;
            case 9:
			/*f=new TakeATourFragment();
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();*/
                f = new ContactUsFragment();
                Bundle b = new Bundle();
                b.putString("purpose", "main");
                f.setArguments(b);
                ft = fmanager.beginTransaction();
                ft.replace(R.id.container, f);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 10:
		/*	f=new ContactUsFragment();
			Bundle b=new Bundle();
			b.putString("purpose","main");
			f.setArguments(b);
			ft=fmanager.beginTransaction();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();
			break;*/
                new GetAppUrlTask(DashboardActivity.this, new DashboardRateListener()).execute();
                break;

            case 11:
                final Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_logout);
                Button ok = (Button) dialog.findViewById(R.id.ok);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
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
            case 12:
			/*final Dialog dialog=new Dialog(DashboardActivity.this);
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
*/
            default:
                break;
        }
        mDrawerLayout.closeDrawer(drawer_layout);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        int size = getSupportFragmentManager().getBackStackEntryCount();
        System.out.println("fragment back stack "+ size);
        if (size == 0) {
            mDrawerLayout.closeDrawer(drawer_layout);
            //finish();
			/*setDisableTitle();*/
			/*abar.setDisplayHomeAsUpEnabled(false);
			abar.setDisplayShowCustomEnabled(true);
			abar.setDisplayShowHomeEnabled(false);
			abar.setDisplayUseLogoEnabled(false);
			onCustomActionView();*/
			/*displayView(1);*/
        } else {

			/*finish();*/
            displayView(1);

                getSupportFragmentManager().popBackStack();



        }

    }

    public class DashboardRateListener {
        public void onSuccess() {
            if (dialog != null && dialog.isShowing()) {
                //loadingViewAnim.stop();
                dialog.dismiss();
            }
            //--
            //Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(StaticData.our_app_list.get(0).getApp_url()));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_Constants.DNP_APP_URL));
            startActivity(intent);
        }

        public void onError(String msg) {
            if (dialog != null && dialog.isShowing()) {
                //loadingViewAnim.stop();
                dialog.dismiss();
            }
            if (msg.equals("slow")) {
                UtilMethod.showServerError(cxt);
            }
        }
    }

    private void setProgressDialog() {
        dialog1 = new Dialog(DashboardActivity.this);
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

    public static void setImage() {
        SharedPreferences sh = cxt.getSharedPreferences("User_login", 1);
        user_name.setText(sh.getString("user_name", null));
        user_email.setText(sh.getString("user_email", null));
    }

    public static void setAmount(String amount1) {
        amount.setText(amount1);
    }

    public static void onCustomActionView(String Amount) {

		/*setDisableTitle();*/
		/*actionBar.setDisplayHomeAsUpEnabled(true);*/

        LayoutInflater inflater = LayoutInflater.from(cxt);
        View customview = inflater.inflate(R.layout.activity_header_view, null);

        ImageView title = (ImageView) customview.findViewById(R.id.title);
        final ImageView menu_btn = (ImageView) customview.findViewById(R.id.menu);
        TextView amount = (TextView) customview.findViewById(R.id.amount_text);
        ImageView add_btn = (ImageView) customview.findViewById(R.id.add_btn);
        ImageView refresh = (ImageView) customview.findViewById(R.id.refresh);
        ImageView searchImageView = (ImageView) customview.findViewById(R.id.search);
        //LinearLayout earn_layout = (LinearLayout) customview.findViewById(R.id.earn_layout);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cxt);
        amount.setText(Amount);

        setDisableTitle();
        //abar.setCustomView(customview);

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
                } else {
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


    public static void onCustomView(int position, String purpose1) {

		/*setDisableTitle();*/
		/*actionBar.setDisplayHomeAsUpEnabled(true);*/

        LayoutInflater inflater = LayoutInflater.from(cxt);
        View customview = inflater.inflate(R.layout.activity_header_view, null);

        ImageView title = (ImageView) customview.findViewById(R.id.title);
        ImageView menu_btn = (ImageView) customview.findViewById(R.id.menu);
        ImageView add_btn = (ImageView) customview.findViewById(R.id.add_btn);
        ImageView refresh = (ImageView) customview.findViewById(R.id.refresh);
        ImageView searchImageView = (ImageView) customview.findViewById(R.id.search);
        LinearLayout earn_layout = (LinearLayout) customview.findViewById(R.id.earn_layout);
        LinearLayout code_layout = (LinearLayout) customview.findViewById(R.id.code_layout);
        code_layout.setVisibility(View.VISIBLE);
        TextView code = (TextView) customview.findViewById(R.id.code);
        if (purpose1.equals("hot")) {
            code.setText(StaticData.hot_coupon_product_list.get(position).getStore_code());
        } else if (purpose1.equals("viewed")) {
            code.setText(StaticData.coupon_product_list.get(position).getStore_code());
        }
        Button copy = (Button) customview.findViewById(R.id.copy);
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

                } else {
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

    OnClickListener menuListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mDrawerLayout.isDrawerOpen(drawer_layout)) {
                mDrawerLayout.closeDrawer(drawer_layout);
            } else {
                mDrawerLayout.openDrawer(drawer_layout);
            }
        }
    };

    public static void setDisableTitle() {
        abar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setImage();
        onCustomActionView("0");
       if(UptoService.uptoService != null ){
     UptoService.uptoService.processAmount(APP_Constants.PENDING_AMOUNT_DETAILS,this);
       }
        //onCustomActionView();
    }

    OnClickListener addbtnListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
			/*f=new PostFeedFragment();
			ft.replace(R.id.container, f);
			ft.addToBackStack(null);
			ft.commit();*/
        }
    };

    public class DashBoardListener {
        public void onSuccess() {
            if (dialog1 != null && dialog1.isShowing()) {
                dialog1.dismiss();
            }
			/*if(getSharedPreferences("User_login",1).getString("type",null).equals("gplus")){*/
            if (LoginActivity.mGoogleApiClient != null) {
                LoginActivity.flag1 = 1;
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
           /* if (Session.getActiveSession() != null) {
                Session.getActiveSession().closeAndClearTokenInformation();
                Session.getActiveSession().close();
                Session.setActiveSession(null);
            }
            if (Session.getActiveSession() != null) {
                Session session = Session.getActiveSession();
                session.closeAndClearTokenInformation();
            }*/
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logOut();
            if(mGoogleApiClient.isConnected())
            {
                mGoogleApiClient.disconnect();
            }
            SharedPreferences shpf = cxt.getSharedPreferences(Constant.pref_name, Context.MODE_PRIVATE);
            Editor edt = shpf.edit();
            edt.clear();
            edt.commit();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        public void onError(String msg) {
            if (dialog1 != null && dialog1.isShowing()) {
                dialog1.dismiss();
            }
            if (msg.equals("slow")) {
                UtilMethod.showServerError(DashboardActivity.this);
            } else {
                final AlertDialog adialog = new AlertDialog.Builder(DashboardActivity.this).create();
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
     *
     * @param lin
     * @param index
     */
    public void selectTab(LinearLayout[] lin, int index) {
        if (lin == null) return;
        int length = lin.length;
        for (int i = 0; i < length; i++) {
            if (i == index)
                lin[index].setBackgroundColor(selected_tab_bg);
            else
                lin[i].setBackgroundColor(unselected_tab_bg);
        }
    }

    /**
     * This method handles Push Notifications
     */
    public void showNotification(int notificationId, String appName) {
        String message = "";
        String title = " ";
        if (notificationId == APP_Constants.INTSALL_NOTIF_ID) {
            message = APP_Constants.INSTALL_MESSAGE + appName;
            title = APP_Constants.INSTALL_TITLE + appName;
        } else if (notificationId == APP_Constants.OPEN_NOTIF_ID) {
            message = APP_Constants.TRY_MESSAGE+ appName;
            title = APP_Constants.TRY_TITLE + appName;
        } else if (notificationId == APP_Constants.SUCCESS_NOTIF_ID) {
            message = APP_Constants.SUCCESS_MESSAGE;
            title = APP_Constants.SUCCESS_TITLE;
        } else if (notificationId == APP_Constants.MONEY_NOTIF_ID) {
            message = APP_Constants.REWARD_MESSAGE;
            title = APP_Constants.REWARD_TITLE;
        }
        else if (notificationId == APP_Constants.DAYS_NOTIF_ID) {
            message = APP_Constants.DATA_MESSAGE;
            title = APP_Constants.DATA_TITLE;
        }
        else if (notificationId == APP_Constants.DATA_NOTIF_ID) {
            message = APP_Constants.DATA_MESSAGE;
            title = APP_Constants.DATA_TITLE;
        }
        if (!message.isEmpty() && !title.isEmpty()) {
            if (actRef != null) {
                notifMgr = (NotificationManager) actRef.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder notify = new NotificationCompat.Builder(actRef);
                notify.setSmallIcon(R.drawable.app_icon);
                notify.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.app_icon));
                Intent resultIntent = new Intent(actRef, DashboardActivity.class);
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                actRef,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                notify.setContentIntent(resultPendingIntent);
                cancelAllNotifications();
                notify.setTicker(title);
                notify.setContentTitle(title);
                notify.setContentText(message);
                notifMgr.notify(notificationId, notify.build());
                addNotificationToDB(title, message);
            }
        }
    }

    /**
     * This method cancels all Notification Messages
     */
    public void cancelAllNotifications() {
        if (notifMgr != null) {
            notifMgr.cancel(APP_Constants.INTSALL_NOTIF_ID);
            notifMgr.cancel(APP_Constants.OPEN_NOTIF_ID);
            notifMgr.cancel(APP_Constants.SUCCESS_NOTIF_ID);
        }
    }

    /**
     * This method adds entry in DB and in Static List of Notifications
     *
     * @param title
     * @param message
     */

    public void addNotificationToDB(String title, String message) {
        if(title.isEmpty()||title==null)return;
        if(message.isEmpty()||title==null)return;
        String date =   UtilMethod.getCurrentDate();
        if(sqLiteHelper==null)
            sqLiteHelper    =   new DBHELPER(this);
        sqLiteHelper.insertNotification(title,message,APP_Constants.USERNAME,date);
    }

    public DashboardActivity getInstance() {
        if (actRef != null)
            return actRef;
        actRef = this;
        return actRef;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actRef = null;
    }

}
