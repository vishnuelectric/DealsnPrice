package com.dnp.data;


import com.dealnprice.activity.DashboardActivity;
import com.dnp.asynctask.InstallAppTask;
import com.dnp.bean.ApplicationBean;
import com.dnp.fragment.OfferDetailFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Receiver extends BroadcastReceiver{
	Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		try {

			mContext = context;
			Uri dataUri = intent.getData();
			String data = dataUri.toString();

			String packageArray[] = data.split(":");
			String installedPackage = packageArray[1];

			String installedPackageName = dataUri.getEncodedSchemeSpecificPart();

			String deviceName = android.os.Build.MODEL;
			String deviceMan = android.os.Build.MANUFACTURER;
			String m_androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

			String user_id=context.getSharedPreferences("User_login", 1).getString("user_id", null);
			TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id=telephonyManager.getDeviceId();

			if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
				String url1=WebService.INSERT_APP_STATUS_SERVICE+"extension=1&userid="+user_id+"&appid="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null)+"&imei="+device_id+"&status=1&stepcode="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null);

				new InstallAppTask(mContext, url1, new AppListener()).execute();
			}
			else if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){
				String url1=WebService.INSERT_APP_STATUS_SERVICE+"extension=1&userid="+user_id+"&appid="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null)+"&imei="+device_id+"&status=0&stepcode="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null);
				new InstallAppTask(mContext, url1, new AppListener()).execute();
			}
			context.getPackageManager().getApplicationInfo("com.dnp.adapter", 1);


		} catch (Exception e) {
			e.printStackTrace();
			//UtilMethod.showToast("Exception is "+e, mContext);

		}
	}
	public static void createAppPreference(ApplicationBean app, Context context, String userid) {

		try{
			SharedPreferences share = context.getSharedPreferences("APP_INFO", 0);
			Editor edit = share.edit();
			edit.putString("packageName", app.getPackage_name());
			edit.putString("app_id", app.getApp_id());
			edit.putLong("time", System.currentTimeMillis());
			edit.putString("user_id", userid);
			edit.putString("pack_id",app.getPackid());
			edit.commit();

		}catch(Exception e){

			e.printStackTrace();

		}

	}
	public class AppListener{
		public void onSuccess(){
			
			
			if(DashboardActivity.actRef!=null)
			{
				if(OfferDetailFragment.fragRef!=null)
				{
					//On App Install
					OfferDetailFragment.fragRef.setProgress(APP_Constants.INSTALLED);
					UtilMethod.showToast("Now Open the Application!", DashboardActivity.actRef.getApplicationContext());
					OfferDetailFragment.fragRef.startDetection(OfferDetailFragment.targetApp);
				}
			}
			
		}
	}

}
