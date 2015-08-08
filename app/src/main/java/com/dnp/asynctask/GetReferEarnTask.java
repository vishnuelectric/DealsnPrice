package com.dnp.asynctask;

import org.json.JSONObject;

import com.dnp.bean.ReferEarnBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.fragment.ReferEarnFragment.ReferEarnListener;

import android.content.Context;
import android.os.AsyncTask;

public class GetReferEarnTask extends AsyncTask<String, Void, String>{
	String url;
	Context cxt;
	ReferEarnListener reListener;
	public GetReferEarnTask(Context cxt,String url,ReferEarnListener relistener){
		this.cxt=cxt;
		this.url=url;
		this.reListener=relistener;
		
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(url);
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
			String status=jobj.getString("message");
			if(status.equals("1")){
				StaticData.referearn_list.clear();
				ReferEarnBean rebean=new ReferEarnBean();
				rebean.setMaxamount(jobj.getInt("amount"));
				JSONObject per_object=jobj.getJSONObject("first");
				rebean.setPersonal_offer(per_object.getInt("offer"));
				rebean.setPersonal_amount(per_object.getString("amount"));
				rebean.setPersonal_totalamount(per_object.getInt("totalamount"));
				JSONObject friend_object=jobj.getJSONObject("second");
				rebean.setFriend_count(friend_object.getString("friend"));
				rebean.setFriend_offer_count(friend_object.getInt("offer"));
				rebean.setFriend_amount(friend_object.getString("amount"));
				rebean.setFriend_totalamount(friend_object.getInt("totalamount"));
				JSONObject morefriend_object=jobj.getJSONObject("third");
				rebean.setFriend_friend_count(morefriend_object.getString("friend"));
				rebean.setFriend_friendcount(morefriend_object.getString("friendcount"));
				rebean.setFriend_offer_count(morefriend_object.getInt("offer"));
				rebean.setFriend_friend_amount(morefriend_object.getString("amount"));
				rebean.setFriend_friend_totalamount(morefriend_object.getInt("totalamount"));
				rebean.setReferlink(jobj.getString("referlink"));
				StaticData.referearn_list.add(rebean);
				
			}
				reListener.onSuccess();
			
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
