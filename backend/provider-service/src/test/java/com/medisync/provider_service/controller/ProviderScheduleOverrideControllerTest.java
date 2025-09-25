package com.medisync.provider_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.medisync.provider_service.dto.ProviderScheduleOverrideDto;
import com.medisync.provider_service.service.ProviderScheduleOverrideService;

@WebMvcTest(ProviderScheduleOverrideController.class)
@AutoConfigureMockMvc(addFilters = false)

public class ProviderScheduleOverrideControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProviderScheduleOverrideService overrideService;

	@Autowired
	private ObjectMapper objectMapper;

	private ProviderScheduleOverrideDto overrideDto;

	@BeforeEach
	void setUp() {
		overrideDto = new ProviderScheduleOverrideDto(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), true,
				LocalTime.of(10, 0), LocalTime.of(14, 0));
	}

	@Test
	void testCreateOverride() throws Exception {
		Mockito.when(overrideService.createOverride(Mockito.any())).thenReturn(ResponseEntity.status(201)
				.body(com.medisync.provider_service.dto.ApiResponse.ok("Created", overrideDto)));

		mockMvc.perform(post("/api/overrides").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(overrideDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.available").value(true));
	}

	@Test
	void testGetOverrideById() throws Exception {
		UUID id = overrideDto.getOverrideId();
		Mockito.when(overrideService.getOverrideById(id))
				.thenReturn(ResponseEntity.ok(com.medisync.provider_service.dto.ApiResponse.ok("Found", overrideDto)));

		mockMvc.perform(get("/api/overrides/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.available").value(true));
	}
}
