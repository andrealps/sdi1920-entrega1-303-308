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
	
	private String tipo;
	private Long tamaño;
	@Type(type = "org.hibernate.type.MaterializedBlobType")
	private byte[] pixel;
	
	@OneToOne(mappedBy = "photo")
	private Post post;
	
	public Photo() {}
	
	public Photo(String tipo, Long tamaño, byte[] pixel) {
		super();
		this.tipo = tipo;
		this.tamaño = tamaño;
		this.pixel = pixel;
	}

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getTamaño() {
		return tamaño;
	}

	public void setTamaño(Long tamaño) {
		this.tamaño = tamaño;
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
