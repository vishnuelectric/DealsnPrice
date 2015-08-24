package com.dnp.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.androidquery.AQuery;
import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.OfferStepAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.ApplicationBean;
import com.dnp.data.APP_Constants;
import com.dnp.data.DBHELPER;
import com.dnp.data.Downloader;
import com.dnp.data.Helper;
import com.dnp.data.Receiver;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class OfferDetailFragment extends Fragment{

	//Class Level Constants
	public static final String TAG		=	OfferDetailFragment.class.getSimpleName();
	public static OfferDetailFragment fragRef	=	null;
	public static String targetApp		=	"";
	private Timer	appDetector_timer	=	null;
	public DBHELPER dbhelper = null;
	public SQLiteDatabase sqLiteDatabase = null;


	//Class Level Variables
	private TextView progressOneTV		=	null;
	private TextView progressTwoTV		=	null;
	private TextView progressThreeTV	=	null;
	private LinearLayout progressOneLL		=	null;
	private LinearLayout progressTwoLL		=	null;
	private Resources mResources		=	null;
	public Activity actRef				=	null;
	private LinearLayout plsWaitLL		=	null;
	private LinearLayout instructionBoxLL	=	null;
	private   NotificationManager notifMgr=	null;
	ArrayList<ApplicationBean> offer_list;


	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	LinearLayout footer_layout;
	TextView app_name,app_description,app_install_detail,open_app_rate,seven_app_rate,instruction_header,instruction_step;
	SharedPreferences shpf;
	String user_id;
	HorizontalScrollView horizontal_id;
	ImageView open_check,seven_check;
	LinearLayout install_layout;
	int position;
	ListView step_list;
	Helper helper;
	ImageView myrating;
	FragmentManager fm;
	Bundle b;
	ImageView normal_image;
	Fragment fragment;
	FragmentTransaction ft;
	LinearLayout upto_layout;// 0 for normal,1 for upto layout
	LinearLayout box_layout,step_layout;
	Dialog dialog;
	//private AnimationDrawable loadingViewAnim;
	ImageView loading_image; 
	TextView install_text;
	ImageView install_image;
	//private UiLifecycleHelper uiHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);*/
		actRef	=	getActivity();
		offer_list= new ArrayList<>();
	}

	/*private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {


		if (state.isOpened()) {
			Request.newMeRequest(session, new Request.GraphUserCallback() {

				// callback after Graph API response with user
				// object
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						// Set view visibility to true
						// publishFeedDialog();
					}
				}
			}).executeAsync();
		} else if (state.isClosed()) {

		}
	}*/


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG,"Inside OnCreateView");
		View view=inflater.inflate(R.layout.activity_offer_detail, container, false);
		mResources		=	getResources();
		fragRef			=	this;
		progressOneTV	=	(TextView)view.findViewById(R.id.progress_startTV);
		plsWaitLL		=	(LinearLayout)view.findViewById(R.id.pleaseWaitRewardLL);
		progressTwoTV	=	(TextView)view.findViewById(R.id.progress_installTV);
		progressThreeTV	=	(TextView)view.findViewById(R.id.progress_tryTV);
		progressOneLL	=	(LinearLayout) view.findViewById(R.id.progress_line_start);
		progressTwoLL	=	(LinearLayout) view.findViewById(R.id.progress_line_install);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		instructionBoxLL	=	(LinearLayout) view.findViewById(R.id.instructionBox);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		box_layout=(LinearLayout) view.findViewById(R.id.box_layout);
		step_list=(ListView) view.findViewById(R.id.step_list);
		upto_layout=(LinearLayout) view.findViewById(R.id.normal_layout);
		normal_image=(ImageView) view.findViewById(R.id.normal_image);
		helper=new Helper();
		step_layout=(LinearLayout) view.findViewById(R.id.step_layout);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		install_text=(TextView) view.findViewById(R.id.install_text);
		install_image=(ImageView) view.findViewById(R.id.install_image);

		/*shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupon");
		referearn_text.setText("Refer & Earn");*/
		//DashboardActivity.onCustomActionView();
		//--
		//--
		LinearLayout lin[]	=	new LinearLayout[]{offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout};
		DashboardActivity.actRef.selectTab(lin, APP_Constants.OFFERS);
		//--

		//--
		//onBackPressListener();
		//--
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();


		priceearn_text.setText("Price Comparison");
		shpf=getActivity().getSharedPreferences("User_login", 1);
		user_id=shpf.getString("user_id",null);
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
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

		app_name=(TextView) view.findViewById(R.id.app_name);
		app_description=(TextView) view.findViewById(R.id.app_description);
		app_install_detail=(TextView) view.findViewById(R.id.app_install_detail);
		instruction_header=(TextView) view.findViewById(R.id.instruction_header);
		/*open_app_rate=(TextView) view.findViewById(R.id.open_app_rate);
		seven_app_rate=(TextView) view.findViewById(R.id.seven_app_rate);*/
		myrating=(ImageView) view.findViewById(R.id.myrating);
		instruction_step=(TextView) view.findViewById(R.id.instruction_step);
		/*open_check=(ImageView) view.findViewById(R.id.open_app);
		seven_check=(ImageView) view.findViewById(R.id.seven_app);*/
		install_layout=(LinearLayout) view.findViewById(R.id.install_layout);
		/*SpannableStringBuilder ssb = new SpannableStringBuilder(StaticData.application_list.get(position).getApp_description());
		ImageSpan imagespan = new ImageSpan(getResources().getDrawable(R.drawable.search_icon)); 
		ssb.setSpan(imagespan, 0, 0+StaticData.application_list.get(position).getApp_description().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


		ImageGetter getter = new ImageGetter(){                 
		    public Drawable getDrawable(String source){   // source is the resource name
		        Drawable d = null;
		        Integer id =  new Integer(0);
		        id = getResources().getIdentifier(source, "drawable", "com.sampleproject");

		        d = getResources().getDrawable(R.drawable.search_icon);   

		        if (d != null)
		            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());

		        return d;
		    }
		};

		String imgString = StaticData.application_list.get(position).getApp_description() + " <img src=\"getResource().getDrawable(R.drawable.search_icon)\"/>";
		app_description.setText(Html.fromHtml(imgString, getter, null));
		 */		
		b=getArguments();
		position=b.getInt("position");
		//--
		/**
		 * +Out of Memory Crash Here , All Lists have been erased by OS as they are static 
		 */ 
		if(StaticData.application_list.isEmpty())
		{
			//UtilMethod.showToast("Out of Memory, Please Free some Memory..", actRef.getApplicationContext());
			//getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
			DashboardActivity.actRef.displayView(1);//Offers Section
			return view;
		}
		//--
		if(StaticData.application_list.get(position).isPackage_flag() && StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("normal")){
			//	normal_image.setImageDrawable(getResources().getDrawable(R.drawable.start_2));
			setProgress(APP_Constants.TRIED);
			//open_check.setImageDrawable(getResources().getDrawable(R.drawable.active_circle));
			install_layout.setVisibility(View.VISIBLE);
			plsWaitLL.setVisibility(View.VISIBLE);
			instructionBoxLL.setVisibility(View.GONE);

			install_image.setImageDrawable(getResources().getDrawable(R.drawable.refer));
			install_text.setText("Refer");
			//install_layout.setVisibility(View.GONE);
		}
		else if(StaticData.application_list.get(position).isPackage_flag() && StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("upto"))
		{
			install_layout.setVisibility(View.VISIBLE);
			instructionBoxLL.setVisibility(View.GONE);
			install_image.setImageDrawable(getResources().getDrawable(R.drawable.refer));
			install_text.setText("Refer");
		}

		String value;
		//UtilMethod.showToast("Descriptin Size is "+StaticData.application_list.get(position).getApp_description().length(), getActivity());
		if(StaticData.application_list.get(position).getApp_description().length()>200){
			value=StaticData.application_list.get(position).getApp_description().substring(0, 200)+"Read More";
			int startIndex = value.indexOf("Read More");
			int endIndex = value.length();
			app_description.setText(value);
			app_description.setMovementMethod(LinkMovementMethod.getInstance());
			app_description.setText(value, BufferType.SPANNABLE);

			Spannable mySpannable = (Spannable) app_description.getText();
			ClickableSpan myClickableSpan = new ClickableSpan() {
				@Override
				public void onClick(View widget) {

					System.out.println("show terms of use box --------  ");
					/*homescreenActivity.showTermsOfService();*/
					onLess();

				}
			};

			mySpannable.setSpan(myClickableSpan, startIndex, endIndex,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		}
		else{
			//UtilMethod.showToast("Description Size inside else is "+StaticData.application_list.get(position).getApp_description().length(), getActivity());
			value=StaticData.application_list.get(position).getApp_description();
			app_description.setText(value);
		}

		if(StaticData.application_list.get(position).getApp_type().equals("normal")){
			Log.e(TAG,"  NORMAL ");
			box_layout.setVisibility(View.GONE);
			upto_layout.setVisibility(View.VISIBLE);
			instruction_header.setText("Instructions to earn Rs."+StaticData.application_list.get(position).getUptotalamount());

		}
		else if(StaticData.application_list.get(position).getApp_type().equals("upto")){
			box_layout.setVisibility(View.VISIBLE);
			upto_layout.setVisibility(View.GONE);

			for(int i=0;i<StaticData.upto_list.size();i++){
				if(StaticData.application_list.get(position).getApp_id().equals(StaticData.upto_list.get(i).getApp_id())){
					ApplicationBean abean=new ApplicationBean();
					abean.setUpto_purpose(StaticData.upto_list.get(i).getUpto_purpose());
					abean.setUpto_amount(StaticData.upto_list.get(i).getUpto_amount());
					abean.setTaskId(StaticData.upto_list.get(i).getTaskId());
					abean.setStep_status(StaticData.upto_list.get(i).getStep_status());
					abean.setApp_id(StaticData.upto_list.get(i).getApp_id());
					abean.setTaskValue(StaticData.upto_list.get(i).getTaskValue());
					offer_list.add(abean);
					System.out.println("in get upto");

				}
			}

			if(StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("upto")){
				dbhelper = new DBHELPER(getActivity());
				sqLiteDatabase = dbhelper.getWritableDatabase();


				Cursor c = sqLiteDatabase.rawQuery("select installdate,datetask,datatask,dataused,targetdata,uid from appinfo_upto where packagename = "+"'"+StaticData.application_list.get(position).getPackage_name()+"' AND userid = '"+ getActivity().getSharedPreferences("User_login", 1).getString("user_id", null)+"'",null);
				if(c.moveToFirst()) {
					Long byt = c.getString(3) == null ? 0 : Long.parseLong(c.getString(3)) + TrafficStats.getUidTxBytes(Integer.parseInt(c.getString(5))) + TrafficStats.getUidRxBytes(Integer.parseInt(c.getString(5)));
					System.out.println(" "+c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "+ c.getString(5) + " " + byt);

					if (c.getString(0) != null && !c.getString(0).equalsIgnoreCase("")  ) {
						int i =0;

						for(ApplicationBean b :offer_list){
                           System.out.println("install "+b.getPackage_name()+ " " +b.getTaskId()+ " ");
							if(b.getTaskId().equalsIgnoreCase("111")) {
								 offer_list.get(i).setStep_status(1);
							}
							i++;
						}


					}
					if ( c.getString(1) != null&&c.getString(1).equalsIgnoreCase("true") ) {
						int i =0;
						for(ApplicationBean b :offer_list){
							System.out.println("date  "+b.getPackage_name()+ " " +b.getTaskId()+ " ");
							if(b.getTaskId().equalsIgnoreCase("222")) {
								offer_list.get(i).setStep_status(1);
							}
									i++;
						}

						//ImageView v = (ImageView) lv.getChildAt(1).findViewById(R.id.step_offer_image);
						//v.setImageDrawable(getResources().getDrawable(R.drawable.active_circle));
					}
					dbhelper = new DBHELPER(getActivity());
					sqLiteDatabase = dbhelper.getWritableDatabase();
					ContentValues content = new ContentValues();
					if ( c.getString(2)!= null && c.getString(4) != null  && (c.getString(2).equalsIgnoreCase("true") || (byt / 1024) > c.getInt(4))) {
						int i =0;
						for(ApplicationBean b :offer_list){
							System.out.println("data "+b.getPackage_name()+ " " +b.getTaskId()+ " ");
							content.put("dataused",(byt/1024));
							sqLiteDatabase.update("appinfo_upto",content,"packagename = "+"'"+ StaticData.application_list.get(position).getPackage_name()+"' AND userid = '"+ getActivity().getSharedPreferences("User_login", 1).getString("user_id", null)+ "'",null);
							if(b.getTaskId().equalsIgnoreCase("333")) {

								offer_list.get(i).setStep_status(1);
							}
							i++;
						}
						//	ImageView v = (ImageView) lv.getChildAt(0).findViewById(R.id.step_offer_image);
						//	v.setImageDrawable(getResources().getDrawable(R.drawable.active_circle));
					}
				}
				sqLiteDatabase.close();
			}
			Log.e(TAG, "Using OfferStep Adapter");
			step_list.setAdapter(new OfferStepAdapter(getActivity(), StaticData.application_list.get(position).getApp_id(), offer_list));
			helper.getListViewSize(step_list);

			instruction_header.setText("Instructions to earn Rs." + StaticData.application_list.get(position).getUptotalamount());
			//int val=Integer.valueOf(String.valueOf(StaticData.application_list.get(position).getAmount_per_install())+Integer.valueOf(StaticData.application_list.get(position).getAmount_per_open()));
			//int val=Integer.valueOf(String.valueOf(StaticData.application_list.get(position).getAmount_per_install()));
			app_install_detail.setText("Install & Get Upto Rs."+StaticData.application_list.get(position).getAmount_per_install());
			instruction_header.setText("Instructions to earn Rs."+StaticData.application_list.get(position).getUptotalamount());
		}
		app_install_detail.setText(StaticData.application_list.get(position).getApp_short_description());
		app_name.setText(StaticData.application_list.get(position).getApp_name());
		//	app_description.setText(StaticData.application_list.get(position).getApp_description());

		//myrating.setRating(StaticData.application_list.get(position).getApp_rate());

		if(StaticData.application_list.get(position).getApp_rate()>0){
			/*myrating.setRating(StaticData.application_list.get(position).getApp_rate());
			LayerDrawable stars = (LayerDrawable) myrating.getProgressDrawable();
			stars.getDrawable(2).setColorFilter(Color.YELLOW,);*/
			if(StaticData.application_list.get(position).getApp_rate()>=0.5 && StaticData.application_list.get(position).getApp_rate()<1.0){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s0_5_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=1.0 && StaticData.application_list.get(position).getApp_rate()<1.5){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s1_star));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=1.5 && StaticData.application_list.get(position).getApp_rate()<2.0){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s1));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=2.0 && StaticData.application_list.get(position).getApp_rate()<2.5){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s2_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=2.5 && StaticData.application_list.get(position).getApp_rate()<3.0){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s2));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=3.0 && StaticData.application_list.get(position).getApp_rate()<3.5){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s3_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=3.5 && StaticData.application_list.get(position).getApp_rate()<4.0){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s3));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=4.0 && StaticData.application_list.get(position).getApp_rate()<4.5){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s4_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()>=4.5 && StaticData.application_list.get(position).getApp_rate()<5.0){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s4));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==5){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s5_stars));
			}		
		}
		//open_app_rate.setText("Rs."+StaticData.application_list.get(position).getAmount_per_open());
		//seven_app_rate.setText("Rs."+StaticData.application_list.get(position).getAmount_per_install());

		instruction_step.setText(Html.fromHtml(StaticData.application_list.get(position).getApp_instruction()));
		AQuery aq = new AQuery(view);
		aq.id(R.id.app_image).image(StaticData.application_list.get(position).getApp_image(),
				true, true, 0, R.drawable.ic_launcher, null,
				AQuery.CACHE_DEFAULT,0.0f).visible();
		fm=getFragmentManager();


		install_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				if(UtilMethod.isNetworkAvailable(getActivity())){

					try{

						if(StaticData.application_list.get(position).isPackage_flag()){
							/*if(StaticData.application_list.get(position).getPurpose_id()==2 || StaticData.application_list.get(position).getPurpose_id()==3){*/
							Log.e(TAG,"true Case Installed REFER");
							final Dialog dialog1=new Dialog(getActivity());
							dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog1.setContentView(R.layout.activity_shareearn);
							LinearLayout whatsapp_share_layout=(LinearLayout) dialog1.findViewById(R.id.whatsapp_share_layout);
							LinearLayout facebook_share_layout=(LinearLayout) dialog1.findViewById(R.id.facebook_share_layout);
							LinearLayout google_plus_share_layout=(LinearLayout) dialog1.findViewById(R.id.google_plus_share_layout);
							LinearLayout twitter_share_layout=(LinearLayout) dialog1.findViewById(R.id.twitter_share_layout);
							dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

							whatsapp_share_layout.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog1.dismiss();
									if(isAppInstalled("com.whatsapp")){
										PackageManager pm=getActivity().getPackageManager();
										try {

											Intent waIntent = new Intent(Intent.ACTION_SEND);
											//waIntent.setType("text/plain");
											// waIntent.setType("*/*");
											String text = "Would you like to earn more money?So, Please install this app and follow the instructions and link is "+WebService.APPLICATION_URL;

											PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
											//Check if package exists or not. If not then code 
											//in catch block will be called
											waIntent.setPackage("com.whatsapp");
											waIntent.putExtra(Intent.EXTRA_TEXT, StaticData.application_list.get(position).getAppshare());
											waIntent.setType("text/plain");

											if(StaticData.application_list.get(position).getApp_image()!=null){
												String extStorageDirectory = Environment.getExternalStorageDirectory()
														.toString();
												File folder = new File(extStorageDirectory, "dnp_images");
												folder.mkdir();
												File file = new File(folder, "sharing_image.png");
												/*File file1=new File(Company_constants.str_candidate_resume);*/
												try {
													file.createNewFile();
												} catch (IOException e1) {
													e1.printStackTrace();
												}
												//Log.v("File Path",Company_constants.str_candidate_resume);
												StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
												StrictMode.setThreadPolicy(policy); 
												Downloader.DownloadFile(StaticData.application_list.get(position).getApp_image(), file);
												waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
												waIntent.setType("image/*");
											}


											getActivity().startActivity(Intent.createChooser(waIntent, "Share image via:"));

										} catch (NameNotFoundException e) {
											/* Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
						                .show();*/
											UtilMethod.showToast("WhatsApp not Installed", getActivity().getApplicationContext());
										}
									}
									else{
										//UtilMethod.showToast("Please install WhatsApp", getActivity());
									}
								}
							});
							/*authButton.setSessionStatusCallback(new Session.StatusCallback() {

						@Override
						public void call(Session session, SessionState state, Exception exception) {
							// TODO Auto-generated method stub
							if(session.isOpened()){
								publishFeedDialog();
							}
							else{
								UtilMethod.showToast("Session is closed", getActivity());
							}
						}
					});*/
							facebook_share_layout.setOnClickListener(new OnClickListener() {

								@SuppressWarnings("deprecation")
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog1.dismiss();
									if(isAppInstalled("com.facebook.katana")){
										ShareDialog shareDialog = new ShareDialog(getActivity());
										ShareLinkContent content = new ShareLinkContent.Builder()
										.setContentUrl(Uri.parse(StaticData.application_list.get(position).getAppshare())).setContentTitle("DealsnPrice").setContentDescription("DealsnPrice").build();
										shareDialog.show(getActivity(),content);
									}
									else{
										UtilMethod.showToast("Please install Facebook App", getActivity());
									}

								}
							});



							twitter_share_layout.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog1.dismiss();
									// String text = "Would you like to earn more money?So, Please install this app and follow the instructions and link is "+WebService.APPLICATION_URL;
									if(isAppInstalled("com.twitter.android")){
										try{
											PackageManager pm=getActivity().getPackageManager();
											PackageInfo info=pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
											//Check if package exists or not. If not then code 
											//in catch block will be called

											Intent tweetIntent = new Intent(Intent.ACTION_SEND);
											tweetIntent.putExtra(Intent.EXTRA_TEXT, StaticData.application_list.get(position).getShare_link());
											tweetIntent.setPackage("com.twitter.android");
											tweetIntent.setType("text/plain");
											getActivity().startActivity(tweetIntent);
										}
										catch(Exception e){

										}
									}
									else{
										UtilMethod.showToast("Please install Twitter App",getActivity());
									}
								}
							});
							/*google_plus_share_layout.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog1.dismiss();

									if(isAppInstalled("com.google.android.apps.plus")){
										Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
												.setText(StaticData.application_list.get(position).getShare_link())
												.setType("text/plain")
												.getIntent()
												.setPackage("com.google.android.apps.plus");
										getActivity().startActivity(shareIntent);
										}

									else{
										UtilMethod.showToast("Please install Google Plus", getActivity());

									}
								}
							});
*/
							dialog1.show();
							/*}*/

						}
						else{
							//--
							dbhelper = new DBHELPER(getActivity());
							sqLiteDatabase=dbhelper.getWritableDatabase();
							if(StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("upto")) {
								ContentValues contentValues = new ContentValues();
								contentValues.put("packagename", StaticData.application_list.get(position).getPackage_name());
								contentValues.put("userid", getActivity().getSharedPreferences("User_login", 1).getString("user_id", null));
								contentValues.put("appid",StaticData.application_list.get(position).getApp_id());
								contentValues.put("appname", StaticData.application_list.get(position).getApp_name());
								for (ApplicationBean b : offer_list) {
									if (b.getTaskId().equalsIgnoreCase("333")) {
										//TODO change condition to value, put value in upto list in applisttask, n get it here n insert in db
										contentValues.put("targetdata", b.getTaskValue() * 1024);
										contentValues.put("datataskamount",b.getUpto_amount());
										System.out.println(b.getTaskValue());
									}
									if (b.getTaskId().equalsIgnoreCase("222")) {
										SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

										Date d = new Date(System.currentTimeMillis());
										d.setTime(d.getTime() + (b.getTaskValue() * 24 * 60 * 60 * 1000));
										System.out.println(simpleDateFormat.format(d) + "  " + b.getTaskValue() + " " + b.getTaskId());
										contentValues.put("targetdate", simpleDateFormat.format(d));
										contentValues.put("datetaskamount",b.getUpto_amount());

									}
								}

								sqLiteDatabase.insertWithOnConflict("appinfo_upto", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
							}
							sqLiteDatabase.close();
							if(!StaticData.application_list.get(position).isOpened() && StaticData.application_list.get(position).isPackage_flag())
							{
								UtilMethod.showToast("You Have to Open the Application Now", getActivity().getApplicationContext());
								OfferDetailFragment.fragRef.startProcessDetection(OfferDetailFragment.targetApp);
							}//--
							else
							{


								/*multipart.addPart("user_id", new StringBody(shpf.getString("user_id",null)));
				multipart.addPart("app_id",new StringBody(StaticData.application_list.get(position).getApp_id()));
				multipart.addPart("app_status",new StringBody("2"));
				setProgressDialog();
				new ReadAppTask(getActivity(), multipart, StaticData.application_list.get(position).getApp_url(),new OfferDetailListener(),position).execute();
								 */
								ApplicationBean abean=new ApplicationBean();
								abean.setPackage_name(StaticData.application_list.get(position).getPackage_name());
								abean.setApp_id(StaticData.application_list.get(position).getApp_id());
								WebView webView= new WebView(getActivity());
								webView.getSettings().setJavaScriptEnabled(true);
								webView.setWebViewClient(new WebViewClient() {
									@Override
									public boolean shouldOverrideUrlLoading(WebView view, String url) {
										System.out.println(url);
										if (url.contains("play.google")) {
											System.out.println("in if");
											Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

											i.setPackage("com.android.vending");
											getActivity().startActivity(i);

											return true;
										}
										System.out.println("out if");
										return false;
									}
								});
								//webView.loadUrl(StaticData.application_list.get(position).getApp_url());
								Receiver.createAppPreference(abean, getActivity(), getActivity().getSharedPreferences("User_login", 1).getString("user_id", null));
								Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(StaticData.application_list.get(position).getApp_url()));
								Log.e("CLICK INSTALL ", "URL " + StaticData.application_list.get(position).getApp_url());
								getActivity().startActivity(intent);
								setProgress(APP_Constants.STARTED);
								UtilMethod.showToast("Click on Install Button", getActivity().getApplicationContext());


								install_text.setText("In Progress");
								targetApp	=	StaticData.application_list.get(position).getPackage_name().trim();
								//targetApp	=	"com.cpuid.cpu_z"; //testing
								Log.e(TAG,"Target App is "+targetApp);
								//startInstallDetection();
								String appName	=	StaticData.application_list.get(position).getApp_name();	
								
								DashboardActivity.actRef.showNotification(APP_Constants.INTSALL_NOTIF_ID,appName);
                                //--

                                //--
							}
						}

					}
					catch(Exception e){
						Log.e(" INSTALL "," CRASH "+ ""+e);
						e.printStackTrace();

					}
					/*else{

				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StaticData.application_list.get(position).getApp_url()));
				getActivity().startActivity(myIntent);
				}*/
				}
				else{
					UtilMethod.showNetworkError(getActivity());
				}


			}


        });



		return view;
	}



	/**
	 * This Threads Detects whether the installation completes or not while user is installing the target application
	 */
	/*protected void startInstallDetection() {
		if(appDetector_timer == null)
			appDetector_timer	=	new Timer();
		else
			stopDetectionTimer();
		TimerTask timerTask	=	new TimerTask() {
			int count	=	0;

			@Override
			public void run() {
				count++;
				Log.e(" INTSALL ","Waiting for app to install  "+count);
				if(StaticData.application_list.get(position).isPackage_flag())
				{
					String appName	=	StaticData.application_list.get(position).getApp_name();	
					stopDetectionTimer();
					DashboardActivity.actRef.showNotification(APP_Constants.OPEN_NOTIF,appName);
					Log.e(" INTSALL ","Install Finished ");

				}
				else
				{
					if(count	==	10*60) //20 Mins
					{	
						stopDetectionTimer();
						UtilMethod.showToast("Time Expired, Process Failed", getActivity().getApplicationContext());
						install_text.setText("Install");
						setProgress(APP_Constants.REVERT);
						Log.e("INSTALL "," install timeout ");
					}
				}
			}
		};
		appDetector_timer.scheduleAtFixedRate(timerTask, 0, 2000);
	}*/


	/**
	 * This Method overrides the functionality of hardware back press button
	 *//*
	private void onBackPressListener() {
		//You need to add the following line for this solution to work; thanks skayred
		fragRef.getView().setFocusableInTouchMode(true);
		fragRef.getView().requestFocus();
		fragRef.getView().setOnKeyListener( new OnKeyListener()
		{
		    @Override
		    public boolean onKey( View v, int keyCode, KeyEvent event )
		    {
		        if( keyCode == KeyEvent.KEYCODE_BACK )
		        {
		        	Log.e("On Bsck Press "," Click Clik Brak Press");
		            return true;
		        }
		        return false;
		    }
		} );		
	}
	//--
	  */	@Override
	  public void onStart() {
		  super.onStart();
		  //flag = false;
		  //Session session=Session.getActiveSession();
		  /*if(session!=null && session.isOpened()){
			session.close();
		}*/
		  //	mGoogleApiClient.connect();
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

	  private void setProgressDialog(){

		  dialog=new Dialog(getActivity());
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.activity_loading);
		  LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
		  loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
		  //loading_image.setBackgroundResource(R.anim.loading_animation);
		  //	loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
		  dialog.setCancelable(false);
		  //loadingViewAnim.start();

		  dialog.show();
	  }

	  public void onLess(){
		  String value=StaticData.application_list.get(position).getApp_description()+"Read Less";
		  int startIndex = value.indexOf("Read Less");
		  int endIndex = value.length();
		  app_description.setText(value);
		  app_description.setMovementMethod(LinkMovementMethod.getInstance());
		  app_description.setText(value, BufferType.SPANNABLE);

		  Spannable mySpannable = (Spannable) app_description.getText();
		  ClickableSpan myClickableSpan = new ClickableSpan() {
			  @Override
			  public void onClick(View widget) {

				  onMore();

				  /*homescreenActivity.showTermsOfService();*/


			  }
		  };

		  mySpannable.setSpan(myClickableSpan, startIndex, endIndex,
				  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	  }
	  public void onMore(){
		  String value;
		  if(StaticData.application_list.get(position).getApp_description().length()>200){
			  value=StaticData.application_list.get(position).getApp_description().substring(0,200)+"Read More";
		  }
		  else{
			  value=StaticData.application_list.get(position).getApp_description();
		  }
		  int startIndex = value.indexOf("Read More");
		  int endIndex = value.length();
		  app_description.setText(value);
		  app_description.setMovementMethod(LinkMovementMethod.getInstance());
		  app_description.setText(value, BufferType.SPANNABLE);
		  Spannable mySpannable = (Spannable) app_description.getText();
		  ClickableSpan myClickableSpan = new ClickableSpan() {
			  @Override
			  public void onClick(View widget) {

				  System.out.println("show terms of use box --------  ");

				  /*homescreenActivity.showTermsOfService();*/
				  onLess();

			  }
		  };

		  mySpannable.setSpan(myClickableSpan, startIndex, endIndex,
				  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	  }
	  OnClickListener offerListener=new OnClickListener() {

		  @Override
		  public void onClick(View v) {

			  fragment=new OfferFragment();
			  onReplace(fragment);
		  }
	  };

	  OnClickListener profileListener=new OnClickListener() {

		  @Override
		  public void onClick(View v) {
			  // TODO Auto-generated method stub
			  fragment=new ProfileFragment();
			  onReplace(fragment);
		  }
	  };

	  OnClickListener favouriteListener=new OnClickListener() {

		  @Override
		  public void onClick(View v) {
			  // TODO Auto-generated method stub
			  fragment=new FavouriteFragment();
			  onReplace(fragment);

		  }
	  };

	  OnClickListener homeListener=new OnClickListener() {

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
			  // TODO Auto-generated method stub
			  fragment=new NotificationFragment();
			  onReplace(fragment);
		  }
	  };

	  OnClickListener shopEarnListener=new OnClickListener() {

		  @Override
		  public void onClick(View v) {
			  // TODO Auto-generated method stub
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
		  DashboardActivity.actRef.cancelAllNotifications();
	  }

	  public class OfferDetailListener{
		  public void onSuccess(){
			  if(dialog!=null && dialog.isShowing()){
				  //loadingViewAnim.stop();
				  dialog.dismiss();
			  }

		  }
		  public void onError(String msg){
			  if(dialog!=null && dialog.isShowing()){
				  //loadingViewAnim.stop();
				  dialog.dismiss();
			  }
			  if(msg.equals("slow")){
				  UtilMethod.showServerError(getActivity());
			  }
			  else{
				  final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
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
	   * This method Updates the progress on UI
	   * @param progress
	   */
	  public void setProgress(int progress)
	  {
		  switch (progress) {
		  case 0: //Revert back all progress 
			  progressOneLL.setBackgroundColor(mResources.getColor(R.color.progress_false));
			  progressOneTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_grey));
			  progressOneTV.setTextColor(mResources.getColor(R.color.progress_text_color_dark));
			  progressTwoLL.setBackgroundColor(mResources.getColor(R.color.progress_false));
			  progressTwoTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_grey));
			  progressTwoTV.setTextColor(mResources.getColor(R.color.progress_text_color_dark));
			  progressThreeTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_grey));
			  progressThreeTV.setTextColor(mResources.getColor(R.color.progress_text_color_dark));
			  StaticData.application_list.get(position).setProgress(progress);
			  break;
		  case 1: //Started the install Process
			  progressOneLL.setBackgroundColor(mResources.getColor(R.color.progress_true));
			  progressOneTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
			  progressOneTV.setTextColor(Color.WHITE);
			  StaticData.application_list.get(position).setProgress(progress);
			  break;
		  case 2: //Installed the app through DnP
			  progressOneLL.setBackgroundColor(mResources.getColor(R.color.progress_true));
			  progressOneTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
			  progressOneTV.setTextColor(Color.WHITE);
			  progressTwoLL.setBackgroundColor(mResources.getColor(R.color.progress_true));
			  progressTwoTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
			  progressTwoTV.setTextColor(Color.WHITE);
			  StaticData.application_list.get(position).setProgress(progress);
			  break;
		  case 3: //Tried the App / Opened the App
			  if(StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("normal")) {
				  progressOneLL.setBackgroundColor(mResources.getColor(R.color.progress_true));
				  progressOneTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
				  progressOneTV.setTextColor(Color.WHITE);
				  progressTwoLL.setBackgroundColor(mResources.getColor(R.color.progress_true));
				  progressTwoTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
				  progressTwoTV.setTextColor(Color.WHITE);
				  progressThreeTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
				  progressThreeTV.setTextColor(Color.WHITE);
				  StaticData.application_list.get(position).setProgress(progress);
			  }
			  else if(StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("upto"))
			  {

			  }
			  break;
			  case 4:
			  case 5:
		  default:
			  break;
		  }
	  }

	  /**
	   * This method starts a thread that detects if the target app installed has been opened by the user or not
	   * @param targetApp
	   */

	  public void startProcessDetection(final String targetApp) {
		  if(appDetector_timer == null)
		  {
			  appDetector_timer	=	new Timer();
		  }
		  else
		  {
			  stopDetectionTimer();
		  }

		  TimerTask timerTask	=	new TimerTask() {
			  int count	=	0;

			  @Override
			  public void run() {

				  Log.e("Detect Process for  "," "+targetApp);
				  count++;
				  if(isNamedProcessRunning(targetApp))
				  {
					  String appName	=	StaticData.application_list.get(position).getApp_name();	
					  DashboardActivity.actRef.showNotification(APP_Constants.SUCCESS_NOTIF_ID,appName);
					  actRef.runOnUiThread(new Runnable() {
						  @Override
						  public void run() {
							  stopDetectionTimer();
							  setProgress(APP_Constants.TRIED);

							  //install_layout.setClickable(false);
							  if(StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("normal")) {
								  UtilMethod.showToast("Now Return to DealsnPrice to earn Reward", getActivity().getApplicationContext());
								  plsWaitLL.setVisibility(View.VISIBLE);
								  instructionBoxLL.setVisibility(View.GONE);
							  }
							  install_image.setImageDrawable(getResources().getDrawable(R.drawable.refer));
							  install_text.setText("Refer");
							  //install_layout.setVisibility(View.GONE);
						  }
					  });
				  }
				  else
				  { actRef.runOnUiThread(new Runnable() {
					  @Override
					  public void run() {
						  if(count	==	3*60) //6 mins
						  {
							  stopDetectionTimer();
							  UtilMethod.showToast("Time Expired, Process Failed", getActivity().getApplicationContext());

							  install_text.setText("Install");
							  plsWaitLL.setVisibility(View.GONE);
							  instructionBoxLL.setVisibility(View.VISIBLE);
						  }
					  }
				  });

				  }
			  }
		  };
		  appDetector_timer.scheduleAtFixedRate(timerTask, 0, 2000);
	  }

	  /**
	   * This method stops the timer thread, if waiting for user to open the target App
	   */
	  public void stopDetectionTimer()
	  {
		  if(appDetector_timer != null)
		  {
			  appDetector_timer.cancel();
			  appDetector_timer.purge();
		  }
	  }

	  /**
	   * This Method checks whether the given process is currently running or not.
	   * @param processName
	   * @return
	   */
	  boolean isNamedProcessRunning(String processName){
		  if (processName == null) 
			  return false;

		  ActivityManager manager = (ActivityManager) actRef.getSystemService(actRef.ACTIVITY_SERVICE);
		  List<RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
		  for (RunningAppProcessInfo process : processes)
		  {
			  if (processName.equals(process.processName))
			  {
				  Log.e("isNamedProcessRunning "," "+processName +" FOUND ");
				  return true;
			  }
		  }
		  return false;
	  }



	  @Override
	  public void onDestroy() {
		  super.onDestroy();
		  fragRef		=	null;
		  stopDetectionTimer();
		  appDetector_timer	=	null;
		  DashboardActivity.actRef.cancelAllNotifications();

	  }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}
