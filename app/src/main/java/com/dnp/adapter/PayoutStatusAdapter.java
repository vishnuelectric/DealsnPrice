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

public class PayoutStatusAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder vholder;
	public PayoutStatusAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.my_paid_detail.size();
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
			vholder=new ViewHolder();
			convertView=inflater.inflate(R.layout.activity_payout_item, parent, false);
			vholder.order_date=(TextView) convertView.findViewById(R.id.order_date);
			vholder.amount=(TextView) convertView.findViewById(R.id.payout_amount);
			vholder.mode_of_payment=(TextView) convertView.findViewById(R.id.mode_of_payment);
			vholder.payout_ref=(TextView) convertView.findViewById(R.id.payout_ref);
			vholder.payout_layout=(LinearLayout) convertView.findViewById(R.id.payout_layout);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		if(position%2==0){
			vholder.payout_layout.setBackgroundColor(cxt.getResources().getColor(R.color.box_bg_color));
		}
		else{
			
		}
		vholder.order_date.setText(StaticData.my_paid_detail.get(position).getPaid_amount_date());
		vholder.amount.setText(""+Math.round(Double.parseDouble(StaticData.my_paid_detail.get(position).getPaid_amount_total())));
		//vholder.mode_of_payment.setText(StaticData.my_paid_detail.get(position).getPaid_amount_mop());
		if(StaticData.my_paid_detail.get(position).getPaid_amount_type().equalsIgnoreCase("1"))
		{
			vholder.mode_of_payment.setText("NEFT");
			vholder.payout_ref.setText(StaticData.my_paid_detail.get(position).getPaid_amount__paid_date());
		}
		else if(StaticData.my_paid_detail.get(position).getPaid_amount_type().equalsIgnoreCase("2"))
		{
			vholder.mode_of_payment.setText("Requested");
			vholder.payout_ref.setText("");
		}
		else if(StaticData.my_paid_detail.get(position).getPaid_amount_type().equalsIgnoreCase("3"))
		{
			vholder.mode_of_payment.setText("Advance Pay");
		}
		//vholder.payout_ref.setText(StaticData.my_paid_detail.get(position).getPaid_amount__paid_date());
		//vholder.payout_ref.setText("");
		return convertView;
	}
	
	public class ViewHolder{
		public TextView order_date;
		public TextView amount;
		public TextView mode_of_payment;
		public TextView payout_ref;
		public LinearLayout payout_layout;
	}

}
