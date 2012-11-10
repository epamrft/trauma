package com.epam.pf.trauma.backend.service.dao;

import java.util.Collection;

import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;


public interface MarkerDAO {
 public Marker addMarker(Marker marker);
 public Collection<Marker> getMarkers(CentralPoint centralPoint);
 public void deleteMarker(int id);
 public Marker editMarker(int id, String desc);
public Collection<Marker> getMarkers();
}
