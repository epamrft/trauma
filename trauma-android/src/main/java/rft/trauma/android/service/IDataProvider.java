package rft.trauma.android.service;

import org.json.JSONArray;

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
	public void addMarker(double latitude, double longitude, String desc);	
	/**
	 * Gets all the Markers from the server
	 * @return returns a list of Markers to be displayed on a MapView
	 */
	public JSONArray getAllMarkers();
	/**
	 * Gets a list of Markers based on a centralPoint (usually central point of the screen)
	 * @param centralPoint central point of the screen
	 * @return returns a list of Markers to be displayed on a MapView
	 */
	public JSONArray getMarkers(double centralLongitude, double centralLatitude, double centralRadius);
	/**
	 * Deletes a Marker object
	 * @param marker the Marker object to be deleted
	 * @return 200 on success, 500 on error
	 */
	public void deleteMarker(int markerID);
	/**
	 * Edits a Marker object
	 * @param marker the object to be edited
	 * @param description the new description of the Marker
	 * @return 200 on success, 500 on error
	 */
	public void editMarker(int markerID, String description);
}
