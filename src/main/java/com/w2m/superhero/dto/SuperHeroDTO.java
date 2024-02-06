package com.w2m.superhero.dto;

import java.io.Serializable;

public class SuperHeroDTO implements Serializable {

	private static final long serialVersionUID = 635792633217191034L;
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
