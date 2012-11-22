package com.epam.pf.trauma.backend.service.dao;

import java.util.Collection;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;
@Service
@Transactional
public class MarkerDatabaseDAO extends AbstractJpaDAO<Marker> implements
		MarkerDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MarkerDatabaseDAO.class);

	@Override
	public Marker addMarker(Marker marker) {
		this.save(marker);
		MarkerDatabaseDAO.LOGGER.debug("Added marker: {}", marker);
		return marker;
	}

	@Override
	public Collection<Marker> getMarkers(CentralPoint centralPoint) {
		MarkerDatabaseDAO.LOGGER.debug("Getting Markers with Central: {}",centralPoint);
		Collection<Marker> markers = new LinkedList<Marker>(this.findAll());
		for (Marker marker : markers) {
			if (!centralPoint.inRadius(marker)) {
				markers.remove(marker);

			}
			MarkerDatabaseDAO.LOGGER.debug("Markers within Central: {}",
					markers);
		}
		return markers;
	}

	@Override
	public Collection<Marker> getMarkers() {
		MarkerDatabaseDAO.LOGGER.debug("Listing all Markers: {}",this.findAll());
		return this.findAll();
	}

	@Override
	public void deleteMarker(int id) {
		MarkerDatabaseDAO.LOGGER.debug("Deleting Marker: {}", this.findOne(id));
		this.deleteById(id);

		MarkerDatabaseDAO.LOGGER.debug("Deleted");
	}

	@Override
	public Marker editMarker(int id, String desc) {
		MarkerDatabaseDAO.LOGGER.debug("Editing Marker: {}", this.findOne(id));
		this.findOne(id).setDesc(desc);
		entityManager.flush();
		MarkerDatabaseDAO.LOGGER.debug("Edited to: {}", this.findOne(id));
		return this.findOne(id);
	}

}