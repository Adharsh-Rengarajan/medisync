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
import com.medisync.provider_service.dto.SpecialtyDto;
import com.medisync.provider_service.service.SpecialtyService;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

	private final SpecialtyService specialtyService;

	@Autowired
	public SpecialtyController(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<SpecialtyDto>> createSpecialty(@RequestBody SpecialtyDto dto) {
		return specialtyService.createSpecialty(dto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<SpecialtyDto>> getSpecialtyById(@PathVariable("id") UUID id) {
		return specialtyService.getSpecialtyById(id);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<SpecialtyDto>>> getAllSpecialties(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return specialtyService.getAllSpecialties(page, size);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<SpecialtyDto>> updateSpecialty(@PathVariable("id") UUID id,
			@RequestBody SpecialtyDto dto) {
		return specialtyService.updateSpecialty(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteSpecialty(@PathVariable("id") UUID id) {
		return specialtyService.deleteSpecialty(id);
	}
}
