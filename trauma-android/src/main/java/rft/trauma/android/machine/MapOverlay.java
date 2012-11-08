package rft.trauma.android.machine;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rft.trauma.android.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;

/**
 * A Marker provider overlay for a MapView object
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class MapOverlay extends ItemizedOverlay<Marker>
{
	//private final String TAG = "trauma-android";
	
	private ArrayList<Marker> mOverlays = new ArrayList<Marker>();
	private Context mContext;
	
	public MapOverlay(Drawable defaultMarker, Context context)
	{
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public void addOverlay(Marker overlay)
	{
		mOverlays.add(overlay);
		populate();
	}
	
	@Override
	protected Marker createItem(int i)
	{
		return mOverlays.get(i);
	}

	@Override
	public int size()
	{
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index)
	{
		Marker item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		View descView = LayoutInflater.from(mContext).inflate(R.layout.marker_description, null);
		
		EditText longEditText = (EditText)descView.findViewById(R.id.markerDescription_longitudeEditText);
		EditText latEditText = (EditText)descView.findViewById(R.id.markerDescription_latitudeEditText);
		EditText addressEditText = (EditText)descView.findViewById(R.id.markerDescription_addressEditText);
		EditText descEditText = (EditText)descView.findViewById(R.id.markerDescription_descriptionEditText);
		
		longEditText.setText(Integer.toString(item.getPoint().getLongitudeE6()));
		latEditText.setText(Integer.toString(item.getPoint().getLatitudeE6()));
		addressEditText.setText(this.getAddressFromGeoPoint(item.getPoint()));
		descEditText.setText(item.getSnippet());
		
		dialog.setView(descView);
		dialog.show();
		dialog.setTitle("Edit Marker description");
		return true;
	}
	
	private String getAddressFromGeoPoint(GeoPoint point)
	{
		Geocoder coder = new Geocoder(mContext, Locale.getDefault());
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
			return "";
		}
	}

}