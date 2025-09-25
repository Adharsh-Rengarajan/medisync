package com.medisync.provider_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.medisync.provider_service.dto.ProviderDto;
import com.medisync.provider_service.service.ProviderService;

@WebMvcTest(controllers = ProviderController.class)
@AutoConfigureMockMvc(addFilters = false)

public class ProviderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProviderService providerService;

	@Autowired
	private ObjectMapper objectMapper;

	private ProviderDto providerDto;

	@BeforeEach
	void setUp() {
		providerDto = new ProviderDto(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "1234567890", "Male",
				LocalDate.of(1985, 5, 15), 10, true);
	}

	@Test
	void testCreateProvider() throws Exception {
		Mockito.when(providerService.createProvider(Mockito.any())).thenReturn(ResponseEntity.status(201)
				.body(com.medisync.provider_service.dto.ApiResponse.ok("Created", providerDto)));

		mockMvc.perform(post("/api/providers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(providerDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.email").value("john.doe@example.com"));
	}

	@Test
	void testGetProviderById() throws Exception {
		UUID id = providerDto.getProviderId();
		Mockito.when(providerService.getProviderById(id))
				.thenReturn(ResponseEntity.ok(com.medisync.provider_service.dto.ApiResponse.ok("Found", providerDto)));

		mockMvc.perform(get("/api/providers/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.firstName").value("John"));
	}
}
