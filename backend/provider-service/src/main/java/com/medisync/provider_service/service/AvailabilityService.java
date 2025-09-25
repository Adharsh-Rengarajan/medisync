package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.AvailabilityDto;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.Availability;
import com.medisync.provider_service.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public ResponseEntity<ApiResponse<AvailabilityDto>> createAvailability(AvailabilityDto dto) {
        try {
            if (dto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Availability data cannot be null"));
            }
            if (dto.getProviderId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID is required"));
            }
            if (!StringUtils.hasText(dto.getDayOfWeek())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Day of week is required"));
            }
            if (dto.getStartTime() == null || dto.getEndTime() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Start time and End time are required"));
            }
            if (!dto.getStartTime().isBefore(dto.getEndTime())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Start time must be before End time"));
            }
            Availability availability = new Availability(
                    null,
                    dto.getProviderId(),
                    dto.getDayOfWeek(),
                    dto.getStartTime(),
                    dto.getEndTime(),
                    dto.getIsActive() != null ? dto.getIsActive() : true,
                    null
            );
            Availability saved = availabilityRepository.save(availability);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Availability created successfully", mapToDto(saved)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error creating availability: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<AvailabilityDto>> getAvailabilityById(UUID availabilityId) {
        try {
            if (availabilityId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Availability ID cannot be null"));
            }
            Optional<Availability> availabilityOpt = availabilityRepository.findById(availabilityId);
            if (availabilityOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Availability not found"));
            }
            return ResponseEntity.ok(ApiResponse.ok("Availability found", mapToDto(availabilityOpt.get())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching availability: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<PageResponse<AvailabilityDto>>> getAvailabilitiesByProvider(UUID providerId, int page, int size) {
        try {
            if (providerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID cannot be null"));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Page index must be >= 0 and size > 0"));
            }
            List<Availability> availabilities = availabilityRepository.findByProviderId(providerId);
            int totalItems = availabilities.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            if (fromIndex >= totalItems) {
                return ResponseEntity.ok(ApiResponse.ok("No availabilities on this page",
                        new PageResponse<>(Collections.emptyList(), totalItems, page, size, totalPages)));
            }
            List<AvailabilityDto> availabilityDtos = availabilities.subList(fromIndex, toIndex)
                    .stream().map(this::mapToDto).collect(Collectors.toList());
            PageResponse<AvailabilityDto> response = new PageResponse<>(availabilityDtos, totalItems, page, size, totalPages);
            return ResponseEntity.ok(ApiResponse.ok("Availabilities fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching availabilities: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<AvailabilityDto>> updateAvailability(UUID availabilityId, AvailabilityDto dto) {
        try {
            if (availabilityId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Availability ID cannot be null"));
            }
            Optional<Availability> availabilityOpt = availabilityRepository.findById(availabilityId);
            if (availabilityOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Availability not found"));
            }
            Availability availability = availabilityOpt.get();
            if (StringUtils.hasText(dto.getDayOfWeek())) availability.setDayOfWeek(dto.getDayOfWeek());
            if (dto.getStartTime() != null && dto.getEndTime() != null) {
                if (!dto.getStartTime().isBefore(dto.getEndTime())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.fail("Start time must be before End time"));
                }
                availability.setStartTime(dto.getStartTime());
                availability.setEndTime(dto.getEndTime());
            }
            if (dto.getIsActive() != null) availability.setIsActive(dto.getIsActive());
            Availability updated = availabilityRepository.save(availability);
            return ResponseEntity.ok(ApiResponse.ok("Availability updated successfully", mapToDto(updated)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error updating availability: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<Void>> deleteAvailability(UUID availabilityId) {
        try {
            if (availabilityId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Availability ID cannot be null"));
            }
            if (!availabilityRepository.existsById(availabilityId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Availability not found"));
            }
            availabilityRepository.deleteById(availabilityId);
            return ResponseEntity.ok(ApiResponse.ok("Availability deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error deleting availability: " + e.getMessage()));
        }
    }

    private AvailabilityDto mapToDto(Availability availability) {
        return new AvailabilityDto(
                availability.getAvailabilityId(),
                availability.getProviderId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getIsActive()
        );
    }
}
