package com.epam.pf.trauma.backend;

public class CentralPoint {

	
	private float longitude;
	private float latitude;
	private float radius;
	public CentralPoint( float longitude, float latitude, float radius) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
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

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}


	

	

}
