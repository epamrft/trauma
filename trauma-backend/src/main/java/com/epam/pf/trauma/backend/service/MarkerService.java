package com.epam.pf.trauma.backend.service;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.epam.pf.trauma.backend.service.dao.MarkerDAO;
import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;

@Service
public class MarkerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarkerService.class);

	@Inject
	public MarkerDAO markerDAO;
	/**
	 * Service level of add marker                           
	 *
	 *    
	 * 
	 *
	 * @param  Marker to be added          
	 * @return The Marker
	 */
	public Marker addMarker(Marker marker) {
		return markerDAO.addMarker(marker);
	}
	/**
	 * Service level of get marker with central                           
	 *
	 *    
	 * 
	 *
	 * @param  Central Point          
	 * @return Collection of Markers
	 */
	public Collection<Marker> getMarkers(CentralPoint centralPoint) {
		LOGGER.debug("get markers: {}", centralPoint);
		Collection<Marker> markers = markerDAO.getMarkers(centralPoint);
		LOGGER.debug("size of founded markers: {}", markers.size());

		return markers;
	}
	/**
	 * Service level of get marker without central                           
	 *
	 *    
	 * 
	 *
	 *         
	 * @return Collection of Markers
	 */
	public Collection<Marker> getMarkers() {
		LOGGER.debug("get all markers: {}");
		Collection<Marker> markers = markerDAO.getMarkers();
		LOGGER.debug("number of found markers: {}", markers.size());

		return markers;
	}
	/**
	 * Service level of delete a Marker                           
	 *
	 *    
	 * 
	 *
	 * @param  Marker id         
	 *
	 */
	public void deleteMarker(int id) {
		markerDAO.deleteMarker(id);
	}
	/**
	 * Service level of edit a Marker                           
	 *
	 *    
	 * 
	 *
	 * @param  Marker id, Description string         
	 * @return Edited Marker
	 */
	public Marker editMarker(int id, String desc) {
		LOGGER.debug("Edit marker: id: {}, desc: {}", id, desc);
		return markerDAO.editMarker(id, desc);
	}

}
