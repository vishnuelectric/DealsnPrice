package com.dealnprice.activity;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dnp.data.APP_Constants;
import com.dnp.data.StaticData;
import com.splunk.mint.Mint;


 
public class StylistActivity extends Activity {
	private static String TAG = StylistActivity.class.getName();
    private static long SLEEP_TIME = 1; 
    SharedPreferences shpf;
    String user_id;
	
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		  TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		  String device_id=telephonyManager.getDeviceId();
		  String deviceName = android.os.Build.MODEL;
		  String deviceMan = android.os.Build.MANUFACTURER;
		  String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
		PreferenceManager.getDefaultSharedPreferences(this).edit().putString("devicename",deviceName).
				putString("manufacturer",deviceMan).putString("androidid",m_androidId).putString("imei",device_id).commit();

		shpf=getSharedPreferences("User_login", 1);
		user_id=shpf.getString("user_id", null);
		Mint.initAndStartSession(this, "dfeda966");
		// To free a unnecessay object 
		System.gc();
		
	    IntentLauncher launcher = new IntentLauncher();
        launcher.start();
	}
     
	  private class IntentLauncher extends Thread {
		    @Override
		    /**
		     * Sleep for some time and than start new activity.
		     */
		    public void run() {
		       try {
		          // Sleeping
		          Thread.sleep(SLEEP_TIME*1000);
		       } catch (Exception e) {
		          Log.e(TAG, e.getMessage());
		       }

		       // Start main activity
		      /* Intent intent = new Intent(StylistActivity.this, MainActivity.class);
		       StylistActivity.this.startActivity(intent);
		       StylistActivity.this.finish();*/
		       if(user_id!=null){
		    	   StaticData.product_price_list.clear();


                 SharedPreferences shpf=getSharedPreferences(Constant.pref_name, 1);
                 APP_Constants.USERNAME   =   shpf.getString("user_email","");
		    	Intent intent=new Intent(StylistActivity.this,DashboardActivity.class);
		    	startActivity(intent);
		    	finish();
		       }
		       else{
		       Intent intent=new Intent(StylistActivity.this,LoginActivity.class);
		       startActivity(intent);
		       finish();
		       }
		    }
		 }
     
} 
