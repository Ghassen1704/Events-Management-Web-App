package org.munic.dao;



import java.util.List;
import java.util.Optional;

import org.munic.entities.Administrateur;
import org.munic.entities.Admin_consult;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Admin_consultRepository extends JpaRepository <Admin_consult, Long> {
	
	
	public List<Admin_consult> findBySuperviseur(Administrateur SuperId);
	public List<Admin_consult> findByTarget(Administrateur TargetId);
	public Optional<Admin_consult> findById(Long a_cId);
	

}
