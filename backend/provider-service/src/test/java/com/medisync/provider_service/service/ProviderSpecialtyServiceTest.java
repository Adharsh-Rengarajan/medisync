package com.medisync.provider_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.ProviderSpecialty;
import com.medisync.provider_service.repository.ProviderSpecialtyRepository;

public class ProviderSpecialtyServiceTest {

	@Mock
	private ProviderSpecialtyRepository providerSpecialtyRepository;

	@InjectMocks
	private ProviderSpecialtyService providerSpecialtyService;

	private ProviderSpecialty providerSpecialty;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		providerSpecialty = new ProviderSpecialty(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null);
	}

	@Test
	void testAssignSpecialty_Success() {
		when(providerSpecialtyRepository.existsByProviderIdAndSpecialtyId(providerSpecialty.getProviderId(),
				providerSpecialty.getSpecialtyId())).thenReturn(false);

		when(providerSpecialtyRepository.save(any(ProviderSpecialty.class))).thenReturn(providerSpecialty);

		ResponseEntity<ApiResponse<ProviderSpecialty>> response = providerSpecialtyService
				.assignSpecialty(providerSpecialty.getProviderId(), providerSpecialty.getSpecialtyId());

		assertEquals(201, response.getStatusCodeValue());
		assertTrue(response.getBody().getSuccess());
	}

	@Test
	void testRemoveSpecialty_NotFound() {
		when(providerSpecialtyRepository.findByProviderIdAndSpecialtyId(providerSpecialty.getProviderId(),
				providerSpecialty.getSpecialtyId())).thenReturn(Optional.empty());

		ResponseEntity<ApiResponse<Void>> response = providerSpecialtyService
				.removeSpecialty(providerSpecialty.getProviderId(), providerSpecialty.getSpecialtyId());

		assertEquals(404, response.getStatusCodeValue());
		assertFalse(response.getBody().getSuccess());
	}
}
