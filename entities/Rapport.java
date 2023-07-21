package org.munic.entities;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import org.munic.service.FormatDate;

@Entity
public class Rapport extends AuditModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="text", length=8000)
	private String text;
	
	private Date date;
	
	
	
		
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "utilisateur", referencedColumnName = "id", nullable = true)
	   //@OnDelete(action = OnDeleteAction.CASCADE)
		private Administrateur utilisateur;		
		
		
		
	 
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	public Comparable<String> getDate() throws ParseException {
		 
		return FormatDate.ConvertDateToString(date);
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate_fd() {
		return date;
	}
	
	public Comparable<String> getDate_json() throws ParseException {
		 
		return FormatDate.ConvertDateToStringJson(date);
	}
	
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Administrateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Administrateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Rapport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Rapport(Long id,Date date,String text,Administrateur utilisateur) {
		super();
		this.id = id;
		this.date = date;
		this.text = text;
		this.utilisateur = utilisateur;
		
	}
	
	
	
	
	
	
	
}
