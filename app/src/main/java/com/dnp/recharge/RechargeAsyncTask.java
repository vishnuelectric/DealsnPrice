package com.dnp.recharge;


import android.os.AsyncTask;

import com.dnp.httpcall.HTTPPostCall;
import com.dnp.httpcall.Response;
import com.dnp.recharge.model.ReqRecharge;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RechargeAsyncTask extends AsyncTask<String, String, Response> {
    private final String URL = "";
    private RequestListener mRequestListener;
    private ReqRecharge mInput;

    public RechargeAsyncTask(RequestListener requestListener, ReqRecharge input) {
        mRequestListener = requestListener;
        mInput = input;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Response doInBackground(String... params) {
        Response response = null;
        HTTPPostCall postCall = new HTTPPostCall(URL);
        postCall.putHeader("Accept", "application/json");
        postCall.putHeader("Content-Type", "application/json");
        postCall.putUrlParam("", "");
        try {
            response = postCall.getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (response != null) {
            if (response.isStatusInternalError()) {
                mRequestListener.onFailure("Internal Server Error.");
            }

            // extract response data
            try {
                JSONObject responseJson = new JSONObject(new String(response.getBody()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(mRequestListener != null){
                mRequestListener.onSuccess(response);
            }else{
                mRequestListener.onFailure("");
            }

        }
    }
}
