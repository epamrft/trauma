package rft.trauma.android.services;

import java.util.List;
import rft.trauma.android.machine.Marker;

/**
 * Provides connection between the server and the app
 * @version 1.0.0
 * @author Nagy Gergo
 */
public interface IDataProvider
{
	/**
	 * Commits a Marker
	 * @param marker The marker to be added
	 * @return 200 on success, 500 on error
	 */
	public int addMarker(Marker marker);	
	/**
	 * Gets all the Markers from the server
	 * @return returns a list of Markers to be displayed on a MapView
	 */
	public List<Marker> getAllMarkers();
	/**
	 * Gets a list of Markers based on a centralPoint (usually central point of the screen)
	 * @param centralPoint central point of the screen
	 * @return returns a list of Markers to be displayed on a MapView
	 */
	public List<Marker> getMarkers(CentralPoint centralPoint);
	/**
	 * Deletes a Marker object
	 * @param marker the Marker object to be deleted
	 * @return 200 on success, 500 on error
	 */
	public int deleteMarker(Marker marker);
	/**
	 * Edits a Marker object
	 * @param marker the object to be edited
	 * @param description the new description of the Marker
	 * @return 200 on success, 500 on error
	 */
	public int editMarker(Marker marker, String description);
}
