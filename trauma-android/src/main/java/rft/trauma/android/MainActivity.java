package rft.trauma.android;

import com.google.android.maps.MapActivity;
import com.google.android.maps.GeoPoint;
import android.os.Bundle;
import android.widget.TextView;
import rft.trauma.android.machine.Marker;
import rft.trauma.android.machine.IMarkerProvider;
import rft.trauma.android.machine.FileMarkerProvider;
import java.util.List;
import java.util.Iterator;

public class MainActivity extends MapActivity
{

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_activity);
	    
	    IMarkerProvider provider = new FileMarkerProvider();
	    provider.placeMarker(new Marker(new GeoPoint(12, 23), "hello"));
	    provider.placeMarker(new Marker(new GeoPoint(33, 544), "hi!"));
	    
	    TextView tv = (TextView)findViewById(R.id.textView);
	    StringBuilder sb = new StringBuilder("");
	    List<Marker> m;
	    m = provider.getMarker(1, 1, 1);
	    Iterator<Marker> i = m.iterator();
	    while(i.hasNext())
	    {
	    	sb.append(i.next().toString());
	    }
	    tv.setText(sb.toString());
	}

	@Override protected boolean isRouteDisplayed()
	{
		return false;
	}
}
