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
import com.dnp.data.UtilMethod;

public class PCSpecificationAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder holder;
	public PCSpecificationAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.pc_specification.size();
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
			convertView=inflater.inflate(R.layout.activity_specification_item, parent, false);
			holder=new ViewHolder();
			holder.general_layout=(LinearLayout) convertView.findViewById(R.id.general_layout);
			holder.normal_layout=(LinearLayout) convertView.findViewById(R.id.normal_layout);
			holder.general_text=(TextView) convertView.findViewById(R.id.general_text);
			holder.left=(TextView) convertView.findViewById(R.id.left);
			holder.right=(TextView) convertView.findViewById(R.id.right);
			convertView.setTag(holder);
			
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		if(UtilMethod.isStringNullOrBlank(StaticData.pc_specification.get(position).getSpecification_right())){
			holder.general_layout.setVisibility(View.VISIBLE);
			holder.normal_layout.setVisibility(View.GONE);
			holder.general_text.setText(StaticData.pc_specification.get(position).getSpecification_left());
		}
		else{
			holder.general_layout.setVisibility(View.GONE);
			holder.normal_layout.setVisibility(View.VISIBLE);
			holder.left.setText(StaticData.pc_specification.get(position).getSpecification_left());
			holder.right.setText(StaticData.pc_specification.get(position).getSpecification_right());
		}
		
		return convertView;
	}

	public class ViewHolder{
		TextView left;
		TextView right;
		TextView general_text;
		LinearLayout general_layout;
		LinearLayout normal_layout;
	}
	
}
