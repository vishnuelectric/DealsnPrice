package com.dealnprice.activity;


import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dnp.asynctask.RegistrationTask;
import com.dnp.data.UtilMethod;
import com.google.android.gcm.GCMRegistrar;
import com.dealnprice.activity.R;

public class RegistrationActivity extends Activity{
	EditText user_email,user_password,confirm_password,mobile_number,refer_code;//,user_name;
	Button submit;
	Dialog dialog1;
	ImageView loading_image;
	//private AnimationDrawable loadingViewAnim;
	String s_refer_code;
	
	//Spinner country_name;
	String s_user_email,s_user_password,s_confirm_password,s_mobile_number,s_referral_code,s_country_name,s_user_name;
	String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static String regId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_layout);
		user_email=(EditText) findViewById(R.id.user_email);
		user_password=(EditText) findViewById(R.id.user_password);
		confirm_password=(EditText) findViewById(R.id.confirm_password);
		mobile_number=(EditText) findViewById(R.id.user_mobile);
		refer_code=(EditText) findViewById(R.id.refer_code);
		//user_name=(EditText) findViewById(R.id.user_name);
		//country_name=(Spinner) findViewById(R.id.spinner_country);
		submit=(Button) findViewById(R.id.sign_up);
		submit.setOnClickListener(registerListener);
		getGCMId();
	
	}
	
	OnClickListener registerListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_user_email=user_email.getText().toString();
			s_user_password=user_password.getText().toString();
			s_confirm_password=confirm_password.getText().toString();
			s_mobile_number=mobile_number.getText().toString();
			s_refer_code=refer_code.getText().toString();
			//s_user_name=user_name.getText().toString();
			
			GCMRegistrar.checkDevice(RegistrationActivity.this);
			GCMRegistrar.checkManifest(RegistrationActivity.this);
			regId = GCMRegistrar.getRegistrationId(RegistrationActivity.this);
			Log.v("gcm reg_id=", ""+regId);
			if (regId.equals("")) {
				GCMRegistrar.register(RegistrationActivity.this, CommonUtilities.SENDER_ID);
				Log.v("s1", ""+regId);
			}


			System.out.println("NEW TEST.....Un.. Register on GCMIntentService");// ====  packagepath : "+context.getPackageCodePath()+" ,,,,,, registrationId : "+registrationId);
			Log.i("TAG", "Device unregistered");

			getGCMId();
			
			if(UtilMethod.isStringNullOrBlank(s_user_email)){
				UtilMethod.showToast(getResources().getString(R.string.user_email_message), RegistrationActivity.this);
			}
			else if(UtilMethod.isStringNullOrBlank(s_user_password)){
				UtilMethod.showToast(getResources().getString(R.string.user_password_message), RegistrationActivity.this);
			}
			else if(UtilMethod.isStringNullOrBlank(s_confirm_password)){
				UtilMethod.showToast(getResources().getString(R.string.user_confirm_password_message), RegistrationActivity.this);
			}
			else if(UtilMethod.isStringNullOrBlank(s_mobile_number)){
				UtilMethod.showToast(getResources().getString(R.string.mobile_number_message), RegistrationActivity.this);
			}
			else if(!UtilMethod.isValidEmail(s_user_email)){
				UtilMethod.showToast(getResources().getString(R.string.valid_email), RegistrationActivity.this);
			}
			else if(!s_user_password.equals(s_confirm_password)){
				UtilMethod.showToast(getResources().getString(R.string.mismatch_passsword), RegistrationActivity.this);
			}
			else if(s_mobile_number.length()>10 || s_mobile_number.length()<10){
				UtilMethod.showToast(getResources().getString(R.string.valid_mobile_number), RegistrationActivity.this);
			}
			else{ 
				try{
				if(UtilMethod.isNetworkAvailable(RegistrationActivity.this)){
				setProgressDialog();
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("txtname", new StringBody(s_user_email.substring(0,s_user_email.indexOf('@'))));
				multipart.addPart("txtemail1", new StringBody(s_user_email));
				multipart.addPart("txtpassword1",new StringBody(s_user_password));
				multipart.addPart("mobileval",new StringBody(s_mobile_number));
			//	UtilMethod.showToast("GCM ID is "+regId, RegistrationActivity.this);
				if(UtilMethod.isStringNullOrBlank(regId)){
					multipart.addPart("gcmid", new StringBody(""));		
				}
				else{
				multipart.addPart("gcmid", new StringBody(regId));
				}
				multipart.addPart("type",new StringBody("0"));
				multipart.addPart("value",new StringBody("1"));
				multipart.addPart("extension",new StringBody("1"));
				if(UtilMethod.isStringNullOrBlank(s_refer_code)){
					multipart.addPart("inviteid", new StringBody(""));
				}
				else{
					multipart.addPart("inviteid", new StringBody(s_refer_code));
				}
				
				
				new RegistrationTask(RegistrationActivity.this, multipart, new RegistrationListener()).execute();
					}
					else{
						UtilMethod.showServerError(RegistrationActivity.this);
					}
				}
					
				
				catch(Exception e){
					UtilMethod.showToast("Exception is "+e, RegistrationActivity.this);
				}
			}
			
		}
	};
/*	private void setProgressDialog(){
		
		dialog1=new Dialog(RegistrationActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.activity_loading);
		LinearLayout loadinglayout=(LinearLayout) dialog1.findViewById(R.id.LinearLayout1);
		loading_image=(ImageView) dialog1.findViewById(R.id.imageView111);
		loading_image.setBackgroundResource(R.anim.loading_animation);
		loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
		loadingViewAnim.start();
		dialog1.show();
	}*/
	private void setProgressDialog() {

		dialog1 = new Dialog(RegistrationActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.activity_loading_progressbar);
		dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog1.setCancelable(false);
		dialog1.show();
	}
	
			
		public class RegistrationListener{
				public void onSuccess(String msg){
					if(dialog1!=null && dialog1.isShowing()){
						//loadingViewAnim.stop();
						dialog1.dismiss();
					}
					final AlertDialog adialog=new AlertDialog.Builder(RegistrationActivity.this).create();
					adialog.setTitle("Message");
					adialog.setMessage(msg);
					adialog.setButton("OK",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							adialog.dismiss();
							finish();
						}
					});
					adialog.show();
				}
				public void onError(String msg){
					if(dialog1!=null && dialog1.isShowing()){
						//loadingViewAnim.stop();
						dialog1.dismiss();
					}
					if(msg!=null && msg.equals("slow")){
						UtilMethod.showNetworkError(RegistrationActivity.this);
					}
					else{
						final AlertDialog adialog=new AlertDialog.Builder(RegistrationActivity.this).create();
						adialog.setTitle("Message");
						adialog.setMessage(msg);
						adialog.setCancelable(false);
						adialog.setButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								adialog.dismiss();
								
							}
						});
						adialog.show();
					}
				}
		}
		private void getGCMId(){
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			final String regId = GCMRegistrar.getRegistrationId(this);
			Log.v("gcm reg_id=", ""+regId);
			if (regId.equals("")) {
				GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
				Log.v("s1", ""+regId);
			}
		}
		
}
