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
import com.medisync.provider_service.dto.AvailabilityDto;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.service.AvailabilityService;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

	private final AvailabilityService availabilityService;

	@Autowired
	public AvailabilityController(AvailabilityService availabilityService) {
		this.availabilityService = availabilityService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<AvailabilityDto>> createAvailability(@RequestBody AvailabilityDto dto) {
		return availabilityService.createAvailability(dto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AvailabilityDto>> getAvailabilityById(@PathVariable("id") UUID id) {
		return availabilityService.getAvailabilityById(id);
	}

	@GetMapping("/provider/{providerId}")
	public ResponseEntity<ApiResponse<PageResponse<AvailabilityDto>>> getAvailabilitiesByProvider(
			@PathVariable UUID providerId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return availabilityService.getAvailabilitiesByProvider(providerId, page, size);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<AvailabilityDto>> updateAvailability(@PathVariable("id") UUID id,
			@RequestBody AvailabilityDto dto) {
		return availabilityService.updateAvailability(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteAvailability(@PathVariable("id") UUID id) {
		return availabilityService.deleteAvailability(id);
	}
}
