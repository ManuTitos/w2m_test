package com.w2m.superhero.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.w2m.superhero.dto.SuperHeroDTO;
import com.w2m.superhero.entities.SuperHeroEntity;
import com.w2m.superhero.exception.SuperHeroException;
import com.w2m.superhero.mappers.SuperHeroMapper;
import com.w2m.superhero.repositories.SuperHeroRepository;

@ExtendWith(MockitoExtension.class)
class SuperHeroServiceTest {

	@Mock
	SuperHeroRepository superHeroRepository;

	@Mock
	SuperHeroMapper superHeroMapper;

	@InjectMocks
	SuperHeroServiceImpl service;

	@Test
	void getAllTest() {
		SuperHeroEntity mock1 = Mockito.mock(SuperHeroEntity.class);
		SuperHeroEntity mock2 = Mockito.mock(SuperHeroEntity.class);
		List<SuperHeroEntity> list = Arrays.asList(mock1, mock2);

		SuperHeroDTO dto1 = new SuperHeroDTO();
		dto1.setId(1L);
		dto1.setName("Superman");
		SuperHeroDTO dto2 = new SuperHeroDTO();
		dto2.setId(2L);
		dto2.setName("Batman");

		when(superHeroRepository.findAll()).thenReturn(list);
		when(superHeroMapper.entityToDTO(mock1)).thenReturn(dto1);
		when(superHeroMapper.entityToDTO(mock2)).thenReturn(dto2);

		List<SuperHeroDTO> result = service.getAll();

		assertEquals(2, result.size());
		assertTrue("Superman".equalsIgnoreCase(result.get(0).getName()));
		assertTrue("Batman".equalsIgnoreCase(result.get(1).getName()));
	}

	@Test
	void getByIdTest() {
		SuperHeroEntity entity = new SuperHeroEntity();
		entity.setId(0L);
		entity.setName("test");

		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		when(superHeroRepository.findById(0L)).thenReturn(Optional.of(entity));
		when(superHeroMapper.entityToDTO(entity)).thenReturn(dto);

		SuperHeroDTO result = service.getById(0L);

		assertTrue("test".equalsIgnoreCase(result.getName()));
	}

	@Test
	void getByIdNotFoundTest() {
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		when(superHeroRepository.findById(0L)).thenReturn(Optional.empty());

		SuperHeroDTO result = service.getById(0L);

		assertNull(result);
	}

	@Test
	void getAllContainingTest() {
		SuperHeroEntity mock1 = Mockito.mock(SuperHeroEntity.class);
		SuperHeroEntity mock2 = Mockito.mock(SuperHeroEntity.class);
		List<SuperHeroEntity> list = Arrays.asList(mock1, mock2);

		SuperHeroDTO dto1 = new SuperHeroDTO();
		dto1.setId(1L);
		dto1.setName("Superman");
		SuperHeroDTO dto2 = new SuperHeroDTO();
		dto2.setId(2L);
		dto2.setName("Batman");

		when(superHeroRepository.findByNameContaining("man")).thenReturn(list);
		when(superHeroMapper.entityToDTO(mock1)).thenReturn(dto1);
		when(superHeroMapper.entityToDTO(mock2)).thenReturn(dto2);

		List<SuperHeroDTO> result = service.getAllContaining("man");

		assertEquals(2, result.size());
		assertTrue("Superman".equalsIgnoreCase(result.get(0).getName()));
		assertTrue("Batman".equalsIgnoreCase(result.get(1).getName()));
	}

	@Test
	void addTest() {
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		SuperHeroEntity entity = new SuperHeroEntity();
		entity.setId(0L);
		entity.setName("test");

		when(superHeroMapper.dtoToEntity(dto)).thenReturn(entity);
		when(superHeroRepository.save(entity)).thenReturn(entity);

		service.add(dto);

		verify(superHeroRepository).save(entity);
	}

	@Test
	void updateTest() throws SuperHeroException {
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		SuperHeroEntity entity = new SuperHeroEntity();
		entity.setId(0L);
		entity.setName("test");

		when(superHeroRepository.findById(0L)).thenReturn(Optional.of(entity));
		when(superHeroMapper.dtoToEntity(dto)).thenReturn(entity);
		when(superHeroRepository.save(entity)).thenReturn(entity);

		service.update(dto);

		verify(superHeroRepository).save(entity);
	}
	
	@Test
	void updateNotFoundTest() throws SuperHeroException {
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		when(superHeroRepository.findById(0L)).thenReturn(Optional.empty());

		SuperHeroException exception = assertThrows(SuperHeroException.class, () -> {
			service.update(dto);
		});

		String expectedMessage = "The super hero could not be found in database. Please check the data sent again.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void deleteTest() throws SuperHeroException {
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		SuperHeroEntity entity = new SuperHeroEntity();
		entity.setId(0L);
		entity.setName("test");

		when(superHeroRepository.findById(0L)).thenReturn(Optional.of(entity));
		when(superHeroMapper.dtoToEntity(dto)).thenReturn(entity);
		doNothing().when(superHeroRepository).delete(entity);

		service.delete(dto);

		verify(superHeroRepository).delete(entity);
	}
	
	@Test
	void deleteNotFoundTest() throws SuperHeroException {
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(0L);
		dto.setName("test");

		when(superHeroRepository.findById(0L)).thenReturn(Optional.empty());

		SuperHeroException exception = assertThrows(SuperHeroException.class, () -> {
			service.delete(dto);
		});

		String expectedMessage = "The super hero could not be found in database. Please check the data sent again.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}
