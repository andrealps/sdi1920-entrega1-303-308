package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "friendrequest")
public class FriendRequest {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	User pidePeticion;

	@ManyToOne
	User recibePeticion;

	FriendRequest() {
	}

	public FriendRequest(User pidePeticion, User recibePeticion) {
		super();
		this.pidePeticion = pidePeticion;
		this.recibePeticion = recibePeticion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getPidePeticion() {
		return pidePeticion;
	}

	public void setPidePeticion(User pidePeticion) {
		this.pidePeticion = pidePeticion;
	}

	public User getRecibePeticion() {
		return recibePeticion;
	}

	public void setRecibePeticion(User recibePeticion) {
		this.recibePeticion = recibePeticion;
	}

}
