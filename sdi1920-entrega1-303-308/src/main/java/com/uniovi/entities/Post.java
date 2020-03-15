package com.uniovi.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.tomcat.util.codec.binary.Base64;

@Entity
public class Post {
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String text;
	private LocalDate date;

	@ManyToOne
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "photo_id", referencedColumnName = "id")
	private Photo photo;

	public Post() {
		this.date = LocalDate.now();
	}

	public Post(String title, String text, User user) {
		this();
		this.title = title;
		this.text = text;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
		photo.setPost(this);
	}

	public boolean hasPhoto() {
		return photo != null ? true : false;
	}
	
	public String getBase64Image() {
		return Base64.encodeBase64String(photo.getPixel());
	}
}
