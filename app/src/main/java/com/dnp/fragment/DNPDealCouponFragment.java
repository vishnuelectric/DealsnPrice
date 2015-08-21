package com.dnp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.dnp.data.APP_Constants;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.CategoryAdapter;
import com.dnp.adapter.CouponAdapter;
import com.dnp.adapter.DNPStealDealAdapter;
import com.dnp.adapter.DemoPriceComparisonAdapter;
import com.dnp.adapter.MostViewedCouponAdapter;
import com.dnp.adapter.TopStoreAdapter;
import com.dnp.asynctask.GetCategoryTask;
import com.dnp.asynctask.GetCouponProductTask;
import com.dnp.asynctask.GetDealStoreTask;
import com.dnp.asynctask.GetTopStoreTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;

public class DNPDealCouponFragment extends Fragment {

	String tag= "DNPDealCouponFragment";
	private HorizontalListView mHlvCustomList,top_stores;
	private HorizontalListView steal_deal_list,most_viewed_deal_list;
	private HorizontalListView hot_coupon_list,most_viewed_coupon_list;
	SharedPreferences shpf;
	Editor edt;
	CategoryAdapter cadapter;
	DemoPriceComparisonAdapter dadapter;
	CouponAdapter coupon_adapter;
	TopStoreAdapter tsadapter;
	HorizontalScrollView horizontal_id;

	LinearLayout steal_deal_layout,most_viewed_deal_layout,hot_coupon_layout,most_viewed_coupon_layout,top_store_layout;
	MostViewedCouponAdapter mvcadapter;
	DNPStealDealAdapter sdadapter;
	//HorizontalListView mHlvCustomList1;

	/*HorizontalListView deal_list,coupon_list;*/
	//GridView category_list;
	LinearLayout offer_layout,shopearn_layout,price_layout,referearn_layout,dealncoupon_layout; 
	TextView offer_text,shopearn_text,price_text,referearn_text,dealprice_text;
	Fragment fragment;
	FragmentManager fmanager;

