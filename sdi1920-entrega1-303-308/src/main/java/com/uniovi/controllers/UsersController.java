package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.PostsService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private RolesService rolesService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendRequestService friendRequestService;

	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private PostsService postsService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping("/user/listUsers")
	public String getListado(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {

		String email = principal.getName();
		User activeUser = usersService.getUserByEmail(email);

		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUserByNameLastNameAndEmail(pageable, searchText);
		} else {
			users = usersService.findUsers(pageable, activeUser);
		}

		model.addAttribute("usersList", comprobarPeticiones(users.getContent(), activeUser, pageable));
		model.addAttribute("page", users);
		return "user/listUsers";
	}

	// Para ver a que usuario le puedes mandar solicitud
	private Page<User> comprobarPeticiones(List<User> users, User user, Pageable pageable) {

		// Comprobar que no se le ha mandado peticion ya
		Page<User> requestsSended = friendRequestService.findFriendRequestByUser(user, pageable);

		for (User u : users) {
			if (requestsSended.getContent().contains(u)) {
				u.setFriendRequestSended(true);
			}
		}

		// Comprobar que no son amigos
		for (User u : users) {
			if (friendshipService.areFriends(user, u, pageable)) {
				u.setFriendRequestSended(true); // se marca como enviada para que no aparezca
				user.setFriendRequestSended(true);
			}
		}

		return new PageImpl<User>(users);
	}

	@RequestMapping("/admin/listUsers")
	public String getListado(Model model, Principal principal) {

		model.addAttribute("usersList", usersService.getUsers());
	
		return "admin/listUsers";
	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
	public String updateList(@RequestParam("idChecked") List<Long> users, Model model) {

		if (users != null) {
			for (Long u : users) {
				postsService.deletePostByUser(u);
				friendshipService.deleteFriendship(u);
				friendRequestService.deleteFriendRequest(u);
				usersService.deleteUser(u);
			}
		}
		model.addAttribute("usersList", usersService.getUsers());
		return "admin/listUsers";
	}
}
