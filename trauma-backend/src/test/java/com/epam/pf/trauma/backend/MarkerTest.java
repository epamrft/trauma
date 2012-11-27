package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;

import java.io.IOException;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MvcResult;
import com.epam.pf.trauma.backend.service.domain.Marker;

public class MarkerTest extends TestBase {
	@Before
	public void ownSetup() throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		addMarker(new Marker(42, 42, "test1"));
		addMarker(new Marker(-42, 42, "test2"));
		addMarker(new Marker(42, -42, "test3"));
		addMarker(new Marker(-42, -42, "test4"));
		addMarker(new Marker(1.123123f, 0.32423f, "test5"));

	}

	@Test
	public void testGetMarkersWithoutCentral() throws Exception {
		Marker[] markers = getMarkersWithOutCentral();
		Assert.assertEquals(5, markers.length);
	}

	@Test
	public void testGetMarkersWithCentral() throws Exception {
		Marker[] markers = getMarkersWithCentral(40, 40, 20);
		Assert.assertEquals(1, markers.length);

	}

	@Test
	public void testDeleteMarkerWhenMarkerIsValid() throws Exception {

		MvcResult deleteresult = this.mockMvc.perform(
				delete("/markers/1").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();

		int saved = deleteresult.getResponse().getStatus();
		Assert.assertEquals(200, saved);

		Marker[] markers = getMarkersWithOutCentral();
		Assert.assertEquals(4, markers.length);
	}

}
