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
import com.medisync.provider_service.dto.ProviderDto;
import com.medisync.provider_service.service.ProviderService;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

	private final ProviderService providerService;

	@Autowired
	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<ProviderDto>> createProvider(@RequestBody ProviderDto dto) {
		return providerService.createProvider(dto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProviderDto>> getProviderById(@PathVariable("id") UUID id) {
		return providerService.getProviderById(id);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<ProviderDto>>> getAllProviders(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return providerService.getAllProviders(page, size);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProviderDto>> updateProvider(@PathVariable("id") UUID id,
			@RequestBody ProviderDto dto) {
		return providerService.updateProvider(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteProvider(@PathVariable("id") UUID id) {
		return providerService.deleteProvider(id);
	}
}
