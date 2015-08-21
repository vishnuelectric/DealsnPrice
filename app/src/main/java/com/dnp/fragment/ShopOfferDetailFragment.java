package com.dnp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;

public class ShopOfferDetailFragment extends Fragment{
	ImageView shop_image;
	TextView shop_name,shop_detail,shop_amount,profit_value;
	String user_id,shop_id;
	int position;
	SharedPreferences shpf;
	Button visit_shop;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_shop_detail, container, false);
		shop_image=(ImageView) view.findViewById(R.id.shop_image);
		shop_name=(TextView) view.findViewById(R.id.shop_name);
		shop_amount=(TextView) view.findViewById(R.id.shop_amount);
		profit_value=(TextView) view.findViewById(R.id.profit_value);
		visit_shop=(Button) view.findViewById(R.id.visit_shop);
		shpf=getActivity().getSharedPreferences("User_name",1);
		position=shpf.getInt("position",0);
		shop_name.setText(StaticData.shop_offer_list.get(position).getShop_name());
		shop_detail.setText(StaticData.shop_offer_list.get(position).getShop_offer_name());
		shop_amount.setText("Rs.300");
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		profit_value.setText("Shop and get Rs.300 Cashback");
		visit_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		return view;
	}
}
