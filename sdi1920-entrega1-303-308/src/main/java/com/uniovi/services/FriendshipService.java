package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipRepository friendshipRepository;

	public void addFriendShip(User user1, User user2) {
		friendshipRepository.save(new Friendship(user1, user2));
	}

	public Page<User> findFriendsByUser(User user, Pageable pageable) {
		Page<Friendship> friendship = friendshipRepository.findFriends(user.getId(), pageable);
		List<User> friends = new ArrayList<User>();
		
		for(Friendship f : friendship) {
			if(user.getId() == f.getUser1().getId()) {
				friends.add(f.getUser2());
			}
			else
				friends.add(f.getUser1());
		}
		
	
		return new PageImpl<User>(friends);
	}

	public boolean areFriends(User activeUser, User userTo, Pageable pageable) {
		Page<Friendship> friends = friendshipRepository.findFriends(activeUser.getId(), pageable);
		for(Friendship f : friends) {
			if (f.getUser1().getId() == userTo.getId() | f.getUser2().getId() == userTo.getId()) 
				return true;
		}
		return false;
	}
	
	public void deleteFriendship(Long id) {
		friendshipRepository.deleteFriendShip(id);
	}

}