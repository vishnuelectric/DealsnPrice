package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class FullContactListAdapter extends BaseAdapter{
	Context cxt;
	ViewHolder vholder;
	LayoutInflater inflater;
	public FullContactListAdapter(Context cxt){
		this.cxt=cxt;
		
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.contact_list.size();
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
			inflater=LayoutInflater.from(cxt);
			convertView=inflater.inflate(R.layout.activity_contact_item, parent, false);
			vholder=new ViewHolder();
			vholder.contact_name=(TextView) convertView.findViewById(R.id.contact_name);
			vholder.contact_number=(TextView) convertView.findViewById(R.id.contact_number);
			//vholder.contact_checkbox=(CheckBox) convertView.findViewById(R.id.contact_checkbox);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		vholder.contact_number.setText(StaticData.contact_list.get(position).getContact_number());
		vholder.contact_name.setText(StaticData.contact_list.get(position).getContact_name());
		/*vholder.contact_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					vholder.contact_checkbox.setChecked(true);
				}
				else{
					vholder.contact_checkbox.setChecked(false);
				}
			}
		});*/
		return convertView;
	}
	
	public class ViewHolder{
		public TextView contact_name;
		public TextView contact_number;
		public CheckBox contact_checkbox;
	}

}
