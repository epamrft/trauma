package rft.trauma.android.machine;

import java.util.List;

/**
 * This interface provides communication between the back-end server and the Android app
 * @author Nagy Gergo
 * @version 1.0.0
 */
public interface IMarkerProvider
{
	/**
	 * Places a Marker on the map
	 * @param marker The Marker that is about to be placed on the map
	 * @return returns 0 on success, 1 otherwise
	 */
	public boolean placeMarker(Marker marker);
	/**
	 * Gets an array of Markers in a circle based on a center point and a radius.
	 * @param longitude The longitude of the center
	 * @param latitude The latitude of the center
	 * @param radius The radius
	 * @return Returns a list of Markers that can be found in the circle
	 */
	public List<Marker> getMarker(int longitude, int latitude, int radius);
}
