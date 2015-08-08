package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class OrderStatusAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context cxt;
	ViewHolder vholder;
	public OrderStatusAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.order_filter_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=inflater.inflate(R.layout.activity_order_status_item, parent, false);
			vholder=new ViewHolder();
			vholder.order_date=(TextView) convertView.findViewById(R.id.order_date);
			vholder.offer_name=(TextView) convertView.findViewById(R.id.offer_name);
			vholder.amount=(TextView) convertView.findViewById(R.id.order_amount);
			vholder.full_layout=(LinearLayout) convertView.findViewById(R.id.full_layout);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		vholder.order_date.setText(StaticData.order_filter_list.get(position).getConversion_date());
		vholder.offer_name.setText(StaticData.order_filter_list.get(position).getOffername());
		vholder.amount.setText(""+Math.round(Double.parseDouble(""+StaticData.order_filter_list.get(position).getConversion_amount_user())));
		if(position%2==0){
			vholder.full_layout.setBackgroundColor(cxt.getResources().getColor(R.color.box_bg_color));
		}
		else{
			vholder.full_layout.setBackgroundColor(cxt.getResources().getColor(R.color.footer_box_color));
		}
		return convertView;
	}

	public class ViewHolder{
		public TextView order_date,offer_name,amount;
		public LinearLayout full_layout;
	}
	
}
