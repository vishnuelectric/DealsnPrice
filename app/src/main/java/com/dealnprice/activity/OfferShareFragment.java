package com.dealnprice.activity;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.androidquery.AQuery;
import com.dnp.asynctask.InsertStartTask;
import com.dnp.data.Downloader;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.DNPDealCouponFragment;
import com.dnp.fragment.FavouriteFragment;
import com.dnp.fragment.NotificationFragment;
import com.dnp.fragment.OfferFragment;
import com.dnp.fragment.PriceComparisonFragment;
import com.dnp.fragment.ProfileFragment;
import com.dnp.fragment.ReferEarnFragment;
import com.dnp.fragment.ShopEarnFragment;
import com.dnp.fragment.StartTaskUrlFragment;

import com.dealnprice.activity.R;

public class OfferShareFragment extends Fragment{
	Bundle b;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	LinearLayout footer_layout;
	HorizontalScrollView horizontal_id;
	FragmentManager fm;
	Fragment fragment;
	ImageView myrating;
	FragmentTransaction ft;
	int position;
	String value;
	SharedPreferences shpf;
	ImageView app_image;
	String user_id;
	TextView app_description;
	LinearLayout facebook_share_layout;
	TextView install_concept;
	TextView app_name,instruction_header,instruction_step,app_install_detail;
	LinearLayout install_layout;
	//private UiLifecycleHelper uiHelper;
	//LoginButton authButton;
	public static String accessToken; 
	Date accessExpir;
	ProgressDialog mDialog;
	boolean flag = false;
	//Facebook fb;
	String APP_ID;
	Dialog dialog;
	AnimationDrawable loadingViewAnim;
	ImageView loading_image;	
	public static boolean isFbDataReceived = false,isServiceCalled = false; 
	 Context cxt;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    //uiHelper.onCreate(savedInstanceState);
	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.activity_offer_share, container, false);
		b=getArguments();
		generateHash();
		/*uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);*/
	    
	    APP_ID=getString(R.string.app_id);
		//fb=new Facebook(APP_ID);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		install_concept=(TextView) view.findViewById(R.id.install_concept);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		app_image=(ImageView) view.findViewById(R.id.app_image);
		app_description=(TextView) view.findViewById(R.id.app_description);
		app_install_detail=(TextView) view.findViewById(R.id.app_install_detail);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		myrating=(ImageView) view.findViewById(R.id.myrating);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		install_layout=(LinearLayout) view.findViewById(R.id.install_layout);
		offer_layout.setOnClickListener(offerListener);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupon");
		cxt=getActivity();
		referearn_text.setText("Refer & Earn");
		//DashboardActivity.onCustomActionView();
		priceearn_text.setText("Price Comparison");
		shpf=getActivity().getSharedPreferences("User_login", 1);
		user_id=shpf.getString("user_id",null);
		isFbDataReceived = false;
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			}
		});
		
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
		install_layout.setOnClickListener(shareListener);
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
		
		if(StaticData.application_list.get(position).getApp_rate()>0){
			/*myrating.setRating(StaticData.application_list.get(position).getApp_rate());
			LayerDrawable stars = (LayerDrawable) myrating.getProgressDrawable();
			stars.getDrawable(2).setColorFilter(Color.YELLOW,);*/
			if(StaticData.application_list.get(position).getApp_rate()>=0.5 && StaticData.application_list.get(position).getApp_rate()<1.0){
				myrating.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.s0));
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
		
		
		app_name=(TextView) view.findViewById(R.id.app_name);
		instruction_header=(TextView) view.findViewById(R.id.instruction_header);
		instruction_step=(TextView) view.findViewById(R.id.instruction_step);
		b=getArguments();
		position=b.getInt("position");
		
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
			UtilMethod.showToast("Description Size inside else is "+StaticData.application_list.get(position).getApp_description().length(), getActivity());
			value=StaticData.application_list.get(position).getApp_description();
			app_description.setText(value);
		}
		
		app_name.setText(StaticData.application_list.get(position).getApp_name());
		if(StaticData.application_list.get(position).getPurpose_id()==2){
			instruction_header.setText("Instructions to earn Rs."+StaticData.application_list.get(position).getUptotalamount());
			install_concept.setText("Refer");
		}
		else if(StaticData.application_list.get(position).getPurpose_id()==3){
			instruction_header.setText("Instructions to earn Rs."+StaticData.application_list.get(position).getUptotalamount());
			install_concept.setText("Share");
		}
		else if(StaticData.application_list.get(position).getPurpose_id()==4){
			instruction_header.setText("Instructions to earn Rs."+StaticData.application_list.get(position).getUptotalamount());
			install_concept.setText("Start Task");
		}
			
			app_install_detail.setText(StaticData.application_list.get(position).getApp_short_description());
			instruction_step.setText(Html.fromHtml(StaticData.application_list.get(position).getApp_instruction()));
			AQuery aq = new AQuery(view);
			aq.id(R.id.app_image).image(StaticData.application_list.get(position).getApp_image(),
					true, true, 0, R.drawable.ic_launcher, null,
					AQuery.CACHE_DEFAULT,0.0f).visible();
			fm=getFragmentManager();


		
	return view;
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

	
	
	public void generateHash(){
		try {
		    PackageInfo info = getActivity().getPackageManager().getPackageInfo(
		            getActivity().getPackageName(), 
		            PackageManager.GET_SIGNATURES);
		    for (Signature signature : info.signatures) {
		        MessageDigest md = MessageDigest.getInstance("SHA");
		        md.update(signature.toByteArray());
		        Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
		        }
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}
	
	OnClickListener shareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(StaticData.application_list.get(position).getPurpose_id()==2 || StaticData.application_list.get(position).getPurpose_id()==3){
			final Dialog dialog=new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_shareearn);
			LinearLayout whatsapp_share_layout=(LinearLayout) dialog.findViewById(R.id.whatsapp_share_layout);
			LinearLayout facebook_share_layout=(LinearLayout) dialog.findViewById(R.id.facebook_share_layout);
			LinearLayout google_plus_share_layout=(LinearLayout) dialog.findViewById(R.id.google_plus_share_layout);
			LinearLayout twitter_share_layout=(LinearLayout) dialog.findViewById(R.id.twitter_share_layout);
			/* authButton=(LoginButton) dialog.findViewById(R.id.authButton);
			 authButton.setFragment(getParentFragment());
			    authButton.setReadPermissions(Arrays.asList("public_profile","email"));
				authButton.setBackgroundResource(R.drawable.share_facbook_icon);
				authButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,0);
				APP_ID=getString(R.string.app_id);
				fb=new Facebook(APP_ID);*/
				 dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
			whatsapp_share_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
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
				            waIntent.putExtra(Intent.EXTRA_TEXT, StaticData.application_list.get(position).getShare_link());
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
					
				           // cxt.startActivity(waIntent);

				   } catch (NameNotFoundException e) {
				       /* Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
				                .show();*/
					  // UtilMethod.showToast("WhatsApp not Installed", cxt);
				   }
				}
					else{
						UtilMethod.showToast("Please install WhatsApp", getActivity());
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
			/*facebook_share_layout.setOnClickListener(new OnClickListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					if(isAppInstalled("com.facebook.katana")){
					FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
					.setLink(StaticData.application_list.get(position).getShare_link()).setCaption(StaticData.application_list.get(position).getApp_name()).setDescription(StaticData.application_list.get(position).getApp_name()).setFragment(getParentFragment()).build();
					uiHelper.trackPendingDialogCall(shareDialog.present());
					}
					else{
						UtilMethod.showToast("Please install Facebook App", getActivity());
					}

			}
			});*/
			
			
					
			twitter_share_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
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
			google_plus_share_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					
		    			
					if(isAppInstalled("com.google.android.apps.plus")){
		    					 Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
								         .setText(StaticData.application_list.get(position).getShare_link())
								         .setType("text/plain")
								         .getIntent()
								         .setPackage("com.google.android.apps.plus");
		    					 getActivity().startActivity(shareIntent);
		    					 /*} catch (IOException e1) {
		    						e1.printStackTrace();
		    					}*/
					}
					else{
						UtilMethod.showToast("Please install Google Plus", getActivity());
					}
					
					;
					
				
		
			
		}
	});

			dialog.show();
			}
			else if(StaticData.application_list.get(position).getPurpose_id()==4){
				TelephonyManager telephonyManager = (TelephonyManager)cxt.getSystemService(Context.TELEPHONY_SERVICE);
				String device_id=telephonyManager.getDeviceId();
				String url2=WebService.INSERT_APP_STATUS_SERVICE+"extension=1&userid="+getActivity().getSharedPreferences("User_login",1).getString("user_id",null)+"16&appid="+StaticData.application_list.get(position).getApp_id()+"&imei="+device_id+"&status=2";
				setProgressDialog();
				new InsertStartTask(getActivity(),url2,new OfferShareListener()).execute();
			}
		}
	};
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //uiHelper.onSaveInstanceState(outState);
    }
	@Override
    public void onDestroy() {
        super.onDestroy();
        //uiHelper.onDestroy();
    }
	 @Override
	    public void onPause() {
	        super.onPause();
	       // uiHelper.onPause();
	    }
	 
	 /*private void onSessionStateChange(Session session, SessionState state,
	            Exception exception) {
	        
	         
	        if (state.isOpened()) {
	           	            Request.newMeRequest(session, new Request.GraphUserCallback() {
	 
	                // callback after Graph API response with user
	                // object
	                @Override
	                public void onCompleted(GraphUser user, Response response) {
	                    if (user != null) {
	                        // Set view visibility to true
	                        /*publishFeedDialog();
	                    }
	                }
	            }).executeAsync();
	        } else if (state.isClosed()) {
	           
	        }
	    }*/
	 
	 public class OfferShareListener{
		 public void onSuccess(){
			 if(dialog!=null && dialog.isShowing()){
				 dialog.dismiss();
			 }
			 if(isAppInstalled(StaticData.application_list.get(position).getPackage_name())){
				 Intent intent1=getActivity().getPackageManager().getLaunchIntentForPackage(StaticData.application_list.get(position).getPackage_name());
				 startActivity(intent1);
			 }
			 else{
				 Fragment fragment=new StartTaskUrlFragment();
				 FragmentManager fmanager=getActivity().getSupportFragmentManager();
				 FragmentTransaction ft=fmanager.beginTransaction();
				 Bundle b=new Bundle();
				 b.putString("url",StaticData.application_list.get(position).getApp_url());
				 fragment.setArguments(b);
				 ft.replace(R.id.container, fragment);
				 ft.addToBackStack(null);
				 ft.commit();
			 }
			 /*Intent intent=new Intent(Intent.ACTION_VIEW)*/
		 }
		 public void onError(){
			 if(dialog!=null && dialog.isShowing()){
					//loadingViewAnim.stop();
					dialog.dismiss();
				}
		 }
	 }
	 
	 
	/* private void setProgressDialog(){
			
			dialog=new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.activity_loading);
			LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
			loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
			loading_image.setBackgroundResource(R.anim.loading_animation);
			loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
			//dialog.setCancelable(false);
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

	public void onResume() {
		super.onResume();
		/*final Session session = Session.getActiveSession();
	    if(fb.isSessionValid()){
	    	accessToken = session.getAccessToken();
	    	accessExpir = session.getExpirationDate(); 
	    	
		        Log.i("MAinActivity", "Logged in...");
		        
		        if (Session.getActiveSession().isOpened()) {
		        	if(mDialog==null){
		        		mDialog = new ProgressDialog(getActivity());
		        	}
		        	if(!mDialog.isShowing()){
			        	mDialog.setMessage("Please Wait...");
						mDialog.setCancelable(false);
						mDialog.show();
		        	}
		        	
		        	 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		             StrictMode.setThreadPolicy(policy);
		        	
		             Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
		                 public void onCompleted(GraphUser user, Response response) {
		                	 Log.v("come here"," "+session);
		                	  session.close();
		                	 
		                	  publishFeedDialog();
		                 }
		             });
	    }
	    }
	    
	    else if(session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    else{
	    	if(flag){
	    		Session s = new Session(getActivity());
		        Session.setActiveSession(s);
		        s.openForRead(new Session.OpenRequest(this).setCallback(callback).setPermissions(Arrays.asList("public_profile","email")));
	    	}
	    }*/
	  //  uiHelper.onResume();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
       // uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i("hello", "OnActivityResult...");
    }
	/*private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};*/
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
	

	@Override
	public void onStart() {
		super.onStart();
		flag = false;
		//Session session=Session.getActiveSession();
		/*if(session!=null && session.isOpened()){
			session.close();
		}*/
	//	mGoogleApiClient.connect();
	}
	
	
	
	
	
	
	
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
	

	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
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
	}
	
	
	}
