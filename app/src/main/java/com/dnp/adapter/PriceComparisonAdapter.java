package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;
import com.dnp.fragment.HorizontalListView;


public class PriceComparisonAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context cxt;
	HorizontalListView price_list;
	
	public PriceComparisonAdapter(Context cxt){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.category_selected.size();
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
		View view=inflater.inflate(R.layout.activity_dealcoupon_item, parent, false);
		price_list=(HorizontalListView) view.findViewById(R.id.horizontal_list);
		TextView category_text=(TextView) view.findViewById(R.id.category_name);
		category_text.setText(StaticData.category_selected.get(position).getSubcategory_name());
		//price_list.setAdapter(new PriceComparisonItemAdapter(cxt,StaticData.product_price_list));
		/*price_list.setAdapter(new );*/
		return view;
	}

}
