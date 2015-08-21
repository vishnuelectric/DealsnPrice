package com.dnp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.fragment.PriceComparisonFragment.PriceComparisonListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PriceComparisonItemAdapter extends ArrayAdapter<PriceComparisonBean>{
	PriceComparisonListener pcListener;
	String purpose;
	DisplayImageOptions opt;
	ImageLoader image_load;
	public PriceComparisonItemAdapter(Context cxt, PriceComparisonListener pclistener,ArrayList<PriceComparisonBean> value,String purpose) {
		super(cxt, R.layout.activity_price_item,value);
		// TODO Auto-generated constructor stub
		this.cxt=cxt;
		this.pcListener=pclistener;
		this.purpose=purpose;
	}
	LayoutInflater inflater;
	Holder holder;
	Context cxt;
	/*public PriceComparisonItemAdapter(Context context, ArrayList<PriceComparisonBean> value) {
		super(context, R.layout.activity_price_item,value);
		// TODO Auto-generated constructor stub
		inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}*/
	/*public PriceComparisonItemAdapter(Context cxt,ArrayList<PriceComparisonBean> value){
		this.cxt=cxt;
	}*/
	
	private static class Holder {
		public TextView product_name;
		public TextView product_price;
		public ImageView product_image;
		public Button view_detail;
		public ImageView favourite_image;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		inflater=(LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.activity_price_item, parent, false);
			holder=new Holder();
			holder.product_name=(TextView) convertView.findViewById(R.id.product_name);
			holder.product_price=(TextView) convertView.findViewById(R.id.product_price);
			holder.product_image=(ImageView) convertView.findViewById(R.id.product_image);
			holder.view_detail=(Button) convertView.findViewById(R.id.view_detail);
			holder.favourite_image=(ImageView) convertView.findViewById(R.id.favourite_item);
			
			convertView.setTag(holder);
		}
		else{
			holder=(Holder) convertView.getTag();
		}
		for(int i=0;i<StaticData.category_selected.size();i++){
			String s_product_name;
		if(getItem(position).getSubcategory_name().equals(StaticData.category_selected.get(i).getSubcategory_name())){
			if(getItem(position).getProduct_name().length()>50){
				s_product_name=getItem(position).getProduct_name().substring(0, 50)+"...";
				
			}
			else{
				s_product_name=getItem(position).getProduct_name();
			}
		holder.product_name.setText(s_product_name);
		holder.product_price.setText("Rs. "+getItem(position).getPrice());
		
		opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		//image_load.displayImage(list.get(position).getImagepath()+list.get(position).getProduct_image(), view_holder.product_image, opt);
		image_load.displayImage(getItem(position).getProduct_image(), holder.product_image, opt);
		
			
		}
		if(getItem(position).getFav_status()==1){
			holder.favourite_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.like));
		}
		else{
			holder.favourite_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.favorite_unlike));
		}
		
		}
		
		holder.favourite_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(getItem(position).getFav_status()==0){
				pcListener.onAddFavouriteItem(getItem(position).getProduct_id(),getItem(position).getCategory_name());
				}
			}
		});
		
		holder.view_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pcListener.onSuccessViewDetail(getItem(position).getProduct_id(),getItem(position).getCategory_name());
			}
		});
		return convertView;
	}

}
