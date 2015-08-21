package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class CopyOfPriceComparisonItemAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Holder holder;
	Context cxt;
	/*public PriceComparisonItemAdapter(Context context, ArrayList<PriceComparisonBean> value) {
		super(context, R.layout.activity_price_item,value);
		// TODO Auto-generated constructor stub
		inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}*/
	public CopyOfPriceComparisonItemAdapter(Context cxt){
		this.cxt=cxt;
	}
	
	private static class Holder {
		public TextView product_name;
		public TextView product_price;
		public ImageView product_image;
		public Button view_detail;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.product_price_list.size();
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
		inflater=(LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.activity_price_item, parent, false);
			holder=new Holder();
			holder.product_name=(TextView) convertView.findViewById(R.id.product_name);
			holder.product_price=(TextView) convertView.findViewById(R.id.product_price);
			holder.product_image=(ImageView) convertView.findViewById(R.id.product_image);
			holder.view_detail=(Button) convertView.findViewById(R.id.view_detail);
			
			
		}
		else{
			holder=(Holder) convertView.getTag();
		}
		for(int i=0;i<StaticData.category_selected.size();i++){
		if(StaticData.product_price_list.get(position).getSubcategory_name().equals(StaticData.category_selected.get(i).getSubcategory_name())){
		holder.product_name.setText(StaticData.product_price_list.get(position).getProduct_name());
		holder.product_price.setText("Rs. "+StaticData.product_price_list.get(position).getPrice());
		}
		}
		return convertView;
	}

}
