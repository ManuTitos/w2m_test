package com.w2m.superhero.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import com.w2m.superhero.dto.SuperHeroDTO;
import com.w2m.superhero.entities.SuperHeroEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SuperHeroMapper {
	public SuperHeroDTO entityToDTO(SuperHeroEntity entity);

	public SuperHeroEntity dtoToEntity(SuperHeroDTO dto);
}
