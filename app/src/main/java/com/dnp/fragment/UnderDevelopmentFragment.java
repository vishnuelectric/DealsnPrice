package com.dnp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dealnprice.activity.R;
import com.dnp.asynctask.GetCategoryTask;
import com.dnp.data.UtilMethod;

public class UnderDevelopmentFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_demo, container, false);
		UtilMethod.showToast("Under Development", getActivity());
		try{
		new GetCategoryTask(getActivity()).execute();
		}
		catch(Exception e){
			
		}
		return view;
	}
	
}
