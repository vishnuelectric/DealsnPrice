package com.dnp.fragment;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.Downloader;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;


public class GetCouponCodeFragment extends Fragment{
	View view;
	Button get_code;
	Bundle bundle;
	int position;
	TextView product_detail;
	ImageView share_deal_through_mail,share_offer_through_whatsapp,share_offer_through_face,share_offer_through_gplus,share_offer_through_ticon;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	String purpose,year,month,day;
	HorizontalScrollView horizontal_id;
	LinearLayout footer_layout;
	TextView expiry_date;
	//private UiLifecycleHelper uiHelper;
	LinearLayout see_all_offer_layout;
	
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
		view=inflater.inflate(R.layout.activity_coupon_active, container, false);
		get_code=(Button) view.findViewById(R.id.get_code);
		product_detail=(TextView) view.findViewById(R.id.product_detail);
		share_deal_through_mail=(ImageView) view.findViewById(R.id.share_deal_through_mail);
		share_offer_through_face=(ImageView) view.findViewById(R.id.share_offer_through_face);
		share_offer_through_gplus=(ImageView) view.findViewById(R.id.share_offer_through_gplus);
		share_offer_through_ticon=(ImageView) view.findViewById(R.id.share_offer_through_ticon);
		share_offer_through_whatsapp=(ImageView) view.findViewById(R.id.share_offer_through_whatsapp);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.couponprice_text);
		expiry_date=(TextView) view.findViewById(R.id.expiry_date);
		see_all_offer_layout=(LinearLayout) view.findViewById(R.id.see_all_offer_layout);
		shopearn_text.setText("Shop & Earn");
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		priceearn_text.setText("Price Comparison");
		
		
		
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
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile));
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
		get_code.setOnClickListener(getCodeListener);
	
		bundle=getArguments();
		position=bundle.getInt("position");
		purpose=bundle.getString("purpose");
		AQuery aq = new AQuery(view);
		if(purpose.equals("hot")){
        aq.id(R.id.product_image).image(StaticData.hot_coupon_product_list.get(position).getStore_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
        product_detail.setText(StaticData.hot_coupon_product_list.get(position).getCategory_name());
        year=StaticData.hot_coupon_product_list.get(position).getCoupon_end().substring(0, 4);
        month=StaticData.hot_coupon_product_list.get(position).getCoupon_end().substring(5, 7);
        day=StaticData.hot_coupon_product_list.get(position).getCoupon_end().substring(8, 10);
        expiry_date.setText("Expe. "+day+"/"+month+"/"+year);
        
		}
		else if(purpose.equals("viewed")){
			aq.id(R.id.product_image).image(StaticData.most_viewed_coupon_list.get(position).getStore_image(),
		            true, true, 0, R.drawable.ic_launcher, null,
		            AQuery.CACHE_DEFAULT,0.0f).visible();
		        product_detail.setText(StaticData.most_viewed_coupon_list.get(position).getCategory_name());
		        year=StaticData.most_viewed_coupon_list.get(position).getCoupon_end().substring(0, 4);
		        month=StaticData.most_viewed_coupon_list.get(position).getCoupon_end().substring(5, 7);
		        day=StaticData.most_viewed_coupon_list.get(position).getCoupon_end().substring(8, 10);
		        expiry_date.setText("Expe. "+day+"/"+month+"/"+year);
		}
		share_offer_through_gplus.setOnClickListener(googleplusshareListener);
		share_offer_through_face.setOnClickListener(facebookshareListener);
		share_offer_through_ticon.setOnClickListener(twittershareListener);
		share_offer_through_whatsapp.setOnClickListener(whatsappshareListener);
		see_all_offer_layout.setOnClickListener(seeAllOfferListener);
		return view; 
	}
	OnClickListener getCodeListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OpenGetCodeUrlFragment();
			Bundle b=new Bundle();
			b.putInt("position",position);
			b.putString("purpose",purpose);
			if(purpose.equals("hot")){
				b.putString("shop_url",StaticData.hot_coupon_product_list.get(position).getCoupon_store_url());
			}
			else if(purpose.equals("viewed")){
				b.putString("shop_url",StaticData.most_viewed_coupon_list.get(position).getCoupon_store_url());	
			}
			
			fragment.setArguments(b);
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
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new OfferFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.commit();
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new FavouriteFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.commit();
		}
	};
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new ProfileFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.commit();
		}
	};
	OnClickListener notificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new NotificationFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.commit();
		}
	};
	
	OnClickListener seeAllOfferListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPSeeAllOfferFragment();
			Bundle bnd=new Bundle();
			bnd.putString("purpose","coupon");
			if(purpose.equals("viewed")){
			bnd.putString("store_name", StaticData.most_viewed_coupon_list.get(position).getStore_name());
			}
			else if(purpose.equals("hot")){
				bnd.putString("store_name", StaticData.hot_coupon_product_list.get(position).getStore_name());	
			}
			fragment.setArguments(bnd);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	OnClickListener facebookshareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isAppInstalled("com.facebook.katana")){
				
				if(purpose.equals("viewed") && StaticData.most_viewed_coupon_list.get(position).getStore_image()!=null){
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
	    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy);*/ 
	    					Downloader.DownloadFile(StaticData.most_viewed_coupon_list.get(position).getStore_image(), file);
			//FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
			//.setLink("http://dealsnprice.com/").setPicture(StaticData.most_viewed_coupon_list.get(position).getStore_image()).setCaption(StaticData.most_viewed_deal_list.get(position).getCategory_name()).setDescription("Hello").setFragment(getParentFragment()).build();
			//uiHelper.trackPendingDialogCall(shareDialog.present());
				}
				else if(purpose.equals("hot") && StaticData.hot_coupon_product_list.get(position).getStore_image()!=null){
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
	    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy);*/ 
	    					Downloader.DownloadFile(StaticData.hot_coupon_product_list.get(position).getStore_image(), file);
			//FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
			//.setLink("http://dealsnprice.com/").setPicture(StaticData.hot_coupon_product_list.get(position).getStore_image()).setCaption("").setDescription(StaticData.hot_coupon_product_list.get(position).getStore_name()).setFragment(getParentFragment()).build();
			//uiHelper.trackPendingDialogCall(shareDialog.present());
				}
			}
			else{
				UtilMethod.showToast("Please install Facebook App", getActivity());
			}

		}
	};
	
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
	
	
	OnClickListener twittershareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try{
				PackageManager pm=getActivity().getPackageManager();
			        PackageInfo info=pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
			        //Check if package exists or not. If not then code 
			        //in catch block will be called
			       
				Intent tweetIntent = new Intent(Intent.ACTION_SEND);
				if(purpose.equals("hot")){
					tweetIntent.putExtra(Intent.EXTRA_TEXT, StaticData.hot_coupon_product_list.get(position).getStore_name());
					tweetIntent.setPackage("com.twitter.android");
					tweetIntent.setType("text/plain");
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
	    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy);*/ 
	    			       // UtilMethod.showToast("Image Path is "+StaticData.hot_coupon_product_list.get(position).getStore_image(), getActivity());
	    					Downloader.DownloadFile(StaticData.hot_coupon_product_list.get(position).getStore_image(), file);
	    					tweetIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
					        tweetIntent.setType("image/*");
	    			
				}
				else if(purpose.equals("viewed")){
					tweetIntent.putExtra(Intent.EXTRA_TEXT, StaticData.most_viewed_coupon_list.get(position).getStore_name());
					tweetIntent.setPackage("com.twitter.android");
					tweetIntent.setType("text/plain");
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
	    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy);*/ 
	    					Downloader.DownloadFile(StaticData.most_viewed_coupon_list.get(position).getStore_image(), file);
	    					tweetIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
					        tweetIntent.setType("image/*");
					
				}
				
				getActivity().startActivity(tweetIntent);
				}
				catch(Exception e){
					
					}
				
		}
	};
	
	OnClickListener whatsappshareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			
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
		        if(purpose.equals("hot")){
		            waIntent.putExtra(Intent.EXTRA_TEXT, StaticData.hot_coupon_product_list.get(position).getStore_name());
		        }
		        else if(purpose.equals("viewed")){
		        	waIntent.putExtra(Intent.EXTRA_TEXT, StaticData.most_viewed_coupon_list.get(position).getStore_name());
		        }
		            waIntent.setType("text/plain");
		            
		            if(purpose.equals("viewed") && StaticData.most_viewed_coupon_list.get(position).getStore_image()!=null){
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
		    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    			        StrictMode.setThreadPolicy(policy);*/ 
		    					Downloader.DownloadFile(StaticData.most_viewed_coupon_list.get(position).getStore_image(), file);
		    					 waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
						            waIntent.setType("image/*");
		    			}
		            else if(purpose.equals("hot") && StaticData.hot_coupon_product_list.get(position).getStore_image()!=null){
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
		    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    			        StrictMode.setThreadPolicy(policy);*/ 
		    			   //     UtilMethod.showToast("Image Path is "+StaticData.hot_coupon_product_list.get(position).getStore_image(), getActivity());
		    					Downloader.DownloadFile(StaticData.hot_coupon_product_list.get(position).getStore_image(), file);
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
				UtilMethod.showToast("Please first install whatsapp", getActivity());
			}
			
		}
	};
	
	OnClickListener googleplusshareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent shareIntent = null;
			if(isAppInstalled("com.google.android.apps.plus")){
				if(purpose.equals("viewed")){
					String extStorageDirectory = Environment.getExternalStorageDirectory()
	    					.toString();
	    					File folder = new File(extStorageDirectory, "dnp_images");
	    					folder.mkdir();
	    					File file = new File(folder, "sharing_image.png");
	    					/*File file1=new File(Company_constants.str_candidate_resume);*/
	    					try {
	    						file.createNewFile();
	    					
	    					//Log.v("File Path",Company_constants.str_candidate_resume);
	    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy);*/ 
	    					Downloader.DownloadFile(StaticData.most_viewed_coupon_list.get(position).getStore_image(), file);
	    					 final String photoUri = MediaStore.Images.Media.insertImage(
							         getActivity().getContentResolver(), file.getAbsolutePath(), null, null);
	    					 
	    			
					
				 shareIntent = ShareCompat.IntentBuilder.from(getActivity())
				         .setText(StaticData.most_viewed_deal_list.get(position).getCategory_name())
				         .setType("image/jpeg")
				         .setStream(Uri.parse(photoUri)).getIntent()
				         .setPackage("com.google.android.apps.plus");
	    					}catch(Exception e){
	    						
	    					}
				}
				}
				else if(purpose.equals("hot")){
					
					String extStorageDirectory = Environment.getExternalStorageDirectory()
	    					.toString();
	    					File folder = new File(extStorageDirectory, "dnp_images");
	    					folder.mkdir();
	    					File file = new File(folder, "sharing_image.png");
	    					/*File file1=new File(Company_constants.str_candidate_resume);*/
	    					try {
	    						file.createNewFile();
	    					
	    					//Log.v("File Path",Company_constants.str_candidate_resume);
	    					/*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy);*/ 
	    					Downloader.DownloadFile(StaticData.hot_coupon_product_list.get(position).getStore_image(), file);
	    					 final String photoUri = MediaStore.Images.Media.insertImage(
							         getActivity().getContentResolver(), file.getAbsolutePath(), null, null);
	    					 
	    			
				
					shareIntent = ShareCompat.IntentBuilder.from(getActivity())
					         .setText(StaticData.hot_coupon_product_list.get(position).getStore_name())
					         .setType("image/jpeg")
							 .setStream(Uri.parse(photoUri))
					         .getIntent()
					         .setPackage("com.google.android.apps.plus");
				}catch(Exception e){
					
				}
				if(shareIntent!=null)
				 getActivity().startActivity(shareIntent);
				 /*} catch (IOException e1) {
					e1.printStackTrace();
				}*/
	}
	else{
		UtilMethod.showToast("Please first install google plus", getActivity());
	}

		}
	};
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
      //  uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i("hello", "OnActivityResult...");
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
	                        publishFeedDialog();
	                    }
	                }
	            }).executeAsync();
	        } else if (state.isClosed()) {
	           
	        }
	    }*/

	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//uiHelper.onStart
		//Session session=Session.getActiveSession();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//uiHelper.onResume();
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      //  uiHelper.onSaveInstanceState(outState);
    }
	@Override
    public void onDestroy() {
        super.onDestroy();
      //  uiHelper.onDestroy();
    }
	@Override
	public void onPause() {
	    super.onPause();
	    //uiHelper.onPause();
	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", StaticData.application_list.get(position).getApp_name());
	//	params.putString("caption", "Build great social apps and get more installs.");
	//	params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		params.putString("link", "https://developers.facebook.com/android");
	//	params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

		//WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(getActivity(), Session.getActiveSession(), params)).setOnCompleteListener(
		//new OnCompleteListener() {


	/*	@Override
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

			}).build();*/
			//feedDialog.show();
			}
	
}
