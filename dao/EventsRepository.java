package org.munic.dao;




import java.util.List;

import org.munic.entities.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventsRepository extends JpaRepository <Events, Long> {
	
	
	 @Query(value = "select * from Events e where e.administrateur = :adminId", nativeQuery = true)
	  public List<Events>   getAllAdminEvents(@Param("adminId") Long adminId);
	
}
