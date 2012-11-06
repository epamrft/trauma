package rft.trauma.android.machine;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Marker extends OverlayItem
{	
	public Marker(GeoPoint point, String title, String snippet)
	{
		super(point, title, snippet);
	}
}
