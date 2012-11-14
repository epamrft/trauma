package com.epam.pf.trauma.backend;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.epam.pf.trauma.backend.config.AppConfig;
import com.epam.pf.trauma.backend.config.WebMvcConfig;
import com.epam.pf.trauma.backend.service.domain.Marker;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;

public class MarkerTest {

	private MockMvc mockMvc;

	private ObjectWriter writer;

	private ObjectReader reader;

	@Before
	public void setup() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		this.writer = objectMapper.typedWriter(Marker.class);
		this.reader = objectMapper.reader(Marker.class);

		this.mockMvc = MockMvcBuilders.annotationConfigSetup(AppConfig.class,
				WebMvcConfig.class).build();
	
		Marker testMarker = new Marker(42, 42, "test1");
		Marker testMarker2 = new Marker(-42, 42, "test2");
		Marker testMarker3 = new Marker(42, -42, "test3");
		Marker testMarker4 = new Marker(-42, -42, "test4");
		Marker testMarker5 = new Marker(1.123123f, 0.32423f, "test5");
		
		this.mockMvc.perform(
				post("/markers")
						.body(this.writer.writeValueAsString(testMarker)
								.getBytes())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();
	
	this.mockMvc.perform(
			post("/markers")
					.body(this.writer.writeValueAsString(testMarker2)
							.getBytes())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andReturn();
	
	this.mockMvc.perform(
			post("/markers")
					.body(this.writer.writeValueAsString(testMarker3)
							.getBytes())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andReturn();
	this.mockMvc.perform(
			post("/markers")
					.body(this.writer.writeValueAsString(testMarker4)
							.getBytes())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andReturn();
	this.mockMvc.perform(
			post("/markers")
					.body(this.writer.writeValueAsString(testMarker5)
							.getBytes())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andReturn();
	
	
	
	
	
	
}

	@Test
	public void testGetMarkers() throws Exception {


		
		
		

		MvcResult result1 = this.mockMvc.perform(
				get("/markers")
						
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andReturn();
		String listOfMarkers = result1.getResponse().getContentAsString();
		listOfMarkers=listOfMarkers.substring(1, listOfMarkers.length()-1);
		List<Marker> markers = new ArrayList<Marker>();
		String markerString[];
		System.out.println(listOfMarkers);
		markerString=listOfMarkers.split(",");
		int i = 0;
		String s="";
		for (String string : markerString) {
			
			i++;if(i==1)s="".concat(string);
			else s=s.concat(","+string);
			if(i==4) i=0;
			System.out.println(s);
			if (i==0) {Marker marker= reader.readValue(string);
			markers.add(marker);}
		}
		//{"longitude":42.0,"latitude":42.0,"id":1,"desc":"test"}
			
			
			
			
			
			
		
		
		Assert.assertEquals(markers.size(),5);

	}


}
