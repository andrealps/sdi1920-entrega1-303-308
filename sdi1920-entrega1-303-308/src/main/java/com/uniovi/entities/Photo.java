package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

@Entity
public class Photo {
	@Id
	@GeneratedValue
	private long id;
	
	@Type(type = "org.hibernate.type.MaterializedBlobType")
	private byte[] pixel;
	
	@OneToOne(mappedBy = "photo")
	private Post post;
	
	public Photo() {}
	
	public Photo(byte[] pixel) {
		super();
		this.pixel = pixel;
	}

	public byte[] getPixel() {
		return pixel;
	}

	public void setPixel(byte[] pixel) {
		this.pixel = pixel;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}	
}
