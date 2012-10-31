package com.epam.pf.trauma.backend;

import java.io.Serializable;

public class Marker implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private float longitude;
	private float latitude;
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public int getId() {
		return id;
	}

	private String desc;

	public Marker() {
		super();
	}

	

	public Marker(int id, float longitude, float latitude, String desc) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
