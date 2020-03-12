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
		requests = getRequestsLists(requests.getContent(), activeUser);
		
		model.addAttribute("requestsList", requests.getContent());
		model.addAttribute("page", requests);
		return "user/listRequests";
	}
	
	@RequestMapping("/user/accept/{email}")
	public String acceptRequest(Model model, Principal principal, @PathVariable String email) {
		
		String emailUserTo = principal.getName();
		User userTo = usersService.getUserByEmail(emailUserTo);
		
		User userFrom = usersService.getUserByEmail(email);
		
		// Ahora son amigos
		friendshipService.addFriendShip(userTo.getId(), userFrom.getId());
		
		// Actualizar la lista de peticiones
		Page<User> requests = new PageImpl<User>(new LinkedList<User>());
		requests = acceptRequest(requests.getContent(), userFrom, userTo);
		
		model.addAttribute("requestsList", requests.getContent());
		model.addAttribute("page", requests);
		
		return "redirect:/user/listRequests";
	}
	

	private Page<User> getRequestsLists(List<User> users, User user) {
		List<Long> requestUserLongs = friendRequestService.findFriendRequestToUser(user);
		List<User> friendRequest = new ArrayList<User>();
		for (Long l : requestUserLongs) {
			friendRequest.add(usersService.findById(l));
		}
		
		return new PageImpl<User>(friendRequest);
	}
	
	private Page<User> acceptRequest(List<User> users, User userFrom, User userTo) {
		List<Long> requests = friendRequestService.acceptFriendRequest(userFrom, userTo);
		List<User> friendRequest = new ArrayList<User>();
		for (Long l : requests) {
			friendRequest.add(usersService.findById(l));
		}
		
		return new PageImpl<User>(friendRequest);
	}


}
