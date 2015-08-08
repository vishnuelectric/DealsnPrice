package com.dnp.data;




import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dealnprice.activity.R;

public class UtilMethod {

	public static ProgressDialog showLoading(ProgressDialog progress,
			Context context) {
		try {
			if (progress == null) {
				progress = new ProgressDialog(context);
			} else {
			}
			progress.setTitle("Loading");
			progress.setMessage("Please Wait...");
			progress.setCancelable(false);

			progress.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return progress;
	}

	public static void showServerError(Context ctx) {
		try {
			if (ctx != null) {
				String message = ctx.getResources().getString(
						R.string.server_error_message);
				Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showNetworkError(Context ctx) {
		try {
			if (ctx != null) {
				String message = ctx.getResources().getString(
						R.string.network_error_message);
				/* Toast.makeText(ctx, message, Toast.LENGTH_LONG).show(); */
				final AlertDialog adialog = new AlertDialog.Builder(ctx)
						.create();
				adialog.setTitle("Message");
				adialog.setMessage("Internet Error!");
				adialog.setButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
					}
				});
				adialog.show();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void hideLoading(ProgressDialog progress) {
		try {
			if (progress != null) {
				if (progress.isShowing()) {
					progress.dismiss();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDownloading(ProgressBar progress) {
		if (progress != null) {
			progress.setVisibility(View.VISIBLE);
		}
	}

	public static void hideDownloading(ProgressBar progress) {
		if (progress != null) {
			progress.setVisibility(View.GONE);
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo localNetworkInfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
	}

	public static boolean isStringNullOrBlank(String str) {
		if (str == null) {
			return true;
		} else if (str.equals("null") || str.equals("")) {
			return true;
		}
		return false;
	}

	public static void showToast(String message, Context ctx) {
		try {
			if (ctx != null)
				Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(target)
						.matches();
	}
	
	public final static void showAlert(String msg,Context cxt){
		final AlertDialog adialog=new AlertDialog.Builder(cxt).create();
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
