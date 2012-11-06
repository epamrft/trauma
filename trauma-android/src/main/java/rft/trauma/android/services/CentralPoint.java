package rft.trauma.android.services;

import com.google.android.maps.GeoPoint;

import rft.trauma.android.machine.Marker;

/**
 * Defines a central point, usually used to communicate the central point of the screen with the server
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class CentralPoint
{
	private long longitude;
	private long latitude;
	private long radius;
	
	public CentralPoint(long longitude, long latitude, long radius)
	{
		setLatitude(latitude);
		setLongitude(longitude);
		setRadius(radius);
	}
	
	public CentralPoint(GeoPoint central, long radius)
	{
		this(central.getLongitudeE6(), central.getLatitudeE6(), radius);
	}
	
	public boolean inRadius(Marker marker)
	{
		long xCenter = longitude;
		long yCenter = latitude;
		long xPoint = marker.getPoint().getLongitudeE6();
		long yPoint = marker.getPoint().getLatitudeE6();
		if (Math.sqrt((xCenter-xPoint)^2+(yCenter-yPoint)^2) <= radius)
		{
			return true;
		}
		return false;
	}

	public long getLongitude()
	{
		return longitude;
	}

	public void setLongitude(long longitude)
	{
		this.longitude = longitude;
	}

	public long getLatitude()
	{
		return latitude;
	}

	public void setLatitude(long latitude)
	{
		this.latitude = latitude;
	}

	public long getRadius()
	{
		return radius;
	}

	public void setRadius(long radius)
	{
		this.radius = radius;
	}
}
