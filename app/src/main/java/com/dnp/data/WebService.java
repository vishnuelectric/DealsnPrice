package com.dnp.data;

public class WebService {
	//public static String HOST_URL="http://cashitback.ypsilonitsolutions.com/webservices/";
	
	public static String WEB_HOST_URL="http://dealsnprice.com/"; //old default
	
	//public static String WEB_HOST_URL="http://ec2-52-26-132-174.us-west-2.compute.amazonaws.com/"; //new server
	//public static String WEB_HOST_URL="http://ec2-52-27-70-107.us-west-2.compute.amazonaws.com/"; //testing
	//public static String WEB_HOST_URL="http://dealsandprice.in/";
	public static String HOST_URL="http://72.167.41.165/cashitback/webservices/";
		
	public static String REGISTRATION_SERVICE=WEB_HOST_URL+"login/register";
	
	public static String LOGIN_SERVICE=WEB_HOST_URL+"login/";
	
	public static String FORGOT_PASSWORD_SERVICE=WEB_HOST_URL+"login/forgot";
	
	public static String PRODUCT_ALERT_SERVICE=WEB_HOST_URL+"drop/appprice/";
	
	public static String REMOVE_FAVOURITE_SERVICE=WEB_HOST_URL+"drop/favalertdelete";
			
	public static String LOCAL_REGISTRATION_SERVICE=HOST_URL+"registered_user.php";
	
	public static String CONTACT_SERVICE=WEB_HOST_URL+"contactus/submit";
	
	public static String USER_INFO_SERVICE=WEB_HOST_URL+"jsonproduct/userinfo/?userid=";
	
	public static String ACCOUNT_DETAIL_SERVICE=WEB_HOST_URL+"accountinfo/accountdetails";
	
	public static String AMOUNT_DETAIL_SERVICE=WEB_HOST_URL+"jsonproduct/amountdetails/?userid=";
	
	public static String CATEGORY_SERVICE=WEB_HOST_URL+"jsonproduct/category?urlcheck=1";
			
	public static String ALL_DEALS_SERVICE=WEB_HOST_URL+"jsonproduct/deals?urlcheck=1";
	
	public static String ALL_COUPON_SERVICE=WEB_HOST_URL+"jsonproduct/coupon?urlcheck=1";
	
	public static String UPDATE_PROFILE_SERVICE=WEB_HOST_URL+"accountinfo/signupaccount";
	
	public static String UPDATE_PASSWORD_SERVICE=WEB_HOST_URL+"login/signuppasswordchangeuser";
	
	public static String GET_SHOPPING_LIST_SERVICE=WEB_HOST_URL+"jsonproduct/stores?urlcheck=1";
	
	public static String REGISTRATION_SOCIAL_SERVICE=WEB_HOST_URL+"login/social/?";
	
	public static String FAVOURITE_WEBSERVICE = WEB_HOST_URL+"drop/addfav";
	
	public static String MISSING_CASHBACK_WEBSERVICE = WEB_HOST_URL+"accountinfo/missingmonth";
	
	public static String LOGOUT_WEBSERVICE = WEB_HOST_URL+"login/logoutapp/?";
	
	public static String TOP_STORE_WEBSERVICE = WEB_HOST_URL+"jsonproduct/storeshort/?urlcheck=1";
	
	public static String FAQ_WEBSERVICE = WEB_HOST_URL+"Jsonproduct/faq";
	
	public static String REFER_EARN_WEBSERVICE = WEB_HOST_URL+"jsonproduct/amountreferlist/?urlcheck=1&userid=";
	
	public static String MISSING_CASHBACK_LIST_WEBSERVICE = WEB_HOST_URL+"jsonproduct/userinfomissing/?userid=";
	
	public static String DNP_FACEBOOK_PAGE = "https://www.facebook.com/dealsnprice";
	
	public static String DNP_TWITTER_PAGE = "https://twitter.com/DealsNPrice";
	
	public static String DNP_GOOGLE_PLUS_PAGE = "https://plus.google.com/108355705586348904932/posts";
	
	
	public static String APP_URL="https://play.google.com/store/apps/details?id=";
	
	public static String APP_URL_SERVICE=HOST_URL+"get_app_url.php";
	
	public static String CHANGE_PASSWORD_SERVICE=HOST_URL+"change_password.php";
	
	public static String GET_STATE_SERVICE=HOST_URL+"get_state.php";
	
	public static String APPLICATION_URL=APP_URL+"com.dealnprice.activity";
	
	//public static String APP_LIST_SERVICE=HOST_URL+"getApp_list.php";
	
	public static String APP_LIST_SERVICE=WEB_HOST_URL+"offer/?";
	
	public static String GET_REDEEM_HISTORY_SERVICE=HOST_URL+"getredeem_history.php";
	
	public static String GET_NOTIFICATION_SERVICE=WEB_HOST_URL+"Jsonnotification/?";
	
	public static String NOTIFICATION_SETTING_SERVICE=HOST_URL+"notification_setting.php";
	
	public static String AMOUNT_NOTIFICATION_SERVICE=HOST_URL+"get_amount_notification.php";
	
	public static String INSERT_APP_STATUS_SERVICE=WEB_HOST_URL+"appintall?";
	
	public static String EDIT_PROFILE_SERVICE=HOST_URL+"edit_profile.php";
	
	public static String GET_SHOPPING_TRIP_SERVICE=HOST_URL+"getshoppingtrip.php";
	
	public static String INSERT_SHOPPING_TRIP_SERVICE=HOST_URL+"insert_shoppingtrip.php";
	
	public static String READ_APP_SERVICE=HOST_URL+"readApp.php";
	
	public static String PAYMENT_TRANSFER_SERVICE=WEB_HOST_URL+"accountinfo/rddemnow?extension=1&userid=";
	
	public static String PLAY_STORE_URL="http://tinyurl.com/p99yp9g";
	
	public static String PENDING_AMOUNT_SERVICE=WEB_HOST_URL+"Jsonproduct/amountpending/?extension=1&userid=";
}
