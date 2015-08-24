package com.dealnprice.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.dnp.asynctask.UptoTask;
import com.dnp.data.APP_Constants;
import com.dnp.data.DBHELPER;
import com.dnp.data.UtilMethod;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class UptoService extends Service {
    DBHELPER dbhelper ;
    SQLiteDatabase sqLiteDatabase;
    Timer t ;
    TimerTask timerTask;
    Notification.Builder builder;
    NotificationManager notificationManager;
    static UptoService uptoService = null;
    SharedPreferences sharedPreferences;
    String appname;

    Cursor c;
    public UptoService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        uptoService = this;
builder = new Notification.Builder(this);
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       // Toast.makeText(this,"f " + dbhelper  + " "+ sqLiteDatabase ,Toast.LENGTH_LONG).show();
        if( sqLiteDatabase != null)
        {


        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbhelper = new DBHELPER(this);
sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sqLiteDatabase = dbhelper.getWritableDatabase();
       t= null;
        timerTask = null;

        t = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                   sqLiteDatabase = dbhelper.getWritableDatabase();
                        boolean result = checkdatatask(c);
                        boolean result1 = checkdatetask(c);

                   sqLiteDatabase.close();



            }
            public boolean checkdatetask(Cursor cursor){



                ContentValues contentValues = new ContentValues();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                contentValues.put("datetask", "true");

                int rows_affected=  sqLiteDatabase.update("appinfo_upto", contentValues, " targetdate < " + "'" + simpleDateFormat.format(new Date()) + "'" + " AND datetask = 'false'", null);
                Cursor c = sqLiteDatabase.rawQuery("select appid,userid,installdate,appname from appinfo_upto where targetdate > "+"'"+simpleDateFormat.format(new Date()) +"'", null);

                try {
                    if (rows_affected > 0 && c.moveToFirst()) {
                        do {
                            MultipartEntity multipartEntity = new MultipartEntity();
                            multipartEntity.addPart("appid", new StringBody(c.getString(0)));
                            multipartEntity.addPart("userid", new StringBody(c.getString(1)));
                            multipartEntity.addPart("data", new StringBody(c.getString(2)));
                            multipartEntity.addPart("taskid", new StringBody("222"));
                            multipartEntity.addPart("imei", new StringBody(sharedPreferences.getString("imei",null)));

                            appname = c.getString(3);
                            new UptoTask(uptoService, multipartEntity, new UptoListener("date"),appname).execute();

                        }
                        while (c.moveToNext());


                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



                return true;


            }
            public boolean checkdatatask(Cursor cursor){

                ContentValues contentValues = new ContentValues();
                contentValues.put("datatask", "true");

                int rows_affected= sqLiteDatabase.updateWithOnConflict("appinfo_upto",contentValues," targetdata < dataused AND datatask = 'false'",null,SQLiteDatabase.CONFLICT_IGNORE);
                //TODO show notifs for datatasks
                Cursor c = sqLiteDatabase.rawQuery("select appid,userid,dataused,appname from appinfo_upto where targetdata > dataused",null);
try {

    if (rows_affected > 0 && c.moveToFirst()) {

        do {
            MultipartEntity multipartEntity = new MultipartEntity();
            multipartEntity.addPart("appid", new StringBody(c.getString(0)));
            multipartEntity.addPart("userid", new StringBody(c.getString(1)));
            multipartEntity.addPart("data", new StringBody(c.getString(2)));
            multipartEntity.addPart("taskid", new StringBody("333"));
            multipartEntity.addPart("imei", new StringBody(sharedPreferences.getString("imei",null)));
           appname = c.getString(2);
            new UptoTask(uptoService, multipartEntity, new UptoListener("data"),appname).execute();
        }
        while (c.moveToNext());


    }
}
catch (Exception e)
{
    e.printStackTrace();
}
                return true;
            }
        };
        if(t != null && timerTask != null)
        {
            t.schedule(timerTask,0,60*1000);
        }
        //Toast.makeText(this,"f " + dbhelper  + " "+ sqLiteDatabase ,Toast.LENGTH_LONG).show();


        return START_STICKY;
    }


    public void processAmount(int amountType, Object context) {
      /*  if (amountType == APP_Constants.AMOUNT_DETAILS) {
            // new AmountTask(amntServiceRef,AMOUNT_DETAIL_URL).execute();
        } else if (amountType == APP_Constants.ACCOUNT_DETAILS) {
            new GetBasicAccountDetailsTask(DashboardActivity.actRef).execute();
            if (APP_Constants.IS_AMOUNT_CHANGED) {
                builder.setContentTitle(APP_Constants.REWARD_TITLE);
                builder.setContentText(APP_Constants.REWARD_MESSAGE);
                builder.setSmallIcon(R.drawable.app_icon);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon));
                Notification n1 = builder.build();
                notificationManager.notify(APP_Constants.MONEY_NOTIF_ID, n1);
                APP_Constants.IS_AMOUNT_CHANGED = false;
               // UtilMethod.showToast(" Amount Changed", DashboardActivity.actRef);
            }
            // UtilMethod.showToast(" Success Amount Service Called",DashboardActivity.actRef);
        } else if (amountType == APP_Constants.PENDING_AMOUNT_DETAILS) {
            new Pending_amount(DashboardActivity.actRef);
        } else if (amountType == APP_Constants.MISSING_CASHBACK_DETAILS) {

        } else if (amountType == APP_Constants.PAYMENT_TRANSFER_DETAILS) {

        } else if (amountType == APP_Constants.UPDATE_BANK_DETAILS) {

        } else if (amountType == APP_Constants.REDEEM_HISTORY_DETAILS) {

        } else if (amountType == APP_Constants.MISSING_CASHBACK_LIST) {

        }
*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uptoService = null;
    }
    public class UptoListener {
        String type;
        public UptoListener(String type){
            this.type= type;
        }
        public void onSuccess(String msg,String app){

if(type.equalsIgnoreCase("date") && msg.equalsIgnoreCase("success"))
{
    builder.setContentTitle("Date task");
    builder.setContentText("Date task finished for " + app + " apps");
    builder.setSmallIcon(R.drawable.app_icon);
    Intent resultIntent = new Intent(uptoService, DashboardActivity.class);
    PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
                    uptoService,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
    builder.setContentIntent(resultPendingIntent);
    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon));
    Notification n1 = builder.build();
    dbhelper.insertNotification(APP_Constants.DAYS_TITLE,APP_Constants.DAYS_MESSAGE,"", UtilMethod.getCurrentDate());

    notificationManager.notify(5, n1);
}
            else if(type.equalsIgnoreCase("data") && msg.equalsIgnoreCase("success")){
    builder.setContentTitle("Data task");

    builder.setContentText("Data task finished for " + app + " apps");
    builder.setSmallIcon(R.drawable.app_icon);
    Intent resultIntent = new Intent(uptoService, DashboardActivity.class);
    PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
                    uptoService,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
    builder.setContentIntent(resultPendingIntent);
    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon));
    Notification n1 = builder.build();

    notificationManager.notify(6, n1);
}

            if(msg.equalsIgnoreCase("error") && type.equalsIgnoreCase("data"))
            { ContentValues content = new ContentValues();
                content.put("datatask","false");
                sqLiteDatabase.update("appinfo_upto",content,"appname ="+ app,null);
            }
            else if(msg.equalsIgnoreCase("error") && type.equalsIgnoreCase("date"))
            {
                ContentValues content = new ContentValues();
                content.put("datetask","false");
                sqLiteDatabase.update("appinfo_upto",content,"appname ="+ app,null);

            }
        }
        public void onError(String msg){

        }
    }

}
