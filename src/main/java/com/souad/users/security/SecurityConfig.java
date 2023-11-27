package com.souad.users.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
 	UserDetailsService userDetailsService;
 	
 	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
 	
 	@Autowired
 	AuthenticationManager authMgr;
	
	
 	@Bean
	public AuthenticationManager authManager(HttpSecurity http, 
			BCryptPasswordEncoder bCryptPasswordEncoder, 
			UserDetailsService userDetailsService) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}
 	
 	 @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
		    http.csrf().disable()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		    
		    .cors().configurationSource(new CorsConfigurationSource() {
		    @Override
		    public CorsConfiguration getCorsConfiguration(HttpServletRequest 
		   request) {
		    CorsConfiguration config = new CorsConfiguration();
		    
		    config.setAllowCredentials(true);
		    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		    config.setAllowedMethods(Collections.singletonList("*"));
		    config.setAllowedHeaders(Collections.singletonList("*"));
		    config.setExposedHeaders(Arrays.asList("Authorization"));
		    config.setMaxAge(3600L);
		    return config;
		    }
		    }).and()
	          /*
		    .authorizeHttpRequests()
		                        .requestMatchers("/login").permitAll()
		                        .requestMatchers("/all").permitAll()
		                        .requestMatchers("/error").permitAll()
		                        .requestMatchers("/add").permitAll()
		                        .requestMatchers("/activateUser/**").permitAll()
		                        .requestMatchers("/deleteUser/**").hasAuthority("ADMIN")
		                        .anyRequest().authenticated().and()
		                        .addFilterBefore(new JWTAuthenticationFilter (authMgr),UsernamePasswordAuthenticationFilter.class)
		                        .addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
		                        return http.build();*/
	          .authorizeHttpRequests()
              .requestMatchers("/login").permitAll()
              .requestMatchers("/all").permitAll()
              .requestMatchers("/addrole").permitAll()
              .requestMatchers("/delrole/*").permitAll()
              //.requestMatchers("/getrolebyid/*").permitAll()
              .requestMatchers("/error").permitAll()
              .requestMatchers("/add").permitAll()
              .requestMatchers("/get/email/{email}").permitAll()
              .requestMatchers("/activateUser/{username}/{verificationCode}").permitAll()
              .requestMatchers("/activateUser/**")
              .hasAuthority("ADMIN")
              .anyRequest().authenticated().and()
              .addFilterBefore(new JWTAuthenticationFilter (authMgr),UsernamePasswordAuthenticationFilter.class)
              .addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);

return http.build();
	}
}
