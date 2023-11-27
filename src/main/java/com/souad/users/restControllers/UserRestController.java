package com.souad.users.restControllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.souad.users.entities.Role;
import com.souad.users.entities.User;
import com.souad.users.service.UserService;



@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
	@Autowired
	UserService userService;
	@RequestMapping(path = "all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
	return userService.findAllUsers();
	}

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public User saveU(@RequestBody User user) {
        return userService.enregistrer(user);
    }
	@RequestMapping(path = "allRole", method = RequestMethod.GET)
	public List<Role> getAllRoles() {
	return userService.findAllRoles();
	}
	@RequestMapping(path = "/adduser", method = RequestMethod.POST)
	public User createuser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	@RequestMapping(path = "/addrole", method = RequestMethod.POST)
	public Role createrole(@RequestBody Role role) {
		return userService.addRole(role);
	}
	@RequestMapping(path = "/updateuser", method = RequestMethod.PUT)
	public User modifieruser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	@RequestMapping(path = "/updaterole", method = RequestMethod.PUT)
	public Role modifierrole(@RequestBody Role role) {
		return userService.updateRole(role);
	}
	@RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable("id") Long id) {
		return userService.getUserbyId(id);
	}
	@RequestMapping(path = "/getrolebyid/{id}", method = RequestMethod.GET)
	public Role getRoleById(@PathVariable("id") Long id) {
		return userService.getRolebyId(id);
	}
	@RequestMapping(path = "/deluser/{id}", method = RequestMethod.DELETE)
	public void supprimeruser(@PathVariable("id") Long id) {
		userService.deleteUserbyId(id);
	}
	@RequestMapping(path = "/delrole/{id}", method = RequestMethod.DELETE)
	public void supprimerrole(@PathVariable("id") Long id) {
		userService.deleteRolebyId(id);
	}

    @RequestMapping(path = "activateUser/{username}/{verificationCode}", method = RequestMethod.POST)
    public User activateUser(@PathVariable String username, @PathVariable String verificationCode) {
        System.out.println("user activated: " + verificationCode);
        return userService.activateUser(username, verificationCode);
    }
    @GetMapping("/get/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findByEmail(email);
        System.out.println(email);

        if (user != null) {
            String userEmail = user.getEmail();
            System.out.println(userEmail);
            return ResponseEntity.ok(user);
        } else {
            String errorMessage = "User not found for email: " + email;
            System.out.println(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
