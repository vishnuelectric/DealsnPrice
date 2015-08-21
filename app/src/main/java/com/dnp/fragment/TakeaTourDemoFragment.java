/*package com.dnp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dealnprice.activity.R;

public class TakeaTourDemoFragment extends Fragment{
	ViewPager vPager;
	View view;
	
	int[] resource_list={
			R.drawable.display_1,
			R.drawable.display_2,
			R.drawable.display_3
			};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_takeatour, container, false);
		vPager=(ViewPager) view.findViewById(R.id.pager);
		vPager.setAdapter(new CustomPageAdapter());
		return view;
	}
	
	
	public class CustomPageAdapter extends PagerAdapter{
		
		LayoutInflater inflater1;
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return resource_list.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		@Override
	    public Object instantiateItem(ViewGroup container1, int position) {
			inflater1=LayoutInflater.from(getActivity());
	        View itemView = inflater1.inflate(R.layout.activity_takeatour_item, container1, false);
	 
	        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
	        imageView.setImageResource(resource_list[position]);
	 
	        container1.addView(itemView);
	 
	        return itemView;
	    }
	 
	    @Override
	    public void destroyItem(ViewGroup container2, int position, Object object) {
	        container2.removeView((LinearLayout) object);
	    }
	}
	
}
*/