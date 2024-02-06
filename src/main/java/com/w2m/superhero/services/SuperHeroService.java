package com.w2m.superhero.services;

import java.util.List;

import com.w2m.superhero.dto.SuperHeroDTO;
import com.w2m.superhero.exception.SuperHeroException;

public interface SuperHeroService {
	/**
	 * Gets all the super heroes stored in database
	 * 
	 * @return the list of all super heroes
	 */
	public List<SuperHeroDTO> getAll();

	/**
	 * Gets the super hero with the provided id
	 * 
	 * @param id the id
	 * @return the super hero DTO
	 */
	public SuperHeroDTO getById(Long id);

	/**
	 * Gets the super heroes with the name containing the provided string
	 * 
	 * @param subString
	 * @return the list of super heroes matching the condition
	 */
	public List<SuperHeroDTO> getAllContaining(String subString);

	/**
	 * Adds a new super hero
	 * 
	 * @param superHero the super hero DTO
	 */
	public void add(SuperHeroDTO superHero);

	/**
	 * Updates an existing super hero. If the id is not present in DB, then throws
	 * an exception
	 * 
	 * @param superHero
	 * @return the super hero DTO
	 * @throws SuperHeroException
	 */
	public SuperHeroDTO update(SuperHeroDTO superHero) throws SuperHeroException;

	/**
	 * Deletes an existing super hero. If the id is not present in DB, then throws
	 * an exception
	 * 
	 * @param superHero
	 * @throws SuperHeroException
	 */
	public void delete(SuperHeroDTO superHero) throws SuperHeroException;
}
