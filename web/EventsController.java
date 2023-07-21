package org.munic.web;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EventsController {

	
	
	//Get page event
	@GetMapping("bo/events") 
	public String events() {

		return "bo/events";
	}
	
	
	

	

}
