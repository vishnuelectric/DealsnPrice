package com.dnp.adapter;

import java.util.ArrayList;

import com.dnp.data.CustomImageLoader;
import com.dnp.data.StaticData;
import com.dnp.data.UniversalImageLoaderHelper;
import com.dnp.data.UtilMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

public class OriginalPhotoGalleryAdapter extends BaseAdapter {
	Context cxt;
	DisplayImageOptions opt;
	ImageLoader image_load;
	ProgressBar pbar;
	int[] mlist;
	
	public OriginalPhotoGalleryAdapter(Context con,int[] list){
		this.cxt=con;
		this.mlist=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.length;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ImageView imageView = new ImageView(cxt);
        imageView.setScaleType(ScaleType.FIT_XY);
        Gallery.LayoutParams params = new Gallery.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);	
        //imageView.setImageResource(mlist[position]);
       // imageView.setImageDrawable(cxt.getResources().getDrawable(mlist[position]));
        Bitmap bm=BitmapFactory.decodeStream(cxt.getResources().openRawResource(mlist[position]));
        imageView.setImageBitmap(bm);
        /*opt=UniversalImageLoaderHelper.setImageOptions();
		image_load=ImageLoader.getInstance();
		image_load.displayImage(mlist[position], imageView, opt, new CustomImageLoader(pbar, cxt));*/
		//UtilMethod.showToast("Image Drawable is "+mlist[position], cxt);
		//imageView.setImageResource(list[position]);
		
		//image_load.DisplayImage(StaticData.event_gallery.get(position).getEvent_place_gallery_image_url(), imageView);
		
		return imageView;
	}



}
