/**
 * 
 */
package com.rv.goAuth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security config to secure the web application and 
 * 	define login, logout methods.
 * 
 * @author aterati
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "/index").permitAll()
        .and().oauth2Login().loginPage("/login")
        .defaultSuccessUrl("/loginSuccess")
        .failureUrl("/login")
        .and()
        .logout().logoutSuccessUrl("/logoutSuccess").permitAll();
		
	}
	

}
