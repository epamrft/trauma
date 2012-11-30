package com.epam.pf.trauma.backend.service.dao;

import java.util.Collection;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;

@Service
public class MarkerDatabaseDAO extends AbstractJpaDAO<Marker> implements MarkerDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarkerDatabaseDAO.class);

	public MarkerDatabaseDAO() {
		setClazz(Marker.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Marker addMarker(Marker marker) {
		this.save(marker);
		MarkerDatabaseDAO.LOGGER.debug("Added marker: {}", marker);
		return marker;
	}

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public Collection<Marker> getMarkers(CentralPoint centralPoint) {
		MarkerDatabaseDAO.LOGGER.debug("Getting Markers with Central: {}", centralPoint);

		Collection<Marker> markers = new LinkedList<Marker>(findAll(centralPoint));
		
			MarkerDatabaseDAO.LOGGER.debug("Markers within Central: {}", markers);
		
		return markers;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<Marker> getMarkers() {
		Collection<Marker> markers = this.findAll();
		MarkerDatabaseDAO.LOGGER.debug("Listing all Markers: {}", markers);
		return markers;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMarker(int id) {
		MarkerDatabaseDAO.LOGGER.debug("Deleting Marker: {}", this.deleteById(id));
		MarkerDatabaseDAO.LOGGER.debug("Deleted");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Marker editMarker(int id, String desc) {
		MarkerDatabaseDAO.LOGGER.debug("Editing Marker Id: {}", id);

		Marker findedOne = this.findOne(id);

		if (findedOne == null) {
			throw new IllegalStateException("Marker not found!");
		}

		MarkerDatabaseDAO.LOGGER.debug("Edited to: {}", findedOne);
		findedOne.setDesc(desc);
		entityManager.merge(findedOne);

		MarkerDatabaseDAO.LOGGER.debug("Edited to: {}", findedOne);
		return findedOne;
	}
}