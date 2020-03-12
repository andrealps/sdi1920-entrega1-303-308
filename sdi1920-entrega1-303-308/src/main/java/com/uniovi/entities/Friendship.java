package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
public class Friendship {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "user1")
	private Long user1;

	@Column(name = "user2")
	private Long user2;

	public Friendship() {
	}

	public Friendship(Long user1, Long user2) {
		super();
		this.user1 = user1;
		this.user2 = user2;
	}

	public Long getUser1() {
		return user1;
	}

	public void setUser1(Long user1) {
		this.user1 = user1;
	}

	public Long getUser2() {
		return user2;
	}

	public void setUser2(Long user2) {
		this.user2 = user2;
	}

}
