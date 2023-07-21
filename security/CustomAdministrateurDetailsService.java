package org.munic.security;

import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import org.munic.entities.Administrateur;
import org.munic.entities.Role;
import org.munic.service.AdministrateurService;



@Service("customAdministrateurDetailsService")
public class CustomAdministrateurDetailsService implements UserDetailsService {
 
	@Autowired
	private AdministrateurService administrateurService;
 
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
 
		if (username.trim().isEmpty()) {
			throw new BadCredentialsException("Nom d'utilisateur est vide");
		}
 
		Administrateur administrateur = administrateurService.findByUsernameOrEmail(username);
 
		
		if (administrateur == null) {
			
			throw new BadCredentialsException("L'utilisateur  " + username + "  n'existe pas");
			
		}

		if (administrateur.isIsactive()==false) {
			throw new BadCredentialsException("Votre compte a été désactivé");
		}

		return new org.springframework.security.core.userdetails.User(administrateur.getUsername(), administrateur.getPassword(), getGrantedAuthorities(administrateur));
	}
 
	private List<GrantedAuthority> getGrantedAuthorities(Administrateur administrateur) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Role role = administrateur.getRole();
		authorities.add(new SimpleGrantedAuthority(role.getRole()));
		return authorities;
	}
 
}