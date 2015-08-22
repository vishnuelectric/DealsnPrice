package com.dealnprice.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dnp.asynctask.LoginTask;
import com.dnp.asynctask.RegistrationWithSocialTask;
import com.dnp.data.UtilMethod;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

public class LoginActivity extends Activity {
	EditText user_email,user_password;
	Button sign_in;
	String google_code;
	WebView v;
	public static LoginActivity la = null;
	Dialog d;
	TextView forgot_password,sign_up;
	String s_user_email,s_user_password;
	String s_user_name,s_email;
	Context cxt;
	CallbackManager callbackManager;
	Dialog dialog;
	private AnimationDrawable loadingViewAnim;
	public LoginManager loginManager;
	public static AccessToken accessToken;
	Date accessExpir;
	String firstName,lastName,email,userFbId,httpRequest;
	//Facebook fb;
	boolean flag = false;
	String APP_ID;
	ImageView loading_image;
	ImageView google_plus_id;
	public static String regId;
	ImageView authButton;
	Profile profile;
	ProgressDialog mDialog;
	public static boolean isFbDataReceived = false,isServiceCalled = false;
	Context con;
	private String googleWarn = "Could not connect with Google+. Please try again";
	private String googleText = "Sign in with Google";

	public static GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	private static final int RC_SIGN_IN = 0;
	public static int flag1=0;


	ConnectionCallbacks googleOnConnectionCallBack = new ConnectionCallbacks() {
		@Override
		public void onConnectionSuspended(int connectionSuspendedReason) {
			Toast.makeText(LoginActivity.this, "User is onConnectionSuspended!" + connectionSuspendedReason, Toast.LENGTH_LONG).show();

		}

		@Override
		public void onConnected(Bundle arg0) {

			if (mSignInClicked && Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				JSONObject jsonObject= null;
				getProfileInformation(jsonObject);


			} else {
				Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
				mGoogleApiClient.disconnect();
				mGoogleApiClient.connect();
			}
		}
	};

	OnConnectionFailedListener googleOnConnectionFailedListener = new OnConnectionFailedListener() {

		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {

			Log.e("Log.e", connectionResult.getErrorCode() + "...");
			if (!connectionResult.hasResolution()) {
				// GooglePlayServicesUtil.getErrorDialog(0, LoginActivity.this,
				// 0);
				//String msg = LoginActivity.this.getResources().getString(R.string.google_play_app_update_message);
				//String packageName = "com.google.android.gms";

				//UpgradeAppPopUp(msg, packageName);

			}

			if (!mIntentInProgress) {
				// Store the ConnectionResult for later usage
				mConnectionResult = connectionResult;

				if (mSignInClicked) {
					// The user has already clicked 'sign-in' so we attempt to
					// resolve all
					// errors until the user is signed in, or they cancel.
					resolveSignInError();
				}
			}

		}
	};

	OnClickListener signInOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			//if (NetworkConnectivityCheck.checkNetConnectivity(LoginActivity.this)) {
			if (!mGoogleApiClient.isConnecting()) {

				//tracker.send(new HitBuilders.EventBuilder().setCategory("GPlusLogin").setAction("ClickonGPlusLogin").setLabel("GPlusLogin").setValue(1).build());

				mSignInClicked = true;
				resolveSignInError();
			}
			//} else {
			//authenticationFail("Seems like you are not connected to the internet. Please try again later");
			//}
		}


	};

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.login_layout);
		user_email = (EditText) findViewById(R.id.user_email);
		user_password=(EditText) findViewById(R.id.user_password);
		forgot_password=(TextView) findViewById(R.id.forgot_password);
		sign_up=(TextView) findViewById(R.id.sign_up);
		sign_in=(Button) findViewById(R.id.sign_in);
		google_plus_id=(ImageView) findViewById(R.id.google_plus_id);
		authButton=(ImageView) findViewById(R.id.authButton);
		sign_up.setOnClickListener(signupListener);
		SharedPreferences shpf=getSharedPreferences("User_login", 1);
		callbackManager = CallbackManager.Factory.create();
		Editor edt = shpf.edit();
		edt.putString("type", "no");
		edt.commit();
		sign_in.setOnClickListener(loginListener);
		forgot_password.setOnClickListener(forgotListener);

		isFbDataReceived = false;
		con=LoginActivity.this;
		loginManager = LoginManager.getInstance();
		
		la = this;
		
		/*authButton.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);*/
		
		/*authButton.setLayoutParams(LayoutParams.FILL_PARENT,"60dp");*/
		APP_ID=getString(R.string.app_id);
		//fb=new Facebook(APP_ID);
		generateHash();	/*if(mGoogleApiClient.isConnected()){*/
		/*if(flag1==0){
			mGoogleApiClient = new GoogleApiClient.Builder(this)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this).addApi(Plus.API)
			.addScope(Plus.SCOPE_PLUS_LOGIN)
			.build();
		}*/

loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
	@Override
	public void onSuccess(LoginResult loginResult) {
		accessToken = loginResult.getAccessToken();

		Arrays.toString(accessToken.getPermissions().toArray());
		Profile.fetchProfileForCurrentAccessToken();
	
		//profile = Profile.getCurrentProfile();

		System.out.println(profile == null);
		System.out.println(Arrays.toString(accessToken.getPermissions().toArray()) +" "+ loginResult.getAccessToken());
		fetchUserDetails();
	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onError(FacebookException e) {

	}
});

		signInWithGplus();
		cxt=this;
		//getGCMId();


		authButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sign_in_with_facebook();
			}
		});

		/*if(mGoogleApiClient.isConnected()){*/
		/*if(getSharedPreferences("User_login", 1).getString("type", null).equals("gplus")){*/
		/*if(flag1==0){
			mGoogleApiClient = new GoogleApiClient.Builder(this)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this).addApi(Plus.API)
			.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		}*/
		/*}*/
google_plus_id.setOnClickListener(signInOnClickListener);
		/*google_plus_id.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				v= new WebView(LoginActivity.getAct());
				d = new Dialog(LoginActivity.getAct());
				d.addContentView(v, new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight()- 100));
				CookieManager cookieManager = CookieManager.getInstance();
				cookieManager.setAcceptThirdPartyCookies(v,true);
				CookieManager.setAcceptFileSchemeCookies(true);
				//cookieManager.setAcceptCookie(true);
				v.getSettings().setJavaScriptEnabled(true);
				v.getSettings().setDomStorageEnabled(true);

				v.setWebViewClient(new WebViewClient() {
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						System.out.println(url);
						if(url.startsWith("https://localhost/"))
						{
							String [] ar = url.trim().split("&");
							google_code = ar[0].trim().split("=")[1];
							System.out.println(google_code);
							d.dismiss();
							JSONObject obj = getAccesstoken();
							//getProfileInformation(obj);
							return true;
						}
						return false;
					}

					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon) {
						super.onPageStarted(view, url, favicon);
						System.out.println(url);
					}

					@Override
					public void onPageFinished(WebView view, String url) {
						super.onPageFinished(view, url);
						System.out.println(url);
						//v.loadUrl("javascript:clickit();");
					}
				});

				v.loadUrl("https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/plus.login+https://www.googleapis.com/auth/userinfo.email&redirect_uri=https://localhost&response_type=code&client_id=342578389641-f2o3ijf95cglblqddv8ddgmumhl33e45.apps.googleusercontent.com");
             //v.loadUrl("file:///android_asset/gplus.html");

				d.show();



			}
		});*/
	}

	public JSONObject getAccesstoken(){
 JSONObject jsonObject1 = null;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("https://www.googleapis.com/oauth2/v3/token");
					HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
					httpURLConnection.setRequestMethod("POST");
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
					OutputStreamWriter writer = new OutputStreamWriter(
							httpURLConnection.getOutputStream());
					writer.write("code="+ google_code +"&redirect_uri=https://localhost&client_id=342578389641-f2o3ijf95cglblqddv8ddgmumhl33e45.apps.googleusercontent.com&client_secret=fHlFs7r2DCTb6PLuLokltbgu&scope=&grant_type=authorization_code");
					writer.flush();
					String response = streamToString(httpURLConnection.getInputStream());
					System.out.println(response);
					JSONObject jsonObject = (JSONObject)new JSONTokener(response).nextValue();
                       String token = jsonObject.getString("access_token");
                        writer.close();

					       httpURLConnection.disconnect();
					URL url1 = new URL("https://www.googleapis.com/plus/v1/people/me?access_token="+ token);
				HttpURLConnection	httpURLConnection1 = (HttpURLConnection) url1.openConnection();
					httpURLConnection1.setRequestMethod("GET");
					httpURLConnection1.setDoInput(true);

					String response1 = streamToString(httpURLConnection1.getInputStream());

					System.out.println(response1);
					JSONObject jsonObject1 =(JSONObject) new JSONTokener(response1).nextValue();



				}
				catch(Exception e)
				{
					e.printStackTrace();
				}




			}
		}).start();
		return jsonObject1;
	}
	private String streamToString(InputStream is) throws IOException {
		String str = "";

		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				reader.close();
			} finally {
				is.close();
			}

			str = sb.toString();
		}

		return str;
	}
	public void sign_in_with_facebook(){
		loginManager.logInWithReadPermissions(this, Arrays.asList("email,public_profile"));
	}




	private void fetchUserDetails() {
		//Log.i("Session is", "Access Token" + session.getAccessToken());




		GraphRequest request =  GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
			@Override
			public void onCompleted(JSONObject jsonObject, final GraphResponse graphResponse) {
				if(graphResponse.getError() == null) {
					getUserFacebookData(jsonObject);
				}
				else
				{
					System.out.println(graphResponse.getError());
				}
			}
		});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id,name,email");
		request.setParameters(parameters);
		request.executeAsync();
	}

	/*private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		flag = true;

		if (state.isOpened()) {
			//if(!isFbDataReceived){
			accessToken = session.getAccessToken();
			accessExpir = session.getExpirationDate();

			/*Log.i("MAinActivity", "Logged in...");

			/*if (state.isOpened()) {
			if(mDialog==null){
				mDialog = new ProgressDialog(LoginActivity.this);
			}
			if(!mDialog.isShowing()){
				mDialog.setMessage("Please Wait...");
				mDialog.setCancelable(false);
				mDialog.show();
			}

			/* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		             StrictMode.setThreadPolicy(policy);

			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
				public void onCompleted(GraphUser user, Response response) {
					Log.v("come here"," "+session);
					session.close();

					/* getUserFacebookData(user);
				}
			});
			 }
			}
		}/*else if(state.equals(SessionState.CLOSED_LOGIN_FAILED)){
	    	UtilMethod.showToast("Unable to connect to facebook1233,Please try again"+session+" 12345",CityGuideMainActivity.this);
	    }
		else if (state.isClosed()) {
			 Log.v("MainActivity", "Logged out...");
			/*UtilMethod.showToast("Logged out", CityGuideMainActivity.this);
		}
	}*/

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode,resultCode,data);
		if (requestCode == RC_SIGN_IN) {
			if (resultCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//uiHelper.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//	uiHelper.onDestroy();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		//uiHelper.onSaveInstanceState(outState);
	}


	private void getUserFacebookData(JSONObject jsonObject){
		if(jsonObject!= null){
			try{
				System.out.println(jsonObject.toString());
				isFbDataReceived = true;


				String gender = "male";//user.getProperty("gender").toString();
				email =  jsonObject.getString("email"); //user.getProperty("email").toString();
				//userFbId = profile.getId();
				/* String loginType = AppConstents.FACEBOOK;*/
				httpRequest = "username="+jsonObject.getString("name")+"&firstname="+jsonObject.getString("name")+
						"&lastname="+""+"&email="+email+
						"&gender="+""+"&logintype="+1+
						"&accesstoken="+accessToken+"&accessexpir="+accessToken.getExpires();
				Log.v("Http Request Facebook Data User",httpRequest); 
				/*if(mDialog.isShowing()){
					mDialog.dismiss();
				}*/
				try{
					isServiceCalled = true;
					SharedPreferences shpf1=getSharedPreferences("User_login", 1);
					Editor edt=shpf1.edit();
					edt.putString("type", "fb");
					edt.commit();

					MultipartEntity multiPart=new MultipartEntity();
					multiPart.addPart("txtname", new StringBody(jsonObject.getString("name")+" "+""));
					multiPart.addPart("txtemail1", new StringBody(email));
					multiPart.addPart("txtpassword1", new StringBody("1234"));
					multiPart.addPart("mobileval", new StringBody(""));
					multiPart.addPart("value", new StringBody("1"));
					multiPart.addPart("extension", new StringBody("1"));
					multiPart.addPart("typeid", new StringBody("1"));
					multiPart.addPart("inviteid",new StringBody("814"));
					new RegistrationWithSocialTask(LoginActivity.this, multiPart, new LoginListener()).execute();
					/*multiPart.addPart("mobile",new StringBody(""));*/
					/*multiPart.addPart("imageurl", new StringBody(profilePic));*/
					// new RegistrationTask(con, multiPart, new RegistrationListenerClass(),"2").execute();

					//uiHelper.onDestroy();
				}

				catch(Exception e){
					//UtilMethod.showToast("Facebook Exception is "+e, LoginActivity.this);
				}


			}
			catch(Exception e) {
				e.printStackTrace();
				Log.d("Exception", "Exception e"+e.toString());
			}

		}


	}

	/*	private void setProgressDialog(){

            dialog=new Dialog(LoginActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_loading);
            LinearLayout loadinglayout=(LinearLayout) dialog.findViewById(R.id.LinearLayout1);
            loading_image=(ImageView) dialog.findViewById(R.id.imageView111);
            loading_image.setBackgroundResource(R.anim.loading_animation);
            loadingViewAnim = (AnimationDrawable) loading_image.getBackground();

            loadingViewAnim.start();
            dialog.setCancelable(false);
            dialog.show();
        }*/
	private void setProgressDialog() {
		dialog = new Dialog(LoginActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_loading_progressbar);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);
		dialog.show();
	}

	public void generateHash(){
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.dealnprice.activity",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	OnClickListener signupListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
			startActivity(intent);
		}
	};

	OnClickListener loginListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			s_user_email=user_email.getText().toString();
			s_user_password=user_password.getText().toString();
			if(UtilMethod.isStringNullOrBlank(s_user_email) && UtilMethod.isStringNullOrBlank(s_user_password)){
				UtilMethod.showToast(getResources().getString(R.string.required_parameter_message), LoginActivity.this);
			}
			else if(UtilMethod.isStringNullOrBlank(s_user_email)){
				UtilMethod.showToast(getResources().getString(R.string.user_name_message), LoginActivity.this);
			}
			else if(UtilMethod.isStringNullOrBlank(s_user_password)){
				UtilMethod.showToast(getResources().getString(R.string.user_password_message), LoginActivity.this);
			}
			else{
				try{
					setProgressDialog();
					//UtilMethod.showToast("GCMID is "+regId, LoginActivity.this);
					SharedPreferences shpf=getSharedPreferences("User_login",1);
					Editor edt=shpf.edit();
					edt.putString("user_pass", s_user_password);
					edt.putString("type", "normal");
					edt.commit();
					MultipartEntity multipart=new MultipartEntity();
					multipart.addPart("txtemail1", new StringBody(s_user_email));
					multipart.addPart("txtpassword1",new StringBody(s_user_password));
					multipart.addPart("extension", new StringBody("1"));
					multipart.addPart("type", new StringBody("0"));
					if(!UtilMethod.isStringNullOrBlank(regId)){
						multipart.addPart("gcmid",new StringBody(regId));
					}
					else{
						multipart.addPart("gcmid",new StringBody(""));
					}
					new LoginTask(LoginActivity.this, multipart, new LoginListener(),"hfdhfhsfhdfh").execute();
				}
				catch(Exception e){
					if(dialog!=null && dialog.isShowing()){
						dialog.dismiss();
					}
				}
			}
		}
	};

	OnClickListener forgotListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
			startActivity(intent);
		}
	};

	public class LoginListener{
		public void onSuccess(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
			startActivity(intent);
			finish();
		}
		public void onSuccesswithSocial(){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
			startActivity(intent);
			finish();
		}
		public void onError(String msg){
			if(dialog!=null && dialog.isShowing()){
				//loadingViewAnim.stop();
				dialog.dismiss();
			}
			if(msg!=null && msg.equals("slow")){
				UtilMethod.showNetworkError(LoginActivity.this);
			}
			else{
				final AlertDialog adialog=new AlertDialog.Builder(LoginActivity.this).create();
				adialog.setTitle("Message");
				adialog.setMessage(msg);
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


	// this code is for Google Plus Login

	protected void onStart() {
		super.onStart();

		if (!mGoogleApiClient.isConnected())
			mGoogleApiClient.connect();

	}

	protected void onStop(){
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	/*private void resolveSignInError() {
		if (mConnectionResult!=null && mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}
*/

	/*public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// remGoogleApiClientsolve all
				// errors until the user is signed in, or they cancel.


				resolveSignInError();
			}
		}

	}*/

	/*public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		// Get user's information
		getProfileInformation();

		// Update the UI after signin
		updateUI(true);

	}*/


	/*private void updateUI(boolean isSignedIn) {
		if (isSignedIn) {
			btnSignIn.setVisibility(View.GONE);
			btnSignmGoogleApiClientOut.setVisibility(View.VISIBLE);
			btnRevokeAccess.setVisibility(View.VISIBLE);
			llProfileLayout.setVisibility(View.VISIBLE);
		} else {
			btnSignIn.setVisibility(View.VISIBLE);
			btnSignOut.setVisibility(View.GONE);
			btnRevokeAccess.setVisibility(View.GONE);
			llProfileLayout.setVisibility(View.GONE);
		}
	}
	 */


	public static LoginActivity getAct()
	{
		return la;
	}
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation(JSONObject jsonObject) {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.e("DnP", "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
				SharedPreferences shpf2=getSharedPreferences(Constant.pref_name,1);
				Editor edt1=shpf2.edit();
				edt1.putString("type","gplus");
				edt1.commit();

				MultipartEntity multipart=new MultipartEntity();
				multipart.addPart("txtname",new StringBody(personName));
				multipart.addPart("txtemail1",new StringBody(email));
				multipart.addPart("txtpassword1",new StringBody("1234"));
				multipart.addPart("mobileval", new StringBody(""));
				multipart.addPart("value",new StringBody("1"));
				multipart.addPart("extension",new StringBody("1"));
				multipart.addPart("typeid", new StringBody("2"));
				multipart.addPart("inviteid",new StringBody("814"));
				setProgressDialog();
				new RegistrationWithSocialTask(LoginActivity.this, multipart, new LoginListener()).execute();

				/*txtName.setText(personName);
				txtEmail.setText(email);*/

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				/*personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;*/

				/*new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);*/

			} else if(jsonObject != null){



			}
			else{
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		/*updateUI(false);*/
	}


	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(googleOnConnectionCallBack)
				.addOnConnectionFailedListener(googleOnConnectionFailedListener)
				.addScope(Plus.SCOPE_PLUS_LOGIN)
				.addApi(Plus.API, Plus.PlusOptions.builder().build())
				.build();

	}

	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			/*updateUI(false);*/
		}
	}

	/**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
					.setResultCallback(new ResultCallback<Status>() {
						@Override
						public void onResult(Status arg0) {
							Log.e("DnP", "User access revoked!");
							mGoogleApiClient.connect();
						}

					});
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
