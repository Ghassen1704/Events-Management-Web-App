package org.munic.dao;



import java.util.List;


import org.munic.entities.Rapport;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RapportRepository extends JpaRepository <Rapport, Long> {
	
	
	
	@Query(value = "select * FROM rapport r where r.utilisateur = :userId Order by r.date DESC",nativeQuery = true)
	   public List<Rapport>  findByIdUser(@Param("userId") Long userId); 
	 
	
}
