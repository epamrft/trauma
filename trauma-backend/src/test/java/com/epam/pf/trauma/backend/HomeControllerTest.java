package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
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
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;

public class HomeControllerTest {

	private MockMvc mockMvc;

	private ObjectWriter writer;

	private ObjectReader reader;

	@Before
	public void setup() {
		ObjectMapper objectMapper = new ObjectMapper();
		this.writer = objectMapper.typedWriter(Marker.class);
		this.reader = objectMapper.reader(Marker.class);

		this.mockMvc = MockMvcBuilders.annotationConfigSetup(AppConfig.class,
				WebMvcConfig.class).build();
	}

	@Test
	public void testAddMarkerWhenMarkerIsValid() throws Exception {

		Marker newMarker = new Marker(42, 42, "test");

		// @formatter:off
		MvcResult result = this.mockMvc.perform(
				put("/markers")
						.body(this.writer.writeValueAsString(newMarker)
								.getBytes())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();
		// @formatter:on
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

		Marker newMarker = new Marker(42, 42, "test");

		MvcResult result = this.mockMvc.perform(
				put("/markers")
						.body(this.writer.writeValueAsString(newMarker)
								.getBytes())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();
		String desc = "edittest";
		MvcResult result2 = this.mockMvc.perform(
				post("/markers/1")
						.body(this.writer.writeValueAsString(desc).getBytes())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();

		Marker savedMarker = reader.readValue(result2.getResponse()
				.getContentAsString());

		Assert.assertNotNull(savedMarker.getId());
		Assert.assertEquals(newMarker.getLongitude(),
				savedMarker.getLongitude());
		Assert.assertEquals(newMarker.getLatitude(), savedMarker.getLatitude());
		Assert.assertEquals(desc, savedMarker.getDesc());
	}

	@Test
	public void testDeleteMarkerWhenMarkerIsValid() throws Exception {

		Marker newMarker = new Marker(42, 42, "test");

		MvcResult result = this.mockMvc.perform(
				put("/markers")
						.body(this.writer.writeValueAsString(newMarker)
								.getBytes())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();

		MvcResult result2 = this.mockMvc.perform(
				delete("/markers/1")
						
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();

		String saved = reader.readValue(result2.getResponse()
				.getContentAsString());

		Assert.assertNotNull(saved);
		Assert.assertEquals(saved, "200");

	}

}