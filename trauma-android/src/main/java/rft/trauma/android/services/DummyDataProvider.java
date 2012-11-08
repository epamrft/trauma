package rft.trauma.android.services;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

import rft.trauma.android.machine.Marker;

/**
 * A dummy data provider for testing and development
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class DummyDataProvider implements IDataProvider
{
	List<Marker> dummyList;
	
	public DummyDataProvider()
	{
		dummyList = new ArrayList<Marker>();
		dummyList.add(new Marker(1, new GeoPoint(19240000, -99120000), "Marker", "Mexico City dummy traffic jam"));
		dummyList.add(new Marker(2, new GeoPoint(35410000, 139460000), "Marker", "Tokyo dummy roadblock"));
	}

	@Override
	public int addMarker(Marker marker)
	{
		int msg = this.msgGenerator();
		if (msg != 5) dummyList.add(marker);
		return msg;
	}

	@Override
	public List<Marker> getMarkers(CentralPoint centralPoint)
	{
		return dummyList;
	}

	@Override
	public int deleteMarker(Marker marker)
	{
		int msg = this.msgGenerator();
		if (msg != 5)
		{
			dummyList.remove(marker);
		}
		return msg;
	}

	@Override
	public int editMarker(Marker marker, String description)
	{
		int msg = this.msgGenerator();
		if (dummyList.contains(marker))
		{
			dummyList.set(dummyList.indexOf(marker), new Marker(marker.getId(), marker.getPoint(), marker.getTitle(), description));
		}
		return msg;
	}

	private int msgGenerator()
	{
		Random rand = new Random();
		if (rand.nextInt(10) == 5) return 500;
		return 200;
	}
}
