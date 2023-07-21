package org.munic.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.munic.entities.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AdministrateurRepository extends JpaRepository <Administrateur, Long> {
	

	
	 @Query("SELECT a FROM Administrateur a WHERE a.username=:usernameOrEmail OR a.email=:usernameOrEmail")
	 Administrateur findByUsernameOrEmail(String usernameOrEmail);
	 
	 
	 @Query("SELECT a FROM Administrateur a WHERE (a.username=:usernameOrEmail OR a.email=:usernameOrEmail) AND a.id !=:id")
	 Administrateur findByUsernameOrEmailExeptId(String usernameOrEmail,@Param("id") Long id);
	 
	 @Query("SELECT a FROM Administrateur a WHERE a.username=:username")
	 Administrateur findByUsername(String username);
	 
	 @Transactional
	 @Modifying(clearAutomatically = true)
	 @Query("UPDATE Administrateur a SET a.nom_fr = :nom_fr, a.prenom_fr = :prenom_fr, a.email = :email,a.photo = :photo  WHERE a.id = :Id")
	 void editProfile(@Param("Id") Long id,@Param("nom_fr") String nom_fr, @Param("prenom_fr") String prenom_fr,@Param("email") String email, @Param("photo") String photo );
	
	 
	 @Transactional
	 @Modifying(clearAutomatically = true)
	 @Query("UPDATE Administrateur a SET a.username = :username WHERE a.id = :Id")
	 void editUsername(@Param("Id") Long id,@Param("username") String username);
	 
	 @Transactional
	 @Modifying(clearAutomatically = true)
	 @Query("UPDATE Administrateur a SET a.password = :password WHERE a.id = :Id")
	 void editPassword(@Param("Id") Long id,@Param("password") String password);
	 
	 
	 @Query(value = "select * FROM administrateur a where a.id != :adminId and a.isactive=true and role_id!=1 Order by a.status DESC",nativeQuery = true)
	   public List<Administrateur>  findAllActiveAdminExeptMe(@Param("adminId") Long adminId); 
	 
	 @Transactional
	 @Modifying(clearAutomatically = true)
	 @Query("UPDATE Administrateur a SET a.status = :status WHERE a.id = :adminId")
	 void editStatus(@Param("adminId") Long adminId,@Param("status") int status);
	 
	 
	 @Query(value = "select * FROM administrateur a,admin_consult ac where ac.target = a.id and a.isactive=true and role_id!=1 and ac.superviseur = :superId Order by a.role_id DESC",nativeQuery = true)
	   public List<Administrateur>  findAdminsByAcces(@Param("superId") Long superId); 
	 
	 
	 
	 
}
