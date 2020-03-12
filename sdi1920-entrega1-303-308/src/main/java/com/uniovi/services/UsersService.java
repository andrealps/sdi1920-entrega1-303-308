package com.uniovi.services;

import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Comentar si no se crean las tablas de nuevo
	@PostConstruct
	public void init() {
	}

	public List<User> getUsers(User user) {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
	public User getUserByEmailAndPassword(String email, String password) {
		return usersRepository.findByEmailAndPassword(email, password);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public Page<User> searchUserByNameLastNameAndEmail(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchByNameLastNameAndEmail(pageable, searchText);
		return users;
	}
	
	public Page<User> findUsers(Pageable pageable, User user) {
		Page<User> users = usersRepository.listUsers(pageable, user);
		return users;
	}

	public User findById(Long l) {
		return usersRepository.findById(l).get();
	}

}