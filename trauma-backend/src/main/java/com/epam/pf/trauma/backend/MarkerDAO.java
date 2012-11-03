package com.epam.pf.trauma.backend;

import java.util.Collection;


public interface MarkerDAO {
 public Marker addMarker(Marker marker);
 public Collection<Marker> getMarkers(CentralPoint centralPoint);
 public void deleteMarker(int id);
 public Marker editMarker(int id, String desc);
}
