package com.souad.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.souad.users.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	User findByEmail(String email);
}