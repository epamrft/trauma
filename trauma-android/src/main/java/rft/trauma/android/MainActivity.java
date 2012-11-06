package rft.trauma.android;

import rft.trauma.android.machine.Marker;
import java.util.List;
import rft.trauma.android.machine.MapOverlay;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MainActivity extends MapActivity
{

    private static String TAG = "trauma-android";

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
        
        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.pin);
        MapOverlay mapOverlay = new MapOverlay(drawable, this);
        
        Marker marker = new Marker(new GeoPoint(19240000, -99120000), "hello", "mexico");
        mapOverlay.addOverlay(marker);
        mapOverlays.add(mapOverlay);
    }
    
    @Override protected boolean isRouteDisplayed()
    {
        return false;
    }

}

