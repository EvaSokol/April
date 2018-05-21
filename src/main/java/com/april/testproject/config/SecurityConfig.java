package com.april.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/spring_security_login/*", "/login**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Autowired      // here is configuration related to spring boot basic authentication
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(auth.isConfigured());
        auth.inMemoryAuthentication()                                               // static users
                .withUser("zone1").password("mypassword").roles("USER")
                .and()
                .withUser("zone2").password("mypassword").roles("USER")
                .and()
                .withUser("zone3").password("mypassword").roles("USER")
                .and()
                .withUser("zone4").password("mypassword").roles("USER")
                .and()
                .withUser("zone5").password("mypassword").roles("USER");
    }
}
