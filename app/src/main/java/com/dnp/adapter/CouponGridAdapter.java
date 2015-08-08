package com.dnp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.dealnprice.activity.R;
import com.dnp.bean.CouponBean;

public class CouponGridAdapter extends ArrayAdapter<CouponBean>{
	LayoutInflater inflater;
	View view;
	Context cxt;
	public CouponGridAdapter(Context context, ArrayList<CouponBean> resource) {
		super(context, R.layout.activity_coupon_grid_item,resource);
		// TODO Auto-generated constructor stub
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		//LayoutInflater infaler = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
		convertView = inflater.inflate(R.layout.activity_coupon_grid_item, parent, false);
		//UtilMethod.showToast("Deal Adapter is come",cxt);
		holder = new Holder();
		//UtilMethod.showToast("Come in Deal Adapter", cxt);
		holder.product_detail=(TextView) convertView.findViewById(R.id.coupon_detail);
		
		convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		//product_detail.setText(StaticData.deal_product_list.get(position).getCategory_name());
		/*String detail;
		if(getItem(position).getCategory_name().length()>100){
			String s1=getItem(position).getCategory_name().substring(0, 100);
			detail=s1+"...";
		}
		else{
			detail=getItem(position).getCategory_name();
		}*/
		holder.product_detail.setText(getItem(position).getCategory_name());
		AQuery aq = new AQuery(convertView);
        aq.id(R.id.store_image).image(getItem(position).getStore_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
		
		return convertView;
	} 
	
	private static class Holder {
		public TextView product_detail;
	}
}
