package com.medisync.provider_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.medisync.provider_service.dto.AvailabilityDto;
import com.medisync.provider_service.service.AvailabilityService;

@WebMvcTest(AvailabilityController.class)
@AutoConfigureMockMvc(addFilters = false)

public class AvailabilityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AvailabilityService availabilityService;

	@Autowired
	private ObjectMapper objectMapper;

	private AvailabilityDto availabilityDto;

	@BeforeEach
	void setUp() {
		availabilityDto = new AvailabilityDto(UUID.randomUUID(), UUID.randomUUID(), "MONDAY", LocalTime.of(9, 0),
				LocalTime.of(17, 0), true);
	}

	@Test
	void testCreateAvailability() throws Exception {
		Mockito.when(availabilityService.createAvailability(Mockito.any())).thenReturn(ResponseEntity.status(201)
				.body(com.medisync.provider_service.dto.ApiResponse.ok("Created", availabilityDto)));

		mockMvc.perform(post("/api/availabilities").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(availabilityDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.dayOfWeek").value("MONDAY"));
	}

	@Test
	void testGetAvailabilityById() throws Exception {
		UUID id = availabilityDto.getAvailabilityId();
		Mockito.when(availabilityService.getAvailabilityById(id)).thenReturn(
				ResponseEntity.ok(com.medisync.provider_service.dto.ApiResponse.ok("Found", availabilityDto)));

		mockMvc.perform(get("/api/availabilities/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.dayOfWeek").value("MONDAY"));
	}
}
