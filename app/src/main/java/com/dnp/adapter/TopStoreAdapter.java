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
import com.dnp.bean.StoreBean;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.fragment.DNPDealCouponFragment.DealCouponListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TopStoreAdapter extends ArrayAdapter<StoreBean>{
	LayoutInflater inflater;
	View view;
	Context cxt;
	DealCouponListener dcListener;
	DisplayImageOptions opt;
	ImageLoader image_load;
	public TopStoreAdapter(Context context, ArrayList<StoreBean> resource,DealCouponListener dclistener) {
		super(context, R.layout.activity_topstore_item,resource);
		// TODO Auto-generated constructor stub
		this.dcListener=dclistener;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
		convertView = inflater.inflate(R.layout.activity_topstore_item, parent, false);
		holder = new Holder();
		holder.cashback=(TextView) convertView.findViewById(R.id.cashback_amount);
		holder.coupon_count=(TextView) convertView.findViewById(R.id.coupon_count);
		holder.deals_count=(TextView) convertView.findViewById(R.id.deal_count);
		holder.store_image=(ImageView) convertView.findViewById(R.id.store_image);
		convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		holder.deals_count.setText(getItem(position).getDeal_count()+" Deals");
		holder.coupon_count.setText(getItem(position).getCoupon_total()+" Coupons");
		holder.cashback.setText(getItem(position).getStore_description().replace("Up to", "").replace("Upto", "").replace("cashback", "").replace("Cashback", "").replace("  ", ""));
		//product_detail.setText(StaticData.deal_product_list.get(position).getCategory_name());
		/*String detail;
		if(getItem(position).getCategory_name().length()>100){
			String s1=getItem(position).getCategory_name().substring(0, 100);
			detail=s1+"...";
		}
		else{
			detail=getItem(position).getCategory_name();
		}*/
        	opt=UniversalImageLoaderHelper.setImageOptions();
        	image_load=ImageLoader.getInstance();
        	image_load.displayImage(getItem(position).getStore_image(), holder.store_image, opt);
        	
		
		return convertView;
	} 
	
	private static class Holder {
		public TextView deals_count;
		public TextView coupon_count;
		public TextView cashback;
		public ImageView store_image;
	}
}
