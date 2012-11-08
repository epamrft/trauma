package rft.trauma.android.machine;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Marker extends OverlayItem
{	
	private int id;
	private String message;
	
	public Marker(int id, GeoPoint point, String title, String message)
	{
		super(point, title, message);
		this.setId(id);
		this.setMessage(message);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
}
