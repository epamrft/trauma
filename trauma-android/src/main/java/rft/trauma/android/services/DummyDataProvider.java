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
	
	public DummyDataProvider()
	{
	}

	@Override
	public int addMarker(Marker marker)
	{
		return this.msgGenerator();
	}

	@Override
	public List<Marker> getMarkers(CentralPoint centralPoint)
	{
		List<Marker> ret = new ArrayList<Marker>();
		ret.add(new Marker(new GeoPoint(19240000, -99120000), "Marker", "x: 19240000\ny:-99120000\nMexico City dummy traffic jam"));
		ret.add(new Marker(new GeoPoint(35410000, 139460000), "Marker", "x: 35410000\ny:139460000\nTokyo dummy roadblock"));
		return ret;
	}

	@Override
	public int deleteMarker(Marker marker)
	{
		return this.msgGenerator();
	}

	@Override
	public int editMarker(Marker marker, String description)
	{
		return this.msgGenerator();
	}

	private int msgGenerator()
	{
		Random rand = new Random();
		if (rand.nextInt(10) == 5) return 500;
		return 200;
	}
}
