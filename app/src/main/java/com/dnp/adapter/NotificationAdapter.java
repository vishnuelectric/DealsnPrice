package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class NotificationAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder vholder;
	public NotificationAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.notification_list.size();
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
			convertView=inflater.inflate(R.layout.activity_notification_item, parent, false);
			vholder.notification_title=(TextView) convertView.findViewById(R.id.notification_title);
			vholder.notification_detail=(TextView) convertView.findViewById(R.id.notification_description);
			vholder.notification_cancel=(ImageView) convertView.findViewById(R.id.notification_cancel);
			vholder.notification_time=(TextView) convertView.findViewById(R.id.notification_date);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		vholder.notification_title.setText(StaticData.notification_list.get(position).getNotification_title());
		vholder.notification_detail.setText(StaticData.notification_list.get(position).getNotification_description());
		vholder.notification_time.setText(StaticData.notification_list.get(position).getNotification_time());
		
		return convertView;
	}

	public class ViewHolder{
		public TextView notification_title;
		public TextView notification_detail;
		public ImageView notification_cancel;
		public TextView notification_time;
	}
	
}
