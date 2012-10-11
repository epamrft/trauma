package rft.trauma.android;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class HelloAndroidActivity extends MapActivity {

    private static String TAG = "trauma-android";
    MapView mapView = null;
    private boolean isClick = false;
    
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        
        mapView = (MapView)findViewById(R.id.main_mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setOnClickListener(onMapClickListener);
        mapView.setOnTouchListener(onMapTouchListener);
    }
    
    private OnClickListener onMapClickListener = new OnClickListener()
	{
		@Override public void onClick(View v)
		{
			Log.i(TAG, "isClick = true");
			isClick = true;
		}
	};
    
    /** Gets the longitude and latitude from tap, starts a new PlaceMarkerActivity with the coordinates */
    private OnTouchListener onMapTouchListener = new OnTouchListener()
	{
		@Override public boolean onTouch(View v, MotionEvent event)
		{
			Log.i(TAG, "touch caught");
			GeoPoint point = null;
			if (isClick)
			{
				isClick = false;
				Log.i(TAG, "action up");
				point = mapView.getProjection().fromPixels((int)event.getX(), (int)event.getY());
				int longitude = point.getLongitudeE6();
				int latitude = point.getLatitudeE6();
				Intent intent = new Intent(HelloAndroidActivity.this, PlaceMarkerActivity.class);
				intent.putExtra("longitude", longitude);
				intent.putExtra("latitude", latitude);
				startActivity(intent);
				return true;
			}
			return false;
		}
	};
    
    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
    	if (item.getItemId() == R.id.main_menu_exit)
    	{
    		finish();
    		System.exit(0);
    	}
    	return true;
    }
    
    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    
    @Override protected boolean isRouteDisplayed()
    {
    	return false;
    }
}

