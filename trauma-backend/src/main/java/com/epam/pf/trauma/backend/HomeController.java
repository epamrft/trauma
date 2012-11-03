package com.epam.pf.trauma.backend;

import java.util.Collection;
import java.util.LinkedList;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	private MarkerService markerService;
	
	
	
	@RequestMapping(value = "/markers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Collection<Marker> getMarkers(
			@RequestParam(value="central-lan") float centrallan,
			@RequestParam(value="central-lng") float centrallng,
			@RequestParam(value="central-rad") float centralrad) {		
		
		return markerService.getMarkers(new CentralPoint(centrallan, centrallng, centralrad));
	}

	@RequestMapping(value = "/markers/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public int deleteMarker(@PathVariable("id") int id) {

		
			markerService.deleteMarker(id);
			return 200;
	}
	@RequestMapping(value = "/markers/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Marker deleteMarker(@PathVariable("id") int id,@RequestBody String desc) {

		
			return  markerService.editMarker(id, desc);
	}

}
