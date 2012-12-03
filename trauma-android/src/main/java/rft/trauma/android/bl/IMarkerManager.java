package rft.trauma.android.bl;

import java.util.List;

public interface IMarkerManager
{
	public void addMarker(Marker marker);
	public void deleteMarker(Marker marker);
	public List<Marker> getMarkers(CentralPoint centralPoint);
	public List<Marker> getAllMarkers();
	public void editMarker(Marker marker, String description);
}
