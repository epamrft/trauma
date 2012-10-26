package com.epam.pf.trauma.backend;

import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/markers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Collection<Marker> markers() {

		Collection<Marker> markers = new LinkedList<Marker>();

		for (int i = 0; i < 30; i++) {
			markers.add(new Marker("Marker" + String.valueOf(i)));
		}

		return markers;
	}
}
