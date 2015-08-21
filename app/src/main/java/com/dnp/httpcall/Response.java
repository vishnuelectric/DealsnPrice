package com.dnp.httpcall;

import org.apache.http.HttpStatus;

/**
 * Class encapsulating results of a {@link HTTPCall}.
 */
public class Response {
    private final int    mStatusCode;
    private       byte[] mBody;

    /**
     * Create a new instance.
     * @param statusCode HTTP response status code.
     */
    public Response(int statusCode) {
        mStatusCode = statusCode;
    }


    /**
     * Get HTTP response status code.
     * @return HTTP response status code.
     */
    public int getStatusCode() {
        return mStatusCode;
    }

    /**
     * Check if HTTP response status is OK. (200  to 299)
     * @return true if response status is OK, false otherwise.
     */
    public boolean isStatusOK() {
        return mStatusCode >= HttpStatus.SC_OK && mStatusCode <= 299;
    }

    /**
     * Get HTTP response body data.
     * @return response body.
     */
    public byte[] getBody() {
        return mBody;
    }

    /**
     * Set HTTP response body data.
     * @param body response body.
     */
    protected void setBody(byte[] body) {
        this.mBody = body;
    }

    /**
     * Check if HTTP response status is UNAUTHORIZED. (401)
     * @return true if response status is UNAUTHORIZED, false otherwise.
     */
    public boolean isStatusUnauthorized() {
        return mStatusCode == HttpStatus.SC_UNAUTHORIZED;
    }

    /**
     * Check if HTTP response status is INTERNAL SERVER ERROR. (500)
     * @return true if response status is INTERNAL SERVER ERROR, false otherwise.
     */
    public boolean isStatusInternalError() {
        return mStatusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

    /**
     * Check if response body has non null, non empty data.
     * @return true if response body has non null, non empty data. false otherwise.
     */
    public boolean hasBodyContent() {
        return mBody != null && mBody.length > 0;
    }

    /**
     * Get string representation of response status code and body content.
     * @return string representation of response object.
     */
    @Override
    public String toString() {
        return mStatusCode + " : " + (mBody != null ? new String(mBody) : "null");
    }
}
