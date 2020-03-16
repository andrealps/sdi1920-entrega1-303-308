package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
//	@Query("SELECT p from Photo p WHERE nombre = ?1")

}
