package com.dnp.httpcall;


import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;

public class HTTPPostCall extends HTTPCall {
    private byte[] mBody;

    /**
     * Create a new instance.
     *
     * @param url HTTP resource URL.
     *            Url query params should be added separately via {@link #putUrlParam(String, String)}
     */
    public HTTPPostCall(String url) {
        super(url);
    }

    /**
     * Set HTTP post body to send to server.
     *
     * @param body HTTP post body.
     */
    public void setBody(byte[] body) {
        mBody = body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getResponse() throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = connect();
            connection.setRequestMethod("POST");

            byte[] data = mBody == null ? new byte[0] : mBody;
            connection.setRequestProperty("Content-Length", Integer.toString(data.length));

            connection.setDoInput(true);
            connection.setDoOutput(true);


            connection.getOutputStream().write(data, 0, data.length);
            connection.getOutputStream().flush();
            connection.getOutputStream().close();

            Response response = new Response(connection.getResponseCode());
            if (response.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
                response.setBody(StreamUtils.getBytes(connection.getErrorStream()));
            } else {
                response.setBody(StreamUtils.getBytes(connection.getInputStream()));
            }
            return response;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
