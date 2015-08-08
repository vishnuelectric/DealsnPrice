package com.dealnprice.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.dnp.adapter.CustomPagerAdapter;



public class TakeATourActivity extends Activity {

/**
 * This is Take a Tour Screen for DNP Application
 * @author Jayant
 *
 */
	public int[] mTakeTourImages	=	null;       //Image Array
	private ViewPager pager			=	null;
	private ImageView dot1			=	null;
	private ImageView dot2			=	null;
	private ImageView dot3			=	null;
	private ImageView dot4			=	null;
	private ImageView dot5			=	null;
	private TextView  skipTV		=	null;
	private TextView  nextTV		=	null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeatour);
		mTakeTourImages	=	new int[]{
				R.drawable.slid1,
				R.drawable.slid2,
				R.drawable.slid3,
				R.drawable.slid4,
				R.drawable.slid5        		
		};
		bindViews(); // find views by id
		Typeface tf	=	Typeface.createFromAsset(getAssets(), "HELVETICANEUELIGHT.TTF");
		skipTV.setTypeface(tf);
		nextTV.setTypeface(tf);
		setTouchListeners();
		CustomPagerAdapter mPagerAdapter	=	new CustomPagerAdapter(this,mTakeTourImages);
		pager.setAdapter(mPagerAdapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					dot1.setImageResource(R.drawable.dot_selected);
					dot2.setImageResource(R.drawable.dot_unselected);
					dot3.setImageResource(R.drawable.dot_unselected);
					dot4.setImageResource(R.drawable.dot_unselected);
					dot5.setImageResource(R.drawable.dot_unselected);
					break;

				case 1:
					dot1.setImageResource(R.drawable.dot_unselected);
					dot2.setImageResource(R.drawable.dot_selected);
					dot3.setImageResource(R.drawable.dot_unselected);
					dot4.setImageResource(R.drawable.dot_unselected);
					dot5.setImageResource(R.drawable.dot_unselected);
					break;

				case 2:
					dot1.setImageResource(R.drawable.dot_unselected);
					dot2.setImageResource(R.drawable.dot_unselected);
					dot3.setImageResource(R.drawable.dot_selected);
					dot4.setImageResource(R.drawable.dot_unselected);
					dot5.setImageResource(R.drawable.dot_unselected);
					break;

				case 3:
					dot1.setImageResource(R.drawable.dot_unselected);
					dot2.setImageResource(R.drawable.dot_unselected);
					dot3.setImageResource(R.drawable.dot_unselected);
					dot4.setImageResource(R.drawable.dot_selected);
					dot5.setImageResource(R.drawable.dot_unselected);
					
					break;
				case 4:
					dot1.setImageResource(R.drawable.dot_unselected);
					dot2.setImageResource(R.drawable.dot_unselected);
					dot3.setImageResource(R.drawable.dot_unselected);
					dot4.setImageResource(R.drawable.dot_unselected);
					dot5.setImageResource(R.drawable.dot_selected);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}

	private void setTouchListeners() {
		
		nextTV.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					nextTV.setTextColor(Color.GRAY);
					break;
				case MotionEvent.ACTION_UP:
					nextTV.setTextColor(Color.WHITE);
					moveNext();
					
					break;

				default:
					break;
				}
				return true;
			}
		});
	}

	/**
	 * Bind Views here ->findViewById
	 */
	private void bindViews() {
		pager		=	(ViewPager) findViewById(R.id.pager_taketour);
		dot1		=	(ImageView) findViewById(R.id.dot1IV);
		dot2		=	(ImageView) findViewById(R.id.dot2IV);
		dot3		=	(ImageView) findViewById(R.id.dot3IV);
		dot4		=	(ImageView) findViewById(R.id.dot4IV);
		dot5		=	(ImageView) findViewById(R.id.dot5IV);
		nextTV		=	(TextView) findViewById(R.id.takeTour_nextBtn);
		skipTV		=	(TextView) findViewById(R.id.takeTour_skipBtn);
	}
	
	/**
	 * Handle Click Events Here
	 */
	public void handleOnClick(View v)
	{
		switch (v.getId()) {
		
		case R.id.takeTour_skipBtn:
			finish();
			break;
		default:
			break;
		}
	}
		
	/**
	 * Use this method: Scroll to next page in View Pager
	 */
	public void moveNext() {
	    //it doesn't matter if you're already in the last item
	    pager.setCurrentItem(pager.getCurrentItem() + 1,true);
	}

	/**
	 * Use this method if required for future use or if functionality changes from Skip to Previous
	 * @param view
	 */
	public void movePrevious() {
	    //it doesn't matter if you're already in the first item
	    pager.setCurrentItem(pager.getCurrentItem() - 1,true);
	}
}
