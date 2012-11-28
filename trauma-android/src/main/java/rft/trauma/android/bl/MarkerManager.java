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

public class MarkerManager
{
	private static IDataProvider dataProvider = new TraumaDataProvider();
	
	/**
	 * @throws ServerException
	 */
	public static void addMarker(Marker marker) throws ServerException
	{
		dataProvider.addMarker(marker.getPoint().getLatitudeE6() / 1E6, marker.getPoint().getLongitudeE6() / 1E6, marker.getMessage());
	}
	/**
	 * @throws ServerException
	 */
	public static void deleteMarker(Marker marker) throws ServerException
	{
		dataProvider.deleteMarker(marker.getId());
	}
	/**
	 * @throws ServerException
	 */
	public static List<Marker> getMarkers(CentralPoint centralPoint) throws ServerException
	{
		return jSONArrayIntoMarkers(dataProvider.getMarkers(centralPoint.getLongitude() / 1E6, centralPoint.getLatitude() / 1E6, centralPoint.getRadius() / 1E6));
	}
	/**
	 * @throws ServerException
	 */
	public static List<Marker> getAllMarkers() throws ServerException
	{
		return jSONArrayIntoMarkers(dataProvider.getAllMarkers());
	}
	/**
	 * @throws ServerException
	 */
	public static void editMarker(Marker marker, String description) throws ServerException
	{
		dataProvider.editMarker(marker.getId(), description);
	}
	
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