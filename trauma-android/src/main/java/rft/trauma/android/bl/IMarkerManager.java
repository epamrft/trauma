package rft.trauma.android.bl;

import java.util.List;

/**
 * An interface that manages the markers outside the service package
 * @author Nagy Gergo
 * @version 1.0.0
 */
public interface IMarkerManager
{
	/**
	 * Sends a new Marker to the IDataProvider service
	 * @param marker the Marker that is about to be sent
	 */
	public void addMarker(Marker marker);
	/**
	 * Sends a delete request to the IDataProvider service
	 * @param marker
	 */
	public void deleteMarker(Marker marker);
	/**
	 * Gets Markers around inside a circle that is represented by a CentralPoint object
	 * @param centralPoint the CentralPoint that defines the location of the recieved Markers
	 * @return Returns all the Markers inside the CentralPoint circle
	 */
	public List<Marker> getMarkers(CentralPoint centralPoint);
	/**
	 * Gets all the Markers from the server
	 * @return Returns all the Markers from the server
	 */
	public List<Marker> getAllMarkers();
	/**
	 * Sends an edit request to the IDataProvider service
	 * @param marker the marker that is about to be edited
	 * @param description the new description of the editable marker
	 */
	public void editMarker(Marker marker, String description);
}
