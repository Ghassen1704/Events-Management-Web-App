package org.munic.service;


import org.munic.dao.AdministrateurRepository;
import org.munic.entities.Administrateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("administrateurService")
public class AdministrateurServiceImpl implements AdministrateurService {
 
	@Autowired
	private AdministrateurRepository administrateurRepository;
 
	@Override
	@Transactional(readOnly = true)
	public Administrateur findByUsernameOrEmail(String usernameOrEmail) {
		Administrateur administrateur = null;
		try {
			administrateur = administrateurRepository.findByUsernameOrEmail(usernameOrEmail);
		} catch (Exception e) {
			throw e;
		}
		return administrateur;
	}
 
}