package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping("/user/listFriends")
	public String getRequests(Model model, Pageable pageable, Principal principal) {

		String email = principal.getName();
		User activeUser = usersService.getUserByEmail(email);

		Page<User> friends = new PageImpl<User>(new LinkedList<User>());
		friends = getFriendsLists(friends.getContent(), activeUser);

		model.addAttribute("friendsList", friends.getContent());
		model.addAttribute("page", friends);
		return "user/listFriends";
	}

	private Page<User> getFriendsLists(List<User> users, User user) {
		List<Long> friendsId = friendshipService.findFriendsByUser(user);
		List<User> friends = new ArrayList<User>();
		for (Long l : friendsId) {
			friends.add(usersService.findById(l));
		}
		
		return new PageImpl<User>(friends);
	}
}
