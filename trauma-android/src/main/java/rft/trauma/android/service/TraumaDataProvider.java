package rft.trauma.android.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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

public class TraumaDataProvider implements IDataProvider
{
	private static String serverRoot = "http://trauma.backend.cloudfoundry.com";
	//private static String errorMessage = "An error occured while communicating with the server";
	
	private static String TAG = "trauma-android";
	
	private final int SUCCESS = 200;
	private final int SUCCESS_ADD = 201;
	
	public TraumaDataProvider()
	{
		
	}
	
	@Override
	public void addMarker(double latitude, double longitude, String desc) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		String url = serverRoot + "/markers";
		HttpPost post = new HttpPost(url);
		
		try
		{
			JSONObject m = new JSONObject();
			m.put("latitude", latitude);
			m.put("longitude", longitude);
			m.put("desc", desc);
			
			StringEntity params = new StringEntity(m.toString());
			post.addHeader("content-type", "application/json");
			post.setEntity(params);
			
			HttpResponse response = client.execute(post);
			
			switch(response.getStatusLine().getStatusCode())
			{
			case SUCCESS:
				break;
			case SUCCESS_ADD:
				break;
			default:
				Log.i(TAG, "status code: "+ Integer.toString(response.getStatusLine().getStatusCode()));
				throw new ServerException("connection error " + response.getStatusLine().getStatusCode());
			}
		}
		catch(ClientProtocolException ex)
		{
			throw new ServerException(ex);
		}
		catch(IOException ex)
		{
			throw new ServerException(ex);
		}
		catch(JSONException ex)
		{
			throw new ServerException(ex);
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
	public JSONArray getAllMarkers() throws ServerException
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
			if (response.getStatusLine().getStatusCode() != SUCCESS)
				throw new ServerException("connection error " + response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null)
			{
				sb.append(output);
			}
			try
			{
				if (response.getStatusLine().getStatusCode() != SUCCESS)
					throw new ServerException("connection error " + response.getStatusLine().getStatusCode());
					Log.i(TAG, sb.toString());
				return new JSONArray(sb.toString());
			}
			catch (JSONException ex)
			{
				throw new ServerException(ex);
			}
		} 
		catch (ClientProtocolException e)
		{
			throw new ServerException(e);
		}
		catch (IOException e)
		{
			throw new ServerException(e);
		}
		catch (Exception ex)
		{
			throw new ServerException(ex);
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
	public JSONArray getMarkers(double centralLongitude, double centralLatitude, double centralRadius) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("central-lan", Double.toString(centralLatitude)));
		params.add(new BasicNameValuePair("central-lng", Double.toString(centralLongitude)));
		params.add(new BasicNameValuePair("central-rad", Double.toString(centralRadius)));
		String query = URLEncodedUtils.format(params, "utf-8");
		String url = serverRoot + "/markers?" + query;
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
		try
		{
			HttpResponse response = client.execute(getRequest);
				Log.i(TAG, "response");
			if (response.getStatusLine().getStatusCode() != SUCCESS)
				throw new ServerException("connection error " + response.getStatusLine().getStatusCode());
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
				return new JSONArray(sb.toString());
			}
			catch (JSONException ex)
			{
				throw new ServerException(ex);
			}
		} 
		catch (ClientProtocolException e)
		{
			throw new ServerException(e);
		}
		catch (IOException e)
		{
			throw new ServerException(e);
		}
		catch (Exception ex)
		{
			throw new ServerException(ex);
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

	@Override
	public void deleteMarker(int markerID) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		String url = serverRoot + "/markers/" + markerID + "?_method=DELETE";
		HttpPost post = new HttpPost(url);
		try
		{
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != SUCCESS)
				throw new ServerException("connection error " + response.getStatusLine().getStatusCode());
		}
		catch(ClientProtocolException ex)
		{
			throw new ServerException(ex);
		}
		catch (IOException ex) {
			throw new ServerException(ex);
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

	@Override
	public void editMarker(int markerID, String description) throws ServerException
	{
		HttpClient client = new DefaultHttpClient();
		String url = serverRoot + "/markers/" + markerID + "?_method=PUT";
		HttpPost post = new HttpPost(url);
		
		try
		{
			StringEntity params = new StringEntity(description);
			//post.addHeader("content-type", "string");
			post.setEntity(params);
			
			HttpResponse response = client.execute(post);
			
			if (response.getStatusLine().getStatusCode() != SUCCESS)
				throw new ServerException("connection error " + response.getStatusLine().getStatusCode());
		}
		catch(ClientProtocolException ex)
		{
			throw new ServerException(ex);
		}
		catch(IOException ex)
		{
			throw new ServerException(ex);
		}
//		catch(JSONException ex)
//		{
//			throw new ServerException(ex);
//		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
	}

}
