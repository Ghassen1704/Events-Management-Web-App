package org.munic.web;




import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Messages_adminController {
	

	
	 @Autowired HttpSession session;
	
	
	//Message page
	@GetMapping("bo/discussion") 
	public String documents(Model model) {

		return "bo/discussion";
	}
	
	

}
