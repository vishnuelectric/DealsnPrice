package com.dnp.recharge;


import android.os.AsyncTask;

import com.dnp.httpcall.HTTPPostCall;
import com.dnp.httpcall.Response;
import com.dnp.recharge.model.ReqRecharge;
import com.dnp.recharge.model.ResGetProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetOperatorListAsyncTask extends AsyncTask<String, String, Response> {
    private final String URL = "";
    private RequestListener mRequestListener;
    private ReqRecharge mInput;

    public GetOperatorListAsyncTask(RequestListener requestListener, ReqRecharge input) {
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
        if (response == null && mRequestListener != null) {
            mRequestListener.onFailure("Some Error occurred.");
        }
        if (response != null) {
            if (response.isStatusInternalError() && mRequestListener != null) {
                mRequestListener.onFailure("Internal Server Error.");
            }

            ResGetProvider providor = new ResGetProvider();
            try {
                JSONObject responseJson = new JSONObject(new String(response.getBody()));

            } catch (JSONException e) {
                e.printStackTrace();
                System.out.print(e.getCause());
            }
            if (mRequestListener != null) {
                mRequestListener.onSuccess(response);
            } else {
                mRequestListener.onFailure("");
            }

        }
    }
}
