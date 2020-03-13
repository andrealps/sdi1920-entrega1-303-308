package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
public class Friendship {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User user1;

	@ManyToOne
	private User user2;

	public Friendship() {
	}

	public Friendship(User user1, User user2) {
		super();
		this.user1 = user1;
		this.user2 = user2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}
