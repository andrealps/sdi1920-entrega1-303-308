package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class FriendRequestController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendRequestService friendRequestService;

	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping("/friend/add/{email}")
	public String sendRequest(Model model, Principal principal, @PathVariable String email) {
		String userEmail = principal.getName();
		User activeUser = usersService.getUserByEmail(userEmail);
		User userTo = usersService.getUserByEmail(email);
		friendRequestService.addRequest(activeUser, userTo);
		return "redirect:/user/listUsers";
	}

	@RequestMapping("/user/listRequests")
	public String getRequests(Model model, Pageable pageable, Principal principal) {

		String email = principal.getName();
		User activeUser = usersService.getUserByEmail(email);

		Page<User> requests = new PageImpl<User>(new LinkedList<User>());
		requests = friendRequestService.findFriendRequestToUser(activeUser, pageable);

		model.addAttribute("requestsList", requests.getContent());
		model.addAttribute("page", requests);
		return "user/listRequests";
	}

	@RequestMapping("/user/accept/{email}")
	public String acceptRequest(Model model, Principal principal, @PathVariable String email, Pageable pageable) {

		String emailUserTo = principal.getName();
		User userTo = usersService.getUserByEmail(emailUserTo);

		User userFrom = usersService.getUserByEmail(email);

		// Ahora son amigos
		friendshipService.addFriendShip(userTo, userFrom);

		// Actualizar la lista de peticiones
		Page<User> requests = new PageImpl<User>(new LinkedList<User>());
		requests = friendRequestService.acceptFriendRequest(userFrom, userTo, pageable);

		model.addAttribute("requestsList", requests.getContent());
		model.addAttribute("page", requests);

		return "redirect:/user/listRequests";
	}

}
