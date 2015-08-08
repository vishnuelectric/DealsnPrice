package com.dealnprice.activity;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class DNPApplication extends Application{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "6zVZdERZfbXLQuwxKOWOKZDSk";
    private static final String TWITTER_SECRET = "5ocetjwhgsCj8sDT645y5VuyPBOsFxne99GfY7V8Fq0CzRf09W";


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));
		initUniversalImageLoader();
	}
	public void initUniversalImageLoader(){
		   ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		   .memoryCacheExtraOptions(800, 1020)
     .threadPoolSize(5)
     .threadPriority(Thread.MIN_PRIORITY + 2)
     .denyCacheImageMultipleSizesInMemory()
     .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation
//      .discCache(new UnlimitedDiscCache(cacheDir)) // You can pass your own disc cache implementation
     .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
 //    .imageDownloader(new DefaultImageDownloader(5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
     .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//      .enableLogging()
		   .build();
		   ImageLoader.getInstance().init(config);
	}

}
