package com.dnp.fragment;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.DashboardActivity;
import com.dealnprice.activity.R;
import com.dnp.adapter.AllPCGridAdapter;
import com.dnp.asynctask.FavouriteProductTask;
import com.dnp.asynctask.GetAllPriceComparisonTask;
import com.dnp.asynctask.Pending_amount;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;

public class PriceComparisonGridFragment extends Fragment{
	GridView category_grid;
	TextView category_header;
	Bundle b;
	String link,link_value,subcategory_id,url,subcategory_name;
	int position;
	LinearLayout offer_layout,shopearn_layout,pricecomparison_layout,dealcoupon_layout,referearn_layout,footer_layout;
	TextView offer_text,shopearn_text,pricecomparison_text,dealcoupon_text,referearn_text;
	Fragment fragment;
	FragmentManager fmanager;
	FragmentTransaction ft;
	Dialog dialog;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	HorizontalScrollView horizontal_id;
	AllPCGridAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_pc_grid, container, false);
		category_grid=(GridView) view.findViewById(R.id.category_grid);
		category_header=(TextView) view.findViewById(R.id.category_header);
		b=getArguments();
		link=b.getString("link");
		link_value=b.getString("link_value");
		subcategory_id=b.getString("subcategory_id");
		subcategory_name=b.getString("subcategory_name");
		position=b.getInt("position");
		category_header.setText(subcategory_name);
		offer_layout=(LinearLayout) view.findViewById(R.id.offer_layout);
		horizontal_id=(HorizontalScrollView) view.findViewById(R.id.horizontal_id);
		shopearn_layout=(LinearLayout) view.findViewById(R.id.shopearn_layout);
		pricecomparison_layout=(LinearLayout) view.findViewById(R.id.pricecomparison_layout);
		dealcoupon_layout=(LinearLayout) view.findViewById(R.id.dealprice_layout);
		referearn_layout=(LinearLayout) view.findViewById(R.id.referearn_layout);
		shopearn_text=(TextView) view.findViewById(R.id.shopearn_text);
		//pricecomparison_list=(ListView) view.findViewById(R.id.pricecomparison_list);
		pricecomparison_text=(TextView) view.findViewById(R.id.pricecomparison_text);
		dealcoupon_text=(TextView) view.findViewById(R.id.dealprice_text);
		footer_layout=(LinearLayout) view.findViewById(R.id.footer_layout);
		referearn_text=(TextView) view.findViewById(R.id.referearn_text);
		fmanager=getActivity().getSupportFragmentManager();
		ft=fmanager.beginTransaction();
		//DashboardActivity.onCustomActionView();
		
		Pending_amount pp= new Pending_amount(getActivity());
		pp.execute();
		
		shopearn_text.setText("Shop & Earn");
		pricecomparison_text.setText("Price Comparison");
		dealcoupon_text.setText("Deals & Coupons");
		referearn_text.setText("Refer & Earn");
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
		profile_layout.setOnClickListener(profileListener);
		favourite_layout.setOnClickListener(favouriteListener);
		notification_layout.setOnClickListener(notificationListener);
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		v1.setLayoutParams(param);
		footer_layout.addView(v1);
		setProgressDialog();
		url=WebService.WEB_HOST_URL+"jsonproduct/"+link+"/?type="+link_value+"&catid="+subcategory_id+"&urlcheck=1&userid="+getActivity().getSharedPreferences("User_login", 1).getString("user_id", null);
		//UtilMethod.showToast("All PC Url is "+url, getActivity());
		//new GetPriceComparisonTask(getActivity(), url,i,new PriceComparisonListener()).execute();
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				horizontal_id.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			}
		});
		new GetAllPriceComparisonTask(getActivity(), url, position,new PriceComparisonGridListener()).execute();
		b=getArguments();
		
		return view;
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
	}*/
	
	private void setProgressDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
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

	public class PriceComparisonGridListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
			//	loadingViewAnim.stop();
				dialog.dismiss();
			}
			adapter=new AllPCGridAdapter(getActivity(), new PriceComparisonGridListener());
			category_grid.setAdapter(adapter);
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showServerError(getActivity());
			}
		}
		public void onViewDetail(String product_id,String category_name1){
			Fragment fragment=new PriceComparisonSellerFragment();
			FragmentManager fmanager=getActivity().getSupportFragmentManager();
			FragmentTransaction ft=fmanager.beginTransaction();
			Bundle b1=new Bundle();
			b1.putString("product_id",product_id);
			SharedPreferences shpf=getActivity().getSharedPreferences("User_login", 1);
			Editor edt=shpf.edit();
			edt.putString("category_name",category_name1);
			edt.commit();
			fragment.setArguments(b1);
			ft.replace(R.id.container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
		
		public void onAddFavourite(String product_id,String purpose,int position){
			try{
			MultipartEntity multipart=new MultipartEntity();
			multipart.addPart("pdid",new StringBody(product_id));
			multipart.addPart("pdty",new StringBody(purpose));
			multipart.addPart("extension", new StringBody("1"));
			multipart.addPart("userid",new StringBody(getActivity().getSharedPreferences("User_login", 1).getString("user_id",null)));
			setProgressDialog();
			new FavouriteProductTask(getActivity(), multipart, new PriceComparisonGridListener(),product_id,position).execute();
			}
			catch(Exception e){
				
			}
		}
		public void onFavSuccess(final int position){
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(getActivity()).create();
			adialog.setTitle("Message");
			adialog.setMessage("Add Favourite Product Successfully!");
			adialog.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					PriceComparisonBean pcbean=new PriceComparisonBean();
					/*pcbean.set*/
					pcbean.setProduct_name(StaticData.product_price_list.get(position).getProduct_name());
					pcbean.setPrice(StaticData.product_price_list.get(position).getPrice());
					pcbean.setProduct_image(StaticData.product_price_list.get(position).getProduct_image());
					pcbean.setProduct_id(StaticData.product_price_list.get(position).getProduct_id());
					pcbean.setCategory_name(StaticData.product_price_list.get(position).getCategory_name());
					pcbean.setFav_status(1);
					StaticData.product_price_list.set(position, pcbean);
					adapter=new AllPCGridAdapter(getActivity(), new PriceComparisonGridListener());
					category_grid.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
			});
			adialog.show();
		}
		
	}
}
