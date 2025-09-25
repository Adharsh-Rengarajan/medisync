package com.medisync.provider_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.dto.ProviderScheduleOverrideDto;
import com.medisync.provider_service.service.ProviderScheduleOverrideService;

@RestController
@RequestMapping("/api/overrides")
public class ProviderScheduleOverrideController {

	private final ProviderScheduleOverrideService overrideService;

	@Autowired
	public ProviderScheduleOverrideController(ProviderScheduleOverrideService overrideService) {
		this.overrideService = overrideService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> createOverride(
			@RequestBody ProviderScheduleOverrideDto dto) {
		return overrideService.createOverride(dto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> getOverrideById(@PathVariable("id") UUID id) {
		return overrideService.getOverrideById(id);
	}

	@GetMapping("/provider/{providerId}")
	public ResponseEntity<ApiResponse<PageResponse<ProviderScheduleOverrideDto>>> getOverridesByProvider(
			@PathVariable UUID providerId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return overrideService.getOverridesByProvider(providerId, page, size);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> updateOverride(@PathVariable("id") UUID id,
			@RequestBody ProviderScheduleOverrideDto dto) {
		return overrideService.updateOverride(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteOverride(@PathVariable("id") UUID id) {
		return overrideService.deleteOverride(id);
	}
}
