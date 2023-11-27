package com.souad.users.service;

import java.util.List;

import com.souad.users.entities.Role;
import com.souad.users.entities.User;

public interface UserService {
	User saveUser(User user);
	User updateUser(User user);
	void deleteUserbyId(Long id);
	User findUserByUsername (String username);
	Role addRole(Role role);
	Role updateRole(Role role);
	void deleteRolebyId(Long id);
	User addRoleToUser(String username, String rolename);
	List<User> findAllUsers();
	List<Role> findAllRoles();
	User getUserbyId(Long id);
	Role getRolebyId(Long id);
	User findByEmail(String email);
	User activateUser(String username, String code);
    User enregistrer(User user);
    void enableUser(User user);


}
