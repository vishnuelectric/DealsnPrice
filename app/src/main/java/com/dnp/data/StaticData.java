package com.dnp.data;

import java.util.ArrayList;

import android.app.Application;

import com.dnp.bean.AlertProductBean;
import com.dnp.bean.ApplicationBean;
import com.dnp.bean.BrandBean;
import com.dnp.bean.CategoryBean;
import com.dnp.bean.ContactAdapter;
import com.dnp.bean.ContactBean;
import com.dnp.bean.CouponBean;
import com.dnp.bean.DealBean;
import com.dnp.bean.FAQBean;
import com.dnp.bean.FavouriteBean;
import com.dnp.bean.MissingAmountBean;
import com.dnp.bean.MyAccountBean;
import com.dnp.bean.NotificationBean;
import com.dnp.bean.PriceComparisonBean;
import com.dnp.bean.RedeemBean;
import com.dnp.bean.ReferEarnBean;
import com.dnp.bean.ShopOfferBean;
import com.dnp.bean.ShoppingTripBean;
import com.dnp.bean.StateBean;
import com.dnp.bean.StoreBean;
import com.dnp.bean.UserBean;

public class StaticData {
	
	public static ArrayList<ApplicationBean> application_list=new ArrayList<ApplicationBean>();
	
	public static ArrayList<Boolean> read_flag=new ArrayList<Boolean>();
	
	public static ArrayList<ShopOfferBean> shop_offer_list=new ArrayList<ShopOfferBean>();
	
	public static ArrayList<String> shop_search=new ArrayList<String>();
	
	public static ArrayList<NotificationBean> notification_list=new ArrayList<NotificationBean>();
	
	public static ArrayList<RedeemBean> redeemhistory_list=new ArrayList<RedeemBean>();
	
	public static ArrayList<ShoppingTripBean> shopping_trip_bean=new ArrayList<ShoppingTripBean>();
	
	public static ArrayList<StateBean> state_list=new ArrayList<StateBean>();
	
	public static ArrayList<ContactAdapter> read_contact_adapter=new ArrayList<ContactAdapter>();
	
	public static ArrayList<String> myphone=new ArrayList<String>();
	
	public static ArrayList<String> contactname=new ArrayList<String>();
	
	public static ArrayList<ApplicationBean> full_upto_amount=new ArrayList<ApplicationBean>();
	
	public static ArrayList<String> contact_id=new ArrayList<String>();
	
	public static ArrayList<String> phone_image=new ArrayList<String>();
	
	public static ArrayList<ApplicationBean> our_app_list=new ArrayList<ApplicationBean>();
	
	public static ArrayList<CategoryBean> category_list=new ArrayList<CategoryBean>();
	
	public static ArrayList<CategoryBean> category_fixed_list=new ArrayList<CategoryBean>();
	
	public static ArrayList<CategoryBean> category_deal_list=new ArrayList<CategoryBean>(); 
	
	public static ArrayList<DealBean> deal_product_list=new ArrayList<DealBean>();
	
	public static ArrayList<CouponBean> coupon_store_list=new ArrayList<CouponBean>();
	
	public static ArrayList<DealBean> deal_store_list=new ArrayList<DealBean>();
	
	public static ArrayList<ReferEarnBean> referearn_list=new ArrayList<ReferEarnBean>();
	
	public static ArrayList<CouponBean> coupon_product_list=new ArrayList<CouponBean>();
	
	public static ArrayList<CouponBean> hot_coupon_product_list=new ArrayList<CouponBean>();
	
	public static ArrayList<CouponBean> most_viewed_coupon_list=new ArrayList<CouponBean>();
	
	public static ArrayList<DealBean> steal_deal_list=new ArrayList<DealBean>();
	
	public static ArrayList<DealBean> most_viewed_deal_list=new ArrayList<DealBean>();
	
	public static ArrayList<UserBean> user_info=new ArrayList<UserBean>();
	
	public static ArrayList<ApplicationBean> upto_list=new ArrayList<ApplicationBean>();
	
	public static ArrayList<UserBean> user_account=new ArrayList<UserBean>(); 
	
	public static ArrayList<BrandBean> brand_list=new ArrayList<BrandBean>();
	
	public static ArrayList<PriceComparisonBean> product_price_list=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<ShopOfferBean> shop_offer_detail=new ArrayList<ShopOfferBean>();
	
	public static ArrayList<FavouriteBean> favourite_list=new ArrayList<FavouriteBean>();
	
	public static ArrayList<ShopOfferBean> shop_offer_search=new ArrayList<ShopOfferBean>();
	
	public static ArrayList<CategoryBean> category_selected=new ArrayList<CategoryBean>();
	
	public static ArrayList<PriceComparisonBean> pc_detail=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<PriceComparisonBean> pc_variant_detail=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<PriceComparisonBean> pc_full_variant=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<PriceComparisonBean> pc_specification=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<PriceComparisonBean> pc_alternatives=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<PriceComparisonBean> product_comparison_list=new ArrayList<PriceComparisonBean>();
	
	public static ArrayList<MyAccountBean> account_detail=new ArrayList<MyAccountBean>();
	
	public static ArrayList<MyAccountBean> my_paid_detail=new ArrayList<MyAccountBean>();
	
	public static ArrayList<MyAccountBean> missing_cashback_detail=new ArrayList<MyAccountBean>();
	
	public static ArrayList<MissingAmountBean> missing_offer_detail=new ArrayList<MissingAmountBean>();
	
	public static ArrayList<MissingAmountBean> missing_shop_detail=new ArrayList<MissingAmountBean>();
	
	public static ArrayList<MyAccountBean> order_status_detail=new ArrayList<MyAccountBean>();
	
	public static ArrayList<MyAccountBean> full_status_amount = new ArrayList<MyAccountBean>();
	
	public static ArrayList<MyAccountBean> order_filter_list = new ArrayList<MyAccountBean>();
	
	public static ArrayList<FAQBean> faq_list = new ArrayList<FAQBean>();
	
	public static ArrayList<ContactBean> contact_list=new ArrayList<ContactBean>();
	
	public static ArrayList<AlertProductBean> alert_lproduct_list=new ArrayList<AlertProductBean>();
	
	public static String amount;
	
	public static ArrayList<StoreBean> top_store_list = new ArrayList<StoreBean>();
	
	public static ArrayList<MyAccountBean> offer_user = new ArrayList<MyAccountBean>();
	
	
	
}
