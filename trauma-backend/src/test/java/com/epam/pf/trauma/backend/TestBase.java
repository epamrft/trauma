package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.epam.pf.trauma.backend.config.AppConfig;
import com.epam.pf.trauma.backend.config.PersistenceJPAConfig;
import com.epam.pf.trauma.backend.config.WebMvcConfig;
import com.epam.pf.trauma.backend.service.domain.Marker;

public class TestBase {

	protected MockMvc mockMvc;

	protected ObjectWriter writer;

	protected ObjectReader reader;
	
	protected ObjectReader arrayReader;

	protected MvcResult result;

	protected Marker newMarker;

	protected Marker savedMarker;

	@Before
	public void setup() throws JsonGenerationException, JsonMappingException,
			IOException, Exception {
		System.setProperty("spring.profiles.active", "dev");
		ObjectMapper objectMapper = new ObjectMapper();
		this.writer = objectMapper.typedWriter(Marker.class);
		this.reader = objectMapper.reader(Marker.class);
		this.arrayReader = objectMapper.reader(Marker[].class);

		this.mockMvc = MockMvcBuilders.annotationConfigSetup(AppConfig.class,
				WebMvcConfig.class, PersistenceJPAConfig.class).build();

		
	}

	protected MvcResult addMarker(Marker marker) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {
		return this.mockMvc
				.perform(
						MockMvcRequestBuilders
								.post("/markers")
								.body(this.writer.writeValueAsString(marker)
										.getBytes())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}

	protected Marker[] getMarkersWithOutCentral() throws Exception {

		MvcResult result = this.mockMvc.perform(
				get("/markers").contentType(MediaType.APPLICATION_JSON).accept(
						MediaType.APPLICATION_JSON)).andReturn();
		String listOfMarkers = result.getResponse().getContentAsString();

		return (Marker[]) arrayReader.readValue(listOfMarkers);
	}

	protected Marker[] getMarkersWithCentral(float lan, float lng, float rad)
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

}
