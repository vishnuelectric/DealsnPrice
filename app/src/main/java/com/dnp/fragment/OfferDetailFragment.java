package com.dnp.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.entity.mime.MultipartEntity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;

import com.androidquery.AQuery;
import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.OfferStepAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.ApplicationBean;
import com.dnp.data.APP_Constants;
import com.dnp.data.Downloader;
import com.dnp.data.Helper;
import com.dnp.data.Receiver;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;


public class OfferDetailFragment extends Fragment{

	//Class Level Constants
	public static final String TAG		=	OfferDetailFragment.class.getSimpleName();
	public static OfferDetailFragment fragRef	=	null;
	public static String targetApp		=	"";
	private Timer	appDetector_timer	=	null;
	
	//Class Level Variables
	private TextView progressOneTV		=	null;
	private TextView progressTwoTV		=	null;
	private TextView progressThreeTV	=	null;
	private ImageView progressOneIV		=	null;
	private ImageView progressTwoIV		=	null;
	private Resources mResources		=	null;
	private Activity actRef				=	null;

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
	//	uiHelper = new UiLifecycleHelper(getActivity(), callback);
	//	uiHelper.onCreate(savedInstanceState);
		actRef	=	getActivity();
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
	}
*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG,"Inside OnCreateView");
		View view=inflater.inflate(R.layout.activity_offer_detail, container, false);
		mResources		=	getResources();
		fragRef			=	this;
		progressOneTV	=	(TextView)view.findViewById(R.id.progress_startTV);
		progressTwoTV	=	(TextView)view.findViewById(R.id.progress_installTV);
		progressThreeTV	=	(TextView)view.findViewById(R.id.progress_tryTV);
		progressOneIV	=	(ImageView) view.findViewById(R.id.progress_line_start);
		progressTwoIV	=	(ImageView) view.findViewById(R.id.progress_line_install);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
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
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
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
		if(StaticData.application_list.get(position).isPackage_flag()){
			normal_image.setImageDrawable(getResources().getDrawable(R.drawable.start_2));
			//open_check.setImageDrawable(getResources().getDrawable(R.drawable.active_circle));
			install_layout.setVisibility(View.VISIBLE);
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
			ArrayList<ApplicationBean> offer_list=new ArrayList<ApplicationBean>();
			for(int i=0;i<StaticData.upto_list.size();i++){
				if(StaticData.application_list.get(position).getApp_id().equals(StaticData.upto_list.get(i).getApp_id())){
					ApplicationBean abean=new ApplicationBean();
					abean.setUpto_purpose(StaticData.upto_list.get(i).getUpto_purpose());
					abean.setUpto_amount(StaticData.upto_list.get(i).getUpto_amount());
					abean.setPackid(StaticData.upto_list.get(i).getPackid());
					abean.setStep_status(StaticData.upto_list.get(i).getStep_status());
					abean.setApp_id(StaticData.upto_list.get(i).getApp_id());
					offer_list.add(abean);
				}
			}

			Log.e(TAG,"Using OfferStep Adapter");
			step_list.setAdapter(new OfferStepAdapter(getActivity(), StaticData.application_list.get(position).getApp_id(), offer_list));
			helper.getListViewSize(step_list);
			instruction_header.setText("Instructions to earn Rs."+StaticData.application_list.get(position).getUptotalamount());
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
							Log.e(TAG,"true Case Install");
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
							facebook_share_layout.setOnClickListener(new OnClickListener() {

								@SuppressWarnings("deprecation")
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog1.dismiss();
									if(isAppInstalled("com.facebook.katana")){
										//FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
										//.setLink(StaticData.application_list.get(position).getAppshare()).setCaption("DealsnPrice").setDescription("DealsnPrice").setFragment(getParentFragment()).build();
										//uiHelper.trackPendingDialogCall(shareDialog.present());
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
							google_plus_share_layout.setOnClickListener(new OnClickListener() {

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
										/*} catch (IOException e1) {
				    						e1.printStackTrace();
				    					}*/
									}
									else{
										UtilMethod.showToast("Please install Google Plus", getActivity());
									}






								}
							});

							dialog1.show();
							/*}*/

						}
						else{

							MultipartEntity multipart=new MultipartEntity();
							SharedPreferences shpf=getActivity().getSharedPreferences("User_login",1);

							/*multipart.addPart("user_id", new StringBody(shpf.getString("user_id",null)));
				multipart.addPart("app_id",new StringBody(StaticData.application_list.get(position).getApp_id()));
				multipart.addPart("app_status",new StringBody("2"));
				setProgressDialog();
				new ReadAppTask(getActivity(), multipart, StaticData.application_list.get(position).getApp_url(),new OfferDetailListener(),position).execute();
							 */
							ApplicationBean abean=new ApplicationBean();
							abean.setPackage_name(StaticData.application_list.get(position).getPackage_name());
							abean.setApp_id(StaticData.application_list.get(position).getApp_id());	
							Receiver.createAppPreference(abean, getActivity(), getActivity().getSharedPreferences("User_login",1).getString("user_id",null));
							Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(StaticData.application_list.get(position).getApp_url()));
							getActivity().startActivity(intent);
							setProgress(APP_Constants.STARTED);
							targetApp	=	StaticData.application_list.get(position).getPackage_name().trim();
							//targetApp	=	"com.cpuid.cpu_z"; //testing
							Log.e(TAG,"Target App is "+targetApp);
						}

					}
					catch(Exception e){

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

	//--

	
	

	@Override
	public void onStart() {
		super.onStart();
		//flag = false;
	//	Session session=Session.getActiveSession();
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
		loading_image.setBackgroundResource(R.anim.loading_animation);
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
			progressOneIV.setImageResource(R.drawable.progress_line_grey);
			progressOneTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_grey));
			progressOneTV.setTextColor(mResources.getColor(R.color.progress_text_color_dark));
			progressTwoIV.setImageResource(R.drawable.progress_line_grey);
			progressTwoTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_grey));
			progressTwoTV.setTextColor(mResources.getColor(R.color.progress_text_color_dark));
			progressThreeTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_grey));
			progressThreeTV.setTextColor(mResources.getColor(R.color.progress_text_color_dark));
			break;
		case 1: //Started the install Process
			progressOneIV.setImageResource(R.drawable.progress_line_blue);
			progressOneTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
			progressOneTV.setTextColor(Color.WHITE);
			break;
		case 2: //Installed the app through DnP
			progressTwoIV.setImageResource(R.drawable.progress_line_blue);
			progressTwoTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
			progressTwoTV.setTextColor(Color.WHITE);
			break;
		case 3: //Tried the App / Opened the App
			progressThreeTV.setBackground(mResources.getDrawable(R.drawable.progress_circle_blue));
			progressThreeTV.setTextColor(Color.WHITE);
			break;
		default:
			break;
		}
	}
	
	/**
	 * This method starts a thread that detects if the target app installed has been opened by the user or not
	 * @param targetApp
	 */
	
	public void startDetection(final String targetApp) {
		if(appDetector_timer == null)
			appDetector_timer	=	new Timer();
		else
			stopDetectionTimer();
		
		TimerTask timerTask	=	new TimerTask() {

			@Override
			public void run() {
			Log.e("Detect "," "+targetApp);
				if(isNamedProcessRunning(targetApp))
				{
					actRef.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							stopDetectionTimer();
							setProgress(APP_Constants.TRIED);
						}
					});
				}
				else
				{
					/* TODO
					 * put handling code if user never opens the app or any other scenario
					 */
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
	}
}
