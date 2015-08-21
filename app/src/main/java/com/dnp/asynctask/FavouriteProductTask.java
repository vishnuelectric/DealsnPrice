package com.dnp.asynctask;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import com.dnp.data.HttpRequest;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.data.sqlHelper;
import com.dnp.fragment.PriceComparisonAlternativeFragment.PCAListener;
import com.dnp.fragment.PriceComparisonCategoryFragment.PCCListener;
import com.dnp.fragment.PriceComparisonFragment.PriceComparisonListener;
import com.dnp.fragment.PriceComparisonGridFragment.PriceComparisonGridListener;

import android.content.Context;
import android.os.AsyncTask;

public class FavouriteProductTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;
	PriceComparisonListener pcListener;
	PCAListener pcalistener;
	PCCListener pcclistener;
	PriceComparisonGridListener pcglistener;
	String product_id;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
    sqlHelper sHelper;
    int position;

	public FavouriteProductTask(Context cxt,MultipartEntity multipart, PriceComparisonListener pclistener,String product_id){
		this.cxt=cxt;
		this.multipart=multipart;
		this.pcListener=pclistener;
		this.product_id=product_id;
	}
	public FavouriteProductTask(Context cxt,MultipartEntity multipart, PCAListener pcalistener,String product_id,int position){
		this.cxt=cxt;
		this.multipart=multipart;
		this.pcalistener=pcalistener;
		this.position=position;
	}
	public FavouriteProductTask(Context cxt,MultipartEntity multipart, PriceComparisonGridListener pcglistener,String product_id,int position){
		this.cxt=cxt;
		this.multipart=multipart;
		this.pcglistener=pcglistener;
		this.product_id=product_id;
		this.position=position;
	}
	public FavouriteProductTask(Context cxt,MultipartEntity multipart, PCCListener pcclistener,String product_id){
		this.cxt=cxt;
		this.multipart=multipart;
		this.pcclistener=pcclistener;
		this.product_id=product_id;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(WebService.FAVOURITE_WEBSERVICE, multipart);
		return response;
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
			//UtilMethod.showToast("Result is "+result, cxt);
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("message");
			//UtilMethod.showToast("Favourite Result is "+result, cxt);
			if(status.equals("1")){
				if(pcListener!=null){
					sHelper=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					sHelper.update_favstatus(product_id, 1);
				pcListener.onfavSuccess();
				}
				else if(pcalistener!=null){
					sHelper=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					sHelper.update_favstatus(product_id, 1);
					pcalistener.onfavSuccess(position);
				}
				else if(pcglistener!=null){
					sHelper=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					sHelper.update_favstatus(product_id, 1);
					pcglistener.onFavSuccess(position);
				}
				else if(pcclistener!=null){
					sHelper=new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					sHelper.update_favstatus(product_id, 1);
					pcclistener.onSuccessFav();
				}
				
			}
			else{
				pcListener.onError("some little issues");
			}
			
			}
			catch(Exception e){
				
			}
		}
		else{
			
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
