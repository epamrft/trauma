package rft.trauma.android.machine;

/**
 * Gets the list of Markers from a file, emulating a server environment
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class FileMarkerProvider implements IMarkerProvider
{
	private String basePath;
	
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
		// TODO Auto-generated method stub
		return false;
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
