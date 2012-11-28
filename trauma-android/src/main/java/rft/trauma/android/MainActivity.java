package rft.trauma.android;

import rft.trauma.android.bl.CentralPoint;
import rft.trauma.android.bl.MapOverlay;
import rft.trauma.android.bl.Marker;
import rft.trauma.android.bl.MarkerManager;

import java.util.Iterator;
import java.util.List;

import rft.trauma.android.service.ServerException;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

/**
 * Main activity with MapView, this activity launches when the app starts
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class MainActivity extends MapActivity
{
    private static String TAG = "trauma-android";
    
    private MapView mapView;
    private Drawable drawable;
    private MapOverlay mapOverlay;
    
    private GeoPoint downLocation;
    private GeoPoint upLocation;

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
        
        drawable = this.getResources().getDrawable(R.drawable.pin);
        mapOverlay = new MapOverlay(drawable, this);
        mapView.getOverlays().add(mapOverlay);
        mapOverlay.fillAll();
        
        //mapOverlay.addOverlay(new Marker(1222, new GeoPoint(200, 300), "igen", "nem"));
        
//        mapView.setOnTouchListener(new OnTouchListener()
//		{
//			@Override
//			public boolean onTouch(View v, MotionEvent event)
//			{
//				populateMap();
//				return false;
//			}
//		});
        //populateMap();
        
    }
    
    @Override public boolean onTouchEvent(MotionEvent event)
    {
    	switch (event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		downLocation = mapView.getMapCenter();
    		break;
    	case MotionEvent.ACTION_UP:
    		upLocation = mapView.getMapCenter();
    		if (!downLocation.equals(upLocation))
    		{
    			Thread thread = new Thread()
    			{
    				@Override public void run()
    				{
    					while (true)
    					{
    						try
    						{
    							Thread.sleep(100);
    						}
    						catch (InterruptedException ex)
    						{
    							
    						}
    						
    						GeoPoint gp = mapView.getMapCenter();
    						if (gp.equals(upLocation))
    							break;
    						upLocation = gp;
    						
    					}
    					onMapStop();
    				}
    			};
    			thread.start();
    		}
    		break;
    	}
    	return super.onTouchEvent(event);
    }
    
    private void onMapStop()
    {
    	GeoPoint topLeft = mapView.getProjection().fromPixels(0, 0);
    	GeoPoint center = mapView.getMapCenter();
    	CentralPoint cp = CentralPoint.generate(center, topLeft);
    	
    	try
    	{
    		mapOverlay.fill(cp);
    	}
    	catch (ServerException ex)
    	{
    		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    		dialog.setTitle("Error");
    		dialog.setMessage(ex.getMessage());
    		dialog.show();
    	}
    }
    
    public void populateMap()
    {
    	//int zoomLevel = mapView.getZoomLevel(); // on 1, the equator is 256 px, each level of zoom multiplies by 2 
    	//GeoPoint mapCenter = mapView.getMapCenter();
    	
    	//CentralPoint centralPoint = new CentralPoint(mapView.getMapCenter(), 20000);
    	try
    	{
    		List<Marker> markers = MarkerManager.getAllMarkers();

        	for (Iterator<Marker> i = markers.iterator(); i.hasNext();)
        	{
        		Marker m = i.next();
        		if (!(mapOverlay.containsOverlay(m)))
        			mapOverlay.addOverlay(i.next());
        	}
    	}
    	catch (ServerException ex)
    	{
    		AlertDialog.Builder msg = new AlertDialog.Builder(this);
    		msg.setTitle("error");
    		msg.setMessage(ex.getMessage());
    		msg.show();
    	}
    	
    }
    
    @Override protected boolean isRouteDisplayed()
    {
        return false;
    }
}

