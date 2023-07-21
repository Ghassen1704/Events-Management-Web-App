package org.munic.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Messages_admin extends AuditModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="message", length=4000)
	private String message;
	private String fichier,extention;
	@Column(columnDefinition="BOOLEAN DEFAULT false")
	private boolean vue;
	
	
	

	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
     @JoinColumn(name = "emetteur", referencedColumnName = "id", nullable = false)
	 private Administrateur emetteur;
	 
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
     @JoinColumn(name = "recepteur", referencedColumnName = "id", nullable = false)
	 private Administrateur recepteur;
	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getFichier() {
		return fichier;
	}


	public void setFichier(String fichier) {
		this.fichier = fichier;
	}


	public String getExtention() {
		return extention;
	}


	public void setExtention(String extention) {
		this.extention = extention;
	}


	public boolean isVue() {
		return vue;
	}


	public void setVue(boolean vue) {
		this.vue = vue;
	}


	public Administrateur getEmetteur() {
		return emetteur;
	}


	public void setEmetteur(Administrateur emetteur) {
		this.emetteur = emetteur;
	}


	public Administrateur getRecepteur() {
		return recepteur;
	}


	public void setRecepteur(Administrateur recepteur) {
		this.recepteur = recepteur;
	}
	
	


	public Messages_admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Messages_admin(Long id, String message, String fichier,String extention,Administrateur emetteur,Administrateur recepteur) {
		super();
		this.id = id;
		this.message = message;
		this.fichier = fichier;
		this.extention = extention;
		this.emetteur = emetteur;
		this.recepteur = recepteur;
		
	}
	
	
	
	
	
	
	
}
