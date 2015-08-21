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

public class ContactListAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	public ContactListAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.read_contact_adapter.size();
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
		View view=inflater.inflate(R.layout.activity_freesms_item, parent, false);
		TextView contact_name=(TextView) view.findViewById(R.id.contact_name);
		TextView contact_number=(TextView) view.findViewById(R.id.contact_number);
		ImageView contact_image=(ImageView) view.findViewById(R.id.contact_image);
		contact_name.setText(StaticData.read_contact_adapter.get(position).getContact_name());
		contact_number.setText(StaticData.read_contact_adapter.get(position).getPhone_number());
		
		/*contact_image.setImageResource(resId);*/
		return view;
	}

}
