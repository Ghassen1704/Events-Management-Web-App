package org.munic.entities;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Administrateur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	
		private String nom_fr,prenom_fr,photo,password;
		@Column(name = "username", unique = true)
		private String username;
		@Column(name = "email", unique = true)
		private String email;
		
		@Column(columnDefinition="int default 0")
		private int status;

		private boolean isactive ;
	 
		@OneToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "role_id")
		private Role role;
		
		
		 @JsonIgnore
		 @OneToMany( mappedBy = "superviseur", cascade = CascadeType.ALL,orphanRemoval = false)
			private List<Admin_consult> admin_superviseur = new ArrayList<>();
			
		 @JsonIgnore
	     @OneToMany( mappedBy = "target", cascade = CascadeType.ALL,orphanRemoval = false)
			private List<Admin_consult> admin_target = new ArrayList<>();
		
		 @JsonIgnore
		 @OneToMany( mappedBy = "administrateur", cascade = CascadeType.ALL,orphanRemoval = false)
		 private List<Events> admin_events = new ArrayList<>();
		
		 
		 @JsonIgnore
		 @OneToMany( mappedBy = "emetteur", cascade = CascadeType.ALL,orphanRemoval = false)
		 private List<Messages_admin> admin_messages_emetteur = new ArrayList<>();
		 @JsonIgnore
		 @OneToMany( mappedBy = "recepteur", cascade = CascadeType.ALL,orphanRemoval = false)
		 private List<Messages_admin> admin_messages_recepteur = new ArrayList<>();
		 
		 
		 @JsonIgnore
		 @OneToMany( mappedBy = "emetteur_group", cascade = CascadeType.ALL,orphanRemoval = false)
		 private List<Messages_admin_groupe> admin_messages_emetteur_group = new ArrayList<>();
	 
		 
		 @JsonIgnore
		 @OneToMany( mappedBy = "utilisateur", cascade = CascadeType.ALL,orphanRemoval = false)
		 private List<Rapport> admin_rapports = new ArrayList<>();
		 
		 
		
	 
		public Long getId() {
			return id;
		}
	 
		public void setId(Long id) {
			this.id = id;
		}

		public String getNom_fr() {
			return nom_fr;
		}

		public void setNom_fr(String nom_fr) {
			this.nom_fr = nom_fr;
		}

	

		public String getPrenom_fr() {
			return prenom_fr;
		}

		public void setPrenom_fr(String prenom_fr) {
			this.prenom_fr = prenom_fr;
		}

		

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public boolean isIsactive() {
			return isactive;
		}

		public void setIsactive(boolean isactive) {
			this.isactive = isactive;
		}
		
		
		


		public List<Admin_consult> getAdmin_superviseur() {
			return admin_superviseur;
		}

		public void setAdmin_superviseur(List<Admin_consult> admin_superviseur) {
			this.admin_superviseur = admin_superviseur;
		}

		public List<Admin_consult> getAdmin_target() {
			return admin_target;
		}

		public void setAdmin_target(List<Admin_consult> admin_target) {
			this.admin_target = admin_target;
		}

		public List<Events> getAdmin_events() {
			return admin_events;
		}

		public void setAdmin_events(List<Events> admin_events) {
			this.admin_events = admin_events;
		}

		
		
		

		public List<Rapport> getAdmin_rapports() {
			return admin_rapports;
		}

		public void setAdmin_rapports(List<Rapport> admin_rapports) {
			this.admin_rapports = admin_rapports;
		}

		public List<Messages_admin> getAdmin_messages_emetteur() {
			return admin_messages_emetteur;
		}

		public void setAdmin_messages_emetteur(List<Messages_admin> admin_messages_emetteur) {
			this.admin_messages_emetteur = admin_messages_emetteur;
		}

		public List<Messages_admin> getAdmin_messages_recepteur() {
			return admin_messages_recepteur;
		}

		public void setAdmin_messages_recepteur(List<Messages_admin> admin_messages_recepteur) {
			this.admin_messages_recepteur = admin_messages_recepteur;
		}
		
		
		
		

		public List<Messages_admin_groupe> getAdmin_messages_emetteur_group() {
			return admin_messages_emetteur_group;
		}

		public void setAdmin_messages_emetteur_group(List<Messages_admin_groupe> admin_messages_emetteur_group) {
			this.admin_messages_emetteur_group = admin_messages_emetteur_group;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public Administrateur() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		//Ajout admin
		public Administrateur(Long id, String nom_fr,String prenom_fr, String email,String photo,boolean isactive, String username, String password, Role role) {
		
			this.id = id;
			this.nom_fr = nom_fr;
			this.prenom_fr = prenom_fr;
			this.email = email;
			this.photo = photo;
			this.isactive = isactive;
			this.username = username;
			this.password = password;
			this.role = role;
			
		}
		
		//Edit admin
				public Administrateur(Long id, String nom_fr,String prenom_fr,String email,boolean isactive, String username, String password, Role role) {
				
					this.id = id;
					this.nom_fr = nom_fr;
					this.prenom_fr = prenom_fr;
					this.email = email;
					this.isactive = isactive;
					this.username = username;
					this.password = password;
					this.role = role;
					
				}
		
		
		
	

	}