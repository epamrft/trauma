package rft.trauma.android.machine;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	@Override
	public String toString()
	{
		try
		{
			JSONObject marker = new JSONObject();
			marker.put("ID", this.id);
			marker.put("latitude", this.getPoint().getLatitudeE6());
			marker.put("longitude", this.getPoint().getLongitudeE6());
			marker.put("desc", this.getMessage());
			return marker.toString();
		}
		catch (JSONException ex)
		{
			return "id: " + this.id + ", latitude: " + this.getPoint().getLatitudeE6() + "longitude: " + this.getPoint().getLongitudeE6() + "desc: " + this.getMessage();
		}
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o != null && o.getClass() == Marker.class)
		{
			Marker m = (Marker)o;
			if (m.id == this.id && m.message == this.message && m.getPoint().getLatitudeE6() == this.getPoint().getLatitudeE6() && m.getPoint().getLongitudeE6() == this.getPoint().getLongitudeE6())
				return true;
		}
		return false;
	}
}
