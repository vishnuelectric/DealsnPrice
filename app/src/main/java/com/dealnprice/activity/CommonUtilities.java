package com.dealnprice.activity;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public final class CommonUtilities {
	
	
	
	
	// give your server registration url here

    // Google project id
//    public static final String SENDER_ID = "252786046190"; 
//    public static final String SENDER_ID = "590605505094";//341417933140
//	 public static final String SENDER_ID = "252786046190";//updated on ghanshyam machines  
	
//	 public static final String SENDER_ID = "18894476902"; //updated on ghanshyam machines 
	
	public static final String SENDER_ID = "768765539654";//"1092003429798";//"752383352792";//updated on akhilesh sir machines 
	
//	public static final String SENDER_ID = "163798936920";//updated on ghanshyam on new machine	

    /**
     * Tag used on log messages.
     */
    static final String TAG = "AndroidHive GCM";

    public static final String  DISPLAY_MESSAGE_ACTION = 
            "universal.gotasker.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
   public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    
   }
   
   public static String getImei(Context context) {
	   TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	   return telephonyManager.getDeviceId();
   
  }
}
