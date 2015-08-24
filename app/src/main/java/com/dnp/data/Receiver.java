package com.dnp.data;


import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.UptoService;
import com.dnp.asynctask.InstallAppTask;
import com.dnp.bean.ApplicationBean;
import com.dnp.fragment.OfferDetailFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Receiver extends BroadcastReceiver{
	Context mContext;
	public static String appName	=	"";
	SQLiteDatabase sqLiteDatabase;
	DBHELPER dbhelper;
	@Override
	public void onReceive(Context context, Intent intent) {
		dbhelper = new DBHELPER(context);
		sqLiteDatabase = dbhelper.getWritableDatabase();
		if(intent.getAction().equalsIgnoreCase(Intent.ACTION_SHUTDOWN))
		{
			Toast.makeText(context, "shutdown1 ", Toast.LENGTH_LONG).show();
			Cursor cc = sqLiteDatabase.rawQuery("select userid, packagename, dataused,uid from appinfo_upto",null);
			ContentValues contentValues = new ContentValues();
			if(cc.moveToFirst()) {
				do {
					Toast.makeText(context,"shutdown2 ",Toast.LENGTH_LONG).show();

					Long byt = cc.getString(2) == null ? 0: Long.parseLong(cc.getString(2)) + TrafficStats.getUidTxBytes(Integer.parseInt(cc.getString(3))) + TrafficStats.getUidRxBytes(Integer.parseInt(cc.getString(3)));

					Toast.makeText(context,"shutdown "+ byt+ cc.getString(0),Toast.LENGTH_LONG).show();
					contentValues.put("dataused",(int) (byt/1024));

					sqLiteDatabase.update("appinfo_upto", contentValues, "userid = " + "'"+cc.getString(0)+"'" + " AND packagename =" + "'"+cc.getString(1)+"'", null);

				}

				while (cc.moveToNext()) ;
			}
			sqLiteDatabase.close();
			Toast.makeText(context,"shutdown3 ",Toast.LENGTH_LONG).show();


          return ;
		}
		else if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
		{



			Intent i = new Intent(context,UptoService.class);
			context.startService(i);



            return;

		}
		// TODO Auto-generated method stub
		try {

			mContext = context;
			Uri dataUri = intent.getData();
			String data = dataUri.toString();
          Bundle extras = intent.getExtras();
			//String packageArray[] = data.split(":");
			//String installedPackage = packageArray[1];

			String installedPackageName = dataUri.getEncodedSchemeSpecificPart();

			String deviceName = android.os.Build.MODEL;
			String deviceMan = android.os.Build.MANUFACTURER;
			String m_androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

			String user_id=context.getSharedPreferences("User_login", 1).getString("user_id", null);

			String device_id = PreferenceManager.getDefaultSharedPreferences(context).getString("imei",null);
			if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
				String url1=WebService.INSERT_APP_STATUS_SERVICE+"extension=1&userid="+user_id+"&appid="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null)+"&imei="+device_id+"&status=1&stepcode="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null);
                   System.out.println(url1);
				new InstallAppTask(mContext, url1, new AppListener()).execute();

				if(!intent.getBooleanExtra(Intent.EXTRA_REPLACING,false)) {
					ContentValues contentValues = new ContentValues();
                   contentValues.put("uid", intent.getIntExtra(Intent.EXTRA_UID, 1));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

					Date d = new Date(System.currentTimeMillis());



					contentValues.put("installdate", simpleDateFormat.format(d));

					contentValues.put("datetask","false");

					contentValues.put("datatask","false");
					contentValues.put("dataused", 0);
					int rows_changed =sqLiteDatabase.update("appinfo_upto",contentValues,"packagename = "+"'"+ dataUri.getSchemeSpecificPart()+"'",null);
				    // Toast.makeText(context," rows updated package add"+ rows_changed,Toast.LENGTH_LONG).show();
					sqLiteDatabase.close();
				}

			}
			else if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){
				/*String url1=WebService.INSERT_APP_STATUS_SERVICE+"extension=1&userid="+user_id+"&appid="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null)+"&imei="+device_id+"&status=0&stepcode="+context.getSharedPreferences("APP_INFO", 0).getString("app_id", null);
				new InstallAppTask(mContext, url1, new AppListener()).execute();*/
				if(!intent.getBooleanExtra(Intent.EXTRA_REPLACING,false)) {

					int rows_deleted = sqLiteDatabase.delete("appinfo_upto", "packagename = "+"'"+ dataUri.getSchemeSpecificPart()+"'",null);
					Toast.makeText(context," rows deleted package remove"+ rows_deleted,Toast.LENGTH_LONG).show();
					sqLiteDatabase.close();
				}
			}
			//context.getPackageManager().getApplicationInfo("com.dnp.adapter", 1);


		} catch (Exception e) {

			e.printStackTrace();
			//UtilMethod.showToast("Exception is "+e, mContext);

		}

	}
	public static void createAppPreference(ApplicationBean app, Context context, String userid) {

		try{
			SharedPreferences share = context.getSharedPreferences("APP_INFO", 0);
			Editor edit = share.edit();
			appName	=	app.getApp_name();
			edit.putString("packageName", app.getPackage_name());
			edit.putString("app_id", app.getApp_id());
			edit.putLong("time", System.currentTimeMillis());
			edit.putString("user_id", userid);
			edit.putString("task_id",app.getTaskId());
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

					if(DashboardActivity.actRef!=null)
						DashboardActivity.actRef.showNotification(APP_Constants.OPEN_NOTIF_ID, appName);
					OfferDetailFragment.fragRef.setProgress(APP_Constants.INSTALLED);
					UtilMethod.showToast("Now Open the Application!", DashboardActivity.actRef.getApplicationContext());
					OfferDetailFragment.fragRef.stopDetectionTimer();//Stops Installation Detection
					OfferDetailFragment.fragRef.startProcessDetection(OfferDetailFragment.targetApp);//start Process Detection if app opens or not

				}
			}

		}
	}

}
