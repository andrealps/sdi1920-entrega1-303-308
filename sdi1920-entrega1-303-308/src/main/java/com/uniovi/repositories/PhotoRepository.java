package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Long>{
//	@Query("SELECT p from Photo p WHERE nombre = ?1")
	
}
