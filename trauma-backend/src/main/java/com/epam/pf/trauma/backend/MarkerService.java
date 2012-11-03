package com.epam.pf.trauma.backend;

import java.util.Collection;

public class MarkerService {

	public MarkerMemoryDAO markerMemoryDAO;
	
	public Marker addMarker(float latitude, float langitude, String description) {
		int i=0;
		while(markerMemoryDAO.memoryDatabase.containsKey(Integer.toString(markerMemoryDAO.memoryDatabase.size()+i))) i++;
		Marker marker = new Marker(markerMemoryDAO.memoryDatabase.size()+i,langitude,latitude,description);
		return (markerMemoryDAO.addMarker(marker));
		
		
		
	}
	public Collection<Marker> getMarkers(CentralPoint centralPoint){
		return markerMemoryDAO.getMarkers(centralPoint);
		
	}
	public void deleteMarker(int id){ 
		markerMemoryDAO.deleteMarker(id);
		
	}
	public Marker editMarker(int id, String desc){
		return markerMemoryDAO.editMarker(id, desc);
		
		
	}
	
}
