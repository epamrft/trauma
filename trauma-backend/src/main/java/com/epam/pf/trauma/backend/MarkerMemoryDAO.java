package com.epam.pf.trauma.backend;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MarkerMemoryDAO implements MarkerDAO {
	ConcurrentMap<String, Marker> memoryDatabase = new ConcurrentHashMap<String,Marker>();
	@Override
	public int addMarker(Marker marker) {
		memoryDatabase.put(Integer.toString(marker.getId()), marker);
		return marker.getId();
	}

	@Override
	public List<Marker> getMarkers(CentralPoint centralPoint) {
		
		return null;
	}

	@Override
	public void deleteMarker(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Marker editMarker(int id, String desc) {
		// TODO Auto-generated method stub
		return null;
	}

}
