package com.dnp.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.androidquery.AQuery;
import com.dealnprice.activity.R;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.fragment.OfferFragment.OfferListener;

public class AppListAdapter extends BaseAdapter{
	Context cxt;
	LayoutInflater inflater;
	OfferListener olistener;
	boolean read_flag=true;
	ViewHolder vholder;
	float amount=0;
	public AppListAdapter(Context cxt,OfferListener olistener){
		this.cxt=cxt;
		inflater=LayoutInflater.from(cxt);
		this.olistener=olistener;
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
		convertView=inflater.inflate(R.layout.activity_offer_item, parent, false);
		
		vholder=new ViewHolder();
		vholder.app_name=(TextView) convertView.findViewById(R.id.app_name);
		//TextView install_text=(TextView) view.findViewById(R.id.install_text);
		vholder.app_detail=(TextView) convertView.findViewById(R.id.app_description);
		vholder.install=(LinearLayout) convertView.findViewById(R.id.install);
		vholder.install_text=(TextView) convertView.findViewById(R.id.install_text);
		vholder.myrating=(ImageView) convertView.findViewById(R.id.myrating);
		vholder.upto_text=(TextView) convertView.findViewById(R.id.upto_text);
		vholder.install_image=(ImageView) convertView.findViewById(R.id.install_image);
		vholder.amount=(TextView) convertView.findViewById(R.id.app_cost);
		convertView.setTag(vholder);
		}
		else{
			vholder=(ViewHolder) convertView.getTag();
		}
		vholder.app_name.setText(StaticData.application_list.get(position).getApp_name());
		if(StaticData.application_list.get(position).getPurpose_id()==1){
			vholder.install_text.setText("Install");
			vholder.install.setBackgroundDrawable(cxt.getResources().getDrawable(R.drawable.deal_install_bg_rounded));
			vholder.install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.download));
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
		
		if(StaticData.application_list.get(position).getAmount_type().equals("0")){
			/*if(read_flag){*/
			String value2;
			if(StaticData.application_list.get(position).getPurpose_id()==3){
				value2="Download and instatll "+StaticData.application_list.get(position).getApp_name()+".Run it for 1 minute and get "+StaticData.application_list.get(position).getShare_amount()+" Rs.";
			}
			else{
				value2="Download and instatll "+StaticData.application_list.get(position).getApp_name()+".Run it for 1 minute and get "+StaticData.application_list.get(position).getAmount_per_open()+" Rs.";	
			}
			
			/*final String value=value2.substring(0, 30)+"Read More";
			int startIndex = value.indexOf("Read More");
			int endIndex = value.length();
			vholder.app_detail.setText(value);
			vholder.app_detail.setMovementMethod(LinkMovementMethod.getInstance());
			vholder.app_detail.setText(value, BufferType.SPANNABLE);

			Spannable mySpannable = (Spannable) vholder.app_detail.getText();
			ClickableSpan myClickableSpan = new ClickableSpan() {
			    @Override
			    public void onClick(View widget) {

			        System.out.println("show terms of use box --------  ");
			       
			        homescreenActivity.showTermsOfService();
			        vholder.app_detail.setTag(vholder);
			       onLess(position,Float.valueOf(StaticData.application_list.get(position).getAmount_per_open()));
			       
			    }
			};

			mySpannable.setSpan(myClickableSpan, startIndex, endIndex,
			        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			else{
				
			}*/
			vholder.app_detail.setText(value2);
			/*if(StaticData.application_list.get(position).getPurpose_id()==1){
			vholder.upto_text.setVisibility(View.GONE);
			}
			else{
				vholder.upto_text.setVisibility(View.GONE);
			}*/
			if(StaticData.application_list.get(position).getApp_type().equals("normal")){
				vholder.upto_text.setText("INSTALL & GET");
				vholder.amount.setText(""+StaticData.application_list.get(position).getAmount_per_install());
			}
			else if(StaticData.application_list.get(position).getApp_type().equals("upto")){
				vholder.upto_text.setText("UPTO");
				for(int k=0;k<StaticData.full_upto_amount.size();k++){
					if(StaticData.upto_list.get(k).getApp_id().equals(StaticData.application_list.get(position).getApp_id())){
						if(!UtilMethod.isStringNullOrBlank(StaticData.upto_list.get(k).getUpto_amount())){
							amount+=Float.valueOf(StaticData.upto_list.get(k).getUpto_amount());
						}
					}
				}
				vholder.amount.setText(""+amount);
			}
			else if(StaticData.application_list.get(position).getPurpose_id()==3){
				vholder.amount.setText(StaticData.application_list.get(position).getShare_amount());
			}
			/*else{
			vholder.amount.setText(StaticData.application_list.get(position).getAmount_per_open());
			}*/
		}
		else{
			final int value=Integer.valueOf(StaticData.application_list.get(position).getAmount_per_open())+Integer.valueOf(String.valueOf(StaticData.application_list.get(position).getAmount_per_install()));
			vholder.upto_text.setVisibility(View.VISIBLE);
			vholder.app_detail.setText("Download and instatll "+StaticData.application_list.get(position).getApp_name()+".Run it for 1 minute and get "+value+" Rs.");
			
			/*if(read_flag){
			Spannable mySpannable = (Spannable) vholder.app_detail.getText();
			ClickableSpan myClickableSpan = new ClickableSpan() {
			    @Override
			    public void onClick(View widget) {

			        System.out.println("show terms of use box --------  ");
			       
			        homescreenActivity.showTermsOfService();
			       onLess(position,value);
			       
			    }
			};

			mySpannable.setSpan(myClickableSpan, startIndex, endIndex,
			        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			else{
				
			}*/
			
			vholder.app_detail.setText("Download and instatll "+StaticData.application_list.get(position).getApp_name()+".Run it for 1 minute and get "+value+" Rs.");
			vholder.amount.setText(""+value);
		}
		
		if(StaticData.application_list.get(position).getApp_rate()>0){
		/*myrating.setRating(StaticData.application_list.get(position).getApp_rate());
		LayerDrawable stars = (LayerDrawable) myrating.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.YELLOW,);*/
			if(StaticData.application_list.get(position).getApp_rate()==0.5){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s0_5_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==1){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s1_star));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==1.5){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s1));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==2){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s2_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==2.5){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s2));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==3){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s3_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==3.5){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s3));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==4){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s4_stars));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==4.5){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s4));
			}
			else if(StaticData.application_list.get(position).getApp_rate()==5){
				vholder.myrating.setImageDrawable(cxt.getResources().getDrawable(R.drawable.s5_stars));
			}
			
		}
		AQuery aq = new AQuery(convertView);
        aq.id(R.id.app_image).image(StaticData.application_list.get(position).getApp_image(),
            true, true, 0, R.drawable.ic_launcher, null,
            AQuery.CACHE_DEFAULT,0.0f).visible();
        /*if(StaticData.application_list.get(position).getCampaign_status().equals("0")){
        	install_text.setText("SHARE");
        	install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.sharing_icon));
        }
        else{*/
        	
        	//install_image.setImageDrawable(cxt.getResources().getDrawable(R.drawable.download));
        /*}*/
        
        
        /*install_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				olistener.setSelect(position);
				
			}
		});*/
        vholder.install.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*if(UtilMethod.isNetworkAvailable(cxt)){
				if(!StaticData.application_list.get(position).getCampaign_status().equals("0"))
				try{
					
					
					
				MultipartEntity multipart=new MultipartEntity();
				SharedPreferences shpf=cxt.getSharedPreferences("User_login",1);
				
				multipart.addPart("user_id", new StringBody(shpf.getString("user_id",null)));
				multipart.addPart("app_id",new StringBody(StaticData.application_list.get(position).getApp_id()));
				multipart.addPart("app_status",new StringBody("2"));
				new ReadAppTask(cxt, multipart, StaticData.application_list.get(position).getApp_url(),olistener,position).execute();
				}
				catch(Exception e){
					
				}
				else{
				
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StaticData.application_list.get(position).getApp_url()));
				cxt.startActivity(myIntent);
				}
				}
				else{
					UtilMethod.showNetworkError(cxt);
				}*/
				olistener.onAppClick(position);
			}
		});
        
		return convertView;
	}
	public void onLess(final int ar,final float price){
		String value2="Download and instatll "+StaticData.application_list.get(ar).getApp_name()+".Run it for 1 minute and get "+price+" Rs.";
		String value=value2+"Read Less";
		int startIndex = value.indexOf("Read Less");
		int endIndex = value.length();
		vholder.app_detail.getTag();
	//	vholder.app_detail.setText(value);
		vholder.app_detail.setMovementMethod(LinkMovementMethod.getInstance());
		vholder.app_detail.setText(value, BufferType.SPANNABLE);
		
		Spannable mySpannable1 = (Spannable) vholder.app_detail.getText();
		ClickableSpan myClickableSpan1 = new ClickableSpan() {
		    @Override
		    public void onClick(View widget) {
		    	vholder.app_detail.setTag(vholder);
		        onMore(ar,price);
		       
		        /*homescreenActivity.showTermsOfService();*/
		        
		       
		    }
		};

		mySpannable1.setSpan(myClickableSpan1, startIndex, endIndex,
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	}
	public void onMore(final int ar1,final float price){
		String value2="Download and instatll "+StaticData.application_list.get(ar1).getApp_name()+".Run it for 1 minute and get "+price+" Rs.";
		String value=value2.substring(0, 30)+"Read More";
		int startIndex = value.indexOf("Read More");
		int endIndex = value.length();
		vholder.app_detail.getTag();
		vholder.app_detail.setText(value);
		vholder.app_detail.setMovementMethod(LinkMovementMethod.getInstance());
		vholder.app_detail.setText(value, BufferType.SPANNABLE);
		Spannable mySpannable2 = (Spannable) vholder.app_detail.getText();
		ClickableSpan myClickableSpan2 = new ClickableSpan() {
		    @Override
		    public void onClick(View widget) {

		        System.out.println("show terms of use box --------  ");
		       
		        /*homescreenActivity.showTermsOfService();*/
		        vholder.app_detail.setTag(vholder);
		       onLess(ar1,price);
		       
		    }
		};

		mySpannable2.setSpan(myClickableSpan2, startIndex, endIndex,
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	}

	private static class ViewHolder{
		public TextView app_name,install_text,amount;
		public ImageView myrating,install_image;
		public LinearLayout install;
		public TextView app_detail;
		public TextView upto_text;
	}
}
