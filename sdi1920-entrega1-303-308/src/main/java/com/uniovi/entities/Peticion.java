package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "peticion")
public class Peticion {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User pidePeticion;
	
	@ManyToOne
	private User recibePeticion;
	
	public Peticion() {}

	public Peticion(User pidePeticion, User recibePeticion) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pidePeticion == null) ? 0 : pidePeticion.hashCode());
		result = prime * result + ((recibePeticion == null) ? 0 : recibePeticion.hashCode());
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
		Peticion other = (Peticion) obj;
		if (pidePeticion == null) {
			if (other.pidePeticion != null)
				return false;
		} else if (!pidePeticion.equals(other.pidePeticion))
			return false;
		if (recibePeticion == null) {
			if (other.recibePeticion != null)
				return false;
		} else if (!recibePeticion.equals(other.recibePeticion))
			return false;
		return true;
	}
	
	
}
