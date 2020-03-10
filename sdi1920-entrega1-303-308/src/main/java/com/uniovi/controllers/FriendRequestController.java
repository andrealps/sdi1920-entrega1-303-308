package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.UsersService;

@Controller
public class FriendRequestController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendRequestService friendRequestService;

	@RequestMapping("/friend/add/{email}")
	public String sendRequest(Model model, Principal principal, @PathVariable String email) {
		String userEmail = principal.getName();
		User activeUser = usersService.getUserByEmail(userEmail);
		User userTo = usersService.getUserByEmail(email);
		friendRequestService.addRequest(activeUser, userTo);
		return "redirect:/user/list";
	}

}
