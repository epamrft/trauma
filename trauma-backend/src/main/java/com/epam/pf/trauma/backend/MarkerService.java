package com.epam.pf.trauma.backend;

public class MarkerService {

	public int id = 0 ;
	public MarkerMemoryDAO markerMemoryDAO;
	
	public int addMarker(float latitude, float langitude, String description) {
		id++;
		Marker marker = new Marker(id,langitude,latitude,description);
		return (markerMemoryDAO.addMarker(marker));
		
		
		
	}
	public void getMarkers(CentralPoint centralPoint){
		
		
	}
	public void deleteMarker(){ 
		
	}
	public void editMarker(){
		
		
		
	}
	
}
