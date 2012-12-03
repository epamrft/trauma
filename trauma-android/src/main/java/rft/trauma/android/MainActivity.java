package rft.trauma.android;

import rft.trauma.android.bl.IMarkerManager;
import rft.trauma.android.bl.Marker;
import rft.trauma.android.bl.MarkerManager;
import rft.trauma.android.service.ServerException;
import rft.trauma.android.service.TraumaDataProvider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    
    private TraumaMapView mapView;
    private EditText descEditText;
    private AlertDialog d = null;
    private IMarkerManager markerManager;
    
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
        
        markerManager = new MarkerManager(new TraumaDataProvider());
        
        Log.i(TAG, "content view set");
        
        //TODO: check internet connection on start
//        if (!isOnline())
//        {
//        	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        	dialog.setMessage("Internet connection not found! The application will now close");
//        	dialog.setTitle("Connection Error!");
//        	dialog.show();
//        	this.finish();
//        }
        
        mapView = (TraumaMapView) findViewById(R.id.mapView);
        mapView.setSatellite(false);
        mapView.setBuiltInZoomControls(true);
        
        mapView.setOnLongpressListener(new rft.trauma.android.TraumaMapView.OnLongpressListener()
		{
			@Override public void onLongpress(MapView view, final GeoPoint lonpressLocation)
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						newMarker(lonpressLocation);
					}
				});
			}
		});
        
        	Log.i(TAG, "onCreate ended");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    	case R.id.menu_changeView:
    		if (item.isChecked())
    		{
    			item.setChecked(false);
    			item.setTitle(R.string.satview);
    			item.setIcon(R.drawable.sat);
    			mapView.setSatellite(false);
    		}
    		else
    		{
    			item.setChecked(true);
    			item.setTitle(R.string.mapview);
    			item.setIcon(R.drawable.map);
    			mapView.setSatellite(true);
    		}
    		return true;
    	case R.id.menu_exit:
    		this.finish();
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    			
    	}
    }
    
    private void newMarker(final GeoPoint point)
    {
    		Log.i(TAG, "new marker method");
    	View descView = LayoutInflater.from(this).inflate(R.layout.marker_description, null);
    		Log.i(TAG, "Layout inflated");
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    		Log.i(TAG, "alert dialog");
    	
    	EditText longEditText = (EditText)descView.findViewById(R.id.markerDescription_longitudeEditText);
		EditText latEditText = (EditText)descView.findViewById(R.id.markerDescription_latitudeEditText);
		EditText addressEditText = (EditText)descView.findViewById(R.id.markerDescription_addressEditText);
		descEditText = (EditText)descView.findViewById(R.id.markerDescription_descriptionEditText);
		Button saveButton = (Button)descView.findViewById(R.id.markerDescription_saveMarkerButton);
		Button deleteButton = (Button)descView.findViewById(R.id.markerDescription_deleteMarkerButton);
		
			Log.i(TAG, "get UI elements");
		
		saveButton.setText(R.string.addmarker);
			Log.i(TAG, "save button set text");
		deleteButton.setText(R.string.cancel);
			Log.i(TAG, "delete button set text");
		longEditText.setText(Integer.toString(point.getLongitudeE6()));
		latEditText.setText(Integer.toString(point.getLatitudeE6()));
		addressEditText.setText(getAddressFromGeoPoint(point));
		
			Log.i(TAG, "init ui");
		
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				boolean success = true;
				String error = null;
				try
				{
					markerManager.addMarker(new Marker(point, "Marker", descEditText.getText().toString()));
					//TODO: this should be changed to fill()
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Thread.sleep(200);
							}
							catch (InterruptedException e)
							{
								
							}
							mapView.getMapOverlay().fillAll();
							mapView.postInvalidate();
						}
					}).start();
				}
				catch(ServerException ex)
				{
					success = false;
					error = ex.getMessage();
				}
				finally
				{
					if (d != null) d.dismiss();
					createAddStatusDialog(success, v.getContext(), error);
				}
			}
		});
		
		deleteButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				d.dismiss();
			}
		});
		
		dialog.setView(descView);
		d = dialog.show();
		dialog.setTitle("Place a new Marker");
    }
    
    public boolean isOnline()
    {
    	ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netInfo = cm.getActiveNetworkInfo();
    	if (netInfo != null && netInfo.isConnected())
    		return true;
    	return false;
    }
    
    @Override protected boolean isRouteDisplayed()
    {
        return false;
    }
    
    private String getAddressFromGeoPoint(GeoPoint point)
	{
		Geocoder coder = new Geocoder(this, Locale.getDefault());
		try
		{
			List<Address> addresses = coder.getFromLocation(point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6, 1);
			StringBuilder builder = new StringBuilder();
			if (addresses.size() > 0)
			{
				for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
				{
					builder.append(addresses.get(0).getAddressLine(i));
					builder.append("\n");
				}
			}
			return builder.toString();
		}
		catch (IOException ex)
		{
			return "error parsing the address";
		}
	}
    
    private AlertDialog createAddStatusDialog(boolean success, Context cnt, String error)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(cnt);
		
		if (success)
		{
			dialog.setTitle(cnt.getResources().getString(R.string.success));
			dialog.setMessage(cnt.getResources().getString(R.string.good_editing));
		}
		else
		{
			dialog.setTitle(cnt.getResources().getString(R.string.fail));
			dialog.setMessage(cnt.getResources().getString(R.string.wrong_editing) + error);
		}
		return dialog.show();
	}
}

