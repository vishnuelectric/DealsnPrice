package com.dnp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.fragment.FavouriteFragment.FavouriteListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FavouriteAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	DisplayImageOptions opt;
	ImageLoader image_load;
	FavouriteListener fListener;
	public FavouriteAdapter(Context cxt,FavouriteListener flistener){
		this.cxt=cxt;
		this.fListener=flistener;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.favourite_list.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_favourite_item, parent, false);
		ImageView favourite_image=(ImageView) view.findViewById(R.id.product_image);
		TextView product_name=(TextView) view.findViewById(R.id.product_name);
		TextView best_price=(TextView) view.findViewById(R.id.best_price);
		TextView product_date=(TextView) view.findViewById(R.id.product_date);
		TextView set_alert=(TextView) view.findViewById(R.id.set_alert);
		ImageView favourite=(ImageView) view.findViewById(R.id.favourite);
		product_name.setText(StaticData.favourite_list.get(position).getProduct_name());
		best_price.setText("Best Price: "+StaticData.favourite_list.get(position).getProduct_price());
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.favourite_list.get(position).getProduct_image(), favourite_image, opt);
		set_alert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fListener.onSetAlert(position);
			}
		});
		favourite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fListener.onRemoveProduct(position);
			}
		});
		return view;
	}

}
