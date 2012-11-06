package rft.trauma.android.machine;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapOverlay extends ItemizedOverlay<Marker>
{
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
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

}
