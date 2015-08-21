package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;
import com.dnp.fragment.FavouriteFragment.FavouriteListener;

import android.content.Context;
import android.os.AsyncTask;

public class RemoveFavouriteTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	FavouriteListener fListener;
	int position;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
    sqlHelper sHelper;
	public RemoveFavouriteTask(Context cxt,MultipartEntity multipart,FavouriteListener flistener,int position){
		this.cxt=cxt;
		this.multipart=multipart;
		this.fListener=flistener;
		this.position=position;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.REMOVE_FAVOURITE_SERVICE, multipart);
		return response;
		}
		catch(Exception e){
			
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null){
			try{
			JSONObject jobj=new JSONObject(result);
			String message=jobj.getString("message");
			if(message.equals("1")){
				sHelper=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
				sHelper.update_favstatus(StaticData.favourite_list.get(position).getProduct_id(), 0);
				
				fListener.onSuccessRemoveProduct(position);
			}
			
			}
			catch(Exception e){
				
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
