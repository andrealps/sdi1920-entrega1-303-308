package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
	
	@Query("SELECT r FROM User r WHERE (LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1))")
	Page<User> searchByNameLastNameAndEmail(Pageable pageable, String searchText);
	
	@Query("SELECT r FROM User r WHERE r!=?1 AND r.role !='ROLE_ADMIN'")
	Page<User> listUsers(Pageable pageable, User user);
	
	@Query("SELECT r FROM User r WHERE (r.email = ?1 AND r.password = ?2)")
	User findByEmailAndPassword(String email, String password);
	
	Page<User> findAll(Pageable pageable);
}

