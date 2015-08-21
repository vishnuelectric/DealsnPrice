package com.dealnprice.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.dealnprice.activity.R;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
	static String payment_mode;
	static String task_id,status,task_bid_status,json,url,url2,task_type;

	static int requestId;
	public static final int NOTIFICATION_ID = 1;
	private static int notificationCount = 1;

	private static NotificationManager notificationManager;

	//	private static NotificationManager testNotificationManager;

	static NotificationCompat.Builder mNotifyBuilder;
	//	private static Context ctx;


	public GCMIntentService() {
		super(CommonUtilities.SENDER_ID);
		//super(SENDER_ID);
		//		ctx = getApplicationContext();
		//		
		//		notificationManager = (NotificationManager)
		//				ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		//		
		//		
		//		testNotificationManager =(NotificationManager)
		//				ctx.getSystemService(Context.NOTIFICATION_SERVICE);


	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		/* Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");
        Log.d("NAME", MainActivity.name);
        ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);*/
		System.out.println("NEW TEST.....Register on GCMIntentService ====  packagepath : "+context.getPackageCodePath()+" ,,,,,, registrationId : "+registrationId);
		//		System.out.println("NEW TEST..... Register on GCMIntentService ====  "+context.getPackageCodePath());
		LoginActivity.regId = registrationId;
		RegistrationActivity.regId = registrationId;
	} 

	/**
	 * Method called on device un registred 
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

		System.out.println("NEW TEST.....Un.. Register on GCMIntentService");// ====  packagepath : "+context.getPackageCodePath()+" ,,,,,, registrationId : "+registrationId);
		Log.i(TAG, "Device unregistered");
	}
	/**
	 * Method called on Receiving a new message
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {
		try
		{
		//	Log.i(TAG, "Received message");
		//	Log.v("price", intent.getExtras().getString("price"));
		String message = intent.getExtras().getString("price");
		//		Log.v("price", intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE));//implement new 
		//		String message = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);
		//System.out.println("NEW TEST.... onMessage GCMIntentService message Received ==  "+message);
		//displayMessage(context, message);
		// notifies user
		//UtilMethod.showToast(message, context);
		generateNotification(context, message);
		}catch(Exception e)
		{
			Log.e("GCMIntentService :: ","GCM Service :: failed "+e);
		}
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");

		String message = getString(R.string.app_name, total);


		generateNotification(context, message);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		System.out.println("NEW TEST... GCMIntentService onError() method error idd == "+errorId);

	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);

		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {

		//		int icon = R.drawable.gotasker;
		//		
		//		long when = System.currentTimeMillis();
		//		
		////		NotificationManager notificationManager = (NotificationManager)
		////				context.getSystemService(Context.NOTIFICATION_SERVICE);
		//		
		//		notificationManager = (NotificationManager)
		//				context.getSystemService(Context.NOTIFICATION_SERVICE);

		//		if(notificationManager == null){
		//			
		//			notificationManager = (NotificationManager)
		//					context.getSystemService(Context.NOTIFICATION_SERVICE);
		//			
		//		}


		Notification notification = null;

		String title = context.getString(R.string.app_name);
		String msg=" ";
		Intent notificationIntent = null;

		Log.v("message", message);

		/*		try{
			JSONObject jsob=new JSONObject(message);
			String notification_status=jsob.getString("notification_status");
			if(notification_status.equals("1")){
				msg=jsob.getString("user_name")+" commented on your feed "+jsob.getString("feed_title");
			}
			else if(notification_status.equals("2")){
				msg=jsob.getString("user_name")+" voted on your feed "+jsob.getString("feed_title"); 
			}
			else if(notification_status.equals("3")){
				msg=jsob.getString("user_name")+" follow to you";
			}
				notificationIntent=new Intent(context,DashBoardActivity.class);
				notificationIntent.putExtra("notification_status",notification_status);
				notificationIntent.putExtra("feed_id",jsob.getString("feed_id"));
				notificationIntent.putExtra("feed_title",jsob.getString("feed_title"));
				notificationIntent.putExtra("user_name",jsob.getString("user_name"));
				notificationIntent.putExtra("user_image",jsob.getString("user_image"));
				notificationIntent.putExtra("user_id",jsob.getString("user_id"));


		}
		catch(Exception e){

		}*/



		//		String msg="",bid_id="0", task_id="0",status="0",task_type;

		/*		try{

			JSONObject jsob=new JSONObject(message);
			task_type=jsob.getString("task_type");
			status=jsob.getString("status");
			Log.v("task_type",task_type);

			if(status.equals("2")){
				// this status for post a bid for the particular task.
				msg=jsob.getString("msg");
				bid_id=jsob.getString("bid_id");
				task_id=jsob.getString("task_id");
//				notification = new Notification(icon, msg, when);

//				System.out.println("NEW TEST... Check Task_Type on GCMInetentService class getNotification()  === "+task_type+" ........  Task Iddd ===  "+task_id);

				if(task_type.equals("0")){

					notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);

//					System.out.println("NEW TEST... status ==0 ,,,Check Task_Type on GCMInetentService class getNotification() 111  Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);

				}
				else{

					notificationIntent = new Intent(context, GoTaskerNotificationItemActivity.class);	
//					System.out.println("NEW TEST... status== 0 ,,,, Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				notificationIntent.putExtra("message",msg);
				notificationIntent.putExtra("bid_id", bid_id);
				Log.v("Bid User ID is ",jsob.getString("bid_user_id"));
				notificationIntent.putExtra("bid_user_id",jsob.getString("bid_user_id"));
				notificationIntent.putExtra("task_id", task_id);
				notificationIntent.putExtra("bidder_image", jsob.getString("bid_user_image"));
				notificationIntent.putExtra("bidder_name", jsob.getString("bid_user_first_name")+" "+jsob.getString("bid_user_last_name"));
				notificationIntent.putExtra("desire_amount", jsob.getString("desire_amount"));
				notificationIntent.putExtra("status", "1");
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));

				System.out.println("NEW TEST...This status for post a bid for the particular task...next status == 1");

			}

			if(status.equals("1")){

//				System.out.println("NEW TEST... status == 1,,,,Check Task_Type on GCMInetentService class getNotification() Next GoTaskerWriteCommentActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				// This status is used for comment notification

				msg = jsob.getString("msg");

//				notification = new Notification(icon, msg, when);
				//call intent of post comment class
				notificationIntent = new Intent(context,GoTaskerWriteCommentActivity.class);
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));
				notificationIntent.putExtra("status", jsob.getString("private_public_status"));
				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(notificationIntent);

				System.out.println("NEW TEST...This status is used for comment notification...next status == "+jsob.getString("private_public_status"));

			}


			if(status.equals("4")){
				// This status is used for "Mark as Completed"
				msg=jsob.getString("msg");
//				notification = new Notification(icon, msg, when);
				if(task_type.equals("0")){
					notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);
//					System.out.println("NEW TEST... status == 4,,,,Check Task_Type on GCMInetentService class getNotification() 111 Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				else{
					notificationIntent = new Intent(context, GoTaskerNotificationItemActivity.class);
//					System.out.println("NEW TEST... status == 4,,,,Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerNotificationItemActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				notificationIntent.putExtra("message", msg);
				bid_id=jsob.getString("bid_id");
				notificationIntent.putExtra("bid_id", bid_id);
				notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));
				notificationIntent.putExtra("bidder_name", jsob.getString("bid_user_first_name")+" "+jsob.getString("bid_user_last_name"));
				notificationIntent.putExtra("bidder_image", jsob.getString("bid_user_image"));
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));
				//notificationIntent.putExtra("desire_amount", jsob.getString("desire_amount"));
				notificationIntent.putExtra("status", "3");
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));

				System.out.println("NEW TEST...This status is used for Mark as Completed...next status == 3");

			}

			if(status.equals("5")){
				// This status is used for the confirming the task
				msg=jsob.getString("msg");
//				notification = new Notification(icon, msg, when);
				if(task_type.equals("0")){
					notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);
//					System.out.println("NEW TEST... status == 5,,,,Check Task_Type on GCMInetentService class getNotification() 111 Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				else{
					notificationIntent = new Intent(context, GoTaskerNotificationItemActivity.class);	
//					System.out.println("NEW TEST... status == 5,,,,Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerNotificationItemActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				notificationIntent.putExtra("message", msg);
				bid_id=jsob.getString("bid_id");

				payment_mode=jsob.getString("payment_mode");
				if(payment_mode.equals("1")){

					notificationIntent.putExtra("status", "4");	
					notificationIntent.putExtra("bid_id", jsob.getString("bid_id"));
					notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));
					notificationIntent.putExtra("bidder_name", jsob.getString("task_user_first_name")+" "+jsob.getString("task_user_last_name"));
					notificationIntent.putExtra("bidder_image", jsob.getString("task_user_image"));
					notificationIntent.putExtra("task_id", jsob.getString("task_id"));
					notificationIntent.putExtra("desire_amount", jsob.getString("desire_amount"));

					System.out.println("NEW TEST...This status is used for the confirming the task...next status == 4");
				}
				else{

					notificationIntent.putExtra("status", "5");
					notificationIntent.putExtra("bid_user_paypal_email", jsob.getString("bid_user_paypal_email"));
					notificationIntent.putExtra("bid_id", jsob.getString("bid_id"));
					notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));
					notificationIntent.putExtra("bidder_name", jsob.getString("task_user_first_name")+" "+jsob.getString("task_user_last_name"));
					notificationIntent.putExtra("bidder_image", jsob.getString("task_user_image"));
					notificationIntent.putExtra("task_id", jsob.getString("task_id"));
					notificationIntent.putExtra("desire_amount", jsob.getString("desire_amount"));

					System.out.println("NEW TEST...This status is used for the confirming the task...next status == 5");
				}

				notificationIntent.putExtra("task_type", jsob.getString("task_type"));


			}

			if(status.equals("5")){
				// notification comes to the task owner for paypal payment.  
				msg=jsob.getString("msg");
				notification = new Notification(icon, msg, when);
				notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);
				notificationIntent.putExtra("message", msg);
				bid_id=jsob.getString("bid_id");
				notificationIntent.putExtra("bid_id", bid_id);
				notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));
				notificationIntent.putExtra("bidder_name", jsob.getString("bid_user_first_name")+" "+jsob.getString("bid_user_last_name"));
				notificationIntent.putExtra("bidder_image", jsob.getString("bid_user_image"));
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));
				notificationIntent.putExtra("desire_amount", jsob.getString("System.out.println("NEW TEST... status ==0 ,,,Check Task_Type on GCMInetentService class getNotification() 111  Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);desire_amount"));
				notificationIntent.putExtra("bid_user_paypal_email", jsob.getString("bid_user_paypal_email"));
				notificationIntent.putExtra("status", jsob.getString("status"));
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));
			}

			if(status.equals("6")){

				// This status is used after paying the payment 

				msg=jsob.getString("msg");
//				notification = new Notification(icon, msg, when);
				if(task_type.equals("0")){
					notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);
					notificationIntent.putExtra("status","6");
					notificationIntent.putExtra("task_id",jsob.getString("task_id"));
					notificationIntent.putExtra("task_type", jsob.getString("task_type"));
					notificationIntent.putExtra("bidder_name", jsob.getString("bid_user_first_name")+" "+jsob.getString("bid_user_last_name"));
					notificationIntent.putExtra("bidder_image", jsob.getString("bid_user_image"));
					notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));

//					System.out.println("NEW TEST... status == 6,,,,Check Task_Type on GCMInetentService class getNotification() 111 Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				else{
					notificationIntent = new Intent(context, GoTaskerNotificationItemActivity.class);
					notificationIntent.putExtra("status","6");
					notificationIntent.putExtra("task_id",jsob.getString("task_id"));
					notificationIntent.putExtra("task_type",jsob.getString("task_type"));
					task_type=jsob.getString("task_type");
					if(task_type.equals("0")){
					notificationIntent.putExtra("bidder_name", jsob.getString("task_user_first_name")+" "+jsob.getString("task_user_last_name"));
					notificationIntent.putExtra("bidder_image", jsob.getString("task_user_image"));
					}
					else{
						notificationIntent.putExtra("bidder_name", jsob.getString("bid_user_first_name")+" "+jsob.getString("bid_user_last_name"));
						notificationIntent.putExtra("bidder_image", jsob.getString("bid_user_image"));
					}

//					System.out.println("NEW TEST... status == 6,,,,Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerNotificationItemActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				notificationIntent.putExtra("flag", "3");
				notificationIntent.putExtra("status", status);
				notificationIntent.putExtra("bidder_name", jsob.getString("task_user_first_name")+" "+jsob.getString("task_user_last_name"));
				notificationIntent.putExtra("bidder_image", jsob.getString("task_user_image"));
				payment_mode=jsob.getString("payment_mode");
				if(payment_mode.equals("0")){
					notificationIntent.putExtra("status", "7");

					notificationIntent.putExtra("task_type", jsob.getString("task_type"));
					notificationIntent.putExtra("task_id", jsob.getString("task_id"));

					System.out.println("NEW TEST...This status is used after paying the payment..next status == 7");
				}
				else{
					notificationIntent.putExtra("status", "6");	

					System.out.println("NEW TEST...This status is used after paying the payment..next status == 6");
					notificationIntent.putExtra("task_type", jsob.getString("task_type"));
					notificationIntent.putExtra("task_id", jsob.getString("task_id"));
				}

				notificationIntent.putExtra("task_type", jsob.getString("task_type"));
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));


			}




			if(status.equals("3")){

				// this status is used for assigning the task

				msg=jsob.getString("msg");
//				notification = new Notification(icon, msg, when);
				if(task_type.equals("0")){
					notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);

//					System.out.println("NEW TEST... status == 3,,,,Check Task_Type on GCMInetentService class getNotification() 111 Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				else{
					notificationIntent = new Intent(context, GoTaskerNotificationItemActivity.class);
					notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));
					notificationIntent.putExtra("bid_id", jsob.getString("bid_id"));
//					System.out.println("NEW TEST... status == 3,,,,Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerNotificationItemActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
				notificationIntent.putExtra("flag", "1");
				notificationIntent.putExtra("bidder_name", jsob.getString("task_user_first_name")+" "+jsob.getString("task_user_last_name"));
				notificationIntent.putExtra("bidder_image", jsob.getString("task_user_image"));
				notificationIntent.putExtra("status","8");
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));
				notificationIntent.putExtra("desire_amount", jsob.getString("desire_amount"));

				System.out.println("NEW TEST...This status is used for assigning the task...next status == 8");
			}


			if(status.equals("12")){

				// this status is used for private message

				msg=jsob.getString("msg");

//				notification = new Notification(icon, msg, when);
				//call intent of post comment class
				notificationIntent = new Intent(context, GoTaskerPrivateMessageActivity.class);
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));
				notificationIntent.putExtra("status", jsob.getString("private_public_status"));//
				context.startActivity(notificationIntent);


				System.out.println("NEW TEST...this status is used for private message...next status == "+jsob.getString("private_public_status"));
//				System.out.println("NEW TEST... status == 12,,,Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerPrivateMessageActivity === "+task_type+" ........  Task Iddd ===  "+task_id);

			}

			if(status.equals("7")){
				// this status is used for review and rating
				msg=jsob.getString("msg");

//				notification=new Notification(icon, msg, when);
				notificationIntent=new Intent(context, GoTaskerNotificationReviewActivity.class);
				notificationIntent.putExtra("task_id", jsob.optString("task_id"));
				notificationIntent.putExtra("task_type", jsob.optString("task_type"));
				notificationIntent.putExtra("status", jsob.optString("status"));
				notificationIntent.putExtra("bidder_name", jsob.optString("user_first_name")+" "+jsob.optString("user_last_name"));
				notificationIntent.putExtra("bidder_image", jsob.optString("user_image"));
				notificationIntent.putExtra("review", jsob.optString("review"));
				notificationIntent.putExtra("rating", jsob.optString("rating"));
				context.startActivity(notificationIntent);

				System.out.println("NEW TEST...this status is used for review and rating...next status == "+jsob.optString("status"));
//				System.out.println("NEW TEST... status == 7,,,Check Task_Type on GCMInetentService class getNotification() Next GoTaskerNotificationReviewActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
			}

			if(status.equals("14")){
				// this status is used for the paying payment with paypal

				msg=jsob.getString("msg");
//				notification=new Notification(icon, msg, when);
				task_type=jsob.getString("task_type");
				if(task_type.equals("0")){
				notificationIntent=new Intent(context, GoTaskerNotificationTaskActivity.class);

//				System.out.println("NEW TEST... status == 14,,,,Check Task_Type on GCMInetentService class getNotification() 111 Next GoTaskerNotificationTaskActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
			}
				else {
					notificationIntent=new Intent(context, GoTaskerNotificationItemActivity.class);
//					System.out.println("NEW TEST... status == 14,,,,Check Task_Type on GCMInetentService class getNotification() 222 Next GoTaskerNotificationItemActivity === "+task_type+" ........  Task Iddd ===  "+task_id);
				}
					notificationIntent.putExtra("task_id", jsob.getString("task_id"));
					notificationIntent.putExtra("task_type", jsob.getString("task_type"));
					notificationIntent.putExtra("status", jsob.getString("status"));
					notificationIntent.putExtra("bidder_name", jsob.getString("task_user_first_name")+" "+jsob.getString("task_user_last_name"));
					notificationIntent.putExtra("bidder_image", jsob.getString("task_user_image"));
					notificationIntent.putExtra("bid_user_id", jsob.getString("bid_user_id"));
					notificationIntent.putExtra("desire_amount", jsob.getString("desire_amount"));
					notificationIntent.putExtra("bid_id", jsob.getString("bid_id"));

					System.out.println("NEW TEST...this status is used for the paying payment with paypal...next status == "+jsob.getString("status"));
			}

			if(status.equals("6")){
				msg=jsob.getString("msg");
				notification = new Notification(icon, msg, when);
				notificationIntent = new Intent(context, GoTaskerNotificationTaskActivity.class);
				notificationIntent.putExtra("flag", "2");
				notificationIntent.putExtra("status", status);
				notificationIntent.putExtra("bidder_name", jsob.getString("bid_user_first_name")+" "+jsob.getString("bid_user_last_name"));
				notificationIntent.putExtra("bidder_image", jsob.getString("bid_user_image"));
				notificationIntent.putExtra("status", jsob.getString("status"));
				notificationIntent.putExtra("task_type", jsob.getString("task_type"));
				notificationIntent.putExtra("task_id", jsob.getString("task_id"));
			}



		}catch(Exception e){


		} */




		//		//implement new for multiple notification handle on same time
		//        int requestId = (int) System.currentTimeMillis();
		//		
		////        notification.number = notification.number++;
		//        
		//		PendingIntent intent =
		//		        PendingIntent.getActivity(context, requestId, notificationIntent, 0);
		////		notificationIntent.setData(Uri.parse("myString"+requestId));
		//		notification.setLatestEventInfo(context, title,msg, intent);
		////		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//		// Play default notification sound
		//		notification.defaults |= Notification.DEFAULT_SOUND;
		//		// Vibrate if vibrate is enabled
		//		notification.defaults |= Notification.DEFAULT_VIBRATE;
		//		
		//		
		//		notificationManager.notify((int)requestId, notification);  
		//
		////		notificationManager.notify((int)requestId , mBuilder.build());
		//		
		//		System.out.println("REQUEST ID ====  "+requestId+"    ---------notification number-----   "+notification.number);


		//		 requestId = (int) System.currentTimeMillis();
		//		
		//		  PendingIntent resultPendingIntent = PendingIntent.getActivity(context,requestId,
		//	                notificationIntent,0);

		//		sendNotifications(context,notificationIntent,title,msg,R.drawable.gotasker);

		//manageNotification(context,notificationIntent,title,msg,R.drawable.changepass_logo);


	}

	//	public static void manageNotification(Context ctx,PendingIntent notificationIntent,String title,String msg,int icon){
	//      
	////		int notifyID = 0;
	////
	////      
	////         if(testNotificationManager == null){
	////
	////            testNotificationManager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	////        	 
	////         }
	////          
	////         TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
	////
	//////            // Adds the back stack
	//////          stackBuilder.addParentStack(ctx);
	////          
	////          // Adds the Intent to the top of the stack
	////          stackBuilder.addNextIntent(notificationIntent);
	////          // Gets a PendingIntent containing the entire back stack
	////          PendingIntent contentIntent = stackBuilder.getPendingIntent(0,
	////                  PendingIntent.FLAG_CANCEL_CURRENT);
	////       
	////          NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
	////
	////          builder.setContentIntent(contentIntent)
	////                      .setSmallIcon(icon)
	////                      .setTicker("Got a new message")
	////                      .setWhen(System.currentTimeMillis())
	////                      .setAutoCancel(true)
	////                      .setContentTitle(title)
	////                      .setContentText(msg)
	////                      .setNumber(notificationCount++);
	////
	////          testNotificationManager.notify(NOTIFICATION_ID, builder.build());
	////
	////          
	//
	//		
	//		
	//		
	//////          NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
	//////          .setSmallIcon(icon)
	//////          .setContentTitle("Event tracker")
	//////          .setContentText("Events received");
	////          
	////      NotificationCompat.InboxStyle inboxStyle =
	////              new NotificationCompat.InboxStyle();
	////      String[] events = new String[6];
	////      // Sets a title for the Inbox in expanded layout
	////      inboxStyle.setBigContentTitle("Event tracker details:");
	////
	////      // Moves events into the expanded layout
	////      for (int i=0; i < events.length; i++) {
	////
	////          inboxStyle.addLine(events[i]);
	////      }
	////    
	////      // Moves the expanded layout object into the notification object.
	////        builder.setStyle(inboxStyle);
	//         
	//       
	//		
	//		if(notificationManager == null){
	//		
	//
	//			notificationManager =
	//			        (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	//			// Sets an ID for the notification, so it can be updated
	//		
	//			mNotifyBuilder = new NotificationCompat.Builder(ctx);
	//				
	//		}
	//			
	//		mNotifyBuilder.setContentIntent(notificationIntent)
	//        .setSmallIcon(icon)
	////        .setTicker("Got a new message")
	//        .setWhen(System.currentTimeMillis())
	//        .setAutoCancel(true)
	//        .setContentTitle(title)
	//        .setContentText(msg)
	//        .setNumber(notificationCount++);      
	//		
	//		
	//		       
	////		    numMessages = 0;
	//		// Start of a loop that processes data and then notifies the user
	//	
	////		    mNotifyBuilder.setContentText(msg)
	////		        .setNumber(notificationCount++);
	//		    // Because the ID remains unchanged, the existing notification is
	//		    // updated.
	//		notificationManager.notify(
	//				requestId,
	//		            mNotifyBuilder.build());
	//
	//	
	//		System.out.println("Request id == "+requestId+"  Notification counter  ==  "+notificationCount);
	//		
	//     }



	public static void manageNotification(Context ctx,Intent notificationIntent,String title,String msg,int icon){


		notificationManager = (NotificationManager)
				ctx.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(icon,msg,System.currentTimeMillis());
		notification.number = notificationCount++;


		requestId = (int) System.currentTimeMillis();

		PendingIntent intent =
				PendingIntent.getActivity(ctx, requestId, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

		notificationIntent.setData(Uri.parse("myString"+requestId));
		notification.setLatestEventInfo(ctx, title, msg, intent);
		//notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		//notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		//Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		//Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;

		//notification.priority = Notification.PRIORITY_HIGH;

		notificationManager.notify((int)requestId, notification);  





	}


	public static void sendNotifications(Context ctx,Intent notificationIntent,String title,String msg,int icon){

		//		int notifyID = 0;
		//		if(notificationManager == null)
		//           notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
		//
		////        for(Message message:messages){
		//
		//
		////            Intent notificationIntent = new Intent(getApplicationContext(),MainActivity.class);
		//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
		//            // Adds the back stack
		//            stackBuilder.addParentStack(GoTaskerNotificationTaskActivity.class);
		//            // Adds the Intent to the top of the stack
		//            stackBuilder.addNextIntent(notificationIntent);
		//            // Gets a PendingIntent containing the entire back stack
		//            PendingIntent contentIntent = stackBuilder.getPendingIntent(0,
		//                    PendingIntent.FLAG_CANCEL_CURRENT);
		////            Resources res = getApplicationContext().getResources();
		//            Notification.Builder builder = new Notification.Builder(ctx);
		//
		//            builder.setContentIntent(contentIntent)
		//                        .setSmallIcon(R.drawable.ic_launcher)
		////                         .setTicker("Got a new message")
		//                        .setWhen(System.currentTimeMillis())
		//                        .setAutoCancel(true)
		//                        .setContentTitle(title)
		//                        .setContentText(msg)
		//                        .setNumber(notificationCount++);
		//
		//             notificationManager.notify(NOTIFICATION_ID, builder.build());

	}
	//    }

}












