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
import com.dnp.data.UniversalImageLoaderHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShopDetailAdapter extends BaseAdapter{
	//LayoutInflater inflater;
	Context cxt;
	DisplayImageOptions opt;
	ImageLoader image_load;
	public ShopDetailAdapter(Context cxt){
		this.cxt=cxt;
		//inflater=LayoutInflater.from(cxt);
	}

	@Override
	public int getCount() {
		return StaticData.shop_offer_detail.size();
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
		LayoutInflater inflater = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.activity_shopdetail_item, parent, false);
		ImageView category_image=(ImageView) view.findViewById(R.id.category_image);
		TextView category_name=(TextView) view.findViewById(R.id.category_name);
		TextView category_value=(TextView) view.findViewById(R.id.cashback);
		if(StaticData.shop_offer_detail.get(position).getCategory_name()!=null){ 
			category_name.setText(StaticData.shop_offer_detail.get(position).getCategory_name());
		}
		if(StaticData.shop_offer_detail.get(position).getCategory_value()!=null){
			category_value.setText(StaticData.shop_offer_detail.get(position).getCategory_value()+" % cashback");
		}
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.shop_offer_detail.get(position).getCategory_image(), category_image, opt);
		return view;
	} 

}
