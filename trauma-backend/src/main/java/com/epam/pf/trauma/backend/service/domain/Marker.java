package com.epam.pf.trauma.backend.service.domain;

import java.io.Serializable;


public class Marker implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private float longitude;

	private float latitude;

	private String desc;

	public Marker() {
		super();
	}

	public Marker(float longitude, float latitude, String desc) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.desc = desc;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Marker [id=" + id + ", longitude=" + longitude + ", latitude=" + latitude + ", desc=" + desc + "]";
	}

}
