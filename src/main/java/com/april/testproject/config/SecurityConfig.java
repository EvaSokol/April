package com.april.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AppUserDetailsService myAppUserDetailsService;
	@Autowired
	private AppAuthenticationEntryPoint appAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
						.cors().disable()
						.authorizeRequests()
						.antMatchers("/api/v1/registration").permitAll()
						.antMatchers("/api/v1/idea").hasAnyRole("USER", "ADMIN")
						.antMatchers("/api/v1/login").hasAnyRole("USER", "ADMIN")
						.antMatchers("/api/v1/like/**").hasAnyRole("USER", "ADMIN")
						.antMatchers("/api/v1/**").hasAnyRole("ADMIN")
						.and().httpBasic().realmName("MY APP REALM")
						.authenticationEntryPoint(appAuthenticationEntryPoint);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder);
	}
}