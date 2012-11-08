package rft.trauma.android;

import rft.trauma.android.machine.Marker;

import java.util.Iterator;
import java.util.List;
import rft.trauma.android.machine.MapOverlay;
import rft.trauma.android.services.CentralPoint;
import rft.trauma.android.services.DummyDataProvider;
import rft.trauma.android.services.IDataProvider;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

/**
 * Main activity with MapView, this activity launches when the app starts
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class MainActivity extends MapActivity
{
	private IDataProvider dataProvider = new DummyDataProvider();
    private static String TAG = "trauma-android";
    
    MapView mapView;
    List<Overlay> mapOverlays;
    Drawable drawable;
    MapOverlay mapOverlay;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main_activity);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.pin);
        mapOverlay = new MapOverlay(drawable, this);
        
        mapOverlays.add(mapOverlay);
        
        mapView.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				populateMap();
				return false;
			}
		});
    }
    
    private void populateMap()
    {
    	//int zoomLevel = mapView.getZoomLevel(); // on 1, the equator is 256 px, each level of zoom multiplies by 2 
    	//GeoPoint mapCenter = mapView.getMapCenter();
    	
    	CentralPoint centralPoint = new CentralPoint(mapView.getMapCenter(), 20000);
    	List<Marker> markers = dataProvider.getMarkers(centralPoint);
    	for (Iterator<Marker> i = markers.iterator(); i.hasNext();)
    	{
    		mapOverlay.addOverlay(i.next());
    	}
    }
    
    @Override protected boolean isRouteDisplayed()
    {
        return false;
    }

	public IDataProvider getDataProvider()
	{
		return dataProvider;
	}

}

