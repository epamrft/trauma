package rft.trauma.android.bl;

import com.google.android.maps.GeoPoint;


/**
 * Defines a central point, usually used to communicate the central point of the screen with the server.
 * This CentralPoint is a circle that consists of a center longitude and latitude and a radius.
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class CentralPoint
{
	private long longitude;
	private long latitude;
	private long radius;
	
	/**
	 * Creates a new instance of the CentralPoint class
	 * @param longitude the longitude of the GeoPoint where the center of the map is
	 * @param latitude the latitude of the GeoPoint where the center of the map is
	 * @param radius the radius of the circle that is represented by the CentralPoint object
	 */
	public CentralPoint(long longitude, long latitude, long radius)
	{
		setLatitude(latitude);
		setLongitude(longitude);
		setRadius(radius);
	}
	
	/**
	 * Creates a new instance of the CentralPoint class
	 * @param central the center location of the CentralPoint
	 * @param radius the radius of the circle that is represented by the CentralPoint object
	 */
	public CentralPoint(GeoPoint central, long radius)
	{
		this(central.getLongitudeE6(), central.getLatitudeE6(), radius);
	}
	
	/**
	 * Checks if the marker is inside the radius of the CentralPoint
	 * @param marker the Marker that is checked
	 * @return true if the Marker is inside the radius of the CentralPoint
	 */
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
	
	/**
	 * Generates a new CentralPoint object from a center point and a corner of the screen
	 * @param central the central point of the map
	 * @param corner the corner of the map
	 * @return a new CentralPoint object
	 */
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

	/**
	 * gets the longitude of the CentralPoint
	 */
	public long getLongitude()
	{
		return longitude;
	}

	/**
	 * sets the longitude of the CentralPoint
	 */
	public void setLongitude(long longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * gets the latitude of the CentralPoint
	 */
	public long getLatitude()
	{
		return latitude;
	}

	/**
	 * sets the latitude of the CentralPoint
	 */
	public void setLatitude(long latitude)
	{
		this.latitude = latitude;
	}

	/**
	 * Gets the radius of the CentralPoint
	 */
	public long getRadius()
	{
		return radius;
	}

	/**
	 * sets the radius of the CentralPoint
	 */
	public void setRadius(long radius)
	{
		this.radius = radius;
	}
}
