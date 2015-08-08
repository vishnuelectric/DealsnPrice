package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class FAQAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder vholder;
	public FAQAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.faq_list.size();
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
			convertView=inflater.inflate(R.layout.activity_faq_item, parent, false);
			vholder=new ViewHolder();
			vholder.add_image=(ImageView) convertView.findViewById(R.id.add_image1);
			vholder.minus_image=(ImageView) convertView.findViewById(R.id.minus_image1);
			vholder.answer=(TextView) convertView.findViewById(R.id.answer1);
			vholder.answer_layout=(LinearLayout) convertView.findViewById(R.id.answer_layout1);
			vholder.question=(TextView) convertView.findViewById(R.id.header1);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		vholder.question.setText(StaticData.faq_list.get(position).getQuestion());
		vholder.answer.setText(StaticData.faq_list.get(position).getAnswer());
		vholder.add_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vholder.minus_image.setVisibility(View.VISIBLE);
				vholder.add_image.setVisibility(View.GONE);
				vholder.answer_layout.setVisibility(View.VISIBLE);
			}
		});
		vholder.minus_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vholder.add_image.setVisibility(View.VISIBLE);
				vholder.minus_image.setVisibility(View.GONE);
				vholder.answer_layout.setVisibility(View.GONE);
			}
		});
		
		return convertView;
	}

	public class ViewHolder{
		public TextView question;
		public TextView answer;
		public LinearLayout answer_layout;
		public ImageView add_image;
		public ImageView minus_image;
	}
	
}
