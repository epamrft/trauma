package rft.trauma.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class PlaceMarkerActivity extends Activity
{
	private int latitude;
	private int longitude;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.place_marker);
	    
	    Intent i = getIntent();
	    latitude = i.getIntExtra("latitude", 0);
	    longitude = i.getIntExtra("longitude", 0);
	    
	    EditText latitudeEditText = (EditText)findViewById(R.id.place_marker_EditText_latitude);
	    EditText longitudeEditText = (EditText)findViewById(R.id.place_marker_EditText_longitude);
	    
	    latitudeEditText.setText(Integer.toString(latitude));
	    longitudeEditText.setText(Integer.toString(longitude));
	}
}
