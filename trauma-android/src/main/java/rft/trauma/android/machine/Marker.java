package rft.trauma.android.machine;

import com.google.android.maps.GeoPoint;

/**
 * A Marker object that can be placed on a MapView.
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class Marker extends GeoPoint
{
	/** A description of the Marker */
	private String description;
	
	/**
	 * Creates a new Marker that can be placed on a MapView
	 * @param point The actual place of the Marker
	 * @param description A description of the Marker
	 */
	public Marker(GeoPoint point, String description)
	{
		super(point.getLatitudeE6(), point.getLongitudeE6());
		this.description = description;
	}
	
	/**
	 * Creates a new Marker that can be placed on a MapView
	 * @param longitudeE6 The longitude of the Marker
	 * @param latitudeE6 The longitude of the Marker
	 * @param description A description of the Marker
	 */
	public Marker(int longitudeE6, int latitudeE6, String description)
	{
		this(new GeoPoint(latitudeE6, longitudeE6), description);
	}

	
	/**
	 * @return Returns the description of the Marker
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Sets the description of the Marker
	 * @param description The new value of the description
	 */

	public void setDescription(String description)
	{
		this.description = description;
	}
	
}
