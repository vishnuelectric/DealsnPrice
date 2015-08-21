package com.dnp.recharge;


public interface RequestListener<O> {
    void onSuccess(O output);
    void onFailure(String errorMsg);
}
