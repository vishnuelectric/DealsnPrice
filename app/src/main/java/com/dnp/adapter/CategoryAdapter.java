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
import com.dnp.bean.CategoryBean;
import com.dnp.data.UniversalImageLoaderHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CategoryAdapter extends ArrayAdapter<CategoryBean>{
	LayoutInflater inflater;
	View view;
	Context cxt;
	DisplayImageOptions opt;
	ImageLoader image_load;
	public CategoryAdapter(Context context, ArrayList<CategoryBean> value) {
		super(context, R.layout.activity_category, value);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}
	
	
	/*public CategoryAdapter(Context context,String[] mSimpleListValues) {
		super(context, R.layout.activity_category);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}*/
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 Holder holder;

	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.activity_category, parent, false);
	            holder = new Holder();
	            holder.textView = (TextView) convertView.findViewById(R.id.category_name);
	            holder.category_image=(ImageView) convertView.findViewById(R.id.category_image);
	            convertView.setTag(holder);
	        } else {
	            holder = (Holder) convertView.getTag();
	        }
	        
	        holder.textView.setText(getItem(position).getCategory_name().toUpperCase());
	        opt=UniversalImageLoaderHelper.setImageOptions();
			image_load=ImageLoader.getInstance();
			image_load.displayImage(getItem(position).getCategory_image(), holder.category_image, opt);
	        return convertView;
	}
	
    private static class Holder {
        public TextView textView;
        public ImageView category_image;
    }

	
	

	
	
	/*public CategoryAdapter(Context cxt){
		this.cxt=cxt;
		//inflater=LayoutInflater.from(cxt);
	}
	
	@Override
	public int getCount() {

		return 10;//StaticData.category_list.size();
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

		inflater=(LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view=inflater.inflate(R.layout.activity_category, null);
		TextView category_name=(TextView) view.findViewById(R.id.category_name);
		//category_name.setText(StaticData.category_list.get(position).getCategory_name());
		//category_name.setText(value[position]);
		category_name.setText("Hello");
		
		return view;
	}
*/
}
