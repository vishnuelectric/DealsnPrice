package com.dnp.bean;

public class CouponBean {
	public String store_id;
	public String store_name;
	public String category_id;
	public String category_name;
	public String store_image;
	public String store_code;
	public String coupon_home;
	public String coupon_end;
	public String coupon_store_url;
	public String getCoupon_store_url() {
		return coupon_store_url;
	}
	public void setCoupon_store_url(String coupon_store_url) {
		this.coupon_store_url = coupon_store_url;
	}
	public String getCoupon_end() {
		return coupon_end;
	}
	public void setCoupon_end(String coupon_end) {
		this.coupon_end = coupon_end;
	}
	public String getCoupon_home() {
		return coupon_home;
	}
	public void setCoupon_home(String coupon_home) {
		this.coupon_home = coupon_home;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getStore_image() {
		return store_image;
	}
	public void setStore_image(String store_image) {
		this.store_image = store_image;
	}
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
}
