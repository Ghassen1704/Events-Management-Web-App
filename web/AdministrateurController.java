package org.munic.web;


import java.text.ParseException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.munic.dao.Admin_consultRepository;
import org.munic.dao.AdministrateurRepository;

import org.munic.dao.RapportRepository;
import org.munic.dao.RoleRepository;
import org.munic.entities.Admin_consult;
import org.munic.entities.Administrateur;
import org.munic.entities.Rapport;
import org.munic.entities.Role;
import org.munic.service.FormatDate;
import org.munic.service.PhotoUpload;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AdministrateurController {
	
	@Autowired
	private AdministrateurRepository administrateurRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Admin_consultRepository admin_consultRepository;
	
	@Autowired
	private RapportRepository rapportRepository;
	

	
	
	 @Autowired HttpSession session;
	 


		//Get login page and Redirect admin to home if user already connected 
		@GetMapping(value={"/", "bo/login"}) 
		public String login() throws ParseException {
			if (session.getAttribute("adminObject")!=null) {
				return "redirect:/bo";
			}
			return "/bo/login";
		}
		
		//Get 403 page
				@GetMapping("bo/403") 
				public String notAutorized() {
					return "bo/403";
				}
				
	 
	 
	 
	//Get index back office
			@GetMapping("bo/") 
			public String indexPage(Model model) throws ParseException {
				 Administrateur user = (Administrateur) session.getAttribute("adminObject");
				 Long superId = user.getId();
				 List<Administrateur> administrateurs = administrateurRepository.findAdminsByAcces(superId);
				 model.addAttribute("user", user);
				 model.addAttribute("administrateurs", administrateurs);
				return "bo/index";
			}
	
	
		
	//Get administrateur page
		@GetMapping("bo/utilisateur") 
		public String administrateur(Model model) throws ParseException {
			 List<Administrateur> administrateurs = administrateurRepository.findAll(Sort.by(Sort.Direction.DESC, "Id"));
			 Administrateur user = (Administrateur) session.getAttribute("adminObject");
			 model.addAttribute("user", user);
			 model.addAttribute("administrateurs", administrateurs);
			return "bo/administrateur";
		}
		
		
		//add Admin
		@PostMapping("/addAdmin")
		  public String addAdmin(Model model,
				  HttpServletRequest request,
				  @RequestParam("nom_fr") String nom_fr,				 
				  @RequestParam("prenom_fr") String prenom_fr,
				  @RequestParam("role") Long role,
				  @RequestParam("username") String username,
				  @RequestParam("email") String email,
				  @RequestParam("password_confirmation") String password_confirmation,
				  
				  final RedirectAttributes redirectAttributes) throws ParseException {
		
			
			if ( administrateurRepository.findByUsernameOrEmail(username) != null   ) {
				redirectAttributes.addFlashAttribute("messageError", "Le nom d'utilisateur "+username+" existe déja !!");
				  return "redirect:bo/utilisateur";
				}else if (administrateurRepository.findByUsernameOrEmail(email) != null) {
					redirectAttributes.addFlashAttribute("messageError", "L'adresse Email "+email+" existe déja !!");
					  return "redirect:bo/utilisateur";
				}
			
			
			Long id=null;
			String photo ="";
			boolean isactive=true;
			Role finalRole = roleRepository.getOne(role); 
		
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
			String encryptedPassword = encoder.encode(password_confirmation);
				
				
				  
			  //Save admin
			  Administrateur a = new Administrateur(id, nom_fr, prenom_fr, email,photo,isactive, username, encryptedPassword, finalRole);
			  administrateurRepository.save(a);
			 
			  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
			  return "redirect:bo/utilisateur";
		  }	
	
		//Delete admin 
		@GetMapping("/deleteAdmin") 
		public String delete (Long id, final RedirectAttributes redirectAttributes) {
			administrateurRepository.deleteById(id);
			 redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
			return "redirect:bo/utilisateur";
			
		}
		
		
		//edit Admin
				@PostMapping("/editAdmin")
				  public String editAdmin(Model model,
						  HttpServletRequest request,
						  @RequestParam("id") Long id,
						  @RequestParam("nom_fr") String nom_fr,
						  @RequestParam("prenom_fr") String prenom_fr,
						  @RequestParam("role") Long role,
						  @RequestParam("username") String username,
						  @RequestParam("email") String email,
						  @RequestParam("password") String password,
						  @RequestParam("old_pass") String old_pass,
						  @RequestParam(name = "isactive",required = false) String isactive,
						 
						  
						  final RedirectAttributes redirectAttributes) throws ParseException {
				
					
					if ( administrateurRepository.findByUsernameOrEmailExeptId(username,id) != null   ) {
						redirectAttributes.addFlashAttribute("messageError", "Le nom d'utilisateur "+username+" existe déja !!");
						  return "redirect:bo/utilisateur";
						}else if (administrateurRepository.findByUsernameOrEmailExeptId(email,id) != null) {
							redirectAttributes.addFlashAttribute("messageError", "L'adresse Email "+email+" existe déja !!");
							  return "redirect:bo/utilisateur";
						}
					
					boolean active = false;
					if (isactive != null){
						
						active = true;
					}
					
					
					Role finalRole = roleRepository.getOne(role); 
				
					String updated_pass="";
					if (password==null) {
						updated_pass=old_pass;
					}else {
						BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
						String encryptedPassword = encoder.encode(password);
						updated_pass=encryptedPassword;
					}
						  
					  //Edit admin
					 Administrateur a = new Administrateur(id, nom_fr, prenom_fr, email,active, username, updated_pass, finalRole);

					  administrateurRepository.saveAndFlush(a);
					 
					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/utilisateur";
				  }	
				
				
				
				//Get Profile page
				@GetMapping("bo/profile") 
				public String profile(Model model) throws ParseException {
					Administrateur admin =new Administrateur();
					admin = (Administrateur) session.getAttribute("adminObject");
					Administrateur user=administrateurRepository.getOne(admin.getId());
					
					 model.addAttribute("user", user);
					
					return "bo/profile";
				}	
				
				
				
				
				//edit Profile
				@PostMapping("/editProfile")
				  public String editProfile(Model model,
						  HttpServletRequest request,
						  @RequestParam("id") Long id,
						  @RequestParam("actual_photo") String actual_photo,
						  @RequestParam("photo") MultipartFile photo,
						  @RequestParam("nom_fr") String nom_fr,					
						  @RequestParam("prenom_fr") String prenom_fr,
						  @RequestParam("email") String email,
						  final RedirectAttributes redirectAttributes) throws ParseException {
					 
					
					
					if (administrateurRepository.findByUsernameOrEmailExeptId(email,id) != null) {
							redirectAttributes.addFlashAttribute("messageError", "L'adresse Email "+email+" existe déja !!");
							  return "redirect:bo/profile";
						}
					
					 String uploadDirectory =  request.getServletContext().getRealPath("uploads");
					 String updated_photo="";
					 
					 if (!photo.isEmpty()) {
						   //Upload and comrpess file and Get new name of file 
						   updated_photo= PhotoUpload.uploadFile(photo,uploadDirectory, "administrateur");
						 }else {
							 updated_photo= actual_photo ;
						 }
					
					
					
					 
					  //Edit profile
					  administrateurRepository.editProfile(id, nom_fr, prenom_fr, email,updated_photo);
					 
					  
				
					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/profile";
				  }	
				
				
				
				
				
				//edit password
				@PostMapping("/editProfileMdp")
				  public String editProfileMdp(Model model,
						  HttpServletRequest request,
						  @RequestParam("iduser") Long iduser,
						  @RequestParam("password") String password,
						  final RedirectAttributes redirectAttributes) throws ParseException {

				
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
					String encryptedPassword = encoder.encode(password);
						  
					  //EDIT password

					//Edit Password
					  administrateurRepository.editPassword(iduser, encryptedPassword);
					
					
					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/profile";
				  }	
				
				
				
				//edit username
				@PostMapping("/editProfileUsername")
				  public String editProfileUsername(Model model,
						  HttpServletRequest request,
						  @RequestParam("iduser") Long iduser,
						  @RequestParam("username") String username,
						  final RedirectAttributes redirectAttributes) throws ParseException {
					if ( administrateurRepository.findByUsernameOrEmailExeptId(username,iduser) != null   ) {
						redirectAttributes.addFlashAttribute("messageError", "Le nom d'utilisateur "+username+" existe déja !!");
						  return "redirect:bo/profile";
					}
 
				
					
					  //EDIT Username
					  administrateurRepository.editUsername(iduser,username);


					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/logout";
				  }	
				
				
				
				//Associer Consult
				@PostMapping("/addAdminConsult")
				  public String addAdminConsult( HttpServletRequest request,
						  @RequestParam("idSuperviseur") Long idSuperviseur,
						  @RequestParam("idTarget") Long[] idTarget,
						  final RedirectAttributes redirectAttributes) throws ParseException {
					
					
			       
					Long id=null;
					 Administrateur superviseur = administrateurRepository.getOne(idSuperviseur);
					for ( Long idtarget : idTarget ) {
						Administrateur target = administrateurRepository.getOne(idtarget);
						
						 Admin_consult ac = new Admin_consult(id, superviseur, target);
						 admin_consultRepository.save(ac);
						
				    }

					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/utilisateur";
				  }
				
				//Dissocier Consult
				@PostMapping("/deleteAdminConsult")
				  public String deleteAdminConsult( HttpServletRequest request,
						  @RequestParam("idTarget") Long[] idTarget,
						  final RedirectAttributes redirectAttributes) throws ParseException {

					for ( Long idtarget : idTarget ) {
						admin_consultRepository.deleteById(idtarget);
						
				    }

					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/utilisateur";
				  }
				
				
				
				//Get rapport page
				@GetMapping("bo/rapport") 
				public String rapport(Model model) throws ParseException {
					
					Administrateur user = (Administrateur)session.getAttribute("adminObject");
					Long userId = user.getId();
					 
					 List<Rapport> rapports = rapportRepository.findByIdUser(userId);
					
					 model.addAttribute("rapports", rapports);
					 
					return "bo/rapport";
				}
				
				
				// add Journal
				@PostMapping("/addJournal")
				  public String addJournal(Model model,
						  @RequestParam("date") String date,
						  @RequestParam("text") String text,	
						  @RequestParam("id") Long id,	
						  final RedirectAttributes redirectAttributes) throws ParseException {
					
					Administrateur user = (Administrateur)session.getAttribute("adminObject");
					Long userId = user.getId();
					 List<Rapport> rapports = rapportRepository.findByIdUser(userId);
					
					
					
					
						 //Change Date format
						 java.sql.Date dateFormatted = FormatDate.ConvertDate(date);
						 
						
						
						 if(id==0) {
							 id=null;
							 for( Rapport rapport : rapports) {

								 if  (rapport.getDate_fd().equals(dateFormatted)){
									 redirectAttributes.addFlashAttribute("messageError", "La date de ce rapport existe déjà");
									  return "redirect:bo/rapport"; 
								 }
							 }	 
						 
						 }
						 
						 
						
						 
						  
					  //Save rapport
					  Rapport r = new Rapport(id, dateFormatted, text, user);
					  rapportRepository.save(r);
					 
					  redirectAttributes.addFlashAttribute("message", "Opération effectuée avec succès");
					  return "redirect:bo/rapport";
				  }
				
				
	
}
