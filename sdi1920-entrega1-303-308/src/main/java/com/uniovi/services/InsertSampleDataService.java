package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@PostConstruct
	public void init() {
		User user1 = new User("ejemplo1@gmail.com", "Pedro", "Díaz");
		user1.setPassword("123456");
//		user1.setRole(rolesService.getRoles()[0]);
		
		User user2 = new User("ejemplo2@gmail.com", "Lucas", "Núñez");
		user2.setPassword("123456");
//		user2.setRole(rolesService.getRoles()[0]);
		
		User user3 = new User("ejemplo3@gmail.com", "María", "Rodríguez");
		user3.setPassword("123456");
//		user3.setRole(rolesService.getRoles()[0]);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
	}
}