	FragmentTransaction ft;
	//ProgressDialog pdialog;
	LinearLayout footer_layout;
	ImageView view_all_deal,view_all_coupon,view_all_steal_deal,view_all_hot_coupon,view_all_top_store;
	Dialog dialog;
	ImageView loading_image;
	EditText shop_search;
//	private AnimationDrawable loadingViewAnim;
	private static final int DATABASE_VERSION = 1;  
	private static final String DATABASE_NAME = "DealsnPrice";
	sqlHelper sHeler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.i(tag, "onCreateView");
		View view=inflater.inflate(R.layout.activity_dnp_dealnprice, container, false);
		mHlvCustomList = (HorizontalListView) view.findViewById(R.id.category_list);
		steal_deal_list=(HorizontalListView) view.findViewById(R.id.steal_deal_list);
		hot_coupon_list=(HorizontalListView) view.findViewById(R.id.hot_coupon_list);
		most_viewed_deal_list=(HorizontalListView) view.findViewById(R.id.most_viewed_deal_list);
		most_viewed_coupon_list=(HorizontalListView) view.findViewById(R.id.most_coupon_list);
		top_stores=(HorizontalListView) view.findViewById(R.id.top_store_list);
		steal_deal_layout=(LinearLayout) view.findViewById(R.id.steal_deal_layout);
		most_viewed_deal_layout=(LinearLayout) view.findViewById(R.id.most_viewed_deal_layout);
		hot_coupon_layout=(LinearLayout) view.findViewById(R.id.hot_coupon_layout);
		most_viewed_coupon_layout=(LinearLayout) view.findViewById(R.id.most_viewed_coupon_layout);
		top_store_layout=(LinearLayout) view.findViewById(R.id.top_store_layout);
		dealncoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		shop_search=(EditText) view.findViewById(R.id.shop_search);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);


		//mHlvCustomList1 = (HorizontalListView) view.findViewById(R.id.coupon_list);


		/*deal_list=(HorizontalListView) view.findViewById(R.id.deal_list);
		coupon_list=(HorizontalListView) view.findViewById(R.id.coupon_list);*/
		//category_list=(GridView) view.findViewById(R.id.category_list);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		price_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		price_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		dealprice_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		view_all_coupon=(ImageView) view.findViewById(R.id.most_view_all_coupon);
		view_all_hot_coupon=(ImageView) view.findViewById(R.id.view_all_hot_coupon);
		view_all_deal=(ImageView) view.findViewById(R.id.most_view_all_deal);
		view_all_steal_deal=(ImageView) view.findViewById(R.id.view_all_steal_deal);
		view_all_top_store=(ImageView) view.findViewById(R.id.view_all_store);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		dealncoupon_layout.setOnClickListener(dealListener);
	/*	shopearn_text.setText("Shop & Earn");
		price_text.setText("Price & Comparison");
		referearn_text.setText("Refer & Earn");
		dealprice_text.setText("Deal & Coupon");*/
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		price_layout.setOnClickListener(priceComparisonListener);
		referearn_layout.setOnClickListener(referEarnListener);
		//--
		//--
		LinearLayout lin[]	=	new LinearLayout[]{offer_layout,shopearn_layout,price_layout,dealncoupon_layout,referearn_layout};
		DashboardActivity.actRef.selectTab(lin,APP_Constants.DEALS_CUPONS);


		//--
		//--
		//	pdialog=new ProgressDialog(getActivity());
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
		shpf=getActivity().getSharedPreferences("User_login", 1);
		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});

		//	pdialog=new ProgressDialog(getActivity());
		/*if(pdialog!=null){
		UtilMethod.showLoading(pdialog, getActivity());
		}*/
		edt=shpf.edit();
		edt.putString("category_name", "all");
		edt.commit();
		setProgressDialog();
		new GetCategoryTask(getActivity(),new DealCouponListener()).execute();

		/*String[] mSimpleListValues = new String[] { "Android", "iPhone", "WindowsMobile",
			            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			            "Linux", "OS/2" };
			category_list.setAdapter(new CategoryAdapter(getActivity()));*/


		return view;
	}

	OnClickListener dealListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPDealCouponFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};

	/*private void setProgressDialog(){

		dialog=new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading);
		LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
		loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
		loading_image.setBackgroundResource(R.anim.loading_animation);
		loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
		dialog.setCancelable(false);
		loadingViewAnim.start();

		dialog.show();
	}*/
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(tag, "OnAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "OnCreate");
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(tag, "onActivityCreated");
	}


	@Override
	public void onStart() {
		super.onStart();
		Log.i(tag, "onStart");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(tag, "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(tag, "onResume");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(tag, "onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i(tag, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(tag, "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(tag, "onDetach");
	}


	OnClickListener notificationListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new NotificationFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener profileListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ProfileFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener favouriteListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new FavouriteFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener offerListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new OfferFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	OnClickListener shopEarnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ShopEarnFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};


	OnClickListener priceComparisonListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};


	OnClickListener referEarnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ReferEarnFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};


	public class DealCouponListener{
		public void onSuccess(){

			String multipartString = null;
			if(shpf.getString("category_name", null).equals("all")){
				multipartString=WebService.WEB_HOST_URL+"jsonproduct/coupon?urlcheck=1";

			}
			else if(shpf.getString("category_name", null).equalsIgnoreCase("Mobiles") || shpf.getString("category_name",null).equalsIgnoreCase("Tablets") || shpf.getString("category_name", null).equals("Cameras") || shpf.getString("category_name", null).equals("Computers")){
				//String multipartString=WebService.WEB_HOST_URL+"jsonproduct/coupon?urlcheck=1";
				multipartString=WebService.WEB_HOST_URL+"jsonproduct/coupon/?type=0&catid="+shpf.getString("subcategory_id", null)+"&urlcheck=1";

			}
			if(!UtilMethod.isStringNullOrBlank(multipartString)){
				//UtilMethod.showToast("Multi url in Coupon is "+multipartString, getActivity());
				new GetCouponProductTask(getActivity(),multipartString,new DealCouponListener()).execute();
			}
		}

		public void onError(){


			UtilMethod.showServerError(getActivity());

		}
		public void onSuccessCoupon(){
			String multiurl = null;

			if(shpf.getString("category_name", null).equals("all")){
				multiurl=WebService.WEB_HOST_URL+"jsonproduct/deals?urlcheck=1";
			}
			else if(shpf.getString("category_name", null).equalsIgnoreCase("Mobiles") || shpf.getString("category_name",null).equalsIgnoreCase("Tablets") || shpf.getString("category_name", null).equals("Cameras") || shpf.getString("category_name", null).equals("Computers")){
				multiurl=WebService.WEB_HOST_URL+"jsonproduct/deals?type=0&catid="+shpf.getString("subcategory_id", null)+"&urlcheck=1";	
			}
			if(!UtilMethod.isStringNullOrBlank(multiurl)){
				//UtilMethod.showToast("Multi url in deal is "+multiurl, getActivity());
				new GetDealStoreTask(getActivity(),multiurl,new DealCouponListener()).execute();
				//UtilMethod.showToast("Coupon Response is Success",getActivity());
			}
		}
		public void onSuccessAll(){
			/*if(pdialog!=null && pdialog.isShowing()){
				pdialog.dismiss();
			}*/
			new GetTopStoreTask(getActivity(), new DealCouponListener()).execute();
		}

		public void onSuccessWithStore(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			setAdapter();

		}

		public void onGetCouponCode(int position,String purpose1){
			Fragment fragment=new GetCouponCodeFragment();
			Bundle b=new Bundle();
			b.putInt("position", position);
			b.putString("purpose", purpose1);
			fragment.setArguments(b); 
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}


	}

	private void setAdapter(){
		/*if(StaticData.deal_product_list.size()>0){*/

		sHeler=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
		cadapter=new CategoryAdapter(getActivity(),StaticData.category_fixed_list);
		dadapter=new DemoPriceComparisonAdapter(getActivity(), sHeler.getDealValue("2"));
		coupon_adapter=new CouponAdapter(getActivity(), sHeler.getCouponValue("1"),new DealCouponListener());
		sdadapter=new DNPStealDealAdapter(getActivity(), sHeler.getDealValue("1"));
		mvcadapter=new MostViewedCouponAdapter(getActivity(), sHeler.getCouponValue("2"),new DealCouponListener());
		tsadapter=new TopStoreAdapter(getActivity(), StaticData.top_store_list, new DealCouponListener());
		top_stores.setAdapter(tsadapter);
		mHlvCustomList.setAdapter(cadapter);
		cadapter.notifyDataSetChanged();
		hot_coupon_list.setAdapter(coupon_adapter);
		most_viewed_deal_list.setAdapter(dadapter);
		most_viewed_coupon_list.setAdapter(coupon_adapter);
		steal_deal_list.setAdapter(dadapter);
		most_viewed_coupon_list.setAdapter(mvcadapter);
		steal_deal_list.setAdapter(sdadapter);
		dadapter.notifyDataSetChanged();
		view_all_coupon.setOnClickListener(mostviewedcouponListener);
		view_all_deal.setOnClickListener(mostvieweddealListener);
		view_all_hot_coupon.setOnClickListener(hotcouponListener);
		view_all_steal_deal.setOnClickListener(stealviewListener);
		view_all_top_store.setOnClickListener(topstoreListener);
		if(StaticData.steal_deal_list.size()==0){
			steal_deal_layout.setVisibility(View.GONE);
		}
		if(StaticData.most_viewed_deal_list.size()==0){
			most_viewed_deal_layout.setVisibility(View.GONE);
		}
		if(StaticData.hot_coupon_product_list.size()==0){
			hot_coupon_layout.setVisibility(View.GONE);
		}
		if(StaticData.most_viewed_coupon_list.size()==0){
			most_viewed_coupon_list.setVisibility(View.GONE);
		}

		//coupon_list.setAdapter(coupon_adapter);
		coupon_adapter.notifyDataSetChanged();
		mHlvCustomList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				SharedPreferences shpf=getActivity().getSharedPreferences("User_login",1);
				Editor edt=shpf.edit();
				edt.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
				edt.putString("subcategory_id", StaticData.category_fixed_list.get(arg2).getSubcategory_id());
				edt.putString("link", StaticData.category_fixed_list.get(arg2).getSubcategory_link());
				edt.putString("link_value",StaticData.category_fixed_list.get(arg2).getSubcategory_link_value());
				edt.commit();
				/*if(pdialog!=null){
						UtilMethod.showLoading(pdialog, getActivity());
					}*/
				setProgressDialog();
				String url=WebService.WEB_HOST_URL+"jsonproduct/coupon/?type=0&catid="+StaticData.category_fixed_list.get(arg2).getSubcategory_id()+"&urlcheck=1";
				new GetCouponProductTask(getActivity(), url, new DealCouponListener()).execute();
			}
		});
		steal_deal_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Fragment fragment=new GetDealCodeFragment();
				Bundle b=new Bundle();
				b.putInt("position", arg2);
				b.putString("purpose","steal");
				fragment.setArguments(b);
				FragmentManager fmanager=getActivity().getSupportFragmentManager();
				FragmentTransaction ft=fmanager.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.addToBackStack(null);
				ft.commit();

			}
		});
		shop_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(UtilMethod.isStringNullOrBlank(s.toString())){
					setAdapter();
				}
				else{
					setAdapter(s.toString());
				}
			}
		});


		/*hot_coupon_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment fragment=new GetCouponCodeFragment();
					Bundle b=new Bundle();
					b.putInt("position", arg2);
					fragment.setArguments(b);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, fragment);
					ft.addToBackStack(null);
					ft.commit();
				}
			});*/
		most_viewed_deal_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Fragment fragment=new GetDealCodeFragment();
				Bundle b=new Bundle();
				b.putInt("position", arg2);
				b.putString("purpose","viewed");
				fragment.setArguments(b);
				FragmentManager fmanager=getActivity().getSupportFragmentManager();
				FragmentTransaction ft=fmanager.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		/*			view_all_coupon.setOnClickListener(viewAllCouponListener);
			view_all_deal.setOnClickListener(viewAllDealListener);
		 */			
		/*}*/



	}

	private void setAdapter(String store){
		/*if(StaticData.deal_product_list.size()>0){*/

		sHeler=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
		cadapter=new CategoryAdapter(getActivity(),StaticData.category_fixed_list);
		dadapter=new DemoPriceComparisonAdapter(getActivity(), sHeler.getDealValuewithStore(store,"2"));
		coupon_adapter=new CouponAdapter(getActivity(), sHeler.getCouponValuewithStore(store,"1"),new DealCouponListener());
		sdadapter=new DNPStealDealAdapter(getActivity(), sHeler.getDealValuewithStore(store,"1"));
		mvcadapter=new MostViewedCouponAdapter(getActivity(), sHeler.getCouponValuewithStore(store,"2"),new DealCouponListener());

		mHlvCustomList.setAdapter(cadapter);
		cadapter.notifyDataSetChanged();
		hot_coupon_list.setAdapter(coupon_adapter);
		most_viewed_deal_list.setAdapter(dadapter);
		most_viewed_coupon_list.setAdapter(coupon_adapter);
		steal_deal_list.setAdapter(dadapter);
		most_viewed_coupon_list.setAdapter(mvcadapter);
		steal_deal_list.setAdapter(sdadapter);
		dadapter.notifyDataSetChanged();
		coupon_adapter.notifyDataSetChanged();
		sdadapter.notifyDataSetChanged();
		mvcadapter.notifyDataSetChanged();
		view_all_coupon.setOnClickListener(mostviewedcouponListener);
		view_all_deal.setOnClickListener(mostvieweddealListener);
		view_all_hot_coupon.setOnClickListener(hotcouponListener);
		view_all_steal_deal.setOnClickListener(stealviewListener);
		view_all_top_store.setOnClickListener(topstoreListener);
		/*if(StaticData.steal_deal_list.size()==0){
				steal_deal_layout.setVisibility(View.GONE);
			}*/
		/*if(StaticData.most_viewed_deal_list.size()==0){
				most_viewed_deal_layout.setVisibility(View.GONE);
			}
			if(StaticData.hot_coupon_product_list.size()==0){
				hot_coupon_layout.setVisibility(View.GONE);
			}
			if(StaticData.most_viewed_coupon_list.size()==0){
				most_viewed_coupon_list.setVisibility(View.GONE);
			}*/

		//coupon_list.setAdapter(coupon_adapter);
		coupon_adapter.notifyDataSetChanged();
		mHlvCustomList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				SharedPreferences shpf=getActivity().getSharedPreferences("User_login",1);
				Editor edt=shpf.edit();
				edt.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
				edt.putString("subcategory_id", StaticData.category_fixed_list.get(arg2).getSubcategory_id());
				edt.putString("link", StaticData.category_fixed_list.get(arg2).getSubcategory_link());
				edt.putString("link_value",StaticData.category_fixed_list.get(arg2).getSubcategory_link_value());
				edt.commit();
				/*if(pdialog!=null){
						UtilMethod.showLoading(pdialog, getActivity());
					}*/
				setProgressDialog();
				String url=WebService.WEB_HOST_URL+"jsonproduct/coupon/?type=0&catid="+StaticData.category_fixed_list.get(arg2).getSubcategory_id()+"&urlcheck=1";
				new GetCouponProductTask(getActivity(), url, new DealCouponListener()).execute();
			}
		});
		steal_deal_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Fragment fragment=new GetDealCodeFragment();
				Bundle b=new Bundle();
				b.putInt("position", arg2);
				b.putString("purpose","steal");
				fragment.setArguments(b);
				FragmentManager fmanager=getActivity().getSupportFragmentManager();
				FragmentTransaction ft=fmanager.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.addToBackStack(null);
				ft.commit();

			}
		});
		shop_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(UtilMethod.isStringNullOrBlank(s.toString())){
					setAdapter();
				}
				else{
					setAdapter(s.toString());
				}
			}
		});


		/*hot_coupon_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment fragment=new GetCouponCodeFragment();
					Bundle b=new Bundle();
					b.putInt("position", arg2);
					fragment.setArguments(b);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, fragment);
					ft.addToBackStack(null);
					ft.commit();
				}
			});*/
		most_viewed_deal_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Fragment fragment=new GetDealCodeFragment();
				Bundle b=new Bundle();
				b.putInt("position", arg2);
				b.putString("purpose","viewed");
				fragment.setArguments(b);
				FragmentManager fmanager=getActivity().getSupportFragmentManager();
				FragmentTransaction ft=fmanager.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		/*			view_all_coupon.setOnClickListener(viewAllCouponListener);
			view_all_deal.setOnClickListener(viewAllDealListener);
		 */			
		/*}*/



	}


	/*OnClickListener viewAllDealListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPDealGridFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};
	OnClickListener viewAllCouponListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPCouponGridFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();

		}
	};*/
	OnClickListener stealviewListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPDealCouponGridFragment();
			Bundle b=new Bundle();
			b.putString("purpose","Steal Deals");
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener mostvieweddealListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPDealCouponGridFragment();
			Bundle b=new Bundle();
			b.putString("purpose","Most Viewed Deals");
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener hotcouponListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPDealCouponGridFragment();
			Bundle b=new Bundle();
			b.putString("purpose","Hot Coupons");
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener mostviewedcouponListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPDealCouponGridFragment();
			Bundle b=new Bundle();
			b.putString("purpose","Most Viewed Coupons");
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener topstoreListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment=new DNPDealCouponGridFragment();
			Bundle b=new Bundle();
			b.putString("purpose","Top Stores");
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}; 

}
