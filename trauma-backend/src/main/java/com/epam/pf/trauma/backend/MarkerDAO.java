package com.epam.pf.trauma.backend;

import java.util.List;

public interface MarkerDAO {
 public int addMarker(Marker marker);
 public List<Marker> getMarkers(CentralPoint centralPoint);
 public void deleteMarker(int id);
 public Marker editMarker(int id, String desc);
}
