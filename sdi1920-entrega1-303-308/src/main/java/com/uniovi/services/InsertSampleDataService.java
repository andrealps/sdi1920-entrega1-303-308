package com.uniovi.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;

	@Autowired
	private PostsService postsService;

	@PostConstruct
	public void init() {
		User user1 = new User("ejemplo1@gmail.com", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		
		User user2 = new User("ejemplo2@gmail.com", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		
		User user3 = new User("ejemplo3@gmail.com", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		
		User user4 = new User("ejemplo4@gmail.com", "Jaime", "Menéndez");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		
		User user5 = new User("ejemplo5@gmail.com", "Andrea", "Peláez");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		
		User user6 = new User("ejemplo6@gmail.com", "Marcos", "García");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		
		User user7 = new User("ejemplo7@gmail.com", "Carmen", "Rodríguez");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[0]);
		
		User admin = new User("admin@email.com", "Manuel", "Suárez");
		admin.setPassword("admin");
		admin.setRole(rolesService.getRoles()[1]);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(admin);
		
		
		Post post = new Post("Titulo 1", "Texto1", user1);
		Post post2 = new Post("Titulo 2", "Texto2", user1);
		Post post3 = new Post("Titulo 3", "Texto3", user1);
		Post post4 = new Post("Titulo 4", "Texto4", user1);
		Post post5 = new Post("Titulo 5", "Texto5", user1);
		Post post6 = new Post("Titulo 6", "Texto6", user1);
		Post post7 = new Post("Titulo 7", "Texto7", user1);
		postsService.addPost(post);
		postsService.addPost(post2);
		postsService.addPost(post3);
		postsService.addPost(post4);
		postsService.addPost(post5);
		postsService.addPost(post6);
		postsService.addPost(post7);
	}
}
