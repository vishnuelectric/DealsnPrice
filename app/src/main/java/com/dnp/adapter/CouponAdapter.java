package com.dnp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.bean.CouponBean;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.fragment.DNPDealCouponFragment.DealCouponListener;
import com.dnp.fragment.DNPDealCouponGridFragment.CouponGridListener;
import com.dnp.fragment.DNPSeeAllOfferFragment.SeeCouponListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CouponAdapter extends ArrayAdapter<CouponBean>{
	LayoutInflater inflater;
	View view;
	Context cxt;
	DealCouponListener dcListener;
	CouponGridListener cgListener;
	SeeCouponListener scListener;
	DisplayImageOptions opt;
	ImageLoader image_load;
	public CouponAdapter(Context context, ArrayList<CouponBean> resource,DealCouponListener dclistener) {
		super(context, R.layout.activity_coupon_item,resource);
		// TODO Auto-generated constructor stub
		this.dcListener=dclistener;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public CouponAdapter(Context context, ArrayList<CouponBean> resource,CouponGridListener cglistener) {
		super(context, R.layout.activity_coupon_item,resource);
		// TODO Auto-generated constructor stub
		this.cgListener=cglistener;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public CouponAdapter(Context context, ArrayList<CouponBean> resource, SeeCouponListener sclistener){
		super(context, R.layout.activity_coupon_item,resource);
		this.scListener=sclistener;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		//LayoutInflater infaler = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
		convertView = inflater.inflate(R.layout.activity_coupon_item, parent, false);
		//UtilMethod.showToast("Deal Adapter is come",cxt);
		holder = new Holder();
		//UtilMethod.showToast("Come in Deal Adapter", cxt);
		holder.product_detail=(TextView) convertView.findViewById(R.id.coupon_detail);
		holder.get_code_layout=(LinearLayout) convertView.findViewById(R.id.get_code_layout);
		holder.store_image=(ImageView) convertView.findViewById(R.id.store_image);
		convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		//product_detail.setText(StaticData.deal_product_list.get(position).getCategory_name());
		/*String detail;
		if(getItem(position).getCategory_name().length()>100){
			String s1=getItem(position).getCategory_name().substring(0, 100);
			detail=s1+"...";
		}
		else{
			detail=getItem(position).getCategory_name();
		}*/
			holder.product_detail.setText(getItem(position).getCategory_name());
        	opt=UniversalImageLoaderHelper.setImageOptions();
        	image_load=ImageLoader.getInstance();
        	image_load.displayImage(getItem(position).getStore_image(), holder.store_image, opt);
        	holder.get_code_layout.setOnClickListener(new OnClickListener() {
			
        		@Override
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			if(dcListener!=null){
        				
        				dcListener.onGetCouponCode(position,"hot");
        			}
        			else if(cgListener!=null){
        				cgListener.onGetCouponCode(position, "hot");
        			}
        			else if(scListener!=null){
        				if(getItem(position).getCoupon_home().equals("1")){
        				scListener.onGetCode(position,"hot");
        				}
        				else if(getItem(position).getCoupon_home().equals("2")){
        				scListener.onGetCode(position, "viewed");	
        				}
        			}
        			
				}
        	});
		
		return convertView;
	} 
	
	private static class Holder {
		public TextView product_detail;
		public LinearLayout get_code_layout;
		public ImageView store_image;
	}
}
