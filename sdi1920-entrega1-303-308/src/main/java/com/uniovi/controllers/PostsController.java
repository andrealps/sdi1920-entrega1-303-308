package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddPostValidator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Controller
public class PostsController {
	@Autowired
	private PostsService postsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AddPostValidator addPostFormValidator;

	@RequestMapping(value = "/post/addPost")
	public String getPost(Model model) {
		model.addAttribute("post", new Post());
		return "post/addPost";
	}

	@RequestMapping(value = "/post/addPost", method = RequestMethod.POST)
	public String setPost(@Validated Post post, Principal principal, BindingResult result) {
		addPostFormValidator.validate(post, result);
		if (result.hasErrors()) {
			return "/post/addPost";
		}
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		post.setUser(user);
		user.getListPost().add(post);
		postsService.addPost(post);
		return "redirect:/post/listPost";
	}

	@RequestMapping("/post/listPost")
	public String getList(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Post> posts = new PageImpl<Post>(new LinkedList<Post>());

		posts = postsService.getPostsForUser(pageable, user);

		model.addAttribute("listPost", posts.getContent());
		model.addAttribute("page", posts);
		model.addAttribute("friend", false);
		return "post/listPost";
	}

	@RequestMapping("/post/listPost/{friendEmail}")
	public String getPostListFriend(Model model, Pageable pageable, Principal principal, @PathVariable String friendEmail) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Post> posts = new PageImpl<Post>(new LinkedList<Post>());

		User friend = usersService.getUserByEmail(friendEmail);
		if (!user.getFriends().stream().map(x -> x.getUser2()).collect(Collectors.toList()).contains(friend)
				&& !user.getFriendOf().stream().map(x -> x.getUser1()).collect(Collectors.toList()).contains(friend))
			return "redirect:/user/listFriends";
		
		posts = postsService.getPostsForUser(pageable, friend);
		model.addAttribute("listPost", posts.getContent());
		model.addAttribute("page", posts);
		model.addAttribute("friend", true);
		return "post/listPost";
	}
}
