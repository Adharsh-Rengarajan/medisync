package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.entity.ProviderSpecialty;
import com.medisync.provider_service.repository.ProviderSpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProviderSpecialtyService {

    private final ProviderSpecialtyRepository providerSpecialtyRepository;

    @Autowired
    public ProviderSpecialtyService(ProviderSpecialtyRepository providerSpecialtyRepository) {
        this.providerSpecialtyRepository = providerSpecialtyRepository;
    }

    public ResponseEntity<ApiResponse<ProviderSpecialty>> assignSpecialty(UUID providerId, UUID specialtyId) {
        try {
            if (providerId == null || specialtyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID and Specialty ID are required"));
            }
            if (providerSpecialtyRepository.existsByProviderIdAndSpecialtyId(providerId, specialtyId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("Specialty already assigned to this provider"));
            }
            ProviderSpecialty providerSpecialty = new ProviderSpecialty(null, providerId, specialtyId, null);
            ProviderSpecialty saved = providerSpecialtyRepository.save(providerSpecialty);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Specialty assigned successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error assigning specialty: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<PageResponse<ProviderSpecialty>>> getSpecialtiesByProvider(UUID providerId, int page, int size) {
        try {
            if (providerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID cannot be null"));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Page index must be >= 0 and size > 0"));
            }
            List<ProviderSpecialty> list = providerSpecialtyRepository.findByProviderId(providerId);
            int totalItems = list.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            if (fromIndex >= totalItems) {
                return ResponseEntity.ok(ApiResponse.ok("No specialties on this page",
                        new PageResponse<>(Collections.emptyList(), totalItems, page, size, totalPages)));
            }
            List<ProviderSpecialty> items = list.subList(fromIndex, toIndex);
            PageResponse<ProviderSpecialty> response = new PageResponse<>(items, totalItems, page, size, totalPages);
            return ResponseEntity.ok(ApiResponse.ok("Specialties fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching specialties: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<PageResponse<ProviderSpecialty>>> getProvidersBySpecialty(UUID specialtyId, int page, int size) {
        try {
            if (specialtyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Specialty ID cannot be null"));
            }
            if (page < 0 || size <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Page index must be >= 0 and size > 0"));
            }
            List<ProviderSpecialty> list = providerSpecialtyRepository.findBySpecialtyId(specialtyId);
            int totalItems = list.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            if (fromIndex >= totalItems) {
                return ResponseEntity.ok(ApiResponse.ok("No providers on this page",
                        new PageResponse<>(Collections.emptyList(), totalItems, page, size, totalPages)));
            }
            List<ProviderSpecialty> items = list.subList(fromIndex, toIndex);
            PageResponse<ProviderSpecialty> response = new PageResponse<>(items, totalItems, page, size, totalPages);
            return ResponseEntity.ok(ApiResponse.ok("Providers fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching providers: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<Void>> removeSpecialty(UUID providerId, UUID specialtyId) {
        try {
            if (providerId == null || specialtyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID and Specialty ID are required"));
            }
            Optional<ProviderSpecialty> existing = providerSpecialtyRepository.findByProviderIdAndSpecialtyId(providerId, specialtyId);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Specialty assignment not found for this provider"));
            }
            providerSpecialtyRepository.delete(existing.get());
            return ResponseEntity.ok(ApiResponse.ok("Specialty removed successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error removing specialty: " + e.getMessage()));
        }
    }
}
