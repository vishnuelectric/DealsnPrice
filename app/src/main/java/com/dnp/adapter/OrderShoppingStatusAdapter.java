package com.dnp.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class OrderShoppingStatusAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context cxt;
	String type;
	ViewHolder vholder;

	public OrderShoppingStatusAdapter(Context cxt,String datatype1){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
		this.type=datatype1;

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
			convertView=inflater.inflate(R.layout.activity_order_shopping_status_item, parent, false);
			vholder=new ViewHolder();
			vholder.order_date=(TextView) convertView.findViewById(R.id.order_date);
			vholder.retailer=(TextView) convertView.findViewById(R.id.retailer);
			vholder.amount=(TextView) convertView.findViewById(R.id.amount);
			vholder.cashback_amount=(TextView) convertView.findViewById(R.id.cashback_amount);
			vholder.expected_date=(TextView) convertView.findViewById(R.id.expected_date);
			vholder.full_layout=(LinearLayout) convertView.findViewById(R.id.full_layout);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		if(position%2==0){
			vholder.full_layout.setBackgroundColor(cxt.getResources().getColor(R.color.box_bg_color));
		}
		else{
			vholder.full_layout.setBackgroundColor(cxt.getResources().getColor(R.color.footer_box_color));
		}

		vholder.order_date.setText(StaticData.order_filter_list.get(position).getCashback_date());
		vholder.retailer.setText(StaticData.order_filter_list.get(position).getRetailer_name());
		vholder.amount.setText(""+Math.round(Double.parseDouble(StaticData.order_filter_list.get(position).getCashback_order())));
		vholder.cashback_amount.setText(""+Math.round(Double.parseDouble(StaticData.order_filter_list.get(position).getCashback_amount())));

		if(type.equals("1"))
		{
			String str[]=StaticData.order_filter_list.get(position).getCashback_date().split("-");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,  Integer.parseInt(str[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(str[1])); // NOTE: 0 is January, 1 is February, etc.
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str[2]));
			cal.add(Calendar.DAY_OF_MONTH, 105);
					
			Date date = cal.getTime();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			String date1 = formatter.format(date);

			vholder.expected_date.setText(cal.get(Calendar.DATE)
					+ "-"
					+ cal.get(Calendar.MONTH) + 1
					+ "-"
					+ cal.get(Calendar.YEAR));
			vholder.expected_date.setText(date1);
		}
		else if(type.equals("2"))
		{
			vholder.expected_date.setText("Confirmed");
		}
		else if(Integer.parseInt(type)>2)
		{
			vholder.expected_date.setText("Rejected");
		}
		else
		{
			vholder.expected_date.setText("");
		}


		return convertView;
	}

	public class ViewHolder{
		public TextView order_date;
		public TextView retailer;
		public TextView amount;
		public TextView cashback_amount;
		public TextView expected_date;
		public LinearLayout full_layout;
	}

}
