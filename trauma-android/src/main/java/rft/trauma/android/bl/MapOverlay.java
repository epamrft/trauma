package rft.trauma.android.bl;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import rft.trauma.android.R;
import rft.trauma.android.service.ServerException;
import rft.trauma.android.service.TraumaDataProvider;

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
	//private final String TAG = "trauma-android";
	
	private ArrayList<Marker> mOverlays = new ArrayList<Marker>();
	private Context mContext;
	private MapView mapView;
	
	private Marker item;
	private EditText descEditText;
	
	private AlertDialog d = null;
	
	private IMarkerManager markerManager;
	
	public MapOverlay(Drawable defaultMarker, Context context, MapView mapView)
	{
		super(boundCenterBottom(defaultMarker));
		this.mapView = mapView;
		markerManager = new MarkerManager(new TraumaDataProvider());
		mContext = context;
		populate();
	}

	public boolean addOverlay(Marker overlay)
	{
		boolean ret = mOverlays.add(overlay);
		setLastFocusedIndex(-1);
		populate();
		return ret;
	}
	
	private void replaceList(List<Marker> list)
	{
		mOverlays = (ArrayList<Marker>) list;
		setLastFocusedIndex(-1);
		populate();
	}
	
	public void wipeOverlay()
	{
		mOverlays.clear();
		setLastFocusedIndex(-1);
		populate();
	}
	
	public void fill(CentralPoint cp) throws ServerException
	{
			Log.i("trauma-android", "fill method started");
		List<Marker> markers = markerManager.getMarkers(cp);
		for(Iterator<Marker> i = markers.iterator(); i.hasNext(); )
		{
			Marker m = i.next();
				Log.d("trauma-android", m.toString());
			if (mOverlays.contains(m));
			{
				addOverlay(m);
					Log.w("trauma-android", "the item was added");
			}
		}
	}
	
	public void fillAll() throws ServerException
	{
		List<Marker> markers = markerManager.getAllMarkers();
		replaceList(markers);
	}
	
	public boolean deleteOverlay(Marker overlay)
	{
		boolean ret = mOverlays.remove(overlay);
		setLastFocusedIndex(-1);
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
				boolean success = true;
				try
				{
					deleteOverlay(item);
					item.delete();
				}
				catch (ServerException ex)
				{
					success = false;
				}
				finally
				{
					if (d != null) d.dismiss();
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
								Log.w("trauma-android", "thread interrupted");
							}
							fillAll();
							mapView.postInvalidate();
						}
					}).start();
					
					createDeleteStatusDialog(success, mContext);
				}
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				boolean success = true;
				try
				{
					item.edit(descEditText.getText().toString());
					deleteOverlay(item);
					addOverlay(item);
				}
				catch (ServerException ex)
				{
					success = false;
				}
				finally
				{
					if (d != null) d.dismiss();
					wipeOverlay();
					//TODO: this should be changed to fill()
					fillAll();
					createEditStatusDialog(success, mContext);
				}
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

	private AlertDialog createDeleteStatusDialog(boolean success, Context cnt)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(cnt);
		
		if (success)
		{
			dialog.setTitle(cnt.getResources().getString(R.string.success));
			dialog.setMessage(cnt.getResources().getString(R.string.good_delete));
		}
		else
		{
			dialog.setTitle(cnt.getResources().getString(R.string.fail));
			dialog.setMessage(cnt.getResources().getString(R.string.wrong_delete));
		}
		
		return dialog.show();
	}
	
	private AlertDialog createEditStatusDialog(boolean success, Context cnt)
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
			dialog.setMessage(cnt.getResources().getString(R.string.wrong_editing));
		}
		
		return dialog.show();
	}
}
