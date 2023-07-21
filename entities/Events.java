package org.munic.entities;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;




import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Events extends AuditModel implements Comparable<Events> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type,color,lieu,date_start,date_end;
	
	
	@Column(columnDefinition="BOOLEAN DEFAULT false")
	private boolean allday;
	
	
	@Column(name="organismes", length=2000)
	private String organismes;
	@Column(name="personnes", length=2000)
	private String personnes;
	@Column(name="description", length=4000)
	private String description;
	@Column(name="observation", length=4000)
	private String observation;
	
	
	
	

	 
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "administrateur", referencedColumnName = "id", nullable = true)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	 private Administrateur administrateur;	
	 
	 
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	
	

	public String getDate_start() {
		return date_start;
	}

	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	

	public boolean isAllday() {
		return allday;
	}

	public void setAllday(boolean allday) {
		this.allday = allday;
	}

	public String getOrganismes() {
		return organismes;
	}

	public void setOrganismes(String organismes) {
		this.organismes = organismes;
	}

	public String getPersonnes() {
		return personnes;
	}

	public void setPersonnes(String personnes) {
		this.personnes = personnes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public Events() {
			super();
			// TODO Auto-generated constructor stub
		}
	
	public Events(Long id,String type, String color,String lieu, String organismes,String personnes, String description, String observation,String date_start,String date_end,boolean allday,Administrateur administrateur) {
	
		super();
		this.id = id;
		this.type = type;
		this.color = color;
		this.lieu = lieu;
		this.organismes = organismes;
		this.personnes = personnes;
		this.description = description;
		this.observation = observation;
		this.date_start = date_start;
		this.date_end = date_end;
		this.allday = allday;
		this.administrateur = administrateur;
		
		
		
	}

	@Override
	public int compareTo(Events o) {
		 return getId().compareTo(o.getId());
	}
	
	
	
	
	
	
	
}
