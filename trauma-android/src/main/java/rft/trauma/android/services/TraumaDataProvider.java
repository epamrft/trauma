package rft.trauma.android.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.maps.GeoPoint;

import rft.trauma.android.machine.Marker;

public class TraumaDataProvider implements IDataProvider
{
	private static String serverRoot = "http://trauma.backend.cloudfoundry.com";
	private static String errorMessage = "An error occured while communicating with the server";
	
	private static String TAG = "trauma-android";
	
	public TraumaDataProvider()
	{
		
	}
	
	@Override
	public int addMarker(Marker marker) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		String url = serverRoot + "/markers";
		HttpPost post = new HttpPost(url);
		
		try
		{
			JSONObject m = new JSONObject();
			m.put("latitude", marker.getPoint().getLatitudeE6());
			m.put("longitude", marker.getPoint().getLongitudeE6());
			m.put("desc", marker.getMessage());
			
			StringEntity params = new StringEntity(m.toString());
			post.addHeader("content-type", "application/json");
			post.setEntity(params);
			
			HttpResponse response = client.execute(post);
			
			return response.getStatusLine().getStatusCode();
		}
		catch(ClientProtocolException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		catch(IOException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		catch(JSONException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

	/**
	 * @throws ServerException
	 */
	@Override
	public List<Marker> getAllMarkers() throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
			Log.i(TAG, "client");
		String url = serverRoot + "/markers";
			Log.i(TAG, "url");
		HttpGet getRequest = new HttpGet(url);
			Log.i(TAG, "httprequest");
		getRequest.addHeader("accept", "application/json");
			Log.i(TAG, "json header");
		try
		{
			HttpResponse response = client.execute(getRequest);
				Log.i(TAG, "execute request");
			if (response.getStatusLine().getStatusCode() != 200) throw new ServerException(errorMessage);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null)
			{
				sb.append(output);
			}
			try
			{
				List<Marker> list = new ArrayList<Marker>();
				JSONArray array = new JSONArray(sb.toString());
				if (array.isNull(0))
				{
					return list;
				}
				for (int i=0; i < array.length(); i++)
				{
					JSONObject obj = array.getJSONObject(i);
					int id = obj.getInt("id");
					int latitude = obj.getInt("latitude");
					int longitude = obj.getInt("longitude");
					
					String desc = obj.getString("desc");
					Marker m = new Marker(id, new GeoPoint(latitude, longitude), "Marker", desc);
						Log.i(TAG, m.toString());
					list.add(m);
				}
					Log.i(TAG, list.toString());
				return list;
			}
			catch (JSONException ex)
			{
				throw new ServerException(ex.getMessage());
			}
		} 
		catch (ClientProtocolException e)
		{
			throw new ServerException(e.getMessage());
		}
		catch (IOException e)
		{
			throw new ServerException(e.getMessage());
		}
		catch (Exception ex)
		{
			throw new ServerException(ex.getMessage());
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

	
	/**
	 * @throws ServerException
	 */
	@Override
	public List<Marker> getMarkers(CentralPoint centralPoint) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("central-lan", Long.toString(centralPoint.getLatitude())));
		params.add(new BasicNameValuePair("central-lng", Long.toString(centralPoint.getLongitude())));
		params.add(new BasicNameValuePair("central-rad", Long.toString(centralPoint.getRadius())));
		String query = URLEncodedUtils.format(params, "utf-8");
		String url = serverRoot + "/markers?" + query;
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
		try
		{
			HttpResponse response = client.execute(getRequest);
				Log.i(TAG, "response");
			if (response.getStatusLine().getStatusCode() != 200) throw new ServerException(errorMessage);
				Log.i(TAG, "check if response is code 200");
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				Log.i(TAG, "buffered reader");
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null)
			{
				sb.append(output);
			}
				Log.i(TAG, "lines");
				Log.i(TAG, sb.toString());
			try
			{
				List<Marker> list = new ArrayList<Marker>();
				JSONArray array = new JSONArray(sb.toString());
				if (array.isNull(0))
				{
						Log.i(TAG, "no markers");
					return list;
				}
				for (int i=0; i < array.length(); i++)
				{
					JSONObject obj = array.getJSONObject(i);
					int id = obj.getInt("id");
					int latitude = obj.getInt("latitude");
					int longitude = obj.getInt("longitude");
					
					String desc = obj.getString("desc");
					Marker m = new Marker(id, new GeoPoint(latitude, longitude), "Marker", desc);
						Log.i(TAG, m.toString());
					list.add(m);
				}
					Log.i(TAG, "ready");
					Log.i(TAG, String.valueOf(list.size()));
					Log.i(TAG, list.toString());
				return list;
			}
			catch (JSONException ex)
			{
				throw new ServerException(ex.getMessage());
			}
		} 
		catch (ClientProtocolException e)
		{
			throw new ServerException(e.getMessage());
		}
		catch (IOException e)
		{
			throw new ServerException(e.getMessage());
		}
		catch (Exception ex)
		{
			throw new ServerException(ex.getMessage());
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

	@Override
	public int deleteMarker(Marker marker) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		String url = serverRoot + "/markers/" + marker.getId() + "?_method=DELETE";
		HttpPost post = new HttpPost(url);
		try
		{
			HttpResponse response = client.execute(post);
			return response.getStatusLine().getStatusCode();
		}
		catch(ClientProtocolException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		catch (IOException ex) {
			throw new ServerException(ex.getMessage());
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

	@Override
	public int editMarker(Marker marker, String description) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		String url = serverRoot + "/markers/" + marker.getId() + "?_method=PUT";
		HttpPost post = new HttpPost(url);
		
		try
		{
			JSONObject m = new JSONObject();
			m.put("desc", marker.getMessage());
			
			StringEntity params = new StringEntity(m.toString());
			post.addHeader("content-type", "application/json");
			post.setEntity(params);
			
			HttpResponse response = client.execute(post);
			
			return response.getStatusLine().getStatusCode();
		}
		catch(ClientProtocolException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		catch(IOException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		catch(JSONException ex)
		{
			throw new ServerException(ex.getMessage());
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

}
