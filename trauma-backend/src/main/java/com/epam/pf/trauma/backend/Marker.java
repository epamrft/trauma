package com.epam.pf.trauma.backend;

import java.io.Serializable;

public class Marker implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private float lng;
	private float lan;
	private String desc;
	public float getLongitude() {
		return lng;
	}

	public void setLongitude(float longitude) {
		this.lng = longitude;
	}

	public float getLatitude() {
		return lan;
	}

	public void setLatitude(float latitude) {
		this.lan = latitude;
	}

	public int getId() {
		return id;
	}



	public Marker() {
		super();
	}

	

	public Marker(int id, float longitude, float latitude, String desc) {
		super();
		this.id = id;
		this.lng = longitude;
		this.lan = latitude;
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
