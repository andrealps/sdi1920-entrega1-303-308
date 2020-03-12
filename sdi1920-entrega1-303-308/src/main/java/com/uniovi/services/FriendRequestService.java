package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {
	
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	public void addRequest(User userFrom, User userTo) {
		friendRequestRepository.save(new FriendRequest(userFrom.getId(), userTo.getId()));
	}
	
	public Page<FriendRequest> getFriendRequest(Pageable pageable, User activeUser) {
		Page<FriendRequest> friendRequests = friendRequestRepository.findAll(pageable);
		return friendRequests;
	}
	
	// Peticiones que envio un usuario
	public List<Long> findFriendRequestByUser(User user) {
		return friendRequestRepository.findFriendRequestByUser(user.getId());
	}
	
	// Peticiones que recibio un usuario
	public List<Long> findFriendRequestToUser(User user) {
		return friendRequestRepository.findFriendRequestToUser(user.getId());
	}
	
	// Aceptar peticion (ser borra la peticion de la bd)
	public List<Long> acceptFriendRequest( User userFrom, User userTo) {
		FriendRequest request = friendRequestRepository.acceptFriendRequest(userFrom.getId(), userTo.getId());
		friendRequestRepository.delete(request);
		return friendRequestRepository.findFriendRequestByUser(userTo.getId());
	}
	
	

	

}