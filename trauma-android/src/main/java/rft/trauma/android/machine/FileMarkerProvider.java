package rft.trauma.android.machine;

import java.io.FileWriter;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	 * @deprecated
	 */
	public FileMarkerProvider(String basePath)
	{
		this.basePath = basePath;
	}
	
	/**
	 * Makes a new FileMarkerProvider object
	 */
	public FileMarkerProvider()
	{
		this.basePath = Globals.basePath;
	}
	
	/**
	 * Places a Marker into the file
	 * @param marker The Marker that is about to be placed
	 * @return 0 on success, 1 otherwise
	 */
	@Override public boolean placeMarker(Marker marker)
	{
		JSONParser parser = new JSONParser();
		
		try
		{
			JSONObject obj = (JSONObject)parser.parse(new FileReader(basePath));
			JSONArray array = (JSONArray)obj.get("list");
			
			obj = new JSONObject();
			array.add(marker);
			obj.put("list", array);
			
			FileWriter file = new FileWriter(basePath);
			file.write(obj.toJSONString());
			file.write("\n");
			file.flush();
			file.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(ParseException ex)
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
	 * @return Returns a list of Markers that can be found in the circle, null on error, empty list if no objects could be found
	 */
	@Override public List<Marker> getMarker(int longitude, int latitude, int radius)
	{
		List<Marker> retval = new LinkedList<Marker>();
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(new FileReader(basePath));
			JSONArray array = (JSONArray)obj.get("list");
			Iterator<Marker> i = array.iterator();
			while(i.hasNext())
			{
				retval.add(i.next());
			}
			return retval;
		}
		catch(FileNotFoundException ex)
		{
			return null;
		}
		catch(IOException ex)
		{
			return null;
		}
		catch(ParseException ex)
		{
			return null;
		}
	}
}
