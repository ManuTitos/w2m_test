package com.w2m.superhero.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.w2m.superhero.dto.SuperHeroDTO;
import com.w2m.superhero.entities.SuperHeroEntity;
import com.w2m.superhero.exception.SuperHeroException;
import com.w2m.superhero.mappers.SuperHeroMapper;
import com.w2m.superhero.repositories.SuperHeroRepository;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

	private static final String ENTITY_NOT_FOUND_MESSAGE = "The super hero could not be found in database. Please check the data sent again.";

	@Autowired
	SuperHeroRepository superHeroRepository;

	@Autowired
	SuperHeroMapper superHeroMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SuperHeroDTO> getAll() {
		return superHeroRepository.findAll().stream().map(e -> superHeroMapper.entityToDTO(e))
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable(value = "superHeroDTO", key = "#id")
	public SuperHeroDTO getById(Long id) {
		Optional<SuperHeroEntity> optionalEntity = superHeroRepository.findById(id);
		// If the super hero is not stored in database it returns null value
		return optionalEntity.isPresent() ? superHeroMapper.entityToDTO(optionalEntity.get()) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SuperHeroDTO> getAllContaining(String subString) {
		return superHeroRepository.findByNameContaining(subString).stream().map(e -> superHeroMapper.entityToDTO(e))
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(SuperHeroDTO superHeroDTO) {
		SuperHeroEntity entity = superHeroMapper.dtoToEntity(superHeroDTO);
		superHeroRepository.save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CachePut(value = "superHeroDTO", key = "#superHeroDTO.id")
	public SuperHeroDTO update(SuperHeroDTO superHeroDTO) throws SuperHeroException {
		Optional<SuperHeroEntity> optionalEntity = superHeroRepository.findById(superHeroDTO.getId());
		// Check if the id is already stored in database
		if (optionalEntity.isPresent()) {
			SuperHeroEntity entity = superHeroMapper.dtoToEntity(superHeroDTO);
			superHeroRepository.save(entity);
			return superHeroDTO;
		} else {
			throw new SuperHeroException(ENTITY_NOT_FOUND_MESSAGE);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CacheEvict(value = "superHeroDTO", key = "#superHeroDTO.id")
	public void delete(SuperHeroDTO superHeroDTO) throws SuperHeroException {
		Optional<SuperHeroEntity> optionalEntity = superHeroRepository.findById(superHeroDTO.getId());
		// Check if the id is already stored in database
		if (optionalEntity.isPresent()) {
			SuperHeroEntity entity = superHeroMapper.dtoToEntity(superHeroDTO);
			superHeroRepository.delete(entity);
		} else {
			throw new SuperHeroException(ENTITY_NOT_FOUND_MESSAGE);
		}
	}
}
