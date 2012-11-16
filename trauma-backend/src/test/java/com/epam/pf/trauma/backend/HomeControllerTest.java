package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;

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
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.epam.pf.trauma.backend.config.AppConfig;
import com.epam.pf.trauma.backend.config.WebMvcConfig;
import com.epam.pf.trauma.backend.service.domain.Marker;

public class HomeControllerTest {

	private MockMvc mockMvc;

	private ObjectWriter writer;

	private ObjectReader reader;
	
	private MvcResult result;

	private Marker newMarker;

	@Before
	public void setup() throws JsonGenerationException, JsonMappingException,
			IOException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		this.writer = objectMapper.typedWriter(Marker.class);
		this.reader = objectMapper.reader(Marker.class);

		this.mockMvc = MockMvcBuilders.annotationConfigSetup(AppConfig.class,
				WebMvcConfig.class).build();

		newMarker = new Marker(42, 42, "test");
		result = addMarker(newMarker);

	}

	@Test
	public void testAddMarkerWhenMarkerIsValid() throws Exception {

		Marker savedMarker = reader.readValue(result.getResponse()
				.getContentAsString());

		Assert.assertNotNull(savedMarker.getId());
		Assert.assertEquals(newMarker.getLongitude(),
				savedMarker.getLongitude());
		Assert.assertEquals(newMarker.getLatitude(), savedMarker.getLatitude());
		Assert.assertEquals(newMarker.getDesc(), savedMarker.getDesc());

	}

	@Test
	public void testEditMarkerWhenMarkerIsValid() throws Exception {

		// edit marker
		String desc = "edittest";
		MvcResult result2 = this.mockMvc.perform(
				put("/markers/1").body(desc.getBytes())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();

		Marker editedMarker = reader.readValue(result2.getResponse()
				.getContentAsString());

		Assert.assertNotNull(editedMarker.getId());
		Assert.assertEquals(newMarker.getLongitude(),
				editedMarker.getLongitude());
		Assert.assertEquals(newMarker.getLatitude(), editedMarker.getLatitude());
		Assert.assertEquals(desc, editedMarker.getDesc());
	}

	

	// private
	private MvcResult addMarker(Marker marker) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {
		return this.mockMvc
				.perform(
						post("/markers")
								.body(this.writer.writeValueAsString(marker)
										.getBytes())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}
}