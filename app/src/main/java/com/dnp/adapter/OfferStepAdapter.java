package com.dnp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.bean.ApplicationBean;

public class OfferStepAdapter extends BaseAdapter{
	Context cxt;
	String app_id;
	LayoutInflater inflater;
	ViewHolder vholder;
	ArrayList<ApplicationBean> list;
	public OfferStepAdapter(Context cxt,String app_id,ArrayList<ApplicationBean> list){
		this.cxt=cxt;
		this.list=list;
		this.app_id=app_id;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			inflater=LayoutInflater.from(cxt);
			convertView=inflater.inflate(R.layout.activity_offerstatus_item, parent, false);
			vholder=new ViewHolder();
			vholder.step_offer=(TextView) convertView.findViewById(R.id.step_offer);
			vholder.step_offer_image=(ImageView) convertView.findViewById(R.id.step_offer_image);
			vholder.step_offer_rate=(TextView) convertView.findViewById(R.id.step_offer_rate);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		
		vholder.step_offer.setText(list.get(position).getUpto_purpose());
		Log.e("getView() "," getView() :: "+list.get(position).getUpto_purpose());
		vholder.step_offer_rate.setText("Rs."+list.get(position).getUpto_amount());
		if(list.get(position).getStep_status()==0){
			vholder.step_offer_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.inactive_circle));
		}
		else if(list.get(position).getStep_status()==1){
			vholder.step_offer_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.active_circle));
		}
		
		return convertView;
	}
	
	public class ViewHolder{
		public TextView step_offer;
		public ImageView step_offer_image;
		public TextView step_offer_rate;
	}

}
