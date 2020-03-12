package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	@Query("SELECT f FROM Friendship f WHERE (f.user1 = ?1 OR f.user2 = ?1)")
	List<Friendship> findFriends(Long idUser);

}
