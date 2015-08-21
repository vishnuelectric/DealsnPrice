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

public class MissingCashbackShoppingAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder vholder;
	public MissingCashbackShoppingAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.missing_shop_detail.size();
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
			convertView=inflater.inflate(R.layout.activity_missing_cashback_item, parent, false);
			vholder=new ViewHolder();
			vholder.order_date=(TextView) convertView.findViewById(R.id.order_date);
			vholder.amount=(TextView) convertView.findViewById(R.id.amount);
			vholder.order_id=(TextView) convertView.findViewById(R.id.order_id);
			vholder.full_layout=(LinearLayout) convertView.findViewById(R.id.full_layout);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		vholder.order_date.setText(StaticData.missing_shop_detail.get(position).getMissing_date());
		vholder.order_id.setText(StaticData.missing_shop_detail.get(position).getMissing_order());
		vholder.amount.setText(StaticData.missing_shop_detail.get(position).getMissing_amount());
		if(position%2==0){
			vholder.full_layout.setBackgroundColor(cxt.getResources().getColor(R.color.box_bg_color));
		}
		else{
			vholder.full_layout.setBackgroundColor(cxt.getResources().getColor(R.color.footer_box_color));
		}
		return convertView;
	}
	
	public class ViewHolder{
		public TextView order_date,amount,order_id;
		public LinearLayout full_layout;
	}

}
