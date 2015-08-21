package com.dnp.adapter;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.dealnprice.activity.R;
import com.dnp.data.APP_Constants;
import com.dnp.data.Downloader;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.ShopEarnFragment.ShopEarnListener;

public class ShopListAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ShopEarnListener seListener;
	SharedPreferences shpf;
	Editor edt;
	public ShopListAdapter(Context cxt,ShopEarnListener selistener){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
		this.seListener=selistener;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.shop_offer_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_shopearn_item, parent, false);
		//TextView more_detail=(TextView) view.findViewById(R.id.more_detail);
		ImageView more_detail=(ImageView) view.findViewById(R.id.more_detail);
		ImageView share_icon=(ImageView) view.findViewById(R.id.share_icon);
		TextView shop_now=(TextView) view.findViewById(R.id.shop_now);
		TextView shop_detail=(TextView) view.findViewById(R.id.shop_detail);
		
		/*TextView shop_amount_detail=(TextView) view.findViewById(R.id.app_amount_detail);
		TextView shop_amount=(TextView) view.findViewById(R.id.amount);*/
		shop_detail.setText(StaticData.shop_offer_list.get(position).getShop_offer_name());
		/*shop_amount.setText("Rs. 300");
		shop_amount_detail.setText("Shop & get Rs.300 cashback");*/
		AQuery aq = new AQuery(view);
        aq.id(R.id.shop_image).image(StaticData.shop_offer_list.get(position).getShop_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
		
		more_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				seListener.onVisit(position);
			}
		});
		shop_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(!UtilMethod.isStringNullOrBlank(StaticData.shop_offer_list.get(position).getShop_url())){
				Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(StaticData.shop_offer_list.get(position).getShop_url()));
				cxt.startActivity(intent);
				}*/
				
				//--
				Log.e(" ON CLICK "," Shop Now");
				String storeName	=	StaticData.shop_offer_list.get(position).getShop_name();
				Log.e(" ON CLICK "," Store "+storeName);
				
				if(storeName !=null && !storeName.isEmpty())
				{
					Log.e(" ON CLICK "," Not Null");
					
					if(storeName.equalsIgnoreCase("Flipkart") )
					{
						isNoPlayStore(APP_Constants.FLIPKART);
						return;
					}
					else if(storeName.equalsIgnoreCase("Myntra") || storeName.equalsIgnoreCase("Myntra123"))
					{
						isNoPlayStore(APP_Constants.MYNTRA);
						return;
					}
				}
				//--
				if(!UtilMethod.isStringNullOrBlank(StaticData.shop_offer_list.get(position).getShop_url())){
				seListener.onUrl(position);
				}
			}
		});
		share_icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog=new Dialog(cxt);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.activity_shareearn);
				LinearLayout facebook_share_layout=(LinearLayout) dialog.findViewById(R.id.facebook_share_layout);
				LinearLayout whatsapp_share_layout=(LinearLayout) dialog.findViewById(R.id.whatsapp_share_layout);
				//LinearLayout google_plus_share_layout=(LinearLayout) dialog.findViewById(R.id.google_plus_share_layout);
				LinearLayout twitter_share_layout=(LinearLayout) dialog.findViewById(R.id.twitter_share_layout);
				whatsapp_share_layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						if(isAppInstalled("com.whatsapp")){
						PackageManager pm=cxt.getPackageManager();
						try {

					        Intent waIntent = new Intent(Intent.ACTION_SEND);
					        //waIntent.setType("text/plain");
					       // waIntent.setType("*/*");
					        String text = "Would you like to earn more money?So, Please install this app and follow the instructions and link is "+WebService.APPLICATION_URL;

					        PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
					        //Check if package exists or not. If not then code 
					        //in catch block will be called
					        waIntent.setPackage("com.whatsapp");

					            waIntent.putExtra(Intent.EXTRA_TEXT, StaticData.shop_offer_list.get(position).getShop_offer_name()+"\n"+StaticData.shop_offer_list.get(position).getShop_url());
					            waIntent.setType("text/plain");
					            
					            if(StaticData.shop_offer_list.get(position).getShop_image()!=null){
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
					    					Downloader.DownloadFile(StaticData.shop_offer_list.get(position).getShop_image(), file);
					    					 waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
									            waIntent.setType("image/*");
					    			}
					            
					            
					           
					            cxt.startActivity(Intent.createChooser(waIntent, "Share image via:"));
					           // cxt.startActivity(waIntent);

					   } catch (NameNotFoundException e) {
					       /* Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
					                .show();*/
						  // UtilMethod.showToast("WhatsApp not Installed", cxt);
					   } 
					  }
						else{
							UtilMethod.showToast("Please install Whatsapp", cxt);
						}
					}
				});
				facebook_share_layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						seListener.onShareFacebook(position);
						
						
					}
				});
				twitter_share_layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						// String text = "Would you like to earn more money?So, Please install this app and follow the instructions and link is "+WebService.APPLICATION_URL;
						if(isAppInstalled("com.twitter.android")){
						try{
						PackageManager pm=cxt.getPackageManager();
					        PackageInfo info=pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
					        //Check if package exists or not. If not then code 
					        //in catch block will be called
					       
						Intent tweetIntent = new Intent(Intent.ACTION_SEND);
						tweetIntent.putExtra(Intent.EXTRA_TEXT, StaticData.shop_offer_list.get(position).getShop_offer_name()+"\n"+StaticData.shop_offer_list.get(position).getShop_url());
						
						tweetIntent.setPackage("com.twitter.android");
						tweetIntent.setType("text/plain");
						 if(StaticData.shop_offer_list.get(position).getShop_image()!=null){
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
				    					Downloader.DownloadFile(StaticData.shop_offer_list.get(position).getShop_image(), file);
				    					tweetIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
								        tweetIntent.setType("image/*");
				    			}
				            
						cxt.startActivity(tweetIntent);
						}
						catch(Exception e){
							
						}
						}
						else{
							UtilMethod.showToast("Please install Twitter app", cxt);
						}
					}
				});
			/*	google_plus_share_layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						if(isAppInstalled("com.google.android.apps.plus")){
						if(StaticData.shop_offer_list.get(position).getShop_image()!=null){
			    			String extStorageDirectory = Environment.getExternalStorageDirectory()
			    					.toString();
			    					File folder = new File(extStorageDirectory, "dnp_images");
			    					folder.mkdir();
			    					File file = new File(folder, "sharing_image.png");
			    					File file1=new File(Company_constants.str_candidate_resume);
			    					try {
			    						file.createNewFile();
			    					
			    					//Log.v("File Path",Company_constants.str_candidate_resume);
			    					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    			        StrictMode.setThreadPolicy(policy); 
			    					Downloader.DownloadFile(StaticData.shop_offer_list.get(position).getShop_image(), file);
			    					Log.e("SHOPLISTADAPTER  "," image URL "+StaticData.shop_offer_list.get(position).getShop_image());
			    					final String photoUri = MediaStore.Images.Media.insertImage(
									         cxt.getContentResolver(), file.getAbsolutePath(), null, null);
			    					
			    					
			    					//String photoUri	=	"";
			    					
			    					
			    					final String photoUri	=	"";
			    					if(cxt!=null)
			    					{
			    						ContentResolver cxtResolver	=	cxt.getContentResolver();
			    						if(cxtResolver!=null)
			    						{
			    							Log.e(" ShopListAdapter "," STEP 1");
			    							if(file!=null)
			    							{
			    								String path	=	file.getAbsolutePath();
			    								Log.e(" ShopListAdapter "," STEP 2 FILE not null");
			    								if(path!=null && !path.isEmpty())
			    								{
			    									Log.e(" ShopListAdapter "," STEP 3"+path);
			    								}
			    							}
			    						}
			    					}
			    					 Intent shareIntent = ShareCompat.IntentBuilder.from((Activity) cxt)
									         .setText(StaticData.shop_offer_list.get(position).getShop_offer_name()+"\n"+StaticData.shop_offer_list.get(position).getShop_url())
									         .setType("image/jpeg")
									         .setStream(Uri.parse(photoUri))
									         .getIntent()
									         .setPackage("com.google.android.apps.plus");
			    					 cxt.startActivity(shareIntent);
			    					} catch (IOException e1) {
			    						e1.printStackTrace();
			    					}
			    			}
						
						
						
					}
						else{
							UtilMethod.showToast("Please first install Google Plus", cxt);
						}
						
					}
				});*/
				dialog.show();
				
			}
		});
		
		return view;
	}
	
	/**
	 * This method opens a given store url in chrome if available else in default browser
	 * @param url
	 */
	private void isNoPlayStore(String url)
	{
		Log.e("isNoPlayStore "," "+url);
		try {
		    Intent i = new Intent("android.intent.action.MAIN");
		    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
		    i.addCategory("android.intent.category.LAUNCHER");
		    i.setData(Uri.parse(url));
		    cxt.startActivity(i);
		}
		catch(ActivityNotFoundException e) {
		    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		    cxt.startActivity(i);
		}
	}
	
	private boolean isAppInstalled(String packageName) {
	    PackageManager pm = cxt.getPackageManager();
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
