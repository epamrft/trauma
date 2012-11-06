package rft.trauma.android.machine;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Marker extends OverlayItem
{	
	private int id;
	
	public Marker(GeoPoint point, String title, String snippet)
	{
		super(point, title, snippet);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	
}
