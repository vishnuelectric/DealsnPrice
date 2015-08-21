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
import com.dnp.fragment.PriceComparisonGridFragment.PriceComparisonGridListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AllPCGridAdapter extends BaseAdapter{
	Context cxt;
	PriceComparisonGridListener pcgListener;
	LayoutInflater inflater;
	ViewHolder view_holder;
	DisplayImageOptions opt;
	ImageLoader image_load;
	public AllPCGridAdapter(Context cxt,PriceComparisonGridListener pcglistener){
		this.cxt=cxt;
		this.pcgListener=pcglistener;
		inflater=LayoutInflater.from(cxt);
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
		view_holder=new ViewHolder();
		convertView=inflater.inflate(R.layout.activity_price_item, parent, false);
		view_holder.product_name=(TextView) convertView.findViewById(R.id.product_name);
		view_holder.product_image=(ImageView) convertView.findViewById(R.id.product_image);
		view_holder.product_price=(TextView) convertView.findViewById(R.id.product_price);
		view_holder.view_detail=(Button) convertView.findViewById(R.id.view_detail);
		view_holder.favourite_item=(ImageView) convertView.findViewById(R.id.favourite_item);
		convertView.setTag(view_holder);
		}
		else{
			view_holder=(ViewHolder) convertView.getTag();
		}
		String s_product_name;
		if(StaticData.product_price_list.get(position).getProduct_name().length()>50){
			s_product_name=StaticData.product_price_list.get(position).getProduct_name().substring(0, 50)+"...";
		}
		else{
			s_product_name=StaticData.product_price_list.get(position).getProduct_name();
		}
		
		view_holder.product_name.setText(s_product_name);
		view_holder.product_price.setText("Rs."+StaticData.product_price_list.get(position).getPrice());
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		if(StaticData.product_price_list.get(position).getFav_status()==1){
			view_holder.favourite_item.setImageDrawable(cxt.getResources().getDrawable(R.drawable.like));
		}
		else{
			view_holder.favourite_item.setImageDrawable(cxt.getResources().getDrawable(R.drawable.favorite_unlike));
		}
		//image_load.displayImage(StaticData.product_price_list.get(position).getImagepath()+StaticData.product_price_list.get(position).getProduct_image(), view_holder.product_image, opt);
		image_load.displayImage(StaticData.product_price_list.get(position).getProduct_image(), view_holder.product_image, opt);
		view_holder.view_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pcgListener.onViewDetail(StaticData.product_price_list.get(position).getProduct_id(),StaticData.product_price_list.get(position).getCategory_name());
			}
		});
		view_holder.favourite_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(StaticData.product_price_list.get(position).getFav_status()==0){
				pcgListener.onAddFavourite(StaticData.product_price_list.get(position).getProduct_id(),StaticData.product_price_list.get(position).getCategory_name(),position);
				}
			}
		});
		
		
		return convertView;
	}

	private class ViewHolder{
		public TextView product_name;
		public ImageView product_image;
		public TextView product_price;
		public Button view_detail;
		public ImageView favourite_item;
	}
	
}
