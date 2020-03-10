package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Long> findFriendRequestByUser(User user) {
		return friendRequestRepository.findFriendRequestByUser(user.getId());
	}

	

}