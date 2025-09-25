package com.medisync.provider_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medisync.provider_service.dto.SpecialtyDto;
import com.medisync.provider_service.service.SpecialtyService;

@WebMvcTest(SpecialtyController.class)
@AutoConfigureMockMvc(addFilters = false)

public class SpecialtyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SpecialtyService specialtyService;

	@Autowired
	private ObjectMapper objectMapper;

	private SpecialtyDto specialtyDto;

	@BeforeEach
	void setUp() {
		specialtyDto = new SpecialtyDto(UUID.randomUUID(), "Cardiology", "Heart specialist");
	}

	@Test
	void testCreateSpecialty() throws Exception {
		Mockito.when(specialtyService.createSpecialty(Mockito.any())).thenReturn(ResponseEntity.status(201)
				.body(com.medisync.provider_service.dto.ApiResponse.ok("Created", specialtyDto)));

		mockMvc.perform(post("/api/specialties").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(specialtyDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Cardiology"));
	}

	@Test
	void testGetSpecialtyById() throws Exception {
		UUID id = specialtyDto.getSpecialtyId();
		Mockito.when(specialtyService.getSpecialtyById(id))
				.thenReturn(ResponseEntity.ok(com.medisync.provider_service.dto.ApiResponse.ok("Found", specialtyDto)));

		mockMvc.perform(get("/api/specialties/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Cardiology"));
	}
}
