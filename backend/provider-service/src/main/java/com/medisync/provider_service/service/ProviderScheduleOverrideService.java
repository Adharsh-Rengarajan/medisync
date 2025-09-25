package com.medisync.provider_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.dto.ProviderScheduleOverrideDto;
import com.medisync.provider_service.entity.ProviderScheduleOverride;
import com.medisync.provider_service.repository.ProviderScheduleOverrideRepository;

@Service
public class ProviderScheduleOverrideService {

	private final ProviderScheduleOverrideRepository overrideRepository;

	@Autowired
	public ProviderScheduleOverrideService(ProviderScheduleOverrideRepository overrideRepository) {
		this.overrideRepository = overrideRepository;
	}

	public ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> createOverride(ProviderScheduleOverrideDto dto) {
		try {
			if (dto == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Override data cannot be null"));
			}
			if (dto.getProviderId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("Provider ID is required"));
			}
			if (dto.getOverrideDate() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Override date is required"));
			}
			if (dto.getAvailable() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Available flag is required"));
			}
			if (dto.getAvailable()) {
				if (dto.getStartTime() == null || dto.getEndTime() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(ApiResponse.fail("Start and End time required when available = true"));
				}
				if (!dto.getStartTime().isBefore(dto.getEndTime())) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(ApiResponse.fail("Start time must be before End time"));
				}
			}
			if (overrideRepository.findByProviderIdAndOverrideDate(dto.getProviderId(), dto.getOverrideDate())
					.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(ApiResponse.fail("Override already exists for this date"));
			}
			ProviderScheduleOverride override = new ProviderScheduleOverride(null, dto.getProviderId(),
					dto.getOverrideDate(), dto.getAvailable(), dto.getStartTime(), dto.getEndTime(), null);
			ProviderScheduleOverride saved = overrideRepository.save(override);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(ApiResponse.ok("Override created successfully", mapToDto(saved)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.fail("Error creating override: " + e.getMessage()));
		}
	}

	public ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> getOverrideById(UUID overrideId) {
		try {
			if (overrideId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Override ID cannot be null"));
			}
			Optional<ProviderScheduleOverride> overrideOpt = overrideRepository.findById(overrideId);
			if (overrideOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail("Override not found"));
			}
			return ResponseEntity.ok(ApiResponse.ok("Override found", mapToDto(overrideOpt.get())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.fail("Error fetching override: " + e.getMessage()));
		}
	}

	public ResponseEntity<ApiResponse<PageResponse<ProviderScheduleOverrideDto>>> getOverridesByProvider(
			UUID providerId, int page, int size) {
		try {
			if (providerId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Provider ID cannot be null"));
			}
			if (page < 0 || size <= 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Page index must be >= 0 and size > 0"));
			}
			List<ProviderScheduleOverride> overrides = overrideRepository.findByProviderId(providerId);
			int totalItems = overrides.size();
			int totalPages = (int) Math.ceil((double) totalItems / size);
			int fromIndex = page * size;
			int toIndex = Math.min(fromIndex + size, totalItems);
			if (fromIndex >= totalItems) {
				return ResponseEntity.ok(ApiResponse.ok("No overrides on this page",
						new PageResponse<>(Collections.emptyList(), totalItems, page, size, totalPages)));
			}
			List<ProviderScheduleOverrideDto> overrideDtos = overrides.subList(fromIndex, toIndex).stream()
					.map(this::mapToDto).collect(Collectors.toList());
			PageResponse<ProviderScheduleOverrideDto> response = new PageResponse<>(overrideDtos, totalItems, page,
					size, totalPages);
			return ResponseEntity.ok(ApiResponse.ok("Overrides fetched successfully", response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.fail("Error fetching overrides: " + e.getMessage()));
		}
	}

	public ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> updateOverride(UUID overrideId,
			ProviderScheduleOverrideDto dto) {
		try {
			if (overrideId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Override ID cannot be null"));
			}
			Optional<ProviderScheduleOverride> overrideOpt = overrideRepository.findById(overrideId);
			if (overrideOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail("Override not found"));
			}
			ProviderScheduleOverride override = overrideOpt.get();
			if (dto.getAvailable() != null) {
				override.setAvailable(dto.getAvailable());
				if (dto.getAvailable()) {
					if (dto.getStartTime() == null || dto.getEndTime() == null) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(ApiResponse.fail("Start and End time required when available = true"));
					}
					if (!dto.getStartTime().isBefore(dto.getEndTime())) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(ApiResponse.fail("Start time must be before End time"));
					}
					override.setStartTime(dto.getStartTime());
					override.setEndTime(dto.getEndTime());
				} else {
					override.setStartTime(null);
					override.setEndTime(null);
				}
			}
			ProviderScheduleOverride updated = overrideRepository.save(override);
			return ResponseEntity.ok(ApiResponse.ok("Override updated successfully", mapToDto(updated)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.fail("Error updating override: " + e.getMessage()));
		}
	}

	public ResponseEntity<ApiResponse<Void>> deleteOverride(UUID overrideId) {
		try {
			if (overrideId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ApiResponse.fail("Override ID cannot be null"));
			}
			if (!overrideRepository.existsById(overrideId)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail("Override not found"));
			}
			overrideRepository.deleteById(overrideId);
			return ResponseEntity.ok(ApiResponse.ok("Override deleted successfully", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ApiResponse.fail("Error deleting override: " + e.getMessage()));
		}
	}

	private ProviderScheduleOverrideDto mapToDto(ProviderScheduleOverride override) {
		return new ProviderScheduleOverrideDto(override.getOverrideId(), override.getProviderId(),
				override.getOverrideDate(), override.getAvailable(), override.getStartTime(), override.getEndTime());
	}
}
