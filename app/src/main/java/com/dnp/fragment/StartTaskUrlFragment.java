package com.dnp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dealnprice.activity.R;

public class StartTaskUrlFragment extends Fragment{
	WebView url_view;
	View view;
	String url;
	Bundle bundle; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_task_web, container, false);
		url_view=(WebView) view.findViewById(R.id.webview);
		bundle=getArguments();
		url=bundle.getString("url");
		
		url_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		url_view.getSettings().setLoadsImagesAutomatically(true); 
		url_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		url_view.loadUrl(bundle.getString("url"));
		url_view.setWebViewClient(new WebViewClient(){
			 @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	               // pbar.setVisibility(View.GONE);
	                return true;
	            }
		});
		return view;
	}
	
}
