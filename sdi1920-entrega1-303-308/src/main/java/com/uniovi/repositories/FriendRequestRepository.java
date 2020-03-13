package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{
	
	@Query("SELECT fr.recibePeticion FROM FriendRequest fr WHERE fr.pidePeticion.id = ?1")
	Page<User> findFriendRequestByUser(Long id, Pageable pageable);
	
	@Query("SELECT fr.pidePeticion FROM FriendRequest fr WHERE fr.recibePeticion.id = ?1")
	Page<User> findFriendRequestToUser(Long idUser, Pageable pageable);

	@Query("SELECT fr FROM FriendRequest fr WHERE (fr.pidePeticion.id = ?1 AND fr.recibePeticion.id = ?2)")
	FriendRequest acceptFriendRequest(Long idUserFrom, Long idUserTo);
}

