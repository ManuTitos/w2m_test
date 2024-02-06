package com.w2m.superhero.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.w2m.superhero.entities.SuperHeroEntity;

public interface SuperHeroRepository extends JpaRepository<SuperHeroEntity, Long> {
	/**
	 * Query to get the list of super heroes containing a subString. It is not case
	 * sensitive
	 * 
	 * @param subString
	 * @return the list of entities matching the condition
	 */
	@Query("SELECT e FROM SuperHeroEntity e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%',:subString,'%'))")
	List<SuperHeroEntity> findByNameContaining(@Param("subString") String subString);
}
