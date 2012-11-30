package com.epam.pf.trauma.backend;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.epam.pf.trauma.backend.service.MarkerService;
import com.epam.pf.trauma.backend.service.domain.CentralPoint;
import com.epam.pf.trauma.backend.service.domain.Marker;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Inject
	private MarkerService markerService;
	/**
	 * Handles the requests on /markers                           
	 *
	 *    
	 * 
	 *
	 * @param  Central point's coordinates and a radius           
	 * @return Collection of markers
	 */
	@RequestMapping(value = "/markers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody
	public Collection<Marker> getMarkers(@RequestParam(value = "central-lan", required=false, defaultValue="0") float centrallan, @RequestParam(value = "central-lng" ,required=false,defaultValue="0") float centrallng,
			@RequestParam(value = "central-rad", required=false,defaultValue="999999999") float centralrad){
		if(centrallan==0&&centrallan==0&&centralrad==999999999) return markerService.getMarkers();
		else
		return markerService.getMarkers(new CentralPoint(centrallan, centrallng, centralrad));
	}
	/**
	 * Handles the delete requests on /markers/{id of a marker}                           
	 *
	 *    
	 * 
	 *
	 *          
	 * 
	 */
	@RequestMapping(value = "/markers/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody
	public void deleteMarker(@PathVariable("id") int id) {
		markerService.deleteMarker(id);
	}
	/**
	 * Handles the edit requests on /markers/{id of a marker}                            
	 *
	 *    
	 * 
	 *
	 * @param  Description string           
	 * @return The edited marker
	 */
	@RequestMapping(value = "/markers/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@ResponseStatus( HttpStatus.OK )
	@ResponseBody
	public Marker editMarker(@PathVariable("id") int id, @RequestBody String desc) {
		return markerService.editMarker(id, desc);
	}
	/**
	 * Handles the  add requests on /markers                            
	 *
	 *    
	 * 
	 *
	 * @param  The markers attributes           
	 * @return The marker
	 */
	@RequestMapping(value = "/markers", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody
	public Marker addMarker(@RequestBody Marker marker) {
		LOGGER.debug("Marker: {}", marker);
		return markerService.addMarker(marker);
	}
}
