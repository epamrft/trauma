package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.epam.pf.trauma.backend.config.AppConfig;
import com.epam.pf.trauma.backend.config.WebMvcConfig;
import com.epam.pf.trauma.backend.service.domain.Marker;

public class MarkerTest {

	private MockMvc mockMvc;

	private ObjectWriter writer;

	private ObjectReader arrayReader;

	private void addMarker(Marker marker) throws Exception {
		this.mockMvc
				.perform(
						post("/markers")
								.body(this.writer.writeValueAsString(marker)
										.getBytes())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}

	private Marker[] getMarkersWithCentral(float lan, float lng, float rad)
			throws Exception {

		MvcResult result = this.mockMvc.perform(
				get(
						"/markers?central-lan=" + lan + "&central-lng=" + lng
								+ "&central-rad=" + rad).contentType(
						MediaType.APPLICATION_JSON).accept(
						MediaType.APPLICATION_JSON)).andReturn();
		String listOfMarkers = result.getResponse().getContentAsString();

		return (Marker[]) arrayReader.readValue(listOfMarkers);
	}

	private Marker[] getMarkersWithOutCentral() throws Exception {

		MvcResult result = this.mockMvc.perform(
				get("/markers").contentType(MediaType.APPLICATION_JSON).accept(
						MediaType.APPLICATION_JSON)).andReturn();
		String listOfMarkers = result.getResponse().getContentAsString();

		return (Marker[]) arrayReader.readValue(listOfMarkers);
	}

	@Before
	public void setup() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		this.writer = objectMapper.typedWriter(Marker.class);
		this.arrayReader = objectMapper.reader(Marker[].class);

		this.mockMvc = MockMvcBuilders.annotationConfigSetup(AppConfig.class,
				WebMvcConfig.class).build();

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
