package com.uniovi.services;

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
		friendRequestRepository.save(new FriendRequest(userFrom, userTo));
	}

	// Peticiones que envio un usuario
	public Page<User> findFriendRequestByUser(User user, Pageable pageable) {
		return friendRequestRepository.findFriendRequestByUser(user.getId(), pageable);
	}

	// Peticiones que recibio un usuario
	public Page<User> findFriendRequestToUser(User user, Pageable pageable) {
		return friendRequestRepository.findFriendRequestToUser(user.getId(), pageable);
	}

	// Aceptar peticion (ser borra la peticion de la bd)
	public Page<User> acceptFriendRequest(User userFrom, User userTo,  Pageable pageable) {
		FriendRequest request = friendRequestRepository.acceptFriendRequest(userFrom.getId(), userTo.getId());
		friendRequestRepository.delete(request);
		
		// Comprobar si hay peticion a la inversa
		Page<User> peticionesEnviadas = findFriendRequestByUser(userTo, pageable);
		for(User u : peticionesEnviadas) {
			if(peticionesEnviadas.getContent().contains(userFrom)) {
				FriendRequest f = friendRequestRepository.acceptFriendRequest(userTo.getId(), userFrom.getId());
				friendRequestRepository.delete(f);
			}
		}
		return friendRequestRepository.findFriendRequestByUser(userTo.getId(), pageable);
	}

}