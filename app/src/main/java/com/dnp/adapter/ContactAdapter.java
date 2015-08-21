package com.dnp.adapter;


public class ContactAdapter {
	String id;
	String name;
	int phone;
	public ContactAdapter(){
		
	}
	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	public int getPhone(){
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	public int compareTo(Object obj) {
		ContactAdapter ca = (ContactAdapter)obj;
		return this.name.compareTo(ca.getName());
		}

}
