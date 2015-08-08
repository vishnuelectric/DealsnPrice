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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.Downloader;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PriceComparisonShareFragment extends Fragment{
	LinearLayout footer_layout,offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout;
	TextView shopearn_text,inviteearn_text,dealprice_text,couponprice_text;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	HorizontalScrollView horizontal_id;
	RelativeLayout sort_layout;
	DisplayImageOptions opt;
	ImageLoader image_load;
	TextView product_name,product_price,seller,specification,alternative;
	ImageView product_image;
	LinearLayout alert_layout,share_layout;
	TextView share_text;
	//private UiLifecycleHelper uiHelper;
	LinearLayout whatsapp_share_layout,facebook_share_layout,google_plus_share_layout,twitter_share_layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_pc_share, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		inviteearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		couponprice_text=(TextView) view.findViewById(R.id.couponprice_text);
		share_text=(TextView) view.findViewById(R.id.share_text);
		product_name=(TextView) view.findViewById(R.id.product_name);
		product_price=(TextView) view.findViewById(R.id.product_price);
		product_image=(ImageView) view.findViewById(R.id.product_image);
		whatsapp_share_layout=(LinearLayout) view.findViewById(R.id.whatsapp_share_layout);
		facebook_share_layout=(LinearLayout) view.findViewById(R.id.facebook_share_layout);
		google_plus_share_layout=(LinearLayout) view.findViewById(R.id.google_plus_share_layout);
		twitter_share_layout=(LinearLayout) view.findViewById(R.id.twitter_share_layout);
		/*helper.getListViewSize(seller_list);*/
		sort_layout=(RelativeLayout) view.findViewById(R.id.sort_layout);
		alert_layout=(LinearLayout) view.findViewById(R.id.alert_layout);
		share_layout=(LinearLayout) view.findViewById(R.id.share_layout);
		seller=(TextView) view.findViewById(R.id.seller);
		specification=(TextView) view.findViewById(R.id.specification);
		alternative=(TextView) view.findViewById(R.id.alternative);
		shopearn_text.setText("Shop & Earn");
		inviteearn_text.setText("Price Comparison");
		dealprice_text.setText("Deals & Coupons");
		couponprice_text.setText("Refer & Earn");
		share_text.setText("Share & Earn");
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
		alert_layout.setOnClickListener(alertListener);
		whatsapp_share_layout.setOnClickListener(whatsappshareListener);
		facebook_share_layout.setOnClickListener(faceboookshareListener);
		google_plus_share_layout.setOnClickListener(googleplusshareListener);
		twitter_share_layout.setOnClickListener(twittershareListener);
		product_name.setText(StaticData.pc_detail.get(0).getProduct_name());
		product_price.setText(StaticData.pc_detail.get(0).getProduct_mrp());
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.pc_detail.get(0).getImagepath()+StaticData.pc_detail.get(0).getProduct_image(), product_image, opt);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		fm=getFragmentManager();
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		pricecomparison_layout.setOnClickListener(inviteEarnListener);
		referearn_layout.setOnClickListener(couponListener);
		dealcoupon_layout.setOnClickListener(dealpriceListener);
		
		return view;
	}
	OnClickListener alertListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonDropAlertFragment();
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
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
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
	OnClickListener twittershareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub


			// String text = "Would you like to earn more money?So, Please install this app and follow the instructions and link is "+WebService.APPLICATION_URL;
			if(isAppInstalled("com.twitter.android")){
			try{
				PackageManager pm=getActivity().getPackageManager();
		        PackageInfo info=pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
		        //Check if package exists or not. If not then code 
		        //in catch block will be called
		       
		        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
				tweetIntent.putExtra(Intent.EXTRA_TEXT, StaticData.pc_detail.get(0).getProduct_name());
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
    					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    			        StrictMode.setThreadPolicy(policy); 
    			        //UtilMethod.showToast("Image Path is "+StaticData.steal_deal_list.get(position).getCategory_image(), getActivity());
    					Downloader.DownloadFile(StaticData.pc_detail.get(0).getProduct_image(), file);
    					tweetIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
				        tweetIntent.setType("image/*");
				        getActivity().startActivity(tweetIntent);
	
			}
			catch(Exception e){
				
				}
			}
			else{
				UtilMethod.showToast("Please first install Twitter App", getActivity());
			}
		
		}
	};
	OnClickListener faceboookshareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isAppInstalled("com.facebook.katana")){
				
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
	    					Downloader.DownloadFile(StaticData.pc_detail.get(0).getProduct_image(), file);
		/*	FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
			.setLink("http://dealsnprice.com/").setPicture(StaticData.pc_detail.get(0).getProduct_image()).setCaption(StaticData.pc_detail.get(0).getProduct_name()).setDescription("Hello").setFragment(getParentFragment()).build();
			uiHelper.trackPendingDialogCall(shareDialog.present());*/
			}
			else{
				UtilMethod.showToast("Please install Facebook App", getActivity());
			}
		}
	};
	OnClickListener googleplusshareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			Intent shareIntent = null;
			if(isAppInstalled("com.google.android.apps.plus")){
					String extStorageDirectory = Environment.getExternalStorageDirectory()
	    					.toString();
	    					File folder = new File(extStorageDirectory, "dnp_images");
	    					folder.mkdir();
	    					File file = new File(folder, "sharing_image.png");
	    					/*File file1=new File(Company_constants.str_candidate_resume);*/
	    					try {
	    						file.createNewFile();
	    					
	    					//Log.v("File Path",Company_constants.str_candidate_resume);
	    					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    			        StrictMode.setThreadPolicy(policy); 
	    					Downloader.DownloadFile(StaticData.pc_detail.get(0).getProduct_image(), file);
	    					 final String photoUri = MediaStore.Images.Media.insertImage(
							         getActivity().getContentResolver(), file.getAbsolutePath(), null, null);
	    					 
	    			
					
				 shareIntent = ShareCompat.IntentBuilder.from(getActivity())
				         .setText(StaticData.pc_detail.get(0).getProduct_name())
				         .setType("image/jpeg")
				         .setStream(Uri.parse(photoUri)).getIntent()
				         .setPackage("com.google.android.apps.plus");
	    					}catch(Exception e){
	    						
	    					}
				}
	else{
		UtilMethod.showToast("Please first install Google Plus", getActivity());
	}

		
		}
	};
	OnClickListener whatsappshareListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
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
		            waIntent.putExtra(Intent.EXTRA_TEXT, StaticData.pc_detail.get(0).getProduct_name());
		            waIntent.setType("text/plain");
		            
		    			String extStorageDirectory = Environment.getExternalStorageDirectory()
		    					.toString();
		    					File folder = new File(extStorageDirectory, "dnp_images");
		    					folder.mkdir();
		    					File file = new File(folder, "sharing_image.png");

		    					try {
		    						file.createNewFile();
		    					} catch (IOException e1) {
		    						e1.printStackTrace();
		    					}

		    					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    			        StrictMode.setThreadPolicy(policy); 
		    					Downloader.DownloadFile(StaticData.pc_detail.get(0).getProduct_image(), file);
		    					 waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
						            waIntent.setType("image/*");

		            getActivity().startActivity(Intent.createChooser(waIntent, "Share image via:"));
		           // cxt.startActivity(waIntent);

		   } catch (NameNotFoundException e) {
		       
		   } 
		}
			else{
				UtilMethod.showToast("Please first install WhatsApp", getActivity());
			}
		
		}
	};
	
	public void onReplace(Fragment fragment1){
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
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
