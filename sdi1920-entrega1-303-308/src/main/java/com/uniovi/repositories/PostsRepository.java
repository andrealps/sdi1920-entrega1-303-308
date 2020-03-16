package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;

public interface PostsRepository extends CrudRepository<Post, Long> {

	Page<Post> findAll(Pageable pageable);

	Page<Post> findByUser(Pageable pageable, User user);

	@Transactional
	@Modifying
	@Query("DELETE FROM Post p WHERE p.user.id = ?1")
	void deletePostById(Long u);

}
