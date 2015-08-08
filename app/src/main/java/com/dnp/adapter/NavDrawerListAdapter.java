package com.dnp.adapter;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.bean.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;

	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)
					context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}
		LinearLayout whole_layout=(LinearLayout) convertView.findViewById(R.id.whole_layout);
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		ImageView arrow=(ImageView) convertView.findViewById(R.id.arrow);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		//TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

		LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) imgIcon.getLayoutParams();
		layoutParam.setMargins(30, 0, 0, 0);
		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		if(navDrawerItems.get(position).getTitle().equals("EARN") || navDrawerItems.get(position).getTitle().equals("REDEEM") || navDrawerItems.get(position).getTitle().equals("SHOPPING SERVICES") || navDrawerItems.get(position).getTitle().equals("NOTIFICATIONS") || navDrawerItems.get(position).getTitle().equals("MY ACCOUNT") || navDrawerItems.get(position).getTitle().equals("FAQs") || navDrawerItems.get(position).getTitle().equals("CONTACT US") || navDrawerItems.get(position).getTitle().equals("TAKE A TOUR") || navDrawerItems.get(position).getTitle().equals("ABOUT US") || navDrawerItems.get(position).getTitle().equals("RATE US") || navDrawerItems.get(position).getTitle().equals("SETTINGS") || navDrawerItems.get(position).getTitle().equals("LOG OUT")){
			if(navDrawerItems.get(position).getTitle().equals("EARN") || navDrawerItems.get(position).getTitle().equals("REDEEM") || navDrawerItems.get(position).getTitle().equals("SHOPPING SERVICES")){
				arrow.setVisibility(View.GONE);
			}
			else{
				arrow.setVisibility(View.VISIBLE);
			}
			imgIcon.setVisibility(View.GONE);
			txtTitle.setVisibility(View.VISIBLE);
			txtTitle.setText(navDrawerItems.get(position).getTitle());
			whole_layout.setBackgroundColor(context.getResources().getColor(R.color.side_high_light_bar));
			txtTitle.setTextSize(15);
		}
		else{
			imgIcon.setVisibility(View.VISIBLE);
			txtTitle.setVisibility(View.VISIBLE);
			arrow.setVisibility(View.VISIBLE);
			whole_layout.setBackgroundColor(context.getResources().getColor(R.color.side_bar_bg));
			if(navDrawerItems.get(position).getTitle().equals("Refer Earn")){
				txtTitle.setText("Refer & Earn");
			}
			else if(navDrawerItems.get(position).getTitle().equals("Shop Earn")){
				txtTitle.setText("Shop & Earn");
			}
			else if(navDrawerItems.get(position).getTitle().equals("Deals Coupons")){
				txtTitle.setText("Deals & Coupons");
			}
			else{
				txtTitle.setText(navDrawerItems.get(position).getTitle());
			}
			txtTitle.setTextSize(12);
		}


		// displaying count
		// check whether it set visible or not
		/*if(navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }*/

		return convertView;
	}

}
