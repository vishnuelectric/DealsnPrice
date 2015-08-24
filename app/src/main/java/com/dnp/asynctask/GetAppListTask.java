package com.dnp.asynctask;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dealnprice.activity.CommonUtilities;
import com.dealnprice.activity.Constant;
import com.dnp.bean.ApplicationBean;
import com.dnp.data.HttpRequest;
import com.dnp.data.StaticData;
import com.dnp.data.UtilMethod;
import com.dnp.data.WebService;
import com.dnp.fragment.OfferFragment.OfferListener;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetAppListTask extends AsyncTask<String, Void, String>{
	Context cxt;
	MultipartEntity multipart;

	OfferListener olistener;
	public GetAppListTask(Context cxt,OfferListener listener){
		this.cxt=cxt;
		this.olistener=listener;

	}

	@Override
	protected String doInBackground(String... params) {

		try{
			TelephonyManager telephonyManager = (TelephonyManager)cxt.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id=telephonyManager.getDeviceId();
			String user_id=cxt.getSharedPreferences(Constant.pref_name,1).getString(Constant.USER_ID, null);
			String response = HttpRequest.post(WebService.APP_LIST_SERVICE+"urlcheck=1&userid="+user_id+"&imei="+device_id);
			System.out.println("offer response"+ response );
			System.out.println(response.equalsIgnoreCase(""));

			return response;
		}
		catch(Exception e){
			UtilMethod.showToast("Exception is "+e, cxt);

		}
		return null;
	}


	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);
		System.out.println(result.equalsIgnoreCase("") + "   "+ "in postexecute");
		if(result!=null && !result.equalsIgnoreCase("")){
			try{
				JSONObject obj=new JSONObject(result);
				String status=obj.getString("status");

				if(status!=null && status.equals("1")){
					StaticData.application_list.clear();
					StaticData.upto_list.clear();
					JSONArray jarray=obj.getJSONArray("app_list");
					for(int i=0;i<jarray.length();i++){
						JSONObject object=jarray.getJSONObject(i);
						ApplicationBean abean=new ApplicationBean();
						abean.setApp_id(object.getString(Constant.APP_ID));
						abean.setApp_name(object.getString(Constant.APP_NAME));
						abean.setApp_url(object.getString(Constant.APP_URL) + "&" + Constant.USER_ID + "=" + cxt.getSharedPreferences(Constant.pref_name,1).getString(Constant.USER_ID, null) + "&" + Constant.IMEI + "=" + CommonUtilities.getImei(cxt));
						//abean.setPackage_name(object.getString("app_pakage_name"));

						if(!UtilMethod.isStringNullOrBlank(object.getString("app_description"))){
							abean.setApp_description(object.getString(Constant.APP_DESCRIPTION).replace("  ",""));
						}
						else{
							abean.setApp_description(object.getString(Constant.APP_DESCRIPTION));	
						}
						if(!UtilMethod.isStringNullOrBlank(object.getString("app_short_description"))){
							abean.setApp_short_description(object.getString("app_short_description").replace("  ",""));
						}
						else{
							abean.setApp_short_description(object.getString("app_short_description"));	
						}
						float f=0;
						abean.setAppshare(obj.getString("appshare"));
						abean.setCampaign_status(object.getString("campaign_status"));
						f=Float.valueOf(object.getString("app_open_rate"));
						abean.setAmount_per_open(""+ (int)f);
						if(object.getString("app_install_rate")!=null){
							abean.setAmount_per_install(Integer.parseInt((object.getString("app_install_rate"))));
						}
						abean.setApp_image(object.getString("app_image"));
						abean.setApp_instruction(object.getString("app_instructions"));
						abean.setAmount_type(object.getString("amount_type"));
						abean.setShare_link(object.getString("share_link"));
						abean.setSharing_hit(object.getString("sharing_hit"));
						abean.setReferamount(object.getString("referamount"));
						abean.setReferamountsecond(object.getString("referamountsecond"));

						abean.setPackage_name(object.getString("packagename"));
						abean.setPurpose_id(object.getInt("purpose_type"));
						abean.setAppinstall(object.getString(Constant.STATUS));
						abean.setAppfinalstatus(object.getInt(Constant.APP_FINAL_STATUS));
						Log.e("GetApp List Task ","APPNAME "+object.getString("packagename")+" APP STATUS "+object.getInt(Constant.APP_FINAL_STATUS));
						StaticData.read_flag.add(true);
						abean.setPackage_flag(isAppInstalled(object.getString("packagename")));
						if(object.getString("rating")!=null){
							abean.setApp_rate(Float.parseFloat(object.getString("rating")));
						}
						else{
							abean.setApp_rate(0);
						}
						JSONArray step_array=object.getJSONArray("steps");
						f=0;
						if(step_array.length()>0){
							for(int k=0;k<step_array.length();k++){
								JSONObject jobj=step_array.getJSONObject(k);
								ApplicationBean abean1=new ApplicationBean();
								abean1.setUpto_purpose(jobj.getString("offer"));
								abean1.setUpto_amount("" + (int) (f = Float.valueOf(jobj.getString("amount"))));
								abean1.setApp_id(object.getString(Constant.APP_ID));
								abean1.setStep_status(jobj.getInt(Constant.STATUS));
								System.out.println(jobj.getString("taskId"));
								abean1.setTaskId(jobj.getString("taskId"));
								System.out.println(jobj.getString("taskValue"));
								abean1.setTaskValue(Integer.parseInt(jobj.getString("taskValue")));
								StaticData.upto_list.add(abean1);
							}
							System.out.println(StaticData.upto_list);
							f=0;
							/*if(!UtilMethod.isStringNullOrBlank(object.getString("app_open_rate"))){
						f+=Float.valueOf(object.getString("app_open_rate"));
						}*/
							if(step_array.length()>0){
								f=0;
							}
							else if(!UtilMethod.isStringNullOrBlank(object.getString("app_install_rate"))){
								f+=Float.valueOf(object.getString("app_install_rate"));
							}

							for(int l=0;l<step_array.length();l++){
								JSONObject jobj=step_array.getJSONObject(l);
								f+=Float.valueOf(jobj.getString("amount"));
							}
							abean.setUptotalamount(""+(int)f);
							abean.setApp_type("upto");
						}
						else{
							abean.setApp_type("normal");
							abean.setUptotalamount(""+Integer.parseInt(object.getString("app_install_rate")));
						}

						abean.setAppinstall(object.getString("status"));
						
						/**
						 * Conditions for showing the application in Offers Section
						 */

						if(object.getString("packagename")!=null && !isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==0){
							System.out.println(object.getString("packagename") + " " + 1);
							StaticData.application_list.add(abean);
						}
						else if(object.getString("packagename")!=null && isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==0){
							//StaticData.application_list.add(abean);
							System.out.println(object.getString("packagename") + " " + 2);
							//Shows the app even if it's all ready been installed
							//App not installed through us but installed in the device through other source.
						}
						else if(object.getString("packagename")!=null && isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==1){
							//StaticData.application_list.add(abean);
							System.out.println(object.getString("packagename") + " " + 3);
							//Appinstalled though us and money received
						}
						else if(object.getString("packagename")!=null && !isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==1){
							StaticData.application_list.add(abean);
							System.out.println(object.getString("packagename") + " " + 4);
							//App installed though us but has been removed and money is also received
						}
						else if(object.getString("packagename")!=null && !isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==2){
							StaticData.application_list.add(abean);
							System.out.println(object.getString("packagename") + " " + 5);
							//App installed through us but money is pending
						}
						else if(object.getString("packagename")!=null && isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==2){
							StaticData.application_list.add(abean);
							System.out.println(object.getString("packagename") + " " + 6);
							//App installed through us but money not received
						}


					else if(object.getString("packagename")!=null && !isAppInstalled(object.getString("packagename")) && object.getInt(Constant.APP_FINAL_STATUS)==3){
						//StaticData.application_list.add(abean);
							System.out.println(object.getString("packagename") + " " + 7);
					}

				
					}
					olistener.onSuccess();
				}
				else{

					olistener.onError("No Offer Found!");

				}

			}
			catch(Exception e){
				//UtilMethod.showToast("Exception is "+e, cxt);
				//UtilMethod.showToast("on Post Exception is "+e, cxt);
			}
		}
		else{
			System.out.println("in else");
			olistener.onError("slow");
		}
	}
	private boolean isAppInstalled(String packageName) {
		PackageManager pm = cxt.getPackageManager();

		boolean installed = false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}
}
