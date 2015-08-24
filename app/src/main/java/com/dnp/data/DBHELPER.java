package com.dnp.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dnp.bean.NotificationBean;

import java.util.ArrayList;




/**
 * Created by root on 10/8/15.
 */
public class DBHELPER extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = APP_Constants.DATABASE_NAME;
    public static final String TABLE_NAME = "notification_details";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MESSAGE = "message";

    public DBHELPER(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table notification_details " +
                        "(id integer primary key autoincrement, title text,message text,email text,date text)"
        );
        db.execSQL("create table appinfo_upto (userid text  not null, appid text not null, packagename text UNIQUE not null, appname text not null," +
                " installdate text ,targetdate text, datetask text ,datetaskamount integer , dataused integer, targetdata integer, datatask text, datataskamount integer , uid text   )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS notification_details");
        onCreate(db);
    }

    public boolean insertNotification(String title, String message, String email,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("message", message);
        contentValues.put("email", email);
        contentValues.put("date", date);
        db.insert("notification_details", null, contentValues);
        return true;
    }



    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateNotification (String title, String message, String email,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("message", message);
        contentValues.put("email", email);
        contentValues.put("date",date);
        db.update("notification_details", contentValues, "email = ? ", new String[] {email} );
        return true;
    }

    public Integer deleteNotification (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("notification_details",
                "id = ? ",
                new String[] { id });
    }
    public void deleteALL(String email)
    {
        SQLiteDatabase db   =   this.getReadableDatabase();
        Cursor res =  db.rawQuery( "delete * from notification_details where email= "+"'"+email+"'", null );
    }

    public ArrayList<NotificationBean> getNotifications(String email)
    {
        ArrayList<NotificationBean> array_list = new ArrayList<NotificationBean>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from notification_details where email= "+"'"+email+"'", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            NotificationBean notificationBean=  new NotificationBean();
            notificationBean.setNotification_title(res.getString(res.getColumnIndex(COLUMN_TITLE)));
            notificationBean.setNotification_description(res.getString(res.getColumnIndex(COLUMN_MESSAGE)));
            notificationBean.setNotification_time(res.getString(res.getColumnIndex("date")));
            notificationBean.setNotification_id(res.getString(res.getColumnIndex("id")));
            array_list.add(notificationBean);
            res.moveToNext();
        }
        return array_list;
    }
}