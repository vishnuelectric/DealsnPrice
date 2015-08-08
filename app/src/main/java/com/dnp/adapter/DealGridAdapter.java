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
import com.dnp.bean.DealBean;

public class DealGridAdapter extends ArrayAdapter<DealBean>{
	Context cxt;
	private LayoutInflater mInflater;
	public DealGridAdapter(Context context, ArrayList<DealBean> value) {
		super(context, R.layout.activity_deal_item, value);
		/*this.cxt=context;*/
		//this.cxt=cxt;
		mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		//LayoutInflater infaler = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.activity_deal_grid_item, parent, false);
			//UtilMethod.showToast("Deal Adapter is come",cxt);
			holder = new Holder();
			//UtilMethod.showToast("Come in Deal Adapter", cxt);
			holder.product_detail=(TextView) convertView.findViewById(R.id.product_detail);
			holder.product_amount=(TextView) convertView.findViewById(R.id.product_amount);

			convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		holder.product_amount.setText("Rs.12000");
		//product_detail.setText(StaticData.deal_product_list.get(position).getCategory_name());
		String detail;
		if(getItem(position).getCategory_name().length()>150){
			String s1=getItem(position).getCategory_name().substring(0, 150);
			detail=s1+"...";
		}
		else{
			detail=getItem(position).getCategory_name();
		}
		holder.product_detail.setText(detail);
		AQuery aq = new AQuery(convertView);
		aq.id(R.id.product_image).image(getItem(position).getCategory_image(),
				true, true, 0, R.drawable.ic_launcher, null,
				AQuery.CACHE_DEFAULT,0.0f).visible();
		AQuery aq1 = new AQuery(convertView);
		aq1.id(R.id.store_image).image(getItem(position).getStore_image(),
				true, true, 0, R.drawable.ic_launcher, null,
				AQuery.CACHE_DEFAULT,0.0f).visible();

		return convertView;
	} 

	private static class Holder {
		public TextView product_detail;
		public TextView product_amount;
	}

}
