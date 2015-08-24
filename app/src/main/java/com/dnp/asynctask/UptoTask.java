package com.dnp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.dealnprice.activity.UptoService;
import com.dnp.data.HttpRequest;
import com.dnp.data.WebService;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

/**
 * Created by vishnuprasad on 24/08/15.
 */
public class UptoTask extends AsyncTask<String,Void, String > {
    Context cxt;
    MultipartEntity multipart;
    UptoService.UptoListener uptoListener;
    String appname;
    public UptoTask(Context cxt,MultipartEntity multipartEntity,UptoService.UptoListener uptoListener,String appname)
    {
        this.cxt=cxt;
        this.multipart = multipartEntity;
        this.uptoListener = uptoListener;
        this.appname= appname;
    }
    @Override
    protected String doInBackground(String... strings) {

        try{
            String response= HttpRequest.post(WebService.UPTO_TASK, multipart);
            return response;
        }
        catch(Exception e){
e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            try{
                JSONObject jobj=new JSONObject(s);
                String status=jobj.getString("status");
                String postback = jobj.getString("postback");
                if(status.equals("1") && postback.equalsIgnoreCase("1")){
                    uptoListener.onSuccess("success",appname);
                }
                else{
                    uptoListener.onSuccess("error",appname);
                }



            }
            catch(Exception e){

            }
        }
        else{
            uptoListener.onError("fail");
        }
    }
}
