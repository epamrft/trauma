package rft.trauma.android;

import java.util.Timer;
import java.util.TimerTask;

import rft.trauma.android.bl.CentralPoint;
import rft.trauma.android.bl.MapOverlay;
import rft.trauma.android.service.ServerException;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class TraumaMapView extends MapView
{
	public interface OnLongpressListener
	{
		public void onLongpress(MapView view, GeoPoint lonpressLocation);
	}
	
	/**
	 * Time in ms before the longpress is triggered
	 */
	static final int LONGPRESS_THRESHOLD = 500;
	
	/**
	 * keep a record of the center of the map to know if the map has been panned
	 */
	private GeoPoint lastMapCenter;
	
	private Timer longpressTimer = new Timer();
	private TraumaMapView.OnLongpressListener longpressListener;
	
	private GeoPoint downLocation;
    private GeoPoint upLocation;
    
    private Drawable drawable;
    private MapOverlay mapOverlay;
    
    private MotionEvent mEvent;
    
    public MapOverlay getMapOverlay()
	{
		return mapOverlay;
	}

	private void initObj()
    {
    	drawable = getContext().getResources().getDrawable(R.drawable.pin);
    	mapOverlay = new MapOverlay(drawable, getContext(), this);
    	getOverlays().add(mapOverlay);
    	mapOverlay.wipeOverlay();
    	//mapOverlay.fill(getCentralPoint());
    	mapOverlay.fillAll();
    	postInvalidate();//
    }
	
	public TraumaMapView(Context context, String apiKey)
	{
		super(context, apiKey);
		initObj();
	}
	
	public TraumaMapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initObj();
	}
	
	public TraumaMapView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initObj();
	}
	
	public void setOnLongpressListener(TraumaMapView.OnLongpressListener listener)
	{
		this.longpressListener = listener;
	}
	
	/**
	 * This method is called every time the user touches the map,
	 * drags a finger on the map or removes fingers from the map
	 */
	@Override public boolean onTouchEvent(MotionEvent event)
	{
		handleLongpress(event);
		return super.onTouchEvent(event);
	}
	
	/**
	 * 
	 */
	private void handleLongpress(MotionEvent event)
	{
		mEvent = event;
			Log.d("trauma-android", "event init");
		if (mEvent.getAction() == MotionEvent.ACTION_DOWN) //finger has touched the screen
		{
			downLocation = getMapCenter();
			longpressTimer = new Timer();
			longpressTimer.schedule(new TimerTask()
			{
				@Override public void run()
				{
					GeoPoint longpressLocation = getProjection().fromPixels((int)mEvent.getX(), (int)mEvent.getY());
					longpressListener.onLongpress(TraumaMapView.this, longpressLocation);
				}
			}, LONGPRESS_THRESHOLD);
			lastMapCenter = getMapCenter();
		}
		if (mEvent.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (!getMapCenter().equals(lastMapCenter))
			{
				longpressTimer.cancel();
			}
		}
		if (mEvent.getAction() == MotionEvent.ACTION_UP)
		{
			longpressTimer.cancel();
			
			upLocation = getMapCenter();
			if (!upLocation.equals(downLocation))
			{
				Thread thread = new Thread()
    			{
    				@Override public void run()
    				{
    					while (true)
    					{
    						try
    						{
    							Thread.sleep(100);
    						}
    						catch (InterruptedException ex)
    						{
    							//intentionally left blank
    						}
    						
    						GeoPoint gp = getMapCenter();
    						if (gp.equals(upLocation))
    							break;
    						upLocation = gp;
    						
    					}
    					onMapStop();
    				}
    			};
    			thread.start();
			}
		}
		if (mEvent.getPointerCount() > 1)
		{
			longpressTimer.cancel();
		}
	}
	
	private void onMapStop()
    {
    	//CentralPoint cp = getCentralPoint();
    	
    	try
    	{
    		//mapOverlay.fill(cp);
    		mapOverlay.fillAll();
    		postInvalidate();//
    			Log.d("trauma-android", Integer.toString(mapOverlay.size()));
    	}
    	catch (ServerException ex)
    	{
    		AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
    		dialog.setTitle("Error");
    		dialog.setMessage(ex.getMessage());
    		dialog.show();
    	}
    }
	
	private CentralPoint getCentralPoint()
	{
		GeoPoint topLeft = getProjection().fromPixels(0, 0);
		GeoPoint center = getMapCenter();
		return CentralPoint.generate(center, topLeft);
	}
}