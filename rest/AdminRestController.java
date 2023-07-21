package org.munic.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.munic.dao.AdministrateurRepository;
import org.munic.dao.EventsRepository;
import org.munic.dao.Messages_adminRepository;
import org.munic.dao.Messages_admin_groupeRepository;
import org.munic.dao.RapportRepository;
import org.munic.entities.Administrateur;
import org.munic.entities.Events;
import org.munic.entities.Messages_admin;
import org.munic.entities.Messages_admin_groupe;
import org.munic.entities.Rapport;
import org.munic.service.PdfUpload;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;





@RestController
@RequestMapping("/admin/api/rest")
public class AdminRestController{
	
	
	@Autowired
	private Messages_adminRepository messages_adminRepository;
	@Autowired
	private Messages_admin_groupeRepository messages_admin_groupeRepository;
	@Autowired
	private AdministrateurRepository administrateurRepository;
	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private RapportRepository rapportRepository;
	
	 @Autowired HttpSession session;
	




//getCurrentAdmin 
@RequestMapping(value = "getCurrentAdmin")	
public Administrateur getCurrentAdmin() { 
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	String adminId = user.getUsername();
	Administrateur admin = administrateurRepository.findByUsername(adminId);
	return admin;
	
}

@RequestMapping(value = "getAllAdminExeptMe")	
public List<Administrateur>  getAllAdminExeptMe() { 
	List<Administrateur> admins;
	
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	Long adminId = user.getId();
	
	admins = administrateurRepository.findAllActiveAdminExeptMe(adminId);

	

	return admins;
	
}


//change admin status
@PostMapping("/changeStatus/{status}")
  public void editProfile(@PathVariable int status) throws ParseException {
	 
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	Long adminId = user.getId();
	  //Edit status
	  administrateurRepository.editStatus(adminId, status);
  }	

//show Convesation by id
@RequestMapping("/showConversation/{recepteur}")
public List<Messages_admin> showConversation(@PathVariable Long recepteur) throws ParseException {
	List<Messages_admin> messages_admin;
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	Long emetteur = user.getId();
	   
	messages_admin = messages_adminRepository.getMessageByid(emetteur, recepteur);
	  
	  return messages_admin;
}	



//get target admin
@RequestMapping("/getTargetAdmin/{target}")
public Administrateur getTargetAdmin(@PathVariable Long target) throws ParseException {
Administrateur admin = administrateurRepository.getOne(target);
	return admin;
}	


// Send message



@RequestMapping(value = "/sendMsgAdmin", method={RequestMethod.POST, RequestMethod.GET})
public void sendMsgAdmin(HttpServletRequest request,
			  @RequestParam("fichier") MultipartFile fichier,
			  @RequestParam("message") String message,
			  @RequestParam("recepteur") Long recepteur
			 ) throws ParseException {
		Long id=null;
		 String updated_fichier="";
		 String updated_extention="";
		String uploadDirectory =  request.getServletContext().getRealPath("uploads");
		Administrateur emetteur = (Administrateur)session.getAttribute("adminObject");
		 Administrateur recept = administrateurRepository.getOne(recepteur);
		
		// Get extention of file
		 if (!fichier.isEmpty()) {
			
				//Upload file and Get new name of file 
				   updated_fichier= PdfUpload.uploadFile(fichier,uploadDirectory, "message");
				 
				// Get extention of file
					int i = fichier.getOriginalFilename().lastIndexOf('.');
					if (i >= 0) {
						updated_extention = fichier.getOriginalFilename().substring(i + 1);
					}
				 }
   
		  //Save message
		  Messages_admin ma = new Messages_admin(id, message, updated_fichier, updated_extention, emetteur, recept);
		  messages_adminRepository.save(ma);
		 
		  
	  }



// get nombre message non lue
@RequestMapping("/getNbrMsgNonLue/{target}")
public int NombreMsgNonLue(@PathVariable Long target) throws ParseException {
	 
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	Long adminId = user.getId();
	  
	  int count = messages_adminRepository.countNbrMsgNonLue(adminId, target);
	return count;
	  
}


//set vue true
@PostMapping("/setVueTrue/{target}")
public void setVueTrue(@PathVariable Long target) throws ParseException {
	 
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	Long adminId = user.getId();
	 
		messages_adminRepository.setVueTrue(adminId, target);
}



//get nombre message all
@RequestMapping("/getNbrAllMsg")
public int NombreMsgNonLue() throws ParseException {
	 
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	Long adminId = user.getId();
	  
	  int count = messages_adminRepository.countNbrMsgAll(adminId);
	return count;
	  
}



//show Convesation groupe
@RequestMapping("/showConversationGroupe")
public List<Messages_admin_groupe> showConversationGroupe() throws ParseException {
	List<Messages_admin_groupe> messages_admin_groupe;
	
	messages_admin_groupe = messages_admin_groupeRepository.findAll();
	  
	  return messages_admin_groupe;
}	

//send Message groupe
@RequestMapping(value = "/sendMsgAdminGroupe", method={RequestMethod.POST, RequestMethod.GET})
public void sendMsgAdminGroupe(HttpServletRequest request,
			  @RequestParam("fichier") MultipartFile fichier,
			  @RequestParam("message") String message
			  
			 ) throws ParseException {
		Long id=null;
		 String updated_fichier="";
		 String updated_extention="";
		String uploadDirectory =  request.getServletContext().getRealPath("uploads");
		Administrateur emetteur = (Administrateur)session.getAttribute("adminObject");
		
		
		// Get extention of file
		 if (!fichier.isEmpty()) {
			
				//Upload file and Get new name of file 
				   updated_fichier= PdfUpload.uploadFile(fichier,uploadDirectory, "message");
				 
				// Get extention of file
					int i = fichier.getOriginalFilename().lastIndexOf('.');
					if (i >= 0) {
						updated_extention = fichier.getOriginalFilename().substring(i + 1);
					}
				 }
   
		  //Save message
		  Messages_admin_groupe mag = new Messages_admin_groupe(id, message, updated_fichier, updated_extention, emetteur);
		  messages_admin_groupeRepository.save(mag);
		 
		  
	  }



//addEvent
@RequestMapping(value = "/addEvent", method={RequestMethod.POST, RequestMethod.GET})
public Long addEvent(HttpServletRequest request) throws ParseException, org.json.simple.parser.ParseException {
		Long id=null;
		
		String jsonObjectString = request.getParameter("jsonObject");
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonObjectString);
		
		
		String title = (String) jsonObject.get("title");
		String color = (String) jsonObject.get("color");
		String lieu = (String) jsonObject.get("lieu");
		String organismes = (String) jsonObject.get("organismes");
		String personnes = (String) jsonObject.get("personnes");
		String description = (String) jsonObject.get("description");
		String observation = (String) jsonObject.get("observation");
		String startt = (String) jsonObject.get("start");
		String endd = (String) jsonObject.get("end");
		boolean allday = (Boolean) jsonObject.get("allDay");
		
		
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		
		java.util.Date date_start =  inputFormat.parse(startt);
		String start = outputFormat.format(date_start);

		java.util.Date date_end =  inputFormat.parse(endd);
		String end = outputFormat.format(date_end);


	
		Administrateur user = (Administrateur)session.getAttribute("adminObject");
		 
		
		
   
		  //Save Event
		  Events ev = new Events(id, title, color, lieu, organismes, personnes, description, observation, start, end, allday, user);
		 
		 eventsRepository.save(ev);
		 
		 Long idReturn = ev.getId();
		 
		 return idReturn;
		  
	  }




//editEvent
@RequestMapping(value = "/editEvent", method={RequestMethod.POST, RequestMethod.GET})
public void editEvent(HttpServletRequest request) throws ParseException, org.json.simple.parser.ParseException {
		
		
		String jsonObjectString = request.getParameter("jsonObject");
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonObjectString);
		
		
		String title = (String) jsonObject.get("title");
		String color = (String) jsonObject.get("color");
		String lieu = (String) jsonObject.get("lieu");
		String organismes = (String) jsonObject.get("organismes");
		String personnes = (String) jsonObject.get("personnes");
		String description = (String) jsonObject.get("description");
		String observation = (String) jsonObject.get("observation");
		String startt = (String) jsonObject.get("start");
		String endd = (String) jsonObject.get("end");
		boolean allday = (Boolean) jsonObject.get("allDay");
		Long id = (Long) jsonObject.get("id");
		
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		
		java.util.Date date_start =  inputFormat.parse(startt);
		String start = outputFormat.format(date_start);

		java.util.Date date_end =  inputFormat.parse(endd);
		String end = outputFormat.format(date_end);


	
		Administrateur user = (Administrateur)session.getAttribute("adminObject");
		 
		
		
 
		  //Save Event
		  Events ev = new Events(id, title, color, lieu, organismes, personnes, description, observation, start, end, allday, user);
		 
		 eventsRepository.save(ev);
		 
		 
		 
		 
		  
	  }



//removeEvent

@RequestMapping(value = "/removeEvent/{id}", method={RequestMethod.POST, RequestMethod.GET})	
public void  removeEvent(@PathVariable Long id) throws ParseException {		

		 eventsRepository.deleteById(id);
  
	  }



//get user connected events
@RequestMapping(value = "/getAllAdminEvents")	
public List<Events>  getAllAdminEvents() { 
	
	Administrateur user = (Administrateur)session.getAttribute("adminObject");
	
	Long id_admin = user.getId();
	
	List<Events> listEvents = eventsRepository.getAllAdminEvents(id_admin);

	return listEvents;
	
}


//get user connected events
@RequestMapping(value = "/getAllAdminEventsById/{target}")	
public List<Events>  getAllAdminEventsById(@PathVariable Long target) throws ParseException {

	List<Events> listEvents = eventsRepository.getAllAdminEvents(target);

	return listEvents;
	
}



//getTargetAdminRapports
@RequestMapping("/getTargetAdminRapports/{target}")
public List<Rapport> getTargetAdminRapports(@PathVariable Long target) throws ParseException {
	 List<Rapport> rapports = rapportRepository.findByIdUser(target);
	return rapports;
}	


	
}

