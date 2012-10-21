package rft.trauma.android.machine;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * Gets the list of Markers from a file, emulating a server environment
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class FileMarkerProvider implements IMarkerProvider
{
	/**The path of the database file*/
	private String basePath;
	
	/**
	 * Makes a new FileMarkerProvider object
	 * @param basePath Path of the database file
	 */
	public FileMarkerProvider(String basePath)
	{
		this.basePath = basePath;
	}
	
	/**
	 * Places a Marker into the file
	 * @param marker The Marker that is about to be placed
	 * @return 0 on success, 1 otherwise
	 */
	@Override public boolean placeMarker(Marker marker)
	{
		JSONObject obj = new JSONObject();
		obj.put("longitude", marker.getLongitudeE6());
		obj.put("latitude", marker.getLatitudeE6());
		obj.put("description", marker.getDescription());
		
		try
		{
			FileWriter file = new FileWriter(basePath);
			file.write(obj.toJSONString());
			file.flush();
			file.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Gets an array of Markers from file in a circle based on a center point and a radius.
	 * @param longitude The longitude of the center
	 * @param latitude The latitude of the center
	 * @param radius The radius
	 * @return Returns a list of Markers that can be found in the circle
	 */
	@Override public Marker[] getMarker(int longitude, int latitude, int radius)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
