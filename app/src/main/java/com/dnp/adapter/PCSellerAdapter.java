package com.dnp.adapter;

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

public class PCSellerAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder holder;
	DisplayImageOptions opt;
	ImageLoader image_load;
	PCSellerListener pcsListener;
	public PCSellerAdapter(Context cxt,PCSellerListener pcslistener){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
		this.pcsListener=pcslistener;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.pc_variant_detail.size();
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
		holder=new ViewHolder();
		holder.store_image=(ImageView) convertView.findViewById(R.id.store_image);
		holder.product_price=(TextView) convertView.findViewById(R.id.product_price);
		holder.buy_now=(Button) convertView.findViewById(R.id.buy_now);
		holder.variants_text=(TextView) convertView.findViewById(R.id.variants_text);
		convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.product_price.setText(StaticData.pc_variant_detail.get(position).getStore_price());
		holder.variants_text.setText("+ "+StaticData.pc_variant_detail.get(position).getVariant_value()+" Variants");
		 opt=UniversalImageLoaderHelper.setImageOptions();
		 image_load=ImageLoader.getInstance();
		 image_load.displayImage(StaticData.pc_variant_detail.get(position).getStore_image(), holder.store_image, opt);
		 holder.buy_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pcsListener.onBuyNow(StaticData.pc_variant_detail.get(position).getStore_url());
			}
		});
		 holder.variants_text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pcsListener.onVariant(StaticData.pc_variant_detail.get(position).getStore_image());
			}
		});
		return convertView;
	}
	
	private static class ViewHolder{
		public ImageView store_image;
		public TextView product_price;
		public TextView variants_text;
		public Button buy_now;
	}

}
