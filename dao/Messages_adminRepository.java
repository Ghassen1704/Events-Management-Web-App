package org.munic.dao;



import java.util.List;

import javax.transaction.Transactional;

import org.munic.entities.Messages_admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Messages_adminRepository extends JpaRepository <Messages_admin, Long> {
	
	
    
	@Query(value = "select * FROM messages_admin m where (m.emetteur IN  (:emetteur , :recepteur)) and (m.recepteur IN  (:emetteur , :recepteur)) Order by m.id ASC",nativeQuery = true)
	   public List<Messages_admin>  getMessageByid(@Param("emetteur") Long emetteur,@Param("recepteur") Long recepteur); 
	   
	@Query(value = "select COUNT(*) as count FROM messages_admin m where m.vue= FALSE and m.emetteur = :target and m.recepteur = :adminId ",nativeQuery = true)
	  public int  countNbrMsgNonLue(@Param("adminId") Long adminId,@Param("target") Long target); 
	 
	@Transactional
	 @Modifying(clearAutomatically = true)
	 @Query(value ="UPDATE messages_admin  SET vue = TRUE WHERE recepteur = :adminId and emetteur = :target",nativeQuery = true)
	 void setVueTrue(@Param("adminId") Long adminId,@Param("target") Long target);
	
	@Query(value = "select COUNT(*) as count FROM messages_admin m where m.vue= FALSE and m.recepteur = :adminId ",nativeQuery = true)
	  public int  countNbrMsgAll(@Param("adminId") Long adminId); 
	
	
	
}
