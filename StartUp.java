package org.munic;

import org.munic.dao.AdministrateurRepository;
import org.munic.dao.RoleRepository;
import org.munic.entities.Administrateur;
import org.munic.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
public class StartUp implements CommandLineRunner{

	@Autowired
	private AdministrateurRepository aRepo;
	@Autowired
	private RoleRepository rRepo;
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		Role role=new Role();
		role.setRole("ROLE_SUPER_ADMIN");
		rRepo.save(role);
		
		Administrateur admin=new Administrateur();
		admin.setEmail("a@gmail.com");
		admin.setIsactive(true);
		admin.setNom_fr("ahmed");
		admin.setPassword(bCryptPasswordEncoder.encode("password"));
		admin.setPhoto("photo place holder");
		admin.setPrenom_fr("mohamed");
		admin.setRole(role);
		admin.setUsername("ahmed");
		aRepo.save(admin);
		
		
		
		
		Role role1=new Role();
		role1.setRole("ADMIN");
		rRepo.save(role1);
		
		Administrateur admin1=new Administrateur();
		admin1.setEmail("g@gmail.com");
		admin1.setIsactive(true);
		admin1.setNom_fr("fouda");
		admin1.setPassword(bCryptPasswordEncoder.encode("password"));
		admin1.setPhoto("photo place holder");
		admin1.setPrenom_fr("ghassen");
		admin1.setRole(role1);
		admin1.setUsername("ghassen");
		aRepo.save(admin1);
		
	}

}
