package org.munic.security;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import org.munic.dao.AdministrateurRepository;
import org.munic.entities.Administrateur;


@Component
public class AdminAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired HttpSession session; //autowiring session
    @Autowired AdministrateurRepository administrateurRepository;; //autowire the admin repo
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
      
    	
    	 String userName = "";
         if(!(authentication.getPrincipal() instanceof Principal)) {
        	  userName = ((User)authentication.getPrincipal()).getUsername();
        	  Administrateur admin = administrateurRepository.findByUsername(userName);
        	  session.setAttribute("adminObject", admin);
         }

        
         
         Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
         if (roles.contains("ROLE_SUPER_ADMIN")) {
        	 redirectStrategy.sendRedirect(request, response, "/bo/utilisateur"); 
         }else {
         redirectStrategy.sendRedirect(request, response, "/bo/");
         }
    }

}