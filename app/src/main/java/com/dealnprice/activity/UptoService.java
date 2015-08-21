package com.dealnprice.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.dnp.asynctask.Pending_amount;
import com.dnp.data.APP_Constants;
import com.dnp.data.DBHELPER;

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

        sqLiteDatabase = dbhelper.getWritableDatabase();
       t= null;
        timerTask = null;

        t = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                        boolean result = checkdatatask(c);
                        boolean result1 = checkdatetask(c);
               /* else if(notificationId == 5)
                {
                    message = "Congratulations!! you have completed your date task";
                    title= "date task completed for"+appName;
                }
                else if(notificationId == 6)
                {
                    message = "Congratulations!! you have completed teh data task";
                    title = "data task completed for"+appName;
                }*/




            }
            public boolean checkdatetask(Cursor cursor){



                ContentValues contentValues = new ContentValues();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                contentValues.put("datetask", "true");
               int rows_affected=  sqLiteDatabase.update("appinfo_upto",contentValues," targetdate < "+"'"+simpleDateFormat.format(new Date()) +"'"+ " AND datetask = 'false'" ,null);
    if(rows_affected > 0) {
        //TODO show notification for date tasks
        builder.setContentTitle("Date task");
        builder.setContentText("Date task finished for one or more apps");
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

        notificationManager.notify(5, n1);
    }

                return true;


            }
            public boolean checkdatatask(Cursor cursor){

                ContentValues contentValues = new ContentValues();
                contentValues.put("datatask", "true");

                int rows_affected= sqLiteDatabase.updateWithOnConflict("appinfo_upto",contentValues," targetdata < dataused AND datatask = 'false'",null,SQLiteDatabase.CONFLICT_IGNORE);
                //TODO show notifs for datatasks
if(rows_affected >0 ) {
    builder.setContentTitle("Data task");

    builder.setContentText("Data task finished for one or more apps");
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


    public void processAmount(int amountType, Object context)
    {
        if(amountType   == APP_Constants.AMOUNT_DETAILS)
        {
           //new AmountTask(uptoService,AMOUNT_DETAIL_URL).execute();
        }
        else if(amountType  ==  APP_Constants.ACCOUNT_DETAILS)
        {

        }
        else if(amountType  ==  APP_Constants.PENDING_AMOUNT_DETAILS)
        {
            new Pending_amount((Activity)context);
        }
        else if(amountType  ==  APP_Constants.MISSING_CASHBACK_DETAILS)
        {

        }
        else if(amountType  ==  APP_Constants.PAYMENT_TRANSFER_DETAILS)
        {

        }
        else if(amountType  ==  APP_Constants.UPDATE_BANK_DETAILS)
        {

        }
        else if(amountType  ==  APP_Constants.REDEEM_HISTORY_DETAILS)
        {

        }
        else if(amountType  ==  APP_Constants.MISSING_CASHBACK_LIST)
        {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uptoService = null;
    }
}
