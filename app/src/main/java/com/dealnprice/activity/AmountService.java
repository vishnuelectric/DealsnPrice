package com.dealnprice.activity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.dnp.asynctask.AmountTask;
import com.dnp.data.WebService;

public class AmountService extends Service{
	String url;
	String user_id;
	SharedPreferences shpf;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		shpf=getSharedPreferences("User_login",1);
		user_id=shpf.getString("user_id", null);
		url=WebService.AMOUNT_DETAIL_SERVICE+user_id+"&extension=1";
		
		new AmountTask(AmountService.this,url).execute();
		
		return null;
		//Status code
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		shpf=getSharedPreferences("User_login",1);
		user_id=shpf.getString("user_id", null);
		url=WebService.AMOUNT_DETAIL_SERVICE+user_id+"&extension=1";
				
		
		new AmountTask(AmountService.this,url).execute();
		
		return super.onStartCommand(intent, flags, startId);
	}

}
