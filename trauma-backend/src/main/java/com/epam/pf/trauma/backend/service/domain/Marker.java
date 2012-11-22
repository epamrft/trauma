package com.epam.pf.trauma.backend.service.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "markers")
public class Marker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
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
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Marker [id=" + this.id + ", longitude=" + this.longitude + ", latitude=" + this.latitude + ", desc=" + this.desc + "]";
	}

}
