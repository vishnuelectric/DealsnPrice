package com.dnp.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;

public class OpenGetCodeUrlFragment extends Fragment{
	WebView webview;
	Bundle b;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	LinearLayout footer_layout;
	ProgressBar pbar;
	int position;
	String purpose,coupon_store_url;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_shop_url, container, false);
		webview=(WebView) view.findViewById(R.id.web_view);
		pbar=(ProgressBar) view.findViewById(R.id.pbar);
		b=getArguments();
		/*webview.getSettings().setJavaScriptEnabled(true);*/
	   /* webview.loadUrl(b.getString("shop_url"));
	    webview.getSettings().setJavaScriptEnabled(true); 
	    webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	    webview.setWebChromeClient(new WebChromeClient());
	    webview.setWebViewClient(new WebViewClient());
	    webview.getSettings().setDomStorageEnabled(true);*/
	    footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
	    LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		home_layout.setOnClickListener(offerListener);
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		pbar.setVisibility(View.GONE);
		
		/*webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(b.getString("shop_url"));*/
        position=b.getInt("position");
        purpose=b.getString("purpose");
        coupon_store_url=b.getString("shop_url");
		DashboardActivity.onCustomView(position,purpose);
		webview.loadUrl(coupon_store_url);
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.getSettings().setLoadsImagesAutomatically(true); 
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webview.loadUrl(coupon_store_url);
		webview.setWebViewClient(new WebViewClient(){
			@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                /*pbar.setVisibility(View.GONE);*/
                return true;
            }
		});
		return view;
	}
	
	public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbar.setVisibility(View.GONE);
        }
    }

    /*public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
		

	
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener notificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	
	public void onResume() {
		super.onResume();	
		DashboardActivity.onCustomView(getArguments().getInt("position"),getArguments().getString("purpose"));
	}
}
