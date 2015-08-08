package com.dnp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.fragment.PriceComparisonSellerFragment.PCSellerListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PCSellerVariantAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder view_holder;
	ImageLoader image_load;
	DisplayImageOptions opt;
	PCSellerListener pcsListener;
	Dialog dialog;
	public PCSellerVariantAdapter(Context cxt,Dialog dialog,PCSellerListener pcslistener){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
		this.pcsListener=pcslistener;
		this.dialog=dialog;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.pc_full_variant.size();
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
		if(convertView==null){
			convertView=inflater.inflate(R.layout.activity_price_seller_item, parent, false);
			view_holder=new ViewHolder();
			view_holder.buy_now=(Button) convertView.findViewById(R.id.buy_now);
			view_holder.product_price=(TextView) convertView.findViewById(R.id.product_price);
			view_holder.store_image=(ImageView) convertView.findViewById(R.id.store_image);
			view_holder.variants_text=(TextView) convertView.findViewById(R.id.variants_text);
			convertView.setTag(view_holder);
		}
		else{
			view_holder=(ViewHolder) convertView.getTag();
			
		}
		view_holder.product_price.setText(StaticData.pc_full_variant.get(position).getStore_price());
		view_holder.variants_text.setVisibility(View.GONE);
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.pc_full_variant.get(position).getStore_image(), view_holder.store_image, opt);
		view_holder.buy_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pcsListener.onDialogBuyNow(dialog,StaticData.pc_full_variant.get(position).getStore_url());
			}
		});
		return convertView;
	}

	public class ViewHolder{
		private TextView product_price;
		private ImageView store_image;
		private Button buy_now;
		private TextView variants_text;
	}
}
