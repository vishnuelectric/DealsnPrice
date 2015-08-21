package com.dnp.fragment;

/**
 * Take a Tour Screen Code
 */

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.adapter.OriginalPhotoGalleryAdapter;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.ExtendedGallery;

public class TakeATourFragment extends Fragment{
	View view;
	LinearLayout offer_layout,shopearn_layout,priceearn_layout,dealprice_layout,referearn_layout;
	TextView shopearn_text,priceearn_text,dealprice_text,referearn_text;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	HorizontalScrollView horizontal_id;
	private TypedArray navMenuIcons;
	LinearLayout footer_layout;
	ExtendedGallery photo_gallery;
	LinearLayout mDotLayout;
	private int mDotsCount;
	//static TextView mDotsText[];
	static ImageView mDotsText[];
	private String[] take_tour;
	LinearLayout skip_layout,next_layout;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_take_tour, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		priceearn_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealprice_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		priceearn_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		shopearn_text.setText("Shop & Earn");
		dealprice_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
		skip_layout=(LinearLayout) view.findViewById(R.id.skip_layout);
		next_layout=(LinearLayout) view.findViewById(R.id.next_layout);
		priceearn_text.setText("Price Comparison");
		photo_gallery=(ExtendedGallery) view.findViewById(R.id.imageView1);
		mDotLayout=(LinearLayout) view.findViewById(R.id.image_count);
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		navMenuIcons=getResources().obtainTypedArray(R.array.take_a_tour_icon);
		//take_tour=getResources().getStringArray(R.array.take_a_tour_icon);
		
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
		ImageView home_icon=(ImageView) v1.findViewById(R.id.home_icon);
		ImageView notification_icon=(ImageView) v1.findViewById(R.id.notification_icon);
		ImageView profile_icon=(ImageView) v1.findViewById(R.id.profile_icon);
		ImageView favourite_icon=(ImageView) v1.findViewById(R.id.favourite_icon);
		//profile_icon.setImageDrawable(getResources().getDrawable(R.drawable.profile_active));
		TextView home_text=(TextView) v1.findViewById(R.id.home_text);
		TextView notification_text=(TextView) v1.findViewById(R.id.notification_text);
		TextView profile_text=(TextView) v1.findViewById(R.id.profile_text);
		TextView favourite_text=(TextView) v1.findViewById(R.id.favorite_text);
		home_text.setTextColor(getResources().getColor(R.color.black));
		profile_text.setTextColor(getResources().getColor(R.color.gray));
		favourite_text.setTextColor(getResources().getColor(R.color.gray));
		notification_text.setTextColor(getResources().getColor(R.color.gray));
		home_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		priceearn_layout.setOnClickListener(inviteEarnListener);
		dealprice_layout.setOnClickListener(dealpriceListener);
		referearn_layout.setOnClickListener(couponListener);
		skip_layout.setOnClickListener(skipListener);
		next_layout.setOnClickListener(nextListener);
		/*ArrayList<Integer> tt_icon=new ArrayList<Integer>();
		tt_icon.add(navMenuIcons.getResourceId(0, -1));
		tt_icon.add(navMenuIcons.getResourceId(1, -1));
		tt_icon.add(navMenuIcons.getResourceId(2, -1));
		tt_icon.add(navMenuIcons.getResourceId(3, -1));
		tt_icon.add(navMenuIcons.getResourceId(4, -1));*/
		
		//UtilMethod.showToast("Fragment Image Drawable is "+tt_icon.get(0), getActivity());
		int[] resource_list={
				R.drawable.display_1,
				R.drawable.display_2,
				R.drawable.display_3
				
		};
		
		photo_gallery.setAdapter(new OriginalPhotoGalleryAdapter(getActivity(),resource_list));
		photo_gallery.setSelection(0);
		
		mDotsCount = photo_gallery.getAdapter().getCount();
		
		mDotsText = new ImageView[mDotsCount];
		for (int i = 0; i < mDotsCount; i++) {
			mDotsText[i] = new ImageView(getActivity());
			//mDotsText[i].setText(".");
			/*android.view.ViewGroup.LayoutParams layoutParam=mDotsText[i].getLayoutParams();
			layoutParam.width=25;
			layoutParam.height=25;*/
			/*MarginLayoutParams mParam=new MarginLayoutParams(mDotsText[i].getLayoutParams());
			mParam.setMargins(10, 10, 10, 10);
			android.view.ViewGroup.LayoutParams layoutParam=new LayoutParams(mParam);
			mDotsText[i].setLayoutParams(layoutParam);*/
			mDotsText[i].setLayoutParams(new ViewGroup.LayoutParams(25, 25));
			
			//mDotsText[i].setTextSize(20);
			//mDotsText[i].setGravity(Gravity.TOP|Gravity.CENTER);
			//mDotsText[i].setTypeface(null, Typeface.BOLD);
			//mDotsText[i].setTextColor(android.graphics.Color.GRAY);
			mDotsText[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_background));
			mDotLayout.addView(mDotsText[i]);
		}
		photo_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		
			

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				for (int i = 0; i < mDotsCount; i++) {
					mDotsText[i]
							.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_without_color_bg));;
				}

				mDotsText[arg2].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_background));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}
	
	OnClickListener skipListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getActivity().getSupportFragmentManager().popBackStack();
		}
	};
	
	OnClickListener nextListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(photo_gallery.getSelectedItemPosition()==0){
				photo_gallery.setSelection(1);
			}
			else if(photo_gallery.getSelectedItemPosition()==1){
				photo_gallery.setSelection(2);
			}
			
			/*mDotsCount = photo_gallery.getAdapter().getCount();
			
			mDotsText = new TextView[mDotsCount];
			for (int i = 0; i < mDotsCount; i++) {
				mDotsText[i] = new TextView(getActivity());
				mDotsText[i].setText(".");
				mDotsText[i].setTextSize(35);
				mDotsText[i].setGravity(Gravity.TOP|Gravity.CENTER);
				mDotsText[i].setTypeface(null, Typeface.BOLD);
				mDotsText[i].setTextColor(android.graphics.Color.GRAY);
				mDotLayout.addView(mDotsText[i]);
			}*/
		}
	};
	
	OnClickListener shopEarnListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ShopEarnFragment();
			onReplace(fragment);
		}
	};
	OnClickListener inviteEarnListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*fragment=new InviteEarnFragment();*/
			fragment=new PriceComparisonFragment();
			onReplace(fragment);
		}
	};
	OnClickListener dealpriceListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPDealCouponFragment();
			onReplace(fragment);
		}
	};
	OnClickListener couponListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ReferEarnFragment();
			onReplace(fragment);
		}
	};
	
	public void onReplace(Fragment fragment1){
		fm=getActivity().getSupportFragmentManager();
		ft=fm.beginTransaction();
		ft.replace(R.id.container, fragment1);
		ft.commit();
	}
OnClickListener offerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			onReplace(fragment);
		}
	};
	OnClickListener profileListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			onReplace(fragment);
			
		}
	};
	OnClickListener notificationListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			onReplace(fragment);
			
		}
	};
	OnClickListener favouriteListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			onReplace(fragment);
		}
	};
}
