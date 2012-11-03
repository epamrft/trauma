package com.epam.pf.trauma.backend;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MarkerMemoryDAO implements MarkerDAO {
	ConcurrentMap<String, Marker> memoryDatabase = new ConcurrentHashMap<String, Marker>();

	@Override
	public Marker addMarker(Marker marker) {
		
		
		memoryDatabase.put(Integer.toString(marker.getId()), marker);
		return marker;
	}

	@Override
	public Collection<Marker> getMarkers(CentralPoint centralPoint) {
		Collection<Marker> markers = new LinkedList<Marker>();
		for (Marker marker : memoryDatabase.values()) {
			if (centralPoint.inRadius(marker)) {
				markers.add(marker);

			}

		}

		return markers;
	}

	@Override
	public void deleteMarker(int id) {
		
			memoryDatabase.remove(Integer.toString(id));
			
			
		
	}

	@Override
	public Marker editMarker(int id, String desc) {
		if(memoryDatabase.containsKey(Integer.toString(id))){
			memoryDatabase.get(Integer.toString(id)).setDesc(desc);
			
			
		}
		return memoryDatabase.get(Integer.toString(id));
	}

}
