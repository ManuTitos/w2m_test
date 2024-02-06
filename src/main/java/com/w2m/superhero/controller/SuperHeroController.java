package com.w2m.superhero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.superhero.annotation.MeasureExecution;
import com.w2m.superhero.dto.SuperHeroDTO;
import com.w2m.superhero.exception.SuperHeroException;
import com.w2m.superhero.services.SuperHeroService;

@RestController
@RequestMapping("super_hero")
public class SuperHeroController {

	@Autowired
	SuperHeroService superHeroService;

	@GetMapping
	public List<SuperHeroDTO> getAll() {
		return superHeroService.getAll();
	}

	@GetMapping("/{id}")
	public SuperHeroDTO getById(@PathVariable Long id) {
		return superHeroService.getById(id);
	}

	@MeasureExecution
	@GetMapping("/containing")
	public List<SuperHeroDTO> getAllContaining(@RequestParam String subString) {
		return superHeroService.getAllContaining(subString);
	}

	@PostMapping
	public ResponseEntity<Void> add(@RequestBody SuperHeroDTO superHeroDTO) {
		superHeroService.add(superHeroDTO);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody SuperHeroDTO superHeroDTO) throws SuperHeroException {
		superHeroService.update(superHeroDTO);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestBody SuperHeroDTO superHeroDTO) throws SuperHeroException {
		superHeroService.delete(superHeroDTO);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(SuperHeroException.class)
	public ResponseEntity<String> handleExceptions(SuperHeroException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}
