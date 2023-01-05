package com.spring.security.config;

import static com.spring.security.config.ApplicationUserRole.ADMIN;
import static com.spring.security.config.ApplicationUserRole.ADMIN_TRAINEE;
import static com.spring.security.config.ApplicationUserRole.STUDENT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("index","/","/css/*","/js/*").permitAll()//white-list the URLs where do not want to provide the authentication
				.antMatchers("/api/**").hasRole(STUDENT.name())//Only Student Role user can access the /api REST API. Role based authentication
				/*
				 * Commenting this line because, we have added the hasAnyRole and hasAuthority in @PreAuthorize annotation in controller class
				 * 
				 */
				//.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
				//.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
				//.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
				//.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails userDetails1 = User.builder()
									.username("boranih")
									.password(passwordEncoder.encode("password"))
									//.roles(STUDENT.name())//ROLE_STUDENT
									.authorities(STUDENT.getGrantedAuthorites())
									.build();
		
		UserDetails userDetails2 = User.builder()
										.username("shivangisxn")
										.password(passwordEncoder.encode("password"))
										//.roles(ADMIN.name())//ROLE_ADMIN
										.authorities(ADMIN.getGrantedAuthorites())
										.build();
		
		UserDetails userDetails3 = User.builder()
										.username("boraniv")
										.password(passwordEncoder.encode("password"))
										//.roles(ADMIN_TRAINEE.name())//ROLE_ADMIN_TRAINEE
										.authorities(ADMIN_TRAINEE.getGrantedAuthorites())
										.build();
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2, userDetails3);
	}

}
