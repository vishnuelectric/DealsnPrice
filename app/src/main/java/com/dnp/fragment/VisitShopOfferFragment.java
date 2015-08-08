package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.InsertShoppingTripTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;

public class VisitShopOfferFragment extends Fragment{
	SharedPreferences shpf;
	String user_id,shop_id;
	int position;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_offer_last_layout, container, false);
		shpf=getActivity().getSharedPreferences("User_name",1);
		position=shpf.getInt("position", 0);
		user_id=shpf.getString("user_id",null);
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		shop_id=StaticData.shop_offer_list.get(position).getShop_id();
		try{
		MultipartEntity multipart=new MultipartEntity();
		multipart.addPart("user_id",new StringBody(user_id));
		multipart.addPart("shop_id",new StringBody(shop_id));
		new InsertShoppingTripTask(getActivity(), multipart,new VisitShopListener()).execute();
		}
		catch(Exception e){
			
		}
		
		return view;
	}
	
	public class VisitShopListener{
		public void onSuccess(){
			if(UtilMethod.isNetworkAvailable(getActivity())){
				Uri uri = Uri.parse(StaticData.shop_offer_list.get(position).getShop_url());
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
			else{
				UtilMethod.showNetworkError(getActivity());
			}
		}
		public void onError(String msg){
			if(msg.equals("slow")){
				UtilMethod.showNetworkError(getActivity());
				
			}
		}
	}
}
