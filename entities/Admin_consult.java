package org.munic.entities;




import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;




@Entity
public class Admin_consult implements Comparable<Admin_consult> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
     @JoinColumn(name = "superviseur", referencedColumnName = "id", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
	 private Administrateur superviseur;
	
	
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
     @JoinColumn(name = "target", referencedColumnName = "id", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
	 private Administrateur target;
	 

	
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Administrateur getSuperviseur() {
		return superviseur;
	}

	public void setSuperviseur(Administrateur superviseur) {
		this.superviseur = superviseur;
	}

	public Administrateur getTarget() {
		return target;
	}

	public void setTarget(Administrateur target) {
		this.target = target;
	}

	public Admin_consult() {
			super();
			// TODO Auto-generated constructor stub
		}
	
	public Admin_consult(Long id, Administrateur superviseur, Administrateur target
			) {
		super();
		this.id = id;
		this.superviseur = superviseur;
		this.target = target;
		
		
	}

	@Override
	public int compareTo(Admin_consult o) {
		 return getId().compareTo(o.getId());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
}
