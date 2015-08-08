package com.dnp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;







import com.dnp.bean.CouponBean;
import com.dnp.bean.DealBean;
import com.dnp.bean.PriceComparisonBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class sqlHelper extends SQLiteOpenHelper
{
	public static int Countrycount,StateCount,CityCount,JobProfileCount,Skillscount;
	
	  private static final int DATABASE_VERSION = 1;  
	    private static final String DATABASE_NAME = "DealsnPrice";  
	    Context cxt;

	public sqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.cxt=context;
		// TODO Auto-generated constructor stub
	}
	


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		  String CREATE_PRODUCT_TABLE = "CREATE TABLE " + "product_details" + "(id integer primary key autoincrement, "  
          + "pd_id" + " TEXT," + "linkvalue" + " TEXT," + "product_name" + " TEXT," + "product_price" + " TEXT," + "brand_name" + " TEXT," + "product_image" + " TEXT," + "fav_status" + " integer," + "subcategory_name" + " TEXT,"+" category_name" +" TEXT,"+"product_price_compare"+" double)";  
		  db.execSQL(CREATE_PRODUCT_TABLE);
		  
		  String CREATE_COUPON_TABLE = "CREATE TABLE coupons_details(id integer primary key autoincrement, coupon_id text, linkvalue text, name text, code text, store text, image text, coupon_end text, coupon_home text, coupon_store_url text)";
		  db.execSQL(CREATE_COUPON_TABLE);
		  
		  String CREATE_DEAL_TABLE = "CREATE TABLE deal_details(id integer primary key autoincrement, deal_id text, linkvalue text, name text, code text, store_id text, store_name text, image text, storeimage text, deals_end text, deals_home text, deals_mrp text, deals_selling text, deals_store_url text)";
		  db.execSQL(CREATE_DEAL_TABLE);
		  
		  String CREATE_SELECTED_PROFILE_TABLE = "CREATE TABLE profile_details(ID integer primary key autoincrement, F_ID text, PROFILE_SKILL text)";
		  db.execSQL(CREATE_SELECTED_PROFILE_TABLE);
		  
		  String CREATE_ALTERNATIVES_TABLE = "CREATE TABLE alternative_details(id integer primary key autoincrement, product_id text, fav integer, product_name text, product_image text, product_selling_price text, product_selling_price_compare double)";
		  db.execSQL(CREATE_ALTERNATIVES_TABLE);
		  
		  String CREATE_STORE_TABLE = "CREATE TABLE store_details(id integer primary key autoincrement, store_price text, store_color text, store_offer text, store_shipping text, store_cod text, store_deal_id text, store_image text, storurl text, store_coupon_offer text, store_discount_type text, store_offer_amount text, product_id text, product_brand text, product_name text, product_description text, imagepath text, product_image text, product_mrp text, product_selling_price text, product_feature text, variant_value text, product_selling_price_compare double)";
		  db.execSQL(CREATE_STORE_TABLE);
		  



	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void Insert_ProductDetail(String product_id, String linkvalue, String product_name, String product_price, String product_brand,String product_image,int fav_status,String subcategory_name,String category_name,double product_price_compare){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details where pd_id='"+product_id+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		if(csor.getCount()>0){
			SQLiteDatabase sqdbl1=this.getWritableDatabase();
			String sql1="update product_details SET linkvalue='"+linkvalue+"' and product_name='"+product_name+"' and product_price='"+product_price+"' and  brand_name='"+product_brand+"' and product_image='"+product_image+"' and subcategory_name='"+subcategory_name+"' and category_name='"+category_name+"' and product_price_compare="+product_price_compare+" and fav_status="+fav_status+" where pd_id='"+product_id+"'";
			sqdbl1.execSQL(sql1);
			sqdbl1.close();
		}	
		else{	
		SQLiteDatabase sqdbl = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("pd_id", product_id);
		values.put("linkvalue", linkvalue);
		values.put("product_name", product_name);
		values.put("product_price", product_price);
		values.put("brand_name", product_brand);
		values.put("product_image", product_image);
		values.put("fav_status", fav_status);
		values.put("subcategory_name", subcategory_name);
		values.put("category_name", category_name);
		values.put("product_price_compare", product_price_compare);
		sqdbl.insert("product_details", null, values);
		sqdbl.close();
		}
	}
	
	
	public void Insert_AlternativeDetail(String product_id, int fav, String product_name, String product_image, String product_selling_price,double product_selling_price_compare){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from alternative_details where product_id='"+product_id+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		if(csor.getCount()>0){
			SQLiteDatabase sqdbl1=this.getWritableDatabase();
			String sql1="update alternative_details SET  product_name='"+product_name+"' and product_selling_price='"+product_selling_price+"' and product_image='"+product_image+"' and product_selling_price_compare="+product_selling_price_compare+" and fav="+fav+" where product_id='"+product_id+"'";
			sqdbl1.execSQL(sql1);
			sqdbl1.close();
		}	
		else{	
		SQLiteDatabase sqdbl = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("product_id", product_id);
		values.put("product_name", product_name);
		values.put("product_selling_price", product_selling_price);
		values.put("product_image", product_image);
		values.put("fav", fav);
		values.put("product_selling_price_compare", product_selling_price_compare);
		sqdbl.insert("alternative_details", null, values);
		sqdbl.close();
		}
	}
	
	public void deleteAlternative_details(){
		SQLiteDatabase sqdb = this.getWritableDatabase();
		String sql="delete from alternative_details" ;
		
		sqdb.execSQL(sql);
		sqdb.close();
	}

	public void deleteStore_details(){
		SQLiteDatabase sqdb = this.getWritableDatabase();
		String sql="delete from store_details" ;
		sqdb.execSQL(sql);
		sqdb.close();
	}
	
	public void Insert_StoreDetail(String store_price, String store_color, String store_offer, String store_shipping, String store_cod, String store_deal_id, String store_image, String storurl, String stor_coupon_offer, String store_discount_type, String store_offer_amount, String product_id, String product_brand, String product_name, String product_description, String imagepath, String product_image, String product_mrp, String product_selling_price, String product_feature, String variant_value, double product_selling_price_compare){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from store_details where product_id='"+product_id+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		/*if(csor.getCount()>0){
			SQLiteDatabase sqdbl1=this.getWritableDatabase();
			String sql1="update coupons_details SET linkvalue='"+linkvalue+"' and name='"+name+"' and code='"+code+"' and  store='"+store+"' and image='"+image+"' and coupon_end='"+coupon_end+"' and coupon_home='"+coupon_home+"' and coupon_store_url='"+coupon_store_url+"' where coupon_id='"+coupon_id+"'";
			UtilMethod.showToast("Update sql is "+sql1, cxt);
			UtilMethod.showToast("Update sql is "+sql1, cxt);
			UtilMethod.showToast("Update sql is "+sql1, cxt);
			sqdbl1.execSQL(sql1);
			sqdbl1.close();
		}	
		else{*/	
		SQLiteDatabase sqdbl = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("store_price", store_price);
		values.put("store_color", store_color);
		values.put("store_offer", store_offer);
		values.put("store_shipping", store_shipping);
		values.put("store_cod",store_cod);
		values.put("store_deal_id", store_deal_id);
		values.put("store_image",store_image);
		values.put("storurl", storurl);
		values.put("store_coupon_offer",stor_coupon_offer);
		values.put("store_discount_type", store_discount_type);
		values.put("store_offer_amount", store_offer_amount);
		values.put("product_id", product_id);
		values.put("product_brand",product_brand);
		values.put("product_name", product_name);
		values.put("product_description", product_description);
		values.put("imagepath", imagepath);
		values.put("product_image", product_image);
		values.put("product_mrp", product_mrp);
		values.put("product_selling_price", product_selling_price);
		values.put("product_feature", product_feature);
		values.put("variant_value", variant_value);
		values.put("product_selling_price_compare", product_selling_price_compare);
		sqdbl.insert("store_details", null, values);
		sqdbl.close();
		/*}*/
	}
	
	
	public void Insert_CouponDetail(String coupon_id, String linkvalue, String name, String code, String store, String image,String coupon_end,String coupon_home,String coupon_store_url){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from coupons_details where coupon_id='"+coupon_id+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		/*if(csor.getCount()>0){
			SQLiteDatabase sqdbl1=this.getWritableDatabase();
			String sql1="update coupons_details SET linkvalue='"+linkvalue+"' and name='"+name+"' and code='"+code+"' and  store='"+store+"' and image='"+image+"' and coupon_end='"+coupon_end+"' and coupon_home='"+coupon_home+"' and coupon_store_url='"+coupon_store_url+"' where coupon_id='"+coupon_id+"'";
			UtilMethod.showToast("Update sql is "+sql1, cxt);
			UtilMethod.showToast("Update sql is "+sql1, cxt);
			UtilMethod.showToast("Update sql is "+sql1, cxt);
			sqdbl1.execSQL(sql1);
			sqdbl1.close();
		}	
		else{*/	
		SQLiteDatabase sqdbl = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("coupon_id", coupon_id);
		values.put("linkvalue", linkvalue);
		values.put("name", name);
		values.put("code", code);
		values.put("store", store);
		values.put("image", image);
		values.put("coupon_end", coupon_end);
		values.put("coupon_home", coupon_home);
		values.put("coupon_store_url", coupon_store_url);
		sqdbl.insert("coupons_details", null, values);
		sqdbl.close();
		/*}*/
	}
	
	public void Insert_DealDetail(String deal_id, String linkvalue, String name, String code, String store_id, String store_name, String image, String storeimage, String deals_end, String deals_home, String deals_mrp, String deals_selling, String deals_store_url){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from deal_details where deal_id='"+deal_id+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		/*if(csor.getCount()>0){
			SQLiteDatabase sqdbl1=this.getWritableDatabase();
			String sql2="update deal_details SET linkvalue='"+linkvalue+"' and name='"+name+"' and code='"+code+"' and  store_id='"+store_id+"' and store_name='"+store_name+"' and image='"+image+"' and storeimage='"+storeimage+"' and deals_end='"+deals_end+"' and deals_home='"+deals_home+"' and deals_mrp='"+deals_mrp+"' and deals_selling='"+deals_selling+"' and deals_store_url='"+deals_store_url+"' where deal_id='"+deal_id+"'";
			sqdbl1.execSQL(sql2);
			sqdbl1.close();
		}	
		else{	*/
		SQLiteDatabase sqdbl = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("deal_id", deal_id);
		values.put("linkvalue", linkvalue);
		values.put("name", name);
		values.put("code", code);
		values.put("store_id", store_id);
		values.put("store_name", store_name);
		values.put("image", image);
		values.put("storeimage", storeimage);
		values.put("deals_end", deals_end);
		values.put("deals_home", deals_home);
		values.put("deals_mrp", deals_mrp);
		values.put("deals_selling",deals_selling);
		values.put("deals_store_url", deals_store_url);
		sqdbl.insert("deal_details", null, values);
		sqdbl.close();
		/*}*/
	}
	
	
	public void update_favstatus(String pd_id,int fav_status){
		SQLiteDatabase sqdbl=this.getWritableDatabase();
		String sql="update product_details SET fav_status="+fav_status+" where pd_id='"+pd_id+"'";
		sqdbl.execSQL(sql);
		sqdbl.close();
	}
	
	
	public void deleteCoupons(){
		SQLiteDatabase sqdb = this.getWritableDatabase();
		String sql="delete from coupons_details" ;
		
		sqdb.execSQL(sql);
		sqdb.close();
	}
	public void deleteDeals(){
		SQLiteDatabase sqd=this.getWritableDatabase();
		String sql="delete from deal_details";
		sqd.execSQL(sql);
		sqd.close();
	}
	
	public void deleteProducts(){
		SQLiteDatabase sqd=this.getWritableDatabase();
		String sql="delete from product_details";
		sqd.execSQL(sql);
		sqd.close();
	}
	
	public void delete_ProfileDetails(String profile){
		SQLiteDatabase sqd=this.getWritableDatabase();
		String sql="delete from profile_details where PROFILE_SKILL='"+profile+"'";
		sqd.execSQL(sql);
		sqd.close();
	}
	
	
	
	public void deleteShortlisted_candidateDetails(){
		SQLiteDatabase sqdb = this.getWritableDatabase();
		String sql="delete from shortlistedcandidateDetail";
		
		sqdb.execSQL(sql);
		sqdb.close();
	}
		
	
	
	public List<String> getCounty_Names(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from country ",null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(1));//adding 2nd column data 
               Countrycount = cursor.getCount();
                Log.v("Counttt%%%%%%%",""+Countrycount );
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }
	
	public ArrayList<PriceComparisonBean> getProductValuewithdes(){
		ArrayList<PriceComparisonBean> product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details ORDER BY product_price_compare DESC";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String pd_id=csor.getString(csor.getColumnIndex("pd_id"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String product_price=csor.getString(csor.getColumnIndex("product_price"));
			String brand_name=csor.getString(csor.getColumnIndex("brand_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String subcategory_name1=csor.getString(csor.getColumnIndex("subcategory_name"));
			String category_name=csor.getString(csor.getColumnIndex("category_name"));
			int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			double product_price_compare=csor.getDouble(csor.getColumnIndex("product_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(pd_id);
			pcbean.setProduct_name(product_name);
			pcbean.setLinkvalue(linkvalue);
			pcbean.setPrice(product_price);
			pcbean.setBrand_name(brand_name);
			pcbean.setProduct_image(product_image);
			pcbean.setSubcategory_name(subcategory_name1);
			pcbean.setFav_status(fav_status);
			pcbean.setCategory_name(category_name);
			pcbean.setProduct_price_compare(product_price_compare);
			product_list.add(pcbean);
			
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return product_list;
		
	}
	
	public ArrayList<PriceComparisonBean> getProductValuewithAsc(){
		ArrayList<PriceComparisonBean> product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details ORDER BY product_price_compare ASC";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String pd_id=csor.getString(csor.getColumnIndex("pd_id"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String product_price=csor.getString(csor.getColumnIndex("product_price"));
			String brand_name=csor.getString(csor.getColumnIndex("brand_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String subcategory_name1=csor.getString(csor.getColumnIndex("subcategory_name"));
			String category_name=csor.getString(csor.getColumnIndex("category_name"));
			int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			double product_price_compare=csor.getDouble(csor.getColumnIndex("product_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(pd_id);
			pcbean.setProduct_name(product_name);
			pcbean.setLinkvalue(linkvalue);
			pcbean.setPrice(product_price);
			pcbean.setBrand_name(brand_name);
			pcbean.setProduct_image(product_image);
			pcbean.setSubcategory_name(subcategory_name1);
			pcbean.setFav_status(fav_status);
			pcbean.setCategory_name(category_name);
			pcbean.setProduct_price_compare(product_price_compare);
			product_list.add(pcbean);
			
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return product_list;
		
	}
	
	
	
	
	public ArrayList<PriceComparisonBean> getStoreValuewithAsc(){
		ArrayList<PriceComparisonBean> product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details ORDER BY product_price_compare ASC";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String pd_id=csor.getString(csor.getColumnIndex("product_id"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String product_price=csor.getString(csor.getColumnIndex("product_price"));
			String brand_name=csor.getString(csor.getColumnIndex("brand_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String subcategory_name1=csor.getString(csor.getColumnIndex("subcategory_name"));
			String category_name=csor.getString(csor.getColumnIndex("category_name"));
			int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			double product_price_compare=csor.getDouble(csor.getColumnIndex("product_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(pd_id);
			pcbean.setProduct_name(product_name);
			pcbean.setLinkvalue(linkvalue);
			pcbean.setPrice(product_price);
			pcbean.setBrand_name(brand_name);
			pcbean.setProduct_image(product_image);
			pcbean.setSubcategory_name(subcategory_name1);
			pcbean.setFav_status(fav_status);
			pcbean.setCategory_name(category_name);
			pcbean.setProduct_price_compare(product_price_compare);
			product_list.add(pcbean);
			
			 
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return product_list;
		
	}
	
	
	public ArrayList<PriceComparisonBean> getStoreProductwithDesc(){
		ArrayList<PriceComparisonBean> store_product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		StaticData.pc_variant_detail.clear();

		String sql="select * from store_details ORDER BY product_selling_price_compare DESC";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String store_price=csor.getString(csor.getColumnIndex("store_price"));
			String store_color=csor.getString(csor.getColumnIndex("store_color"));
			String store_offer=csor.getString(csor.getColumnIndex("store_offer"));
			String store_shipping=csor.getString(csor.getColumnIndex("store_shipping"));
			String store_cod=csor.getString(csor.getColumnIndex("store_cod"));
			String store_deal_id=csor.getString(csor.getColumnIndex("store_deal_id"));
			String store_image=csor.getString(csor.getColumnIndex("store_image"));
			String storurl=csor.getString(csor.getColumnIndex("storurl"));
			String store_coupon_offer=csor.getString(csor.getColumnIndex("store_coupon_offer"));
			String store_discount_type=csor.getString(csor.getColumnIndex("store_discount_type"));
			String store_offer_amount=csor.getString(csor.getColumnIndex("store_offer_amount"));
			String product_id=csor.getString(csor.getColumnIndex("product_id"));
			String product_brand=csor.getString(csor.getColumnIndex("product_brand"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String product_description=csor.getString(csor.getColumnIndex("product_description"));
			String imagepath=csor.getString(csor.getColumnIndex("imagepath"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String product_mrp=csor.getString(csor.getColumnIndex("product_mrp"));
			String product_selling_price=csor.getString(csor.getColumnIndex("product_selling_price"));
			String product_feature=csor.getString(csor.getColumnIndex("product_feature"));
			String variant_value=csor.getString(csor.getColumnIndex("variant_value"));
			double product_selling_price_compare=csor.getDouble(csor.getColumnIndex("product_selling_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setStore_price(store_price);
			pcbean.setStore_color(store_color);
			pcbean.setStore_offer(store_offer);
			pcbean.setStore_shipping(store_shipping);
			pcbean.setStore_code(store_cod);
			pcbean.setStore_deal_id(store_deal_id);
			pcbean.setStore_image(store_image);
			pcbean.setStore_url(storurl);
			pcbean.setStore_coupon_offer(store_coupon_offer);
			pcbean.setStore_discount_type(store_discount_type);
			pcbean.setStore_offer_amount(store_offer_amount);
			pcbean.setProduct_id(product_id);
			pcbean.setBrand_name(product_brand);
			pcbean.setProduct_name(product_name);
			pcbean.setProduct_description(product_description);
			pcbean.setImagepath(imagepath);
			pcbean.setProduct_image(product_image);
			pcbean.setProduct_mrp(product_mrp);
			pcbean.setProduct_selling_price(product_selling_price);
			pcbean.setProduct_feature(product_feature);
			pcbean.setVariant_value(variant_value);
			pcbean.setProduct_selling_price_compare(product_selling_price_compare);
			store_product_list.add(pcbean);
			StaticData.pc_variant_detail.add(pcbean);
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return store_product_list;
		
	}
	
	
	public ArrayList<PriceComparisonBean> getStoreProductwithAsc(){
		ArrayList<PriceComparisonBean> store_product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from store_details ORDER BY product_selling_price_compare ASC";
		StaticData.pc_variant_detail.clear();
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		UtilMethod.showToast("Cursor Count is "+csor.getCount(), cxt);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String store_price=csor.getString(csor.getColumnIndex("store_price"));
			String store_color=csor.getString(csor.getColumnIndex("store_color"));
			String store_offer=csor.getString(csor.getColumnIndex("store_offer"));
			String store_shipping=csor.getString(csor.getColumnIndex("store_shipping"));
			String store_cod=csor.getString(csor.getColumnIndex("store_cod"));
			String store_deal_id=csor.getString(csor.getColumnIndex("store_deal_id"));
			String store_image=csor.getString(csor.getColumnIndex("store_image"));
			String storurl=csor.getString(csor.getColumnIndex("storurl"));
			String store_coupon_offer=csor.getString(csor.getColumnIndex("store_coupon_offer"));
			String store_discount_type=csor.getString(csor.getColumnIndex("store_discount_type"));
			String store_offer_amount=csor.getString(csor.getColumnIndex("store_offer_amount"));
			String product_id=csor.getString(csor.getColumnIndex("product_id"));
			String product_brand=csor.getString(csor.getColumnIndex("product_brand"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String product_description=csor.getString(csor.getColumnIndex("product_description"));
			String imagepath=csor.getString(csor.getColumnIndex("imagepath"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String product_mrp=csor.getString(csor.getColumnIndex("product_mrp"));
			String product_selling_price=csor.getString(csor.getColumnIndex("product_selling_price"));
			String product_feature=csor.getString(csor.getColumnIndex("product_feature"));
			String variant_value=csor.getString(csor.getColumnIndex("variant_value"));
			double product_selling_price_compare=csor.getDouble(csor.getColumnIndex("product_selling_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setStore_price(store_price);
			pcbean.setStore_color(store_color);
			pcbean.setStore_offer(store_offer);
			pcbean.setStore_shipping(store_shipping);
			pcbean.setStore_code(store_cod);
			pcbean.setStore_deal_id(store_deal_id);
			pcbean.setStore_image(store_image);
			pcbean.setStore_url(storurl);
			pcbean.setStore_coupon_offer(store_coupon_offer);
			pcbean.setStore_discount_type(store_discount_type);
			pcbean.setStore_offer_amount(store_offer_amount);
			pcbean.setProduct_id(product_id);
			pcbean.setBrand_name(product_brand);
			pcbean.setProduct_name(product_name);
			pcbean.setProduct_description(product_description);
			pcbean.setImagepath(imagepath);
			pcbean.setProduct_image(product_image);
			pcbean.setProduct_mrp(product_mrp);
			pcbean.setProduct_selling_price(product_selling_price);
			pcbean.setProduct_feature(product_feature);
			pcbean.setVariant_value(variant_value);
			pcbean.setProduct_selling_price_compare(product_selling_price_compare);
			store_product_list.add(pcbean);
			StaticData.pc_variant_detail.add(pcbean);
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return store_product_list;
		
	}
	
	
	public ArrayList<PriceComparisonBean> getAlternativeProductwithAsc(){
		ArrayList<PriceComparisonBean> alternative_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from alternative_details ORDER BY product_selling_price_compare ASC";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String product_id=csor.getString(csor.getColumnIndex("product_id"));
			int fav=csor.getInt(csor.getColumnIndex("fav"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String product_selling_price=csor.getString(csor.getColumnIndex("product_selling_price"));
			double product_selling_price_compare=csor.getDouble(csor.getColumnIndex("product_selling_price_compare"));
			
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(product_id);
			pcbean.setFav_status(fav);
			pcbean.setProduct_name(product_name);
			pcbean.setProduct_image(product_image);
			pcbean.setProduct_selling_price(product_selling_price);
			pcbean.setProduct_selling_price_compare(product_selling_price_compare);
			alternative_list.add(pcbean);
			StaticData.pc_alternatives.add(pcbean);
			
		}while(csor.moveToNext());
		}
		
		
		return alternative_list;
		
	}
	
	public ArrayList<PriceComparisonBean> getAlternativeProductwithDesc(){
		ArrayList<PriceComparisonBean> alternative_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from alternative_details ORDER BY product_selling_price_compare DESC";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String product_id=csor.getString(csor.getColumnIndex("product_id"));
			int fav=csor.getInt(csor.getColumnIndex("fav"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String product_selling_price=csor.getString(csor.getColumnIndex("product_selling_price"));
			double product_selling_price_compare=csor.getDouble(csor.getColumnIndex("product_selling_price_compare"));
			
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(product_id);
			pcbean.setFav_status(fav);
			pcbean.setProduct_name(product_name);
			pcbean.setProduct_image(product_image);
			pcbean.setProduct_selling_price(product_selling_price);
			pcbean.setProduct_selling_price_compare(product_selling_price_compare);
			alternative_list.add(pcbean);
			StaticData.pc_alternatives.add(pcbean);
			
		}while(csor.moveToNext());
		}
		
		
		return alternative_list;
		
	}
	
	public ArrayList<PriceComparisonBean> getProductValuewithBrand(String product){
		ArrayList<PriceComparisonBean> product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String pd_id=csor.getString(csor.getColumnIndex("pd_id"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String product_price=csor.getString(csor.getColumnIndex("product_price"));
			String brand_name=csor.getString(csor.getColumnIndex("brand_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String subcategory_name1=csor.getString(csor.getColumnIndex("subcategory_name"));
			String category_name=csor.getString(csor.getColumnIndex("category_name"));
			int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			double product_price_compare=csor.getDouble(csor.getColumnIndex("product_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(pd_id);
			pcbean.setProduct_name(product_name);
			pcbean.setLinkvalue(linkvalue);
			pcbean.setPrice(product_price);
			pcbean.setBrand_name(brand_name);
			pcbean.setProduct_image(product_image);
			pcbean.setSubcategory_name(subcategory_name1);
			pcbean.setFav_status(fav_status);
			pcbean.setCategory_name(category_name);
			pcbean.setProduct_price_compare(product_price_compare);
			product_list.add(pcbean);
			
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return product_list;
		
	}
	public ArrayList<DealBean> getDealValue(String deal_home){
		ArrayList<DealBean> deal_list=new ArrayList<DealBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		//String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		String sql="select * from deal_details where deals_home='"+deal_home+"'";
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String deal_id=csor.getString(csor.getColumnIndex("deal_id"));
			String name=csor.getString(csor.getColumnIndex("name"));
			//String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String code=csor.getString(csor.getColumnIndex("code"));
			String store_id=csor.getString(csor.getColumnIndex("store_id"));
			String store_name=csor.getString(csor.getColumnIndex("store_name"));
			String image=csor.getString(csor.getColumnIndex("image"));
			String storeimage=csor.getString(csor.getColumnIndex("storeimage"));
			String deals_end=csor.getString(csor.getColumnIndex("deals_end"));
			String deals_home=csor.getString(csor.getColumnIndex("deals_home"));
			String deals_mrp=csor.getString(csor.getColumnIndex("deals_mrp"));
			String deals_selling=csor.getString(csor.getColumnIndex("deals_selling"));
			String deals_store_url=csor.getString(csor.getColumnIndex("deals_store_url"));
			//int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			DealBean dbean=new DealBean();
			dbean.setCategory_id(deal_id);
			dbean.setCategory_name(name);
			//dbean.setLinkvalue(linkvalue);
			dbean.setStore_code(code);
			dbean.setStore_id(store_id);
			dbean.setStore_name(store_name);
			dbean.setCategory_image(image);
			dbean.setStore_image(storeimage);
			dbean.setDeals_end(deals_end);
			dbean.setDeals_home(deals_home);
			dbean.setDeals_mrp(deals_mrp);
			dbean.setDeals_selling(deals_selling);
			dbean.setDeals_store_url(deals_store_url);
			deal_list.add(dbean);
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return deal_list;
		
	}
	public ArrayList<DealBean> getDealValuewithStore(String store,String deal_home){
		ArrayList<DealBean> deal_list=new ArrayList<DealBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		//String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		String sql="select * from deal_details where deals_home='"+deal_home+"' and store_name LIKE '"+store+"%'";
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String deal_id=csor.getString(csor.getColumnIndex("deal_id"));
			String name=csor.getString(csor.getColumnIndex("name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String code=csor.getString(csor.getColumnIndex("code"));
			String store_id=csor.getString(csor.getColumnIndex("store_id"));
			String store_name=csor.getString(csor.getColumnIndex("store_name"));
			String image=csor.getString(csor.getColumnIndex("image"));
			String storeimage=csor.getString(csor.getColumnIndex("storeimage"));
			String deals_end=csor.getString(csor.getColumnIndex("deals_end"));
			String deals_home=csor.getString(csor.getColumnIndex("deals_home"));
			String deals_mrp=csor.getString(csor.getColumnIndex("deals_mrp"));
			String deals_selling=csor.getString(csor.getColumnIndex("deals_selling"));
			String deals_store_url=csor.getString(csor.getColumnIndex("deals_store_url"));
			//int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			DealBean dbean=new DealBean();
			dbean.setCategory_id(deal_id);
			dbean.setCategory_name(name);
			dbean.setLinkvalue(linkvalue);
			dbean.setStore_code(code);
			dbean.setStore_id(store_id);
			dbean.setStore_name(store_name);
			dbean.setCategory_image(image);
			dbean.setStore_image(storeimage);
			dbean.setDeals_end(deals_end);
			dbean.setDeals_home(deals_home);
			dbean.setDeals_mrp(deals_mrp);
			dbean.setDeals_selling(deals_selling);
			dbean.setDeals_store_url(deals_store_url);
			deal_list.add(dbean);
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return deal_list;
		
	}
	
	public ArrayList<DealBean> getAllDealValuewithStore(String store){
		ArrayList<DealBean> deal_list=new ArrayList<DealBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		//String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		String sql="select * from deal_details where store_name LIKE '"+store+"%'";
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String deal_id=csor.getString(csor.getColumnIndex("deal_id"));
			String name=csor.getString(csor.getColumnIndex("name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String code=csor.getString(csor.getColumnIndex("code"));
			String store_id=csor.getString(csor.getColumnIndex("store_id"));
			String store_name=csor.getString(csor.getColumnIndex("store_name"));
			String image=csor.getString(csor.getColumnIndex("image"));
			String storeimage=csor.getString(csor.getColumnIndex("storeimage"));
			String deals_end=csor.getString(csor.getColumnIndex("deals_end"));
			String deals_home=csor.getString(csor.getColumnIndex("deals_home"));
			String deals_mrp=csor.getString(csor.getColumnIndex("deals_mrp"));
			String deals_selling=csor.getString(csor.getColumnIndex("deals_selling"));
			String deals_store_url=csor.getString(csor.getColumnIndex("deals_store_url"));
			//int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			DealBean dbean=new DealBean();
			dbean.setCategory_id(deal_id);
			dbean.setCategory_name(name);
			dbean.setLinkvalue(linkvalue);
			dbean.setStore_code(code);
			dbean.setStore_id(store_id);
			dbean.setStore_name(store_name);
			dbean.setCategory_image(image);
			dbean.setStore_image(storeimage);
			dbean.setDeals_end(deals_end);
			dbean.setDeals_home(deals_home);
			dbean.setDeals_mrp(deals_mrp);
			dbean.setDeals_selling(deals_selling);
			dbean.setDeals_store_url(deals_store_url);
			deal_list.add(dbean);
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return deal_list;
		
	}
	
	
	public ArrayList<CouponBean> getCouponValuewithStore(String store,String coupon_home){
		ArrayList<CouponBean> coupon_list=new ArrayList<CouponBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		//String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		String sql="select * from coupons_details where coupon_home='"+coupon_home+"' or store LIKE '"+store+"%'";
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String coupon_id=csor.getString(csor.getColumnIndex("coupon_id"));
			String name=csor.getString(csor.getColumnIndex("name"));
		//	String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String code=csor.getString(csor.getColumnIndex("code"));
			String store1=csor.getString(csor.getColumnIndex("store"));
			String image=csor.getString(csor.getColumnIndex("image"));
			//String storeimage=csor.getString(csor.getColumnIndex("storeimage"));
			String coupon_end=csor.getString(csor.getColumnIndex("coupon_end"));
			String coupon_home1=csor.getString(csor.getColumnIndex("coupon_home"));
			String coupon_store_url=csor.getString(csor.getColumnIndex("coupon_store_url"));
			//int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			CouponBean cbean=new CouponBean();
			cbean.setCategory_id(coupon_id);
			cbean.setCategory_name(name);
			cbean.setStore_code(code);
			cbean.setStore_name(store1);
			cbean.setStore_image(image);
			cbean.setCoupon_end(coupon_end);
			cbean.setCoupon_home(coupon_home1);
			cbean.setCoupon_store_url(coupon_store_url);
			coupon_list.add(cbean);
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return coupon_list;
		
	}
	
	
	public ArrayList<CouponBean> getAllCouponValuewithStore(String store){
		ArrayList<CouponBean> coupon_list=new ArrayList<CouponBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		//String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		String sql="select * from coupons_details where store='"+store+"' and coupon_home!='0'";
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String coupon_id=csor.getString(csor.getColumnIndex("coupon_id"));
			String name=csor.getString(csor.getColumnIndex("name"));
		//	String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String code=csor.getString(csor.getColumnIndex("code"));
			String store1=csor.getString(csor.getColumnIndex("store"));
			String image=csor.getString(csor.getColumnIndex("image"));
			//String storeimage=csor.getString(csor.getColumnIndex("storeimage"));
			String coupon_end=csor.getString(csor.getColumnIndex("coupon_end"));
			String coupon_home1=csor.getString(csor.getColumnIndex("coupon_home"));
			String coupon_store_url=csor.getString(csor.getColumnIndex("coupon_store_url"));
			//int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			CouponBean cbean=new CouponBean();
			cbean.setCategory_id(coupon_id);
			cbean.setCategory_name(name);
			cbean.setStore_code(code);
			cbean.setStore_name(store1);
			cbean.setStore_image(image);
			cbean.setCoupon_end(coupon_end);
			cbean.setCoupon_home(coupon_home1);
			cbean.setCoupon_store_url(coupon_store_url);
			coupon_list.add(cbean);
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return coupon_list;
		
	}
	
	
	public ArrayList<CouponBean> getCouponValue(String coupon_home){
		ArrayList<CouponBean> coupon_list=new ArrayList<CouponBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		//String sql="select * from product_details where brand_name='"+product+"' or product_name LIKE '"+product+"%'";
		String sql="select * from coupons_details where coupon_home='"+coupon_home+"'";
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String coupon_id=csor.getString(csor.getColumnIndex("coupon_id"));
			String name=csor.getString(csor.getColumnIndex("name"));
			//String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String code=csor.getString(csor.getColumnIndex("code"));
			String store=csor.getString(csor.getColumnIndex("store"));
			String image=csor.getString(csor.getColumnIndex("image"));
			//String storeimage=csor.getString(csor.getColumnIndex("storeimage"));
			String coupon_end=csor.getString(csor.getColumnIndex("coupon_end"));
			String coupon_home1=csor.getString(csor.getColumnIndex("coupon_home"));
			String coupon_store_url=csor.getString(csor.getColumnIndex("coupon_store_url"));
			//int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			CouponBean cbean=new CouponBean();
			cbean.setCategory_id(coupon_id);
			cbean.setCategory_name(name);
			cbean.setStore_code(code);
			cbean.setStore_name(store);
			cbean.setStore_image(image);
			cbean.setCoupon_end(coupon_end);
			cbean.setCoupon_home(coupon_home1);
			cbean.setCoupon_store_url(coupon_store_url);
			coupon_list.add(cbean);
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return coupon_list;
		
	}
	
	public ArrayList<PriceComparisonBean> getProductValue(String subcategory_name){
		ArrayList<PriceComparisonBean> product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details where subcategory_name='"+subcategory_name+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String pd_id=csor.getString(csor.getColumnIndex("pd_id"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String product_price=csor.getString(csor.getColumnIndex("product_price"));
			String brand_name=csor.getString(csor.getColumnIndex("brand_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String subcategory_name1=csor.getString(csor.getColumnIndex("subcategory_name"));
			String category_name=csor.getString(csor.getColumnIndex("category_name"));
			int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			double product_price_compare=csor.getDouble(csor.getColumnIndex("product_price_compare"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(pd_id);
			pcbean.setProduct_name(product_name);
			pcbean.setLinkvalue(linkvalue);
			pcbean.setPrice(product_price);
			pcbean.setBrand_name(brand_name);
			pcbean.setProduct_image(product_image);
			pcbean.setSubcategory_name(subcategory_name1);
			pcbean.setFav_status(fav_status);
			pcbean.setCategory_name(category_name);
			pcbean.setProduct_price_compare(product_price_compare);
			product_list.add(pcbean);
			
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return product_list;
		
	}

	public ArrayList<PriceComparisonBean> getProductValuewithCategory(String category_name){
		ArrayList<PriceComparisonBean> product_list=new ArrayList<PriceComparisonBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="select * from product_details where category_name='"+category_name+"'";
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String pd_id=csor.getString(csor.getColumnIndex("pd_id"));
			String product_name=csor.getString(csor.getColumnIndex("product_name"));
			String linkvalue=csor.getString(csor.getColumnIndex("linkvalue"));
			String product_price=csor.getString(csor.getColumnIndex("product_price"));
			String brand_name=csor.getString(csor.getColumnIndex("brand_name"));
			String product_image=csor.getString(csor.getColumnIndex("product_image"));
			String subcategory_name1=csor.getString(csor.getColumnIndex("subcategory_name"));
			String category_name1=csor.getString(csor.getColumnIndex("category_name"));
			int fav_status=csor.getInt(csor.getColumnIndex("fav_status"));
			PriceComparisonBean pcbean=new PriceComparisonBean();
			pcbean.setProduct_id(pd_id);
			pcbean.setProduct_name(product_name);
			pcbean.setLinkvalue(linkvalue);
			pcbean.setPrice(product_price);
			pcbean.setBrand_name(brand_name);
			pcbean.setProduct_image(product_image);
			pcbean.setSubcategory_name(subcategory_name1);
			pcbean.setFav_status(fav_status);
			pcbean.setCategory_name(category_name1);
			product_list.add(pcbean);
			
			
			/*HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);*/
			
			
			/*skills_list.add(hm);*/
			
		}while(csor.moveToNext());
		}
		
		
		return product_list;
		
	}

	
	
	public ArrayList<HashMap<String,String>> getSkillsValue1(ArrayList<String> f_id){
		ArrayList<HashMap<String,String>> skills_list=new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getReadableDatabase();
		for(int i=0;i<f_id.size();i++){
		String sql="select * from jobskills where JobProfileID="+f_id.get(i);
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String value=csor.getString(csor.getColumnIndex("SkillName"));
			String f_id1=csor.getString(csor.getColumnIndex("JobProfileID"));
			String tech_skillID=csor.getString(csor.getColumnIndex("TechSkillID"));
			HashMap<String,String> hm=new HashMap<String, String>();
			hm.put("f_id", f_id1);
			hm.put("tech_skillID", tech_skillID);
			hm.put("skill_name",value);
			
			skills_list.add(hm);
			
		}while(csor.moveToNext());
		}
		}
		
		return skills_list;
		
	}
	
	
	
	public List<String> getState_Names(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from state ",null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(2));//adding 2nd column data 
              StateCount = cursor.getCount();
                Log.v("stateCounttt",""+StateCount );
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }  
	
	public List<String> getKeywordSkill(){
		List<String> list=new ArrayList<String>();
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("select * from keyword_details", null);
		if(cursor.moveToFirst()){
			do{
				list.add(cursor.getString(3));
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
		
	}
	
	public ArrayList<HashMap<String, String>> getProfileSkills(){
		ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("select * from profile_details", null);
		if(cursor.moveToFirst()){
			do{
				HashMap<String, String> hm=new HashMap<String, String>();
				String f_id=cursor.getString(cursor.getColumnIndex("F_ID"));
				String f_name=cursor.getString(cursor.getColumnIndex("PROFILE_SKILL"));
				hm.put("f_id",f_id);
				hm.put("f_name", f_name);
				list.add(hm);
				
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
		
	}
	
	
	
	public List<String> getSelectedCompany(){
		List<String> list = new ArrayList<String>();
		
		return list;
	}
	
	
	public List<String> getCity_Names(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from city ",null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(3));//adding 2nd column data 
                CityCount = cursor.getCount();
                Log.v("Counttt%%%%%%%",""+CityCount );
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }  
	
	/*public ArrayList<UplaodeBean> getCompany_Details(){
		ArrayList<UplaodeBean> list=new ArrayList<UplaodeBean>();
		String sql = "select * from company_details where ID = '"+id+"'";
		SQLiteDatabase db1 = this.getReadableDatabase();
		Cursor c = db1.rawQuery(sql, null);
		while (c.moveToNext()) {
			UplaodeBean ub=new UplaodeBean();
			ub.setCompany_profile_id(c.getString(1));
			ub.setUser_profile_id(c.getString(2));
			ub.setCompany_selection_candidate_status(c.getString(3));
			ub.setLogin_priority_status(c.getString(4));
			list.add(ub);
			
		}
		c.close();
		db1.close();
		
		return list;
		
	}*/
	/*public ArrayList<UplaodeBean> getCompany_Details(){
		ArrayList<UplaodeBean> list=new ArrayList<UplaodeBean>();
		String sql = "select * from company_details";
		SQLiteDatabase db1 = this.getReadableDatabase();
		Cursor c = db1.rawQuery(sql, null);
		while (c.moveToNext()) {
			UplaodeBean ub=new UplaodeBean();
			ub.setCompany_profile_id(c.getString(1));
			ub.setUser_profile_id(c.getString(2));
			ub.setCompany_selection_candidate_status(c.getString(3));
			ub.setLogin_priority_status(c.getString(4));
			list.add(ub);
			
		}
		c.close();
		db1.close();
		
		return list;
		
	}*/

	/*public ArrayList<UplaodeBean> getCandidate_Details(){
		ArrayList<UplaodeBean> mlist=new ArrayList<UplaodeBean>();
		String query = "select * from candidateDetail";
		SQLiteDatabase sqldb = this.getReadableDatabase();
		Cursor c = sqldb.rawQuery(query, null);
		

		
		while (c.moveToNext()) {
			UplaodeBean ub=new UplaodeBean();
			Log.v("print cursor", ".."+c.getString(1));
			ub.setCompany_profile_id(c.getString(1));
			Log.v("print cursor", ".."+c.getString(2));
			ub.setUser_profile_id(c.getString(2));
			Log.v("print cursor", ".."+c.getString(3));
			ub.setCompany_selection_candidate_status(c.getString(3));
			Log.v("print cursor", ".."+c.getString(4));
			ub.setLogin_priority_status(c.getString(4));
			mlist.add(ub);
			
			Log.v("Insert list value", ""+mlist);
			
		}
		c.close();
		sqldb.close();
		
		return mlist;
		
	}
*/	
	
	/*public ArrayList<UploadInterviewSchedule> getInterviewSchedule_Details(){
		ArrayList<UploadInterviewSchedule> mlist=new ArrayList<UploadInterviewSchedule>();
		String query = "select * from shortlistedcandidateDetail";
		SQLiteDatabase sqldb = this.getReadableDatabase();
		Cursor c = sqldb.rawQuery(query, null);
		

		
		while (c.moveToNext()) {
			UploadInterviewSchedule ub=new UploadInterviewSchedule();
			Log.v("print cursor", ".."+c.getString(1));
			ub.setCompany_shortlistcandidate_id(c.getString(1));
			Log.v("print cursor", ".."+c.getString(2));
			ub.setMode_email(c.getString(2));
			Log.v("print cursor", ".."+c.getString(3));
			ub.setInterview_date(c.getString(3));
			Log.v("print cursor", ".."+c.getString(4));
			ub.setInterview_time(c.getString(4));
			mlist.add(ub);
			
			Log.v("Insert list value", ""+mlist);
			
		}
		c.close();
		sqldb.close();
		
		return mlist;
		
	}*/
	
	
	public List<String> getSelectedKeywordSkill(ArrayList<String> f_id){
		ArrayList<String> list=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Log.v("Size of Skill List"," "+f_id.size());
		for(int i=0;i<f_id.size();i++){
		String sql="select * from keyword_details where FA_id="+f_id.get(i);
		
		Cursor csor=null;
		csor=db.rawQuery(sql,null);
		int csor1=csor.getCount();
		Log.v("Cursor Count",""+csor.getCount());
		if(csor.getCount()>0){
			csor.moveToFirst();
		do{
			String value=csor.getString(csor.getColumnIndex("KEYWORD_SKILL"));
			
			
			list.add(value);
			
		}while(csor.moveToNext());
		}
		}
		
		return list;
		
	}
	
	
	
	/*public List<String> getJobProfile_Names(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from jobprofile ",null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(3));//adding 2nd column data
                JobProfileCount = cursor.getCount();
                Log.v("Counttt%%%%%%%",""+JobProfileCount );
             
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }*/
	
	
	public ArrayList<HashMap<String, String>> getJobProfile_Names(){  
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        
   
        // Select All Query  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from jobprofile ",null);//selectQuery,selectedArguments  
        int count=cursor.getCount();
        // looping through all rows and adding to list 
        if(count>0){
        if (cursor.moveToFirst()) {  
            do {
            	HashMap<String, String> hmap=new HashMap<String, String>();
            	hmap.put("f_id", cursor.getString(0));
            	hmap.put("f_name", cursor.getString(3));
            	
                list.add(hmap);//adding 2nd column data
                JobProfileCount = cursor.getCount();
                Log.v("Counttt%%%%%%%",""+JobProfileCount );
             
            } while (cursor.moveToNext());  
          }  
        }
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> getSkills_Name(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from jobskills ",null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(4));//adding 2nd column data  
                Skillscount = cursor.getCount();
                Log.v("Counttt%%%%%%%",""+Skillscount );
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }

	
	
	
}
