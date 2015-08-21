package com.dnp.asynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dnp.bean.BrandBean;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.sqlHelper;
import com.dnp.fragment.PriceComparisonGridFragment.PriceComparisonGridListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetAllPriceComparisonTask extends AsyncTask<String, Void, String>{
	Context cxt;
	String url;
	PriceComparisonGridListener pcgListener;
	//ProgressDialog pdialog;
	int id;
	private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "DealsnPrice";
    sqlHelper sHelper;
	public GetAllPriceComparisonTask(Context cxt,String url,int id,PriceComparisonGridListener pcglistener){
		this.cxt=cxt;
		this.url=url;
		this.pcgListener=pcglistener;
		this.id=id;
		//pdialog=new ProgressDialog(cxt);
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		String response=HttpRequest.post(url);
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
		/*if(pdialog!=null && pdialog.isShowing()){
			pdialog.dismiss();
		}*/
		if(result!=null){
			try{
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("check");
			if(status.equals("1")){
				StaticData.brand_list.clear();
				StaticData.product_price_list.clear();
				JSONArray jarray=jobj.getJSONArray("brandlist");
				for(int i=0;i<jarray.length();i++){
					
					JSONObject brand_object=jarray.getJSONObject(i);
					BrandBean bbean=new BrandBean();
					bbean.setB_id(brand_object.getString("b_id"));
					bbean.setB_name(brand_object.getString("b_name"));
					StaticData.brand_list.add(bbean);
				}
				/*StaticData.category_selected.add(StaticData.category_list.get(id));*/
				JSONArray product_array=jobj.getJSONArray("product");
				for(int j=0;j<product_array.length();j++){
					JSONObject product_object=product_array.getJSONObject(j);
					PriceComparisonBean pcbean=new PriceComparisonBean();
					pcbean.setProduct_id(product_object.getString("pd_id"));
					pcbean.setProduct_name(product_object.getString("name"));
					pcbean.setPrice(product_object.getString("price"));
					pcbean.setBrand_id(product_object.getString("brand"));
					pcbean.setProduct_image(product_object.getString("image"));
					pcbean.setFav_status(product_object.getInt("fav"));
					pcbean.setSubcategory_id(StaticData.category_selected.get(id).getSubcategory_id());
					pcbean.setSubcategory_name(StaticData.category_selected.get(id).getSubcategory_name());
					pcbean.setCategory_name(StaticData.category_selected.get(id).getCategory_name());
					StaticData.product_price_list.add(pcbean);
					for(int i=0;i<StaticData.brand_list.size();i++){
						if(StaticData.brand_list.get(i).getB_id().equals(product_object.getString("brand"))){
					sHelper = new sqlHelper(cxt, DATABASE_NAME, null, DATABASE_VERSION);
					sHelper.Insert_ProductDetail(product_object.getString("pd_id"), product_object.getString("linkvalue"), product_object.getString("name"), product_object.getString("price"), StaticData.brand_list.get(i).getB_name(), product_object.getString("image"), product_object.getInt("fav"),StaticData.category_list.get(id).getSubcategory_name(),StaticData.category_list.get(id).getCategory_name(),Double.valueOf(product_object.getString("price")));
					break;
						}
					}
				}
				
				
				
				//UtilMethod.showToast("Product Size is "+id+" "+StaticData.product_price_list.size(), cxt);
				/*if(pcListener!=null){
					pcListener.onSuccess();
				}*/
				/*if(id==StaticData.category_list.size()){
					UtilMethod.showToast("Come in full success list", cxt);*/
					pcgListener.onSuccess(); 
				/*}*/
				}
			else{
				/*if(id==StaticData.category_list.size()){
					UtilMethod.showToast("Come in full success list", cxt);*/
					pcgListener.onSuccess();
				/*}*/
			}

			
			}
			catch(Exception e){
				
			}
		}
		else{
			pcgListener.onError("slow");
		}
			
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//UtilMethod.showLoading(pdialog, cxt);
		
	}

}
