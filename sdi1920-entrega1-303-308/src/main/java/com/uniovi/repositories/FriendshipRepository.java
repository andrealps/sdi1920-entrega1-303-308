package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	@Query("SELECT f FROM Friendship f WHERE (f.user1.id = ?1 OR f.user2.id = ?1)")
	Page<Friendship> findFriends(Long idUser, Pageable pageable);

}
