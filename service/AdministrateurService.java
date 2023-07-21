package org.munic.service;

import org.munic.entities.Administrateur;

public interface AdministrateurService {

	Administrateur findByUsernameOrEmail(String usernameOrEmail);
}
