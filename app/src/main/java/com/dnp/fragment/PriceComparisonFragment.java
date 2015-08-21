package com.dnp.fragment;

import java.util.ArrayList;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.dnp.adapter.PriceComparisonItemAdapter;
import com.dnp.asynctask.FavouriteProductTask;
import com.dnp.asynctask.GetCategoryTask;
import com.dnp.asynctask.GetPriceComparisonTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.APP_Constants;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;

public class PriceComparisonFragment extends Fragment{
	LinearLayout offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout,footer_layout;
	TextView offer_text,shopearn_text,pricecomparison_text,dealcoupon_text,referearn_text;
	Fragment fragment;
	FragmentManager fmanager;
	HorizontalListView category_list;
	FragmentTransaction ft;
	LinearLayout whole_layout;
	private HorizontalListView horizontal_list;
	ProgressDialog pdialog;
	EditText shop_search;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	private static final int DATABASE_VERSION = 1;  
	private static final String DATABASE_NAME = "DealsnPrice";
	sqlHelper sHelper;
	HorizontalScrollView horizontal_id;
	//ListView pricecomparison_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_pricecomparison, container, false);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		//pricecomparison_list=(ListView) view.findViewById(R.id.pricecomparison_list);
		category_list=(HorizontalListView) view.findViewById(R.id.category_list);
		whole_layout=(LinearLayout) view.findViewById(R.id.whole_layout);
		pricecomparison_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealcoupon_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		shop_search=(EditText) view.findViewById(R.id.shop_search);
		shop_search.setHintTextColor(getResources().getColor(R.color.search_text_color));
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();
		//--
		LinearLayout lin[]	=	new LinearLayout[]{offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout};
		DashboardActivity.actRef.selectTab(lin, APP_Constants.PRICE_COMPARISION);
		//--
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();

		/*shopearn_text.setText("Shop & Earn");
		pricecomparison_text.setText("Price Comparison");
		dealcoupon_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");*/
		offer_layout.setOnClickListener(offerListener);
		shopearn_layout.setOnClickListener(shopEarnListener);
		referearn_layout.setOnClickListener(referListener);
		dealcoupon_layout.setOnClickListener(dealCouponListener);
		pricecomparison_layout.setOnClickListener(inviteearnListener);
		LayoutInflater inflater1=LayoutInflater.from(getActivity());
		View v1=inflater1.inflate(R.layout.activity_footer,null);
		LinearLayout home_layout=(LinearLayout) v1.findViewById(R.id.home_layout);
		LinearLayout profile_layout=(LinearLayout) v1.findViewById(R.id.profile_layout);
		LinearLayout favourite_layout=(LinearLayout) v1.findViewById(R.id.favourite_layout);
		LinearLayout notification_layout=(LinearLayout) v1.findViewById(R.id.notification_layout);
		home_layout.setOnClickListener(offerListener);

		pdialog=new ProgressDialog(getActivity());
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		v1.setLayoutParams(param);
		footer_layout.addView(v1);

		if(StaticData.product_price_list.size()>0){
			category_list.setAdapter(new CategoryAdapter(getActivity(), StaticData.category_fixed_list));
			LinearLayout ll=new LinearLayout(getActivity());
			LinearLayout[] parentlayout=new LinearLayout[StaticData.category_selected.size()];
			for(int i=0;i<parentlayout.length;i++){
				parentlayout[i]=new LinearLayout(getActivity());
				parentlayout[i].setId(i); 
				final int position=i;
				LayoutInflater inflater2=LayoutInflater.from(getActivity());
				View view1=inflater2.inflate(R.layout.activity_pricencomparison_item,null);
				TextView header_text=(TextView) view1.findViewById(R.id.header_text);
				ImageView view_all=(ImageView) view1.findViewById(R.id.view_all);
				horizontal_list=(HorizontalListView) view1.findViewById(R.id.horizontal_list1);
				StaticData.product_comparison_list.clear();
				ArrayList<PriceComparisonBean> value=new ArrayList<PriceComparisonBean>();
				for(int j=0;j<StaticData.product_price_list.size();j++){
					if(StaticData.category_selected.get(i).getSubcategory_id().equals(StaticData.product_price_list.get(j).getSubcategory_id())){
						value.add(StaticData.product_price_list.get(j));


					}
				}
				view_all.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Fragment fragment=new PriceComparisonGridFragment();
						FragmentManager fmanager=getActivity().getSupportFragmentManager();
						Bundle b=new Bundle();
						b.putString("link",StaticData.category_selected.get(position).getLink());
						b.putString("link_value",StaticData.category_selected.get(position).getLink_value());
						b.putString("subcategory_id",StaticData.category_selected.get(position).getSubcategory_id());
						b.putString("subcategory_name", StaticData.category_selected.get(position).getSubcategory_name());
						b.putString("purpose", "sub_category");
						b.putInt("position",position);
						fragment.setArguments(b);
						FragmentTransaction ft=fmanager.beginTransaction();
						ft.replace(R.id.container, fragment);
						ft.addToBackStack(null);
						ft.commit();
					}
				});

				category_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Fragment f=new PriceComparisonCategoryFragment();
						Bundle b=new Bundle();
						b.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
						f.setArguments(b);
						FragmentManager fmanager=getActivity().getSupportFragmentManager();
						FragmentTransaction ft=fmanager.beginTransaction();
						ft.replace(R.id.container, f);
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
							onSetChangeEditTextBlank();
						}
						else{
							onSetChangeEditText(s.toString());
						}
					}
				});

				view.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
					}
				});


				/*if(StaticData.category_selected.get(i).getSubcategory_name().equals("Price Comparison")){
				header_text.setText(StaticData.category_selected.get(i).getCategory_name().toUpperCase());
				PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),value,StaticData.category_selected.get(i).getCategory_name());
				horizontal_list.setAdapter(pciadapter);
				pciadapter.notifyDataSetChanged();
				}
				else{

				 */				
				sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
				header_text.setText(StaticData.category_selected.get(i).getSubcategory_name().toUpperCase());
				PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name()),StaticData.category_selected.get(i).getSubcategory_name());
				horizontal_list.setAdapter(pciadapter);
				pciadapter.notifyDataSetChanged();
				/*}*/

				//parentlayout[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				parentlayout[i].addView(view1);
				whole_layout.addView(parentlayout[i]);

			}
		}
		else{
			sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
			sHelper.deleteProducts();
			onCallTask();
		}


		return view;
	}

	OnClickListener inviteearnListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new PriceComparisonFragment();
			ft.replace(R.id.container,fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	public void onSetChangeEditText(String s){
		whole_layout.removeAllViews();
		category_list.setAdapter(new CategoryAdapter(getActivity(), StaticData.category_fixed_list));
		//UtilMethod.showToast("Category Selected List Size is"+StaticData.category_selected.size(), getActivity());
		LinearLayout ll=new LinearLayout(getActivity());
		//UtilMethod.showToast("Category Selected Listsize is"+StaticData.category_selected.size(), getActivity());
		LinearLayout[] parentlayout=new LinearLayout[StaticData.category_selected.size()];
		//	UtilMethod.showToast("Parent Layout Length is "+parentlayout.length, getActivity());
		for(int i=0;i<parentlayout.length;i++){
			parentlayout[i]=new LinearLayout(getActivity());
			parentlayout[i].setId(i); 
			final int position=i;
			LayoutInflater inflater1=LayoutInflater.from(getActivity());
			View view1=inflater1.inflate(R.layout.activity_pricencomparison_item,null);
			TextView header_text=(TextView) view1.findViewById(R.id.header_text);
			ImageView view_all=(ImageView) view1.findViewById(R.id.view_all);
			horizontal_list=(HorizontalListView) view1.findViewById(R.id.horizontal_list1);
			StaticData.product_comparison_list.clear();
			ArrayList<PriceComparisonBean> value=new ArrayList<PriceComparisonBean>();
			for(int j=0;j<StaticData.product_price_list.size();j++){
				/*for(int k=0;k<StaticData.category_selected.size();k++){*/
				if(StaticData.category_selected.get(i).getSubcategory_id().equals(StaticData.product_price_list.get(j).getSubcategory_id())){
					value.add(StaticData.product_price_list.get(j));
					/*break;*/
					/*}*/

				}
			}
			view_all.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Fragment fragment=new PriceComparisonGridFragment();
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					Bundle b=new Bundle();
					b.putString("link",StaticData.category_selected.get(position).getLink());
					b.putString("link_value",StaticData.category_selected.get(position).getLink_value());
					b.putString("subcategory_id",StaticData.category_selected.get(position).getSubcategory_id());
					b.putInt("position",position);
					fragment.setArguments(b);
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.addToBackStack(null);
					ft.replace(R.id.container, fragment);
					ft.commit();
				}
			});

			category_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment f=new PriceComparisonCategoryFragment();
					Bundle b=new Bundle();
					b.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
					f.setArguments(b);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, f);
					ft.addToBackStack(null);
					ft.commit();
				}
			});
			/*if(StaticData.category_selected.get(i).getSubcategory_name().equals("Price Comparison")){
			header_text.setText(StaticData.category_selected.get(i).getCategory_name().toUpperCase());
			PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),value,StaticData.category_selected.get(i).getCategory_name());
			horizontal_list.setAdapter(pciadapter);
			pciadapter.notifyDataSetChanged();
			}
			else{

			 */				
			sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
			header_text.setText(StaticData.category_selected.get(i).getSubcategory_name().toUpperCase());
			PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),sHelper.getProductValuewithBrand(s),StaticData.category_selected.get(i).getSubcategory_name());
			ArrayList<PriceComparisonBean> list=sHelper.getProductValuewithBrand(s);
			//UtilMethod.showToast("Dynamic List Size is "+list.size(), getActivity());
			horizontal_list.setAdapter(pciadapter);
			pciadapter.notifyDataSetChanged();
			/*}*/

			//parentlayout[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			parentlayout[i].addView(view1);
			whole_layout.addView(parentlayout[i]);

		}
	}


	public void onSetChangeEditTextBlank(){
		whole_layout.removeAllViews();
		category_list.setAdapter(new CategoryAdapter(getActivity(), StaticData.category_fixed_list));
		//UtilMethod.showToast("Category Selected List Size is"+StaticData.category_selected.size(), getActivity());
		LinearLayout ll=new LinearLayout(getActivity());
		//UtilMethod.showToast("Category Selected Listsize is"+StaticData.category_selected.size(), getActivity());
		LinearLayout[] parentlayout=new LinearLayout[StaticData.category_selected.size()];
		//	UtilMethod.showToast("Parent Layout Length is "+parentlayout.length, getActivity());
		for(int i=0;i<parentlayout.length;i++){
			parentlayout[i]=new LinearLayout(getActivity());
			parentlayout[i].setId(i); 
			final int position=i;
			LayoutInflater inflater1=LayoutInflater.from(getActivity());
			View view1=inflater1.inflate(R.layout.activity_pricencomparison_item,null);
			TextView header_text=(TextView) view1.findViewById(R.id.header_text);
			ImageView view_all=(ImageView) view1.findViewById(R.id.view_all);
			horizontal_list=(HorizontalListView) view1.findViewById(R.id.horizontal_list1);
			StaticData.product_comparison_list.clear();
			ArrayList<PriceComparisonBean> value=new ArrayList<PriceComparisonBean>();
			for(int j=0;j<StaticData.product_price_list.size();j++){
				/*for(int k=0;k<StaticData.category_selected.size();k++){*/
				if(StaticData.category_selected.get(i).getSubcategory_id().equals(StaticData.product_price_list.get(j).getSubcategory_id())){
					value.add(StaticData.product_price_list.get(j));
					/*break;*/
					/*}*/

				}
			}
			view_all.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Fragment fragment=new PriceComparisonGridFragment();
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					Bundle b=new Bundle();
					b.putString("link",StaticData.category_selected.get(position).getLink());
					b.putString("link_value",StaticData.category_selected.get(position).getLink_value());
					b.putString("subcategory_id",StaticData.category_selected.get(position).getSubcategory_id());
					b.putString("subcategory_name", StaticData.category_selected.get(position).getSubcategory_name());
					b.putInt("position",position);
					fragment.setArguments(b);
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.addToBackStack(null);
					ft.replace(R.id.container, fragment);
					ft.commit();
				}
			});

			category_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Fragment f=new PriceComparisonCategoryFragment();
					Bundle b=new Bundle();
					b.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
					f.setArguments(b);
					FragmentManager fmanager=getActivity().getSupportFragmentManager();
					FragmentTransaction ft=fmanager.beginTransaction();
					ft.replace(R.id.container, f);
					ft.addToBackStack(null);
					ft.commit();
				}
			});
			/*if(StaticData.category_selected.get(i).getSubcategory_name().equals("Price Comparison")){
			header_text.setText(StaticData.category_selected.get(i).getCategory_name().toUpperCase());
			PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),value,StaticData.category_selected.get(i).getCategory_name());
			horizontal_list.setAdapter(pciadapter);
			pciadapter.notifyDataSetChanged();
			}
			else{

			 */				
			sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
			header_text.setText(StaticData.category_selected.get(i).getSubcategory_name().toUpperCase());
			PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name()),StaticData.category_selected.get(i).getSubcategory_name());
			ArrayList<PriceComparisonBean> list=sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name());
			//UtilMethod.showToast("Dynamic List Size is "+list.size(), getActivity());
			horizontal_list.setAdapter(pciadapter);
			pciadapter.notifyDataSetChanged();
			/*}*/

			//parentlayout[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			parentlayout[i].addView(view1);
			whole_layout.addView(parentlayout[i]);

		}
	}



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
	}
*/
	
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}
	public void onCallTask(){
		setProgressDialog();

		new GetCategoryTask(getActivity(),new PriceComparisonListener()).execute();
	}

	/*class Starter implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

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
	OnClickListener referListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new ReferEarnFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};
	OnClickListener dealCouponListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fragment=new DNPDealCouponFragment();
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	public class PriceComparisonListener{
		public void onSuccess(){
			StaticData.product_price_list.clear();
			StaticData.brand_list.clear();
			StaticData.category_selected.clear();
			//UtilMethod.showToast("Category List Size is "+StaticData.category_list.size(), getActivity());
			for(int i=0;i<StaticData.category_list.size();i++){

				String url=WebService.WEB_HOST_URL+"jsonproduct/"+StaticData.category_list.get(i).getLink()+"/?type="+StaticData.category_list.get(i).getLink_value()+"&catid="+StaticData.category_list.get(i).getSubcategory_id()+"&urlcheck=1&userid="+getActivity().getSharedPreferences("User_login",1).getString("user_id", null);


				//UtilMethod.showToast("URL for Price is "+url, getActivity());
				new GetPriceComparisonTask(getActivity(), url,i,new PriceComparisonListener()).execute();
				/*if(i==StaticData.category_list.size()-1){
					onSuccessList();
				}*/
				//pricecomparison_list.setAdapter(new PriceComparisonAdapter(getActivity()));
			}
			/*if()*/


		}
		public void onfavSuccess(){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage("Add Favourite Product Successfully!");
			adialog.setCancelable(false);
			adialog.setButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					whole_layout.removeAllViews();
					category_list.setAdapter(new CategoryAdapter(getActivity(), StaticData.category_fixed_list));
					//UtilMethod.showToast("Category Selected List Size is"+StaticData.category_selected.size(), getActivity());
					LinearLayout ll=new LinearLayout(getActivity());
					//UtilMethod.showToast("Category Selected Listsize is"+StaticData.category_selected.size(), getActivity());
					LinearLayout[] parentlayout=new LinearLayout[StaticData.category_selected.size()];
					//	UtilMethod.showToast("Parent Layout Length is "+parentlayout.length, getActivity());
					for(int i=0;i<parentlayout.length;i++){
						parentlayout[i]=new LinearLayout(getActivity());
						parentlayout[i].setId(i); 
						final int position=i;
						LayoutInflater inflater1=LayoutInflater.from(getActivity());
						View view1=inflater1.inflate(R.layout.activity_pricencomparison_item,null);
						TextView header_text=(TextView) view1.findViewById(R.id.header_text);
						ImageView view_all=(ImageView) view1.findViewById(R.id.view_all);
						horizontal_list=(HorizontalListView) view1.findViewById(R.id.horizontal_list1);
						StaticData.product_comparison_list.clear();
						ArrayList<PriceComparisonBean> value=new ArrayList<PriceComparisonBean>();
						for(int j=0;j<StaticData.product_price_list.size();j++){
							/*for(int k=0;k<StaticData.category_selected.size();k++){*/
							if(StaticData.category_selected.get(i).getSubcategory_id().equals(StaticData.product_price_list.get(j).getSubcategory_id())){
								value.add(StaticData.product_price_list.get(j));
								/*break;*/
								/*}*/

							}
						}
						view_all.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Fragment fragment=new PriceComparisonGridFragment();
								FragmentManager fmanager=getActivity().getSupportFragmentManager();
								Bundle b=new Bundle();
								b.putString("link",StaticData.category_selected.get(position).getLink());
								b.putString("link_value",StaticData.category_selected.get(position).getLink_value());
								b.putString("subcategory_id",StaticData.category_selected.get(position).getSubcategory_id());
								b.putString("subcategory_name", StaticData.category_selected.get(position).getSubcategory_name());
								b.putInt("position",position);
								fragment.setArguments(b);
								FragmentTransaction ft=fmanager.beginTransaction();
								ft.addToBackStack(null);
								ft.replace(R.id.container, fragment);
								ft.commit();
							}
						});

						category_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
								// TODO Auto-generated method stub
								Fragment f=new PriceComparisonCategoryFragment();
								Bundle b=new Bundle();
								b.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
								f.setArguments(b);
								FragmentManager fmanager=getActivity().getSupportFragmentManager();
								FragmentTransaction ft=fmanager.beginTransaction();
								ft.replace(R.id.container, f);
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
									onSetChangeEditTextBlank();
								}
								else{
									onSetChangeEditText(s.toString());
								}
							}
						});
						/*if(StaticData.category_selected.get(i).getSubcategory_name().equals("Price Comparison")){
						header_text.setText(StaticData.category_selected.get(i).getCategory_name().toUpperCase());
						PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),value,StaticData.category_selected.get(i).getCategory_name());
						horizontal_list.setAdapter(pciadapter);
						pciadapter.notifyDataSetChanged();
						}
						else{

						 */				
						sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
						header_text.setText(StaticData.category_selected.get(i).getSubcategory_name().toUpperCase());
						PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name()),StaticData.category_selected.get(i).getSubcategory_name());
						ArrayList<PriceComparisonBean> list=sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name());
						UtilMethod.showToast("Dynamic List Size is "+list.size(), getActivity());
						horizontal_list.setAdapter(pciadapter);
						pciadapter.notifyDataSetChanged();
						/*}*/

						//parentlayout[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
						parentlayout[i].addView(view1);
						whole_layout.addView(parentlayout[i]);

					}
				}
			});
			adialog.show();
		}

		public void onError(String msg){

			/*if(pdialog!=null && pdialog.isShowing()){
					pdialog.dismiss();
				}*/
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
				adialog.setButton("OK",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
					}
				});
				adialog.show();
			}
			/*UtilMethod.showToast("Come in exception", getActivity());*/
		}
		public void onSuccessList(){
			/*if(pdialog!=null && pdialog.isShowing()){
				pdialog.dismiss();
			}*/
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			//UtilMethod.showToast("Category Selected Size is "+StaticData.category_selected.size(), getActivity());
			category_list.setAdapter(new CategoryAdapter(getActivity(), StaticData.category_fixed_list));
			//UtilMethod.showToast("Category Selected List Size is"+StaticData.category_selected.size(), getActivity());
			LinearLayout ll=new LinearLayout(getActivity());
			//UtilMethod.showToast("Category Selected Listsize is"+StaticData.category_selected.size(), getActivity());
			LinearLayout[] parentlayout=new LinearLayout[StaticData.category_selected.size()];
			//	UtilMethod.showToast("Parent Layout Length is "+parentlayout.length, getActivity());
			for(int i=0;i<parentlayout.length;i++){
				parentlayout[i]=new LinearLayout(getActivity());
				parentlayout[i].setId(i); 
				final int position=i;
				LayoutInflater inflater1=LayoutInflater.from(getActivity());
				View view1=inflater1.inflate(R.layout.activity_pricencomparison_item,null);
				TextView header_text=(TextView) view1.findViewById(R.id.header_text);
				ImageView view_all=(ImageView) view1.findViewById(R.id.view_all);
				horizontal_list=(HorizontalListView) view1.findViewById(R.id.horizontal_list1);
				StaticData.product_comparison_list.clear();
				ArrayList<PriceComparisonBean> value=new ArrayList<PriceComparisonBean>();
				for(int j=0;j<StaticData.product_price_list.size();j++){
					/*for(int k=0;k<StaticData.category_selected.size();k++){*/
					if(StaticData.category_selected.get(i).getSubcategory_id().equals(StaticData.product_price_list.get(j).getSubcategory_id())){
						value.add(StaticData.product_price_list.get(j));
						/*break;*/
						/*}*/

					}
				}
				view_all.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Fragment fragment=new PriceComparisonGridFragment();
						FragmentManager fmanager=getActivity().getSupportFragmentManager();
						Bundle b=new Bundle();
						b.putString("link",StaticData.category_selected.get(position).getLink());
						b.putString("link_value",StaticData.category_selected.get(position).getLink_value());
						b.putString("subcategory_id",StaticData.category_selected.get(position).getSubcategory_id());
						b.putString("subcategory_name", StaticData.category_selected.get(position).getSubcategory_name());
						b.putInt("position",position);
						fragment.setArguments(b);
						FragmentTransaction ft=fmanager.beginTransaction();
						ft.addToBackStack(null);
						ft.replace(R.id.container, fragment);
						ft.commit();
					}
				});

				category_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Fragment f=new PriceComparisonCategoryFragment();
						Bundle b=new Bundle();
						b.putString("category_name", StaticData.category_fixed_list.get(arg2).getCategory_name());
						f.setArguments(b);
						FragmentManager fmanager=getActivity().getSupportFragmentManager();
						FragmentTransaction ft=fmanager.beginTransaction();
						ft.replace(R.id.container, f);
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
							onSetChangeEditTextBlank();
						}
						else{
							onSetChangeEditText(s.toString());
						}
					}
				});

				/*if(StaticData.category_selected.get(i).getSubcategory_name().equals("Price Comparison")){
				header_text.setText(StaticData.category_selected.get(i).getCategory_name().toUpperCase());
				PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),value,StaticData.category_selected.get(i).getCategory_name());
				horizontal_list.setAdapter(pciadapter);
				pciadapter.notifyDataSetChanged();
				}
				else{

				 */				
				sHelper=new sqlHelper(getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
				header_text.setText(StaticData.category_selected.get(i).getSubcategory_name().toUpperCase());
				PriceComparisonItemAdapter pciadapter=new PriceComparisonItemAdapter(getActivity(),new PriceComparisonListener(),sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name()),StaticData.category_selected.get(i).getSubcategory_name());
				ArrayList<PriceComparisonBean> list=sHelper.getProductValue(StaticData.category_selected.get(i).getSubcategory_name());
				//UtilMethod.showToast("Dynamic List Size is "+list.size(), getActivity());
				horizontal_list.setAdapter(pciadapter);
				pciadapter.notifyDataSetChanged();
				/*}*/

				//parentlayout[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				parentlayout[i].addView(view1);
				whole_layout.addView(parentlayout[i]);

			}

			//for(int )


			//pricecomparison_list.setAdapter(new PriceComparisonAdapter(getActivity()));
		}
		public void onSuccessViewDetail(String product_id,String category_name1){
			Fragment fragment=new PriceComparisonSellerFragment();
			Bundle b=new Bundle();
			b.putString("product_id", product_id);
			SharedPreferences shpf=getActivity().getSharedPreferences("User_login", 1);
			Editor edt=shpf.edit();
			edt.putString("category_name",category_name1);
			edt.commit();
			fragment.setArguments(b);
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			ft.addToBackStack(null);
			ft.replace(R.id.container, fragment);
			ft.commit();
		}

		public void onAddFavouriteItem(String product_id,String purpose1){
			try{
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("pdid", new StringBody(product_id));
				multipart.addPart("pdty", new StringBody(purpose1.toLowerCase()));
				multipart.addPart("userid", new StringBody(getActivity().getSharedPreferences("User_login",1).getString("user_id", null)));
				multipart.addPart("extension",new StringBody("1"));
				/*if(purpose1.equalsIgnoreCase("Top Mobiles") || purpose1.equalsIgnoreCase("Hot Mobiles") || ){
				UtilMethod.showToast("Purpose is "+purpose1, getActivity());
				multipart.addPart("pdty", new StringBody("mobile"));	
			}
			else if(purpose1.equalsIgnoreCase("camera")){
				UtilMethod.showToast("Purpose is "+purpose1, getActivity());
				multipart.addPart("pdty", new StringBody("camera"));
			}
			else if(purpose1.equalsIgnoreCase("computer")){
				UtilMethod.showToast("Purpose is "+purpose1, getActivity());
				multipart.addPart("pdty", new StringBody("computer"));
			}
			else if(purpose1.equalsIgnoreCase("Tablets")){
				UtilMethod.showToast("Purpose is "+purpose1, getActivity());
				multipart.addPart("pdty", new StringBody("tablet"));
			}*/
				setProgressDialog();
				new FavouriteProductTask(getActivity(), multipart,new PriceComparisonListener(),product_id).execute();
			}
			catch(Exception e){

			}

		}

	}
}
