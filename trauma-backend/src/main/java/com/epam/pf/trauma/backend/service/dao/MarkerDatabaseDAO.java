package com.epam.pf.trauma.backend.service.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;

public class MarkerDatabaseDAO implements MarkerDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarkerDatabaseDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Marker addMarker(Marker marker) {
		return null;
	}

	@Override
	public Collection<Marker> getMarkers(CentralPoint centralPoint) {
		return null;
	}

	@Override
	public Collection<Marker> getMarkers() {
		return null;
	}

	@Override
	public void deleteMarker(int id) {

	}

	@Override
	public Marker editMarker(int id, String desc) {
		return null;
	}

}