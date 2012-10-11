package rft.trauma.android;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HelloAndroidActivity extends MapActivity {

    private static String TAG = "trauma-android";

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
        
        MapView mapView = (MapView)findViewById(R.id.main_mapView);
        mapView.setBuiltInZoomControls(true);
    }
    
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

