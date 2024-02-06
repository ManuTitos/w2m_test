package com.w2m.superhero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.superhero.dto.SuperHeroDTO;

@SpringBootTest(classes = ApplicationTests.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	void test010getAll() throws Exception {
		String uri = "/super_hero";
		MvcResult result = mockMvc.perform(get(uri)).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		assertEquals("[]", result.getResponse().getContentAsString());
	}

	@ParameterizedTest
	@ValueSource(strings = { "Superman", "Spiderman", "Batman", "Hulk" })
	void test020add(String argument) throws Exception {
		String uri = "/super_hero";
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setName(argument);
		String requestJson = new ObjectMapper().writeValueAsString(dto);

		MvcResult result = mockMvc.perform(post(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(requestJson)).andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void test021getAll() throws Exception {
		String uri = "/super_hero";
		MvcResult result = mockMvc.perform(get(uri)).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		assertEquals(
				"[{\"id\":1,\"name\":\"Superman\"},{\"id\":2,\"name\":\"Spiderman\"},{\"id\":3,\"name\":\"Batman\"},{\"id\":4,\"name\":\"Hulk\"}]",
				result.getResponse().getContentAsString());
	}

	@Test
	void test022getId() throws Exception {
		String uri = "/super_hero/{id}";
		MvcResult result = mockMvc.perform(get(uri, 2l)).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		assertEquals("{\"id\":2,\"name\":\"Spiderman\"}", result.getResponse().getContentAsString());
	}

	@Test
	void test023getAllContaining() throws Exception {
		String uri = "/super_hero/containing";
		MvcResult result = mockMvc.perform(get(uri).param("subString", "man")).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		assertEquals(
				"[{\"id\":1,\"name\":\"Superman\"},{\"id\":2,\"name\":\"Spiderman\"},{\"id\":3,\"name\":\"Batman\"}]",
				result.getResponse().getContentAsString());
	}

	@Test
	void test030update() throws Exception {
		String uri = "/super_hero";
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(3L);
		dto.setName("Ironman");
		String requestJson = new ObjectMapper().writeValueAsString(dto);

		MvcResult result = mockMvc.perform(put(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(requestJson)).andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void test031updateWrongId() throws Exception {
		String uri = "/super_hero";
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(10L);
		dto.setName("Aquaman");
		String requestJson = new ObjectMapper().writeValueAsString(dto);

		MvcResult result = mockMvc.perform(put(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(requestJson)).andReturn();

		assertEquals(500, result.getResponse().getStatus());
	}

	@Test
	void test032delete() throws Exception {
		String uri = "/super_hero";
		SuperHeroDTO dto = new SuperHeroDTO();
		dto.setId(1L);
		dto.setName("Superman");
		String requestJson = new ObjectMapper().writeValueAsString(dto);

		MvcResult result = mockMvc.perform(delete(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(requestJson)).andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void test033getAll() throws Exception {
		String uri = "/super_hero";
		MvcResult result = mockMvc.perform(get(uri)).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		assertEquals("[{\"id\":2,\"name\":\"Spiderman\"},{\"id\":3,\"name\":\"Ironman\"},{\"id\":4,\"name\":\"Hulk\"}]",
				result.getResponse().getContentAsString());
	}
}
