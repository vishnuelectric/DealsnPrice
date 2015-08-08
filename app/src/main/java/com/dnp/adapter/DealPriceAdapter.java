package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.dealnprice.activity.R;
import com.dnp.data.StaticData;

public class DealPriceAdapter extends BaseAdapter{
	/*View view;
	LayoutInflater inflater;*/
	Context cxt;

	public DealPriceAdapter(Context cxt){
		this.cxt=cxt;
		//inflater=LayoutInflater.from(cxt);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.deal_product_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return StaticData.deal_product_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//view = convertView;
		//view=inflater.inflate(R.layout.activity_deal_item, parent, false);
		
		LayoutInflater infaler = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = infaler.inflate(R.layout.activity_deal_item, parent, false);
		

		//UtilMethod.showToast("Come in Deal Adapter", cxt);
		TextView product_detail=(TextView) view.findViewById(R.id.product_detail);
		TextView product_amount=(TextView) view.findViewById(R.id.product_amount);
		product_amount.setText("Rs.12000");
		product_detail.setText(StaticData.deal_product_list.get(position).getCategory_name());
		AQuery aq = new AQuery(view);
        aq.id(R.id.product_image).image(StaticData.deal_product_list.get(position).getCategory_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
		AQuery aq1 = new AQuery(view);
        aq1.id(R.id.store_image).image(StaticData.deal_product_list.get(position).getStore_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();


		return view;
	}

}
