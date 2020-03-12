package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipRepository friendshipRepository;

	public void addFriendShip(Long id1, Long id2) {
		friendshipRepository.save(new Friendship(id1, id2));
	}

	public List<Long> findFriendsByUser(User user) {
		List<Friendship> friendship = friendshipRepository.findFriends(user.getId());
		List<Long> friendsIds = new ArrayList<Long>();
		
		for(Friendship f : friendship) {
			if(user.getId() == f.getUser1()) {
				friendsIds.add(f.getUser2());
			}
			else
				friendsIds.add(f.getUser1());
		}
		
		return friendsIds;
	}

}