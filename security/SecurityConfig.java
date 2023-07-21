package org.munic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customAdministrateurDetailsService")
	UserDetailsService userDetailsService;
	
	
	@Autowired
	AdminAuthenticationSuccessHandlerImpl adminAuthenticationSuccessHandlerImpl;
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
			http.headers().frameOptions().disable();
			http.authorizeRequests()
			.antMatchers("/assets_bo/**").permitAll();




			
		
		
		http.csrf().disable()
			 .authorizeRequests()
			 .antMatchers("/bo/utilisateur").hasAuthority("ROLE_SUPER_ADMIN")
			 .antMatchers("/bo/","/bo/discussion","/bo/rapport","/bo/events").hasAnyAuthority("ROLE_FNVT","ROLE_PARTENAIRE")
             .anyRequest().authenticated()
             .and().formLogin().loginPage("/bo/login").permitAll().successHandler(adminAuthenticationSuccessHandlerImpl) 
             .and().logout().deleteCookies("JSESSIONID").logoutUrl("/bo/logout").logoutSuccessUrl("/bo/login?logout=true").permitAll()
     		 .and().exceptionHandling().accessDeniedPage("/bo/403");

	}
	
	
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}