package rft.trauma.android.machine;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rft.trauma.android.MainActivity;
import rft.trauma.android.R;
import rft.trauma.android.services.CentralPoint;
import rft.trauma.android.services.IDataProvider;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;

/**
 * A Marker provider overlay for a MapView object
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class MapOverlay extends ItemizedOverlay<Marker>
{
	private final String TAG = "trauma-android";
	
	private ArrayList<Marker> mOverlays = new ArrayList<Marker>();
	private Context mContext;
	
	private Marker item;
	private EditText descEditText;
	
	private AlertDialog d = null;
	
	public MapOverlay(Drawable defaultMarker, Context context)
	{
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public boolean addOverlay(Marker overlay)
	{
		boolean ret = mOverlays.add(overlay);
		populate();
		return ret;
	}
	
	public boolean deleteOverlay(Marker overlay)
	{
		boolean ret = mOverlays.remove(overlay);
		populate();
		return ret;
	}
	
	public boolean containsOverlay(Marker overlay)
	{
		return mOverlays.contains(overlay);
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
		item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		View descView = LayoutInflater.from(mContext).inflate(R.layout.marker_description, null);
		
		EditText longEditText = (EditText)descView.findViewById(R.id.markerDescription_longitudeEditText);
		EditText latEditText = (EditText)descView.findViewById(R.id.markerDescription_latitudeEditText);
		EditText addressEditText = (EditText)descView.findViewById(R.id.markerDescription_addressEditText);
		descEditText = (EditText)descView.findViewById(R.id.markerDescription_descriptionEditText);
		Button saveButton = (Button)descView.findViewById(R.id.markerDescription_saveMarkerButton);
		Button deleteButton = (Button)descView.findViewById(R.id.markerDescription_deleteMarkerButton);
		
		deleteButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IDataProvider provider = ((MainActivity)mContext).getDataProvider();
				AlertDialog.Builder msgDialog = new AlertDialog.Builder(mContext);
				
				int msg = provider.deleteMarker(item);
				if (msg == 200)
				{
					msgDialog.setTitle(v.getResources().getString(R.string.success));
					msgDialog.setMessage(v.getResources().getString(R.string.good_delete));
				}
				else
				{
					msgDialog.setTitle(v.getResources().getString(R.string.fail));
					msgDialog.setMessage(v.getResources().getString(R.string.wrong_delete));
				}
				if (d != null) d.dismiss();
				msgDialog.show();
				
				/**
				 * Log!
				 */
				{
					MainActivity ma = (MainActivity)mContext;
					MapView mv = (MapView)ma.findViewById(R.id.mapView);
					Log.i(TAG, Integer.toString(provider.getAllMarkers().size()));
				}
				/**
				 * Log end!
				 */
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IDataProvider provider = ((MainActivity)mContext).getDataProvider();
				AlertDialog.Builder msgDialog = new AlertDialog.Builder(mContext);
				
				int msg = provider.editMarker(item, descEditText.getText().toString());
				if (msg == 200)
				{
					msgDialog.setTitle(v.getResources().getString(R.string.success));
					msgDialog.setMessage(v.getResources().getString(R.string.good_editing));
				}
				else
				{
					msgDialog.setTitle(v.getResources().getString(R.string.fail));
					msgDialog.setMessage(v.getResources().getString(R.string.wrong_editing) + " " + Integer.toString(msg));
				}
				msgDialog.show();
			}
		});
		
		longEditText.setText(Integer.toString(item.getPoint().getLongitudeE6()));
		latEditText.setText(Integer.toString(item.getPoint().getLatitudeE6()));
		addressEditText.setText(this.getAddressFromGeoPoint(item.getPoint()));
		descEditText.setText(item.getSnippet());
		
		dialog.setView(descView);
		d = dialog.show();
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
			return "error parsing the address";
		}
	}

}
