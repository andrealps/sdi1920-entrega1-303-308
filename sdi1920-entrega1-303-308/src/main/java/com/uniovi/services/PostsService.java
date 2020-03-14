package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostsService {

	@Autowired
	private PostsRepository postsRepository;

	// Comentar si no se crean las tablas de nuevo
	@PostConstruct
	public void init() {
	}

	public Page<Post> getPosts(Pageable pageable, Post post) {
		Page<Post> posts = postsRepository.findAll(pageable);
		return posts;
	}

	public Post getPost(Long id) {
		return postsRepository.findById(id).get();
	}

	public void addPost(Post post) {
		postsRepository.save(post);
	}

	public void savePost(Post post) {
		postsRepository.save(post);
	}

	public void deletePost(Long id) {
		postsRepository.deleteById(id);
	}

	public Post findById(Long l) {
		return postsRepository.findById(l).get();
	}

	public Page<Post> getPostsForUser(Pageable pageable, User user) {
		return postsRepository.findByUser(pageable, user);
	}

}
