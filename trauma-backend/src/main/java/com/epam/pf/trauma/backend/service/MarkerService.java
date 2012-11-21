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

	public Marker addMarker(Marker marker) {
		return markerDAO.addMarker(marker);
	}

	public Collection<Marker> getMarkers(CentralPoint centralPoint) {
		LOGGER.debug("get markers: {}", centralPoint);
		Collection<Marker> markers = markerDAO.getMarkers(centralPoint);
		LOGGER.debug("size of founded markers: {}", markers.size());

		return markers;
	}
	public Collection<Marker> getMarkers() {
		LOGGER.debug("get all markers: {}");
		Collection<Marker> markers = markerDAO.getMarkers();
		LOGGER.debug("number of found markers: {}", markers.size());

		return markers;
	}

	public void deleteMarker(int id) {
		markerDAO.deleteMarker(id);
	}

	public Marker editMarker(int id, String desc) {
		return markerDAO.editMarker(id, desc);
	}

}
