package com.epam.pf.trauma.backend.service.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;

@Service
public class MarkerMemoryDAO implements MarkerDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarkerMemoryDAO.class);
	
	public ConcurrentMap<String, Marker> memoryDatabase = new ConcurrentHashMap<String, Marker>();

	@Override
	public Marker addMarker(Marker marker) {
		marker.setId(memoryDatabase.size() + 1);
		memoryDatabase.put(Integer.toString(marker.getId()), marker);
		LOGGER.debug("Added marker: {}", marker);
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
		if (memoryDatabase.containsKey(Integer.toString(id))) {
			memoryDatabase.get(Integer.toString(id)).setDesc(desc);
		}
		
		return memoryDatabase.get(Integer.toString(id));
	}

}
