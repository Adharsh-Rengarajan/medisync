package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.SpecialtyDto;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.Specialty;
import com.medisync.provider_service.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public ResponseEntity<ApiResponse<SpecialtyDto>> createSpecialty(SpecialtyDto dto) {
        try {
            if (dto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Specialty data cannot be null"));
            }
            if (!StringUtils.hasText(dto.getName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Specialty name is required"));
            }
            if (specialtyRepository.existsByName(dto.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("Specialty already exists"));
            }
            Specialty specialty = new Specialty(null, dto.getName(), dto.getDescription(), null);
            Specialty saved = specialtyRepository.save(specialty);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Specialty created successfully", mapToDto(saved)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error creating specialty: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<SpecialtyDto>> getSpecialtyById(UUID specialtyId) {
        try {
            if (specialtyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Specialty ID cannot be null"));
            }
            Optional<Specialty> specialtyOpt = specialtyRepository.findById(specialtyId);
            if (specialtyOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Specialty not found"));
            }
            return ResponseEntity.ok(ApiResponse.ok("Specialty found", mapToDto(specialtyOpt.get())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching specialty: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<PageResponse<SpecialtyDto>>> getAllSpecialties(int page, int size) {
        try {
            if (page < 0 || size <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Page index must be >= 0 and size > 0"));
            }
            List<Specialty> specialties = specialtyRepository.findAll();
            int totalItems = specialties.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            if (fromIndex >= totalItems) {
                return ResponseEntity.ok(ApiResponse.ok("No specialties on this page",
                        new PageResponse<>(Collections.emptyList(), totalItems, page, size, totalPages)));
            }
            List<SpecialtyDto> specialtyDtos = specialties.subList(fromIndex, toIndex)
                    .stream().map(this::mapToDto).collect(Collectors.toList());
            PageResponse<SpecialtyDto> response = new PageResponse<>(specialtyDtos, totalItems, page, size, totalPages);
            return ResponseEntity.ok(ApiResponse.ok("Specialties fetched successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching specialties: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<SpecialtyDto>> updateSpecialty(UUID specialtyId, SpecialtyDto dto) {
        try {
            if (specialtyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Specialty ID cannot be null"));
            }
            Optional<Specialty> specialtyOpt = specialtyRepository.findById(specialtyId);
            if (specialtyOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Specialty not found"));
            }
            Specialty specialty = specialtyOpt.get();
            if (StringUtils.hasText(dto.getName())) specialty.setName(dto.getName());
            if (StringUtils.hasText(dto.getDescription())) specialty.setDescription(dto.getDescription());
            Specialty updated = specialtyRepository.save(specialty);
            return ResponseEntity.ok(ApiResponse.ok("Specialty updated successfully", mapToDto(updated)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error updating specialty: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<Void>> deleteSpecialty(UUID specialtyId) {
        try {
            if (specialtyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Specialty ID cannot be null"));
            }
            if (!specialtyRepository.existsById(specialtyId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Specialty not found"));
            }
            specialtyRepository.deleteById(specialtyId);
            return ResponseEntity.ok(ApiResponse.ok("Specialty deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error deleting specialty: " + e.getMessage()));
        }
    }

    private SpecialtyDto mapToDto(Specialty specialty) {
        return new SpecialtyDto(
                specialty.getSpecialtyId(),
                specialty.getName(),
                specialty.getDescription()
        );
    }
}
