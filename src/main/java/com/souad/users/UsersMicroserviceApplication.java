package com.souad.users;
import com.souad.users.entities.Role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.souad.users.entities.User;
import com.souad.users.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class UsersMicroserviceApplication {

	@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(UsersMicroserviceApplication.class, args);
	}
	
	
	  @PostConstruct void init_users() { 
		  /*//ajouter les rôles
	  userService.addRole(new Role(null,"ADMIN"));
	  userService.addRole(new Role(null,"USER"));
	 
	  //ajouter les users userService.saveUser(new
	  userService.saveUser(new User(null,"admin","souad.benattia9@gmail.com",true,"123",true,null));
		userService.saveUser(new User(null,"souad","123",true,null));
		userService.saveUser(new User(null,"mohamed","123",true,null));
	  
	  //ajouter les rôles aux users userService.addRoleToUser("admin", "ADMIN");
	  userService.addRoleToUser("admin", "USER");
	  
	  userService.addRoleToUser("souad", "USER");
	  userService.addRoleToUser("mohamed", "USER"); 
	*/
	  }
	@Bean
	BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();
		
	}


}