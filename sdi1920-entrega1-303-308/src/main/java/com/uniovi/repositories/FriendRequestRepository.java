package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{
	
	@Query("SELECT fr.userTo FROM FriendRequest fr WHERE fr.userFrom = ?1")
	List<Long> findFriendRequestByUser(Long idUser);
	
	@Query("SELECT fr.userFrom FROM FriendRequest fr WHERE fr.userTo = ?1")
	List<Long> findFriendRequestToUser(Long idUser);
}

