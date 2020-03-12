package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "friendrequest")
public class FriendRequest {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "userFrom")
	Long userFrom;
	@Column(name = "userTo")
	Long userTo;

	FriendRequest() {
	}

	public FriendRequest(Long userFrom, Long userTo) {
		super();
		this.userFrom = userFrom;
		this.userTo = userTo;
	}

	public Long getUserFrom() {
		return userFrom;
	}

	public void setLongFrom(Long userFrom) {
		this.userFrom = userFrom;
	}

	public Long getLongTo() {
		return userTo;
	}

	public void setLongTo(Long LongTo) {
		this.userTo = LongTo;
	}

}
