package com.dealnprice.activity;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class DNPApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Stetho.initialize(
				Stetho.newInitializerBuilder(this)
						.enableDumpapp(
								Stetho.defaultDumperPluginsProvider(this))
						.enableWebKitInspector(
								Stetho.defaultInspectorModulesProvider(this))
						.build());

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
