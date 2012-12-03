package rft.trauma.android.bl;

import com.google.android.maps.GeoPoint;


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
	
	public static CentralPoint generate(GeoPoint central, GeoPoint corner)
	{
		double x1 = corner.getLatitudeE6() / 1E6;
    	double y1 = corner.getLongitudeE6() / 1E6;
    	double x2 = central.getLatitudeE6() / 1E6;
    	double y2 = central.getLongitudeE6() / 1E6;
    	double powX = Math.pow((x2 - x1), 2D);
    	double powY = Math.pow((y2 - y1), 2D);
    	Double radius = Math.sqrt(powX + powY);
    	
    	return new CentralPoint(central, radius.intValue());
	}
	
	@Override public boolean equals(Object o)
	{
		if (o == null) return false;
		if (!(o instanceof CentralPoint)) return false;
		if (o == this) return true;
		CentralPoint obj = (CentralPoint)o;
		return (obj.getLatitude() == this.getLatitude() && obj.getLongitude() == this.getLongitude() && obj.getRadius() == this.getRadius());
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
