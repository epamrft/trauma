package rft.trauma.android.machine;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Marker extends OverlayItem
{	
	private int id;
	
	public Marker(int id, GeoPoint point, String title, String snippet)
	{
		super(point, title, snippet);
		this.setId(id);
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
