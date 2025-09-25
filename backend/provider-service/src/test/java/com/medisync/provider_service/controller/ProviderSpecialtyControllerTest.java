package com.medisync.provider_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medisync.provider_service.entity.ProviderSpecialty;
import com.medisync.provider_service.service.ProviderSpecialtyService;

@WebMvcTest(ProviderSpecialtyController.class)
@AutoConfigureMockMvc(addFilters = false)

public class ProviderSpecialtyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProviderSpecialtyService providerSpecialtyService;

	@Autowired
	private ObjectMapper objectMapper;

	private ProviderSpecialty providerSpecialty;

	@BeforeEach
	void setUp() {
		providerSpecialty = new ProviderSpecialty(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null);
	}

	@Test
	void testAssignSpecialty() throws Exception {
		UUID providerId = providerSpecialty.getProviderId();
		UUID specialtyId = providerSpecialty.getSpecialtyId();

		Mockito.when(providerSpecialtyService.assignSpecialty(providerId, specialtyId)).thenReturn(ResponseEntity
				.status(201).body(com.medisync.provider_service.dto.ApiResponse.ok("Assigned", providerSpecialty)));

		mockMvc.perform(post("/api/provider-specialties").param("providerId", providerId.toString())
				.param("specialtyId", specialtyId.toString())).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.providerId").value(providerId.toString()));
	}

	@Test
	void testRemoveSpecialty() throws Exception {
		UUID providerId = providerSpecialty.getProviderId();
		UUID specialtyId = providerSpecialty.getSpecialtyId();

		Mockito.when(providerSpecialtyService.removeSpecialty(providerId, specialtyId))
				.thenReturn(ResponseEntity.ok(com.medisync.provider_service.dto.ApiResponse.ok("Removed", null)));

		mockMvc.perform(delete("/api/provider-specialties").param("providerId", providerId.toString())
				.param("specialtyId", specialtyId.toString())).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.message").value("Removed"));
	}
}
