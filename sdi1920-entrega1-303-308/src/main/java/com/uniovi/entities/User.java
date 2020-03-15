package com.uniovi.entities;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set; //A collection that contains no duplicate elements

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;

	private String password;
	@Transient // propiedad que no se almacena en la tabla.
	private String passwordConfirm;

	@Transient
	private boolean friendRequestSended;

	@OneToMany(mappedBy = "pidePeticion")
	private Set<FriendRequest> peticionesEnviadas = new HashSet<FriendRequest>();

	@OneToMany(mappedBy = "recibePeticion")
	private Set<FriendRequest> peticionesRecibidas = new HashSet<FriendRequest>();

	@OneToMany(mappedBy = "user1")
	private Set<Friendship> friends = new HashSet<Friendship>();

	@OneToMany(mappedBy = "user2")
	private Set<Friendship> friendOf = new HashSet<Friendship>();
	
	@OneToMany(mappedBy = "user")
	private Set<Post> listPost = new HashSet<Post>();

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isFriendRequestSended() {
		return friendRequestSended;
	}

	public void setFriendRequestSended(boolean friendRequestSended) {
		this.friendRequestSended = friendRequestSended;
	}

	public Set<FriendRequest> getPeticionesEnviadas() {
		return peticionesEnviadas;
	}

	public void setPeticionesEnviadas(Set<FriendRequest> peticionesEnviadas) {
		this.peticionesEnviadas = peticionesEnviadas;
	}

	public Set<FriendRequest> getPeticionesRecibidas() {
		return peticionesRecibidas;
	}

	public void setPeticionesRecibidas(Set<FriendRequest> peticionesRecibidas) {
		this.peticionesRecibidas = peticionesRecibidas;
	}

	public Set<Post> getListPost() {
		return listPost;
	}

	public void setListPost(Set<Post> listPost) {
		this.listPost = listPost;
	}

	public Set<Friendship> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friendship> friends) {
		this.friends = friends;
	}

	public Set<Friendship> getFriendOf() {
		return friendOf;
	}

	public void setFriendOf(Set<Friendship> friendOf) {
		this.friendOf = friendOf;
	}
}
