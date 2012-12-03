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

/**
 * A custom MapView that can be displayed on the screen
 * @author Nagy Gergo
 * @version 1.0.0
 */
public class TraumaMapView extends MapView
{
	/**
	 * The interface representing a Longpress action
	 * @author Nagy Gergo
	 * @version 1.0.0
	 */
	public interface OnLongpressListener
	{
		/**
		 * This event fires when the map is longpressed
		 * @param view The MapView in which the longpress occured
		 * @param lonpressLocation The location of the longpress
		 */
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
    
    /**
     * Returns a list of Markers that are currently on the map
     * @return the list of Markers that are currently displayed on the map
     */
    public MapOverlay getMapOverlay()
	{
		return mapOverlay;
	}

    /**
     * Initializes the TraumaMapView
     */
	private void initObj()
    {
    	drawable = getContext().getResources().getDrawable(R.drawable.pin);
    	mapOverlay = new MapOverlay(drawable, getContext(), this);
    	getOverlays().add(mapOverlay);
    	mapOverlay.wipeOverlay();
    	//TODO: mapOverlay.fill(getCentralPoint());
    	mapOverlay.fillAll();
    	postInvalidate();//
    }
	
	/**
	 * Creates a new instance of the TraumaMapView 
	 * @param context the context where the MapView is
	 * @param apiKey the apiKey of the MapView
	 */
	public TraumaMapView(Context context, String apiKey)
	{
		super(context, apiKey);
		initObj();
	}
	
	/**
	 * Creates a new instance of the TraumaMapView
	 * @param context The context where the map is
	 * @param attrs a set of attributes that are passed on the that MapView
	 */
	public TraumaMapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initObj();
	}
	
	/**
	 * Creates a new instance of the TraumaMapView
	 * @param context the context where the MapView is
	 * @param attrs a set of attributes that are passed on the the MapView
	 * @param defStyle defStyle of the MapView
	 */
	public TraumaMapView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initObj();
	}
	
	/**
	 * Sets the OnLongpressListener of the MapView
	 * @param listener the TraumaMapView.OnLongpressListener that defines what happenes on longpress
	 */
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
	 *  Handles the longpress event
	 * @param event the motion event that occured on the map
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
	
	/**
	 * This method fires when the map is stopped.
	 * It repopulates the map
	 */
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
	
	/**
	 * Gets the CentralPoint object of the current state of the map.
	 * @return returns the CentralPoint of the map
	 */
	private CentralPoint getCentralPoint()
	{
		GeoPoint topLeft = getProjection().fromPixels(0, 0);
		GeoPoint center = getMapCenter();
		return CentralPoint.generate(center, topLeft);
	}
}