package com.dnp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.bean.DealBean;
import com.dnp.data.UniversalImageLoaderHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DNPStealDealAdapter extends ArrayAdapter<DealBean>{
	Context cxt;
	private LayoutInflater mInflater;
	ImageLoader image_load;
	DisplayImageOptions opt;
	public DNPStealDealAdapter(Context context, ArrayList<DealBean> value) {
		super(context, R.layout.activity_deal_item, value);
		/*this.cxt=context;*/
		//this.cxt=cxt;
		mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	} 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		//LayoutInflater infaler = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
		convertView = mInflater.inflate(R.layout.activity_deal_item, parent, false);
		//UtilMethod.showToast("Deal Adapter is come",cxt);
		holder = new Holder();
		//UtilMethod.showToast("Come in Deal Adapter", cxt);
		holder.product_detail=(TextView) convertView.findViewById(R.id.product_detail);
		holder.product_mrp=(TextView) convertView.findViewById(R.id.product_mrp);
		holder.product_selling=(TextView) convertView.findViewById(R.id.product_selling);
		holder.product_image=(ImageView) convertView.findViewById(R.id.product_image);
		holder.store_image=(ImageView) convertView.findViewById(R.id.store_image);
		
		convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		holder.product_mrp.setText("Rs. "+getItem(position).getDeals_mrp());
		holder.product_selling.setText(getItem(position).getDeals_selling());
		//product_detail.setText(StaticData.deal_product_list.get(position).getCategory_name());
		String detail;
		if(getItem(position).getCategory_name().length()>100){
			String s1=getItem(position).getCategory_name().substring(0, 100);
			detail=s1+"...";
		}
		else{
			detail=getItem(position).getCategory_name();
		}
		holder.product_detail.setText(detail);
		/*AQuery aq = new AQuery(convertView);
        aq.id(R.id.product_image).image(getItem(position).getCategory_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
		AQuery aq1 = new AQuery(convertView);
        aq1.id(R.id.store_image).image(getItem(position).getStore_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();*/
        
        image_load=ImageLoader.getInstance();
        opt=UniversalImageLoaderHelper.setImageOptions();
        image_load.displayImage(getItem(position).getCategory_image(), holder.product_image, opt);
        image_load.displayImage(getItem(position).getStore_image(), holder.store_image, opt);
      /*  dgdg*/
        
		
		return convertView;
	} 
	
	private static class Holder {
		public TextView product_detail;
		public TextView product_mrp;
		public TextView product_selling;
		public ImageView product_image;
		public ImageView store_image;
	}

}
