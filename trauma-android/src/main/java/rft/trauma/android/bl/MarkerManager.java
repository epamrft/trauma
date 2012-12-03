package rft.trauma.android.bl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import rft.trauma.android.service.IDataProvider;
import rft.trauma.android.service.ServerException;
import rft.trauma.android.service.TraumaDataProvider;

/**
 * The MarkerManager class that sends request to the IDataProvider service
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class MarkerManager implements IMarkerManager
{
	private IDataProvider dataProvider;
	
	/**
	 * Creates a new MarkerManager object. Uses TraumaDataProvider by defualt
	 */
	public MarkerManager()
	{
		dataProvider = new TraumaDataProvider();
	}
	
	/**
	 * Creates a new MarkerManager object
	 * @param dataProvider the DataProvider that recieces the requests and forewards them to the server
	 */
	public MarkerManager(IDataProvider dataProvider)
	{
		this.dataProvider = dataProvider;
	}
	
	/**
	 * sends a new marker to the servers
	 * @param marker the marker that is about to be sent to the server
	 * @throws ServerException thrown if something goes wrong
	 */
	public void addMarker(Marker marker) throws ServerException
	{
		dataProvider.addMarker(marker.getPoint().getLatitudeE6() / 1E6, marker.getPoint().getLongitudeE6() / 1E6, marker.getMessage());
	}
	/**
	 * sends a new marker deletion request to the server
	 * @param marker the marker that is about to be deleted
	 * @throws ServerException thrown if something goes wrong
	 */
	public void deleteMarker(Marker marker) throws ServerException
	{
		dataProvider.deleteMarker(marker.getId());
	}
	/**
	 * Gets markers under a circle represented by a CentralPoint object
	 * @param centralPoint the centralPoint that points to the location of the recieved markers
	 * @return returns a list of markers under a centralpoint circle
	 * @throws ServerException thrown if something goes wrong
	 */
	public List<Marker> getMarkers(CentralPoint centralPoint) throws ServerException
	{
		return jSONArrayIntoMarkers(dataProvider.getMarkers(centralPoint.getLongitude() / 1E6, centralPoint.getLatitude() / 1E6, centralPoint.getRadius() / 1E6));
	}
	/**
	 * Gets all the markers from the server
	 * @return all the markers from the server
	 * @throws ServerException thrown if something goes wrong
	 */
	public List<Marker> getAllMarkers() throws ServerException
	{
		return jSONArrayIntoMarkers(dataProvider.getAllMarkers());
	}
	/**
	 * Sends an edit request to the server
	 * @param marker the marker that is about to be edited
	 * @param description the new description of the marker
	 * @throws ServerException thrown if something goes wrong
	 */
	public void editMarker(Marker marker, String description) throws ServerException
	{
		dataProvider.editMarker(marker.getId(), description);
	}
	
	/**
	 * Converts a JSON array into a list of markers
	 * @param array the JSON array that contains the Markers
	 * @return a list of markers parsed from the JSON array
	 * @throws ServerException thrown if something goes wrong
	 */
	private static List<Marker> jSONArrayIntoMarkers(JSONArray array) throws ServerException
	{
		List<Marker> list = new ArrayList<Marker>();
		if (array.isNull(0))
		{
			return list;
		}
		try
		{
			for (int i=0; i < array.length(); i++)
			{
				JSONObject obj = array.getJSONObject(i);
				int id = obj.getInt("id");
				double latitude = obj.getDouble("latitude");
				double longitude = obj.getDouble("longitude");
			
				String desc = obj.getString("desc");
				Marker m = new Marker(id, new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6)), "Marker", desc);
				list.add(m);
			}
			return list;
		}
		catch (JSONException ex)
		{
			throw new ServerException(ex);
		}
	}
}
