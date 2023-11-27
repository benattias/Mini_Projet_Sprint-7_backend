package com.souad.users.service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;

import com.souad.users.entities.Role;
import com.souad.users.entities.User;
import com.souad.users.repos.RoleRepository;
import com.souad.users.repos.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRep;
	@Autowired
	RoleRepository roleRep;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired
	    private JavaMailSender javaMailSender;
	    @Value("${spring.mail.username}")
	    private String mailAddress;
	
	 @Override
	   public User enregistrer(User user) {
	        String verificationCode = UUID.randomUUID().toString();
	        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	        simpleMailMessage.setSubject("Activation Code");
	        simpleMailMessage.setText("Your activation code is: " + verificationCode);
	        simpleMailMessage.setTo(user.getEmail());
	        simpleMailMessage.setFrom(mailAddress);
	        javaMailSender.send(simpleMailMessage);
	        
	        if (userRep.findByEmail(user.getEmail()) != null) {
				throw new RuntimeException("Email already registered");
			}
	        user.setVerificationCode(verificationCode);
	        List<Role>roles=new ArrayList<>();
	        Role r=roleRep.findByRole("USER");
	        roles.add(r);
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setRoles(roles);
	        user.setEnabled(false);
	        return userRep.save(user);
	    }
	 @Override
		public User activateUser(String username, String code) {
			User user = userRep.findByEmail(username);
			if (user != null) {
				if (user.getEnabled() == null || user.getEnabled() == false) {
					if (user.getVerificationCode().equals(code) == true) {
						user.setEnabled(true);
						user.setVerificationCode(code);
						userRep.save(user);
						return user;
					} else {
						System.out.println(user.getVerificationCode());
						return user;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

	    @Override
	    public User findByEmail(String email) {
	        return userRep.findByEmail(email);
	    }

	 @Override
		public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRep.save(user);
		}
		@Override
		public User addRoleToUser(String username, String rolename) {
		User usr = userRep.findByUsername(username);
		Role r = roleRep.findByRole(rolename);
		usr.getRoles().add(r);
		return usr;
		}
		@Override
		public Role addRole(Role role) {
		return roleRep.save(role);
		}
		@Override
		public User findUserByUsername(String username) {
		return userRep.findByUsername(username);
		}
		@Override
		public List<User> findAllUsers() {
			
			return userRep.findAll();
		}
		@Override
		public User updateUser(User user) {
			
			return userRep.save(user) ;
		}
		@Override
		public User getUserbyId(Long id) {
			
			return userRep.findById(id).get();
		}
		@Override
		public void deleteUserbyId(Long id) {
			
			 userRep.deleteById(id);
		}
		@Override
		public Role updateRole(Role role) {
			
			return roleRep.save(role);
		}
		@Override
		public void deleteRolebyId(Long id) {
			roleRep.deleteById(id);
			
		}
		@Override
		public Role getRolebyId(Long id) {
		
			return roleRep.findById(id).get();
		}
		@Override
		public List<Role> findAllRoles() {
			
			return roleRep.findAll();
		}
		 @Override
		    public void enableUser(User user) {
			 userRep.save(user);
		    }
}
