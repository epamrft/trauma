package com.epam.pf.trauma.backend;

import java.io.IOException;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import com.epam.pf.trauma.backend.service.domain.Marker;

public class HomeControllerTest extends TestBase {

	@Before
	public void ownSetup() throws JsonGenerationException, JsonMappingException, IOException, Exception{
		
		this.newMarker = new Marker(42, 42, "test");
		this.result = this.addMarker(this.newMarker);
		this.savedMarker = this.reader.readValue(this.result.getResponse()
				.getContentAsString());
		
		
		
	}
	@Test
	public void testGetMarker() throws Exception {
		Marker[] markers = getMarkersWithOutCentral();
		Assert.assertNotNull(markers);
		Assert.assertEquals(1, markers.length);
	}
	
	@Test
	public void testAddMarkerWhenMarkerIsValid() throws Exception {
		Assert.assertNotNull(savedMarker.getId());
		Assert.assertEquals(this.newMarker.getLongitude(), savedMarker.getLongitude());
		Assert.assertEquals(this.newMarker.getLatitude(), savedMarker.getLatitude());
		Assert.assertEquals(this.newMarker.getDesc(), savedMarker.getDesc());
	}

	@Test
	public void testEditMarkerWhenMarkerIsValid() throws Exception {

		// edit marker
		String desc = "edittest";
		String url = String.format("/markers/%d", this.savedMarker.getId());
		MvcResult result2 = this.mockMvc.perform(MockMvcRequestBuilders.put(url).body(desc.getBytes()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		Marker editedMarker = this.reader.readValue(result2.getResponse().getContentAsString());

		Assert.assertNotNull(editedMarker.getId());
		Assert.assertEquals(this.savedMarker.getId(), editedMarker.getId());
		Assert.assertEquals(this.savedMarker.getLongitude(), editedMarker.getLongitude());
		Assert.assertEquals(this.savedMarker.getLatitude(), editedMarker.getLatitude());
		Assert.assertEquals(desc, editedMarker.getDesc());
	}

	
}