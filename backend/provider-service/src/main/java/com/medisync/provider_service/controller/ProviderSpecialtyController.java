package com.medisync.provider_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.entity.ProviderSpecialty;
import com.medisync.provider_service.service.ProviderSpecialtyService;

@RestController
@RequestMapping("/api/provider-specialties")
public class ProviderSpecialtyController {

	private final ProviderSpecialtyService providerSpecialtyService;

	@Autowired
	public ProviderSpecialtyController(ProviderSpecialtyService providerSpecialtyService) {
		this.providerSpecialtyService = providerSpecialtyService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<ProviderSpecialty>> assignSpecialty(@RequestParam UUID providerId,
			@RequestParam UUID specialtyId) {
		return providerSpecialtyService.assignSpecialty(providerId, specialtyId);
	}

	@GetMapping("/provider/{providerId}")
	public ResponseEntity<ApiResponse<PageResponse<ProviderSpecialty>>> getSpecialtiesByProvider(
			@PathVariable UUID providerId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return providerSpecialtyService.getSpecialtiesByProvider(providerId, page, size);
	}

	@GetMapping("/specialty/{specialtyId}")
	public ResponseEntity<ApiResponse<PageResponse<ProviderSpecialty>>> getProvidersBySpecialty(
			@PathVariable UUID specialtyId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return providerSpecialtyService.getProvidersBySpecialty(specialtyId, page, size);
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> removeSpecialty(@RequestParam UUID providerId,
			@RequestParam UUID specialtyId) {
		return providerSpecialtyService.removeSpecialty(providerId, specialtyId);
	}
}
