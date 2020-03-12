package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "amistad")
public class Amistad {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User usuario1;
	
	@ManyToOne
	private User usuario2;
	
	public Amistad() {}

	public Amistad(User usuario1, User usuario2) {
		this.usuario1 = usuario1;
		this.usuario2 = usuario2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(User usuario1) {
		this.usuario1 = usuario1;
	}

	public User getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(User usuario2) {
		this.usuario2 = usuario2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario1 == null) ? 0 : usuario1.hashCode());
		result = prime * result + ((usuario2 == null) ? 0 : usuario2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Amistad other = (Amistad) obj;
		if (usuario1 == null) {
			if (other.usuario1 != null)
				return false;
		} else if (!usuario1.equals(other.usuario1))
			return false;
		if (usuario2 == null) {
			if (other.usuario2 != null)
				return false;
		} else if (!usuario2.equals(other.usuario2))
			return false;
		return true;
	}	
}
