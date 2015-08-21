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
import com.dnp.fragment.PriceComparisonAlternativeFragment.PCAListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PCAlternativeAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	ViewHolder holder;
	DisplayImageOptions opt;
	ImageLoader image_load;
	PCAListener pcalistener;
	public PCAlternativeAdapter(Context cxt,PCAListener pcalistener){
		this.cxt=cxt;
		this.pcalistener=pcalistener;
		inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.pc_alternatives.size();
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
			convertView=inflater.inflate(R.layout.activity_price_item, parent, false);
			holder=new ViewHolder();
			holder.product_name=(TextView) convertView.findViewById(R.id.product_name);
			holder.product_price=(TextView) convertView.findViewById(R.id.product_price);
			holder.product_image=(ImageView) convertView.findViewById(R.id.product_image);
			holder.view_detail=(Button) convertView.findViewById(R.id.view_detail);
			holder.favourite_item=(ImageView) convertView.findViewById(R.id.favourite_item);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		String s_product_name;
		if(StaticData.pc_alternatives.get(position).getProduct_name().length()>50){
			s_product_name=StaticData.pc_alternatives.get(position).getProduct_name().substring(0, 50)+"...";
		}
		else{
			s_product_name=StaticData.pc_alternatives.get(position).getProduct_name();
		}
		holder.product_name.setText(s_product_name);
		holder.product_price.setText("Rs. "+StaticData.pc_alternatives.get(position).getProduct_mrp());
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(StaticData.pc_detail.get(0).getImagepath()+StaticData.pc_alternatives.get(position).getProduct_image(), holder.product_image, opt);
		
		if(StaticData.pc_alternatives.get(position).getFav_status()==0){
			holder.favourite_item.setImageDrawable(cxt.getResources().getDrawable(R.drawable.favorite_unlike));
		}
		else{
			holder.favourite_item.setImageDrawable(cxt.getResources().getDrawable(R.drawable.like));
		}
		
		holder.favourite_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(StaticData.pc_alternatives.get(position).getFav_status()==0){
				pcalistener.onAddFavouriteProduct(StaticData.pc_alternatives.get(position).getProduct_id(),position);
				}
			}
		});
		holder.view_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pcalistener.onSuccessViewDetail(StaticData.pc_alternatives.get(position).getProduct_id());
			}
		});
		return convertView;
	}
	public class ViewHolder{
		ImageView product_image;
		TextView product_name;
		TextView product_price;
		Button view_detail;
		ImageView favourite_item;
	}

}
