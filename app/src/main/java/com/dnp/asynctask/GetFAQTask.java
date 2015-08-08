package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.FAQBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.WebService;
import com.dnp.fragment.FAQFragment.FAQListener;

import android.content.Context;
import android.os.AsyncTask;

public class GetFAQTask extends AsyncTask<String, Void, String>{
	Context cxt;
	FAQListener fListener;
	public GetFAQTask(Context cxt,FAQListener flistener){
		this.cxt=cxt;
		this.fListener=flistener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String result=HttpRequest.post(WebService.FAQ_WEBSERVICE);
		return result;
		}
		catch(Exception e){
			
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null){
			StaticData.faq_list.clear();
			try{
			JSONObject jobj=new JSONObject(result);
			JSONArray jarray=jobj.getJSONArray("steplist");
			for(int i=0;i<jarray.length();i++){
				JSONObject step_object=jarray.getJSONObject(i);
				JSONArray step_value=step_object.getJSONArray("value");
				for(int j=0;j<step_value.length();j++){
					JSONObject value_object=step_value.getJSONObject(j);
					FAQBean fbean=new FAQBean();
					fbean.setQuestion(value_object.getString("que"));
					fbean.setAnswer(value_object.getString("ans"));
					StaticData.faq_list.add(fbean);
				}
				
			}
			
			fListener.onSuccess();
			}
			catch(Exception e){
				
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
