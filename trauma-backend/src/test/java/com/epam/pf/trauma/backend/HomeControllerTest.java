package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;

import java.io.IOException;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.epam.pf.trauma.backend.config.AppConfig;
import com.epam.pf.trauma.backend.config.PersistenceJPAConfig;
import com.epam.pf.trauma.backend.config.WebMvcConfig;
import com.epam.pf.trauma.backend.service.domain.Marker;
@ActiveProfiles(profiles="dev")
public class HomeControllerTest {

	private MockMvc mockMvc;

	private ObjectWriter writer;

	private ObjectReader reader;

	private ObjectReader arrayReader;

	private MvcResult result;

	private Marker newMarker;

	private Marker savedMarker;

	@Before
	public void setup() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		System.setProperty("spring.profiles.active", "dev");
		ObjectMapper objectMapper = new ObjectMapper();
		this.writer = objectMapper.typedWriter(Marker.class);
		this.reader = objectMapper.reader(Marker.class);
		this.arrayReader = objectMapper.reader(Marker[].class);

		this.mockMvc = MockMvcBuilders.annotationConfigSetup(AppConfig.class, WebMvcConfig.class, PersistenceJPAConfig.class).build();

		this.newMarker = new Marker(42, 42, "test");
		this.result = this.addMarker(this.newMarker);
		this.savedMarker = this.reader.readValue(this.result.getResponse().getContentAsString());
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

	// private
	private MvcResult addMarker(Marker marker) throws JsonGenerationException, JsonMappingException, IOException, Exception {
		return this.mockMvc.perform(
				MockMvcRequestBuilders.post("/markers").body(this.writer.writeValueAsString(marker).getBytes()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

	}

	private Marker[] getMarkersWithOutCentral() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/markers").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		String listOfMarkers = result.getResponse().getContentAsString();

		return (Marker[]) arrayReader.readValue(listOfMarkers);
	}
}