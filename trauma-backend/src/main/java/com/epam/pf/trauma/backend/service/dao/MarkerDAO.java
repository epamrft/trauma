package com.epam.pf.trauma.backend.service.dao;

import java.util.Collection;

import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;

public interface MarkerDAO {
	/**
	 * DAO level of add marker                           
	 *
	 *    
	 * 
	 *
	 * @param marker 	 Marker to be added          
	 * @return The Marker
	 */
	public Marker addMarker(Marker marker);
	/**
	 * DAO level of get marker with central                           
	 *
	 *    
	 * 
	 *
	 * @param  centralPoint The Central Point          
	 * @return markers 	Collection of valid Markers
	 */
	public Collection<Marker> getMarkers(CentralPoint centralPoint);
	/**
	 * DAO level of delete a Marker                           
	 *
	 *    
	 * 
	 *
	 * @param  id ID of marker to be deleted     
	 *
	 */
	public void deleteMarker(int id);
	/**
	 * DAO level of edit a Marker                           
	 *
	 *    
	 * 
	 *
	 * @param  id	ID of marker to be edited,
	 * @param desc	 Description string         
	 * @return Edited Marker
	 */
	public Marker editMarker(int id, String desc);
	/**
	 * DAO level of get marker without central                           
	 *
	 *    
	 * 
	 *
	 *         
	 * @return markers	 Collection of Markers
	 */
	public Collection<Marker> getMarkers();
}
