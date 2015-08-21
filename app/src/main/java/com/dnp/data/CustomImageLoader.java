package com.dnp.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class CustomImageLoader implements ImageLoadingListener{

	ProgressBar progress;
	Context mcContext;
	public CustomImageLoader(ProgressBar progressBar,Context ct){
		this.progress = progressBar;
		mcContext = ct;
	}
	@Override
	public void onLoadingStarted(String imageUri, View view) {
		if(progress!=null){
			progress.setVisibility(View.VISIBLE);
		}
	//	view.setBackgroundDrawable(mcContext.getResources().getDrawable(R.drawable.piklar_default_image));
	}

	@Override
	public void onLoadingFailed(String imageUri, View view,
			FailReason failReason) {
	//	view.setBackgroundDrawable(mcContext.getResources().getDrawable(R.drawable.piklar_default_image));
		//UtilMethod.showToast("unable to download image !!"+failReason.getCause(),mcContext );
		if(progress!=null){
			progress.setVisibility(View.GONE);
		}
	}

	@Override
	public void onLoadingComplete(String imageUri, View postImage,
			Bitmap loadedImage) {
		if(progress!=null){
			progress.setVisibility(View.GONE);
		}
		postImage.setBackgroundDrawable(null);
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
			if(progress!=null){
				progress.setVisibility(View.GONE);
			}
	}
}