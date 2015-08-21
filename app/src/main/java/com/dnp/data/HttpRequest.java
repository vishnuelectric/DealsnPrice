package com.dnp.data;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

public class HttpRequest {
        static long  init  =   0;
        static long end        =   0;
        static long timetaken  =   0;
	
	  static InputStream is = null;
	  static String response = "";
	 
	
	  public static String post(String url) throws UnknownHostException {
	        try {
                init    =   System.currentTimeMillis();
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            /*httpPost.setHeader("Content-type", "application/json");*/
	            httpPost.setHeader("Accept", "application/json");
	       //     httpPost.setHeader("Content-type", "text/plain");
			//	httpPost.setEntity( new StringEntity(strParam));
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	            int statusCode = httpResponse.getStatusLine().getStatusCode();
	            Log.e("Status Code <><><<><><>", "cod = " + statusCode);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	            
	        }
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();
	            response = sb.toString();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	        return response;
	    }
	  
	  public static String post(String url,MultipartEntity multipart) {
	        try {
	            DefaultHttpClient httpClient = new DefaultHttpClient();
				System.out.println("httprequest url "+ url);
	            HttpPost httpPost = new HttpPost(url);
	            /*httpPost.setHeader("Content-type", "application/json");*/
	            httpPost.setHeader("Accept", "application/json");
				httpPost.setEntity(multipart);
				
				/*HttpParams httpParameters = httpPost.getParams();
		        // Set the timeout in milliseconds until a connection is
		        // established.
		        int timeoutConnection = 100000;
		        HttpConnectionParams.setConnectionTimeout(httpParameters,
		                timeoutConnection);
		        // Set the default socket timeout (SO_TIMEOUT)
		        // in milliseconds which is the timeout for waiting for data.
		        int timeoutSocket = 100000;
		        HttpConnectionParams
		                .setSoTimeout(httpParameters, timeoutSocket);*/
				
				
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	            int statusCode = httpResponse.getStatusLine().getStatusCode();
	            Log.e("Status Code <><><<><><>", "cod = " + statusCode);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();
	            response = sb.toString();
	            Log.v("REsponse Value",response);
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	            response = null;
	        }

            end =   System.currentTimeMillis();
            timetaken   =   end - init;

            Log.e(" "," TIME TAKEN "+timetaken);

	        return response;
	    }
}