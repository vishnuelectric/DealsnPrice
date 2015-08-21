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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dnp.asynctask.ForgotPasswordTask;
import com.dnp.data.UtilMethod;
import com.dealnprice.activity.R;

public class ForgotPasswordActivity extends Activity{
	EditText email_text;
	Button get_password;
	String s_email_text;
	Dialog dialog;
	ImageView loading_image;
	private AnimationDrawable loadingViewAnim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpass);
		email_text=(EditText) findViewById(R.id.email);
		get_password=(Button) findViewById(R.id.get_password);
		get_password.setOnClickListener(getPasswordListener);
		
	}
	/*private void setProgressDialog(){
		
		dialog=new Dialog(ForgotPasswordActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading);
		LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
		loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
		loading_image.setBackgroundResource(R.anim.loading_animation);
		loadingViewAnim = (AnimationDrawable) loading_image.getBackground();
		
		loadingViewAnim.start();
		
		dialog.show();
	}*/
	private void setProgressDialog() {
		dialog = new Dialog(ForgotPasswordActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}
	OnClickListener getPasswordListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			s_email_text=email_text.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_email_text)){
				UtilMethod.showToast(getResources().getString(R.string.user_email_message), ForgotPasswordActivity.this);
			}
			else if(!UtilMethod.isValidEmail(s_email_text)){
				UtilMethod.showToast("Please Enter Valid Email ID", ForgotPasswordActivity.this);
			}
			else{
				try{
				setProgressDialog();
				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("txtemail1", new StringBody(s_email_text));
				multipart.addPart("extension",new StringBody("1"));
				/*multipart.addPart("", contentBody);*/
				new ForgotPasswordTask(ForgotPasswordActivity.this, multipart,new ForgotPasswordListener()).execute();
				}
				catch(Exception e){
					
				}
			}
		}
	};
	
	public class ForgotPasswordListener{
		public void onSuccess(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			final AlertDialog adialog=new AlertDialog.Builder(ForgotPasswordActivity.this).create();
			adialog.setTitle("Message");
			adialog.setMessage(msg);
			adialog.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					adialog.dismiss();
					finish();
				}
			});
			adialog.show();
			
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg.equals("slow")){
				UtilMethod.showNetworkError(ForgotPasswordActivity.this);
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(ForgotPasswordActivity.this).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
				adialog.setButton("OK",new DialogInterface.OnClickListener() {
					
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
}
