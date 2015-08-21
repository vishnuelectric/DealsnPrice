package com.dnp.httpcall;



import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract class representing an HTTP server call that uses {@link HttpURLConnection}.
 * This class can be used to configure a HTTP call, by providing a url, adding headers and url query parameters.
 * Finally, {@link #getResponse()} method can be called to run the server request and get response back.
 * There are implementations available for GET, POST and Multipart upload requests.
 * @see HTTPPostCall
 */
public abstract class HTTPCall {
    public static final  int                 CONNECT_TIMEOUT = 30000;
    public static final  int                 READ_TIMEOUT    = 30000;
    private final        Map<String, String> mHeaders        = new HashMap<>();
    private final        Map<String, String> mUrlParams      = new LinkedHashMap<>();
    private final String mUrl;

    /**
     * Create a new instance.
     * @param url HTTP resource URL.
     *            Url query params should be added separately via {@link #putUrlParam(String, String)}
     */
    public HTTPCall(String url) {
        mUrl = url;
    }

    /**
     * Get headers associated with the request.
     * @return request headers.
     */
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    /**
     * Get url query parameters associated with the request.
     * @return url query parameters.
     */
    public Map<String, String> getUrlParams() {
        return mUrlParams;
    }

    /**
     * Get request url.
     * @return request url.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Add a header to HTTP call.
     * @param key header key.
     * @param value header value.
     */
    public void putHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    /**
     * Add a url query param to HTTP call.
     * @param key query key.
     * @param value query value.
     */
    public void putUrlParam(String key, String value) {
        mUrlParams.put(key, value);
    }

    /**
     * Opens a connection to server, using set headers and url query parameters.
     * @return Connection. This can then be configured and used for IO.
     * @throws IOException If there is an error opening connection.
     */
    protected HttpURLConnection connect() throws IOException {
        String url = buildUrlWithParam();
        URL webUrl = new URL(url);
        Log.d("Connecting to: %s", webUrl.toString());
        HttpURLConnection connection = (HttpURLConnection) webUrl.openConnection();

        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);

        for (String key : mHeaders.keySet()) {
            String value = mHeaders.get(key);
            connection.setRequestProperty(key, value);
        }

        return connection;
    }

    /**
     * Append configures url query parameters to call url.
     * @return formatted url with query parameters added.
     */
    private String buildUrlWithParam() {
        StringBuilder sb = new StringBuilder(mUrl);

        boolean paramsAlreadyPresent = mUrl.contains("?");

        if (paramsAlreadyPresent) {
            sb.append("&");
        } else {
            sb.append("?");
        }

        int i = 0;
        for (String key : mUrlParams.keySet()) {
            if (i > 0) {
                sb.append('&');
            }
            sb.append(key);
            sb.append('=');
            sb.append(mUrlParams.get(key));
            i++;
        }
        return sb.toString();

    }


    /**
     * Execute server call and get response.
     * @return Server response.
     * @throws IOException If there is an error executing server call.
     */
    public abstract Response getResponse() throws IOException;
}
