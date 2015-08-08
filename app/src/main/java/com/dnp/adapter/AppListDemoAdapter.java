package com.dnp.adapter;

import java.util.StringTokenizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.dealnprice.activity.R;
import com.dnp.data.StaticData;
import com.dnp.fragment.OfferFragment.OfferListener;

public class AppListDemoAdapter extends BaseAdapter{
	Context cxt;
	ViewHolder vholder;
	LayoutInflater inflater;
	OfferListener oListener;
	float amount_value=0;

	public AppListDemoAdapter(Context cxt, OfferListener olistener){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
		this.oListener=olistener;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return StaticData.application_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			vholder=new ViewHolder();
			convertView=inflater.inflate(R.layout.activity_offer_item, parent, false);
			vholder.app_name=(TextView) convertView.findViewById(R.id.app_name);
			vholder.app_image=(ImageView) convertView.findViewById(R.id.app_image);
			vholder.myrating=(ImageView) convertView.findViewById(R.id.myrating);
			vholder.app_description=(TextView) convertView.findViewById(R.id.app_description);
			vholder.upto_text=(TextView) convertView.findViewById(R.id.upto_text);
			vholder.app_cost=(TextView) convertView.findViewById(R.id.app_cost);
			vholder.install=(LinearLayout) convertView.findViewById(R.id.install);
			vholder.install_text=(TextView) convertView.findViewById(R.id.install_text);
			vholder.install_image=(ImageView) convertView.findViewById(R.id.install_image);
			convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		int count=0;
		StringTokenizer st=new StringTokenizer(StaticData.application_list.get(position).getApp_short_description());
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			count++;
		}
		vholder.app_name.setText(StaticData.application_list.get(position).getApp_name());
		if(StaticData.application_list.get(position).getApp_short_description().length()>80){
			vholder.app_description.setText(StaticData.application_list.get(position).getApp_short_description().substring(0, 79)+"...");
		}
		else{
			vholder.app_description.setText(StaticData.application_list.get(position).getApp_short_description());
		}
		if(StaticData.application_list.get(position).getPurpose_id()==3){
			vholder.upto_text.setVisibility(View.GONE);
			vholder.app_cost.setText(""+StaticData.application_list.get(position).getAmount_per_install());
		}
		else if(StaticData.application_list.get(position).getPurpose_id()==4){
			vholder.upto_text.setVisibility(View.GONE);
			vholder.app_cost.setText(""+StaticData.application_list.get(position).getAmount_per_install());
		}
		else if(StaticData.application_list.get(position).getApp_type().equals("normal")){
			vholder.upto_text.setText("INSTALL & GET");
			vholder.upto_text.setVisibility(View.GONE);
			vholder.app_cost.setText(""+StaticData.application_list.get(position).getAmount_per_install());
		}
		else if(StaticData.application_list.get(position).getApp_type().equals("upto")){
			vholder.upto_text.setText("UPTO");
			vholder.upto_text.setVisibility(View.VISIBLE);
			/*for(int j=0;j<StaticData.upto_list.size();j++){
				if(!UtilMethod.isStringNullOrBlank(StaticData.upto_list.get(j).getUpto_amount()))
				amount_value+=Float.valueOf(StaticData.upto_list.get(j).getUpto_amount());
			}*/
			vholder.app_cost.setText(StaticData.application_list.get(position).getUptotalamount());
		}
		
		if(StaticData.application_list.get(position).getPurpose_id()==1){
			
			if(StaticData.application_list.get(position).getAppinstall().equals("null")){
				if(StaticData.application_list.get(position).getApp_type().equalsIgnoreCase("normal"))
				{
					vholder.install_text.setText("Install");
				}
				else
				{
					vholder.install_text.setText("install & getupto");
				}
			
			vholder.install.setBackgroundDrawable(cxt.getResources().getDrawable(R.drawable.deal_install_bg_rounded));
			vholder.install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.download));
			
			}
			else if(StaticData.application_list.get(position).getAppinstall().equals("1")){
				vholder.install_text.setText("In Progress");
				vholder.install.setBackgroundDrawable(cxt.getResources().getDrawable(R.drawable.deal_install_bg_rounded));
				vholder.install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.download));
				vholder.install_image.setVisibility(View.GONE);
			}
		}
		else if(StaticData.application_list.get(position).getPurpose_id()==2){
			vholder.install_text.setText("Refer");
			vholder.install.setBackgroundDrawable(cxt.getResources().getDrawable(R.drawable.deal_refer_bg_rounded));
			vholder.install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.share_refer));
		}
		else if(StaticData.application_list.get(position).getPurpose_id()==3){
			vholder.install_text.setText("Share");
			vholder.install.setBackgroundDrawable(cxt.getResources().getDrawable(R.drawable.deal_refer_bg_rounded));
			vholder.install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.share_refer));
		}
		else if(StaticData.application_list.get(position).getPurpose_id()==4){
			vholder.install_text.setText("Details");
			vholder.install.setBackgroundDrawable(cxt.getResources().getDrawable(R.drawable.deal_task_bg_rounded));
			vholder.install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.details));
		}
		if(StaticData.application_list.get(position).getApp_rate()>=0.5 && StaticData.application_list.get(position).getApp_rate()<1.0){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s0));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=1.0 && StaticData.application_list.get(position).getApp_rate()<1.5){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s1_star));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=1.5 && StaticData.application_list.get(position).getApp_rate()<2.0){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s1));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=2.0 && StaticData.application_list.get(position).getApp_rate()<2.5){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s2_stars));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=2.5 && StaticData.application_list.get(position).getApp_rate()<3.0){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s2));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=3.0 && StaticData.application_list.get(position).getApp_rate()<3.5){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s3_stars));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=3.5 && StaticData.application_list.get(position).getApp_rate()<4.0){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s3));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=4.0 && StaticData.application_list.get(position).getApp_rate()<4.5){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s4_stars));
		}
		else if(StaticData.application_list.get(position).getApp_rate()>=4.5 && StaticData.application_list.get(position).getApp_rate()<5.0){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s4));
		}
		else if(StaticData.application_list.get(position).getApp_rate()==5){
			vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s5_stars));
		}
		
		vholder.install.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oListener.onAppClick(position);
			}
		});
			
		AQuery aq = new AQuery(convertView);
        aq.id(R.id.app_image).image(StaticData.application_list.get(position).getApp_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
		
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView app_image;
		public TextView app_name;
		public ImageView myrating;
		public TextView app_description;
		public TextView upto_text;
		public TextView app_cost;
		public LinearLayout install;
		public TextView install_text;
		public ImageView install_image;
	}
}
