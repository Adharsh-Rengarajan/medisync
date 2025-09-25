package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.ProviderDto;
import com.medisync.provider_service.dto.PageResponse;
import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.Provider;
import com.medisync.provider_service.repository.ProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final Validator validator;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    public ResponseEntity<ApiResponse<ProviderDto>> createProvider(ProviderDto dto) {
        try {
            if (dto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider data cannot be null"));
            }

            // Basic validation
            if (!StringUtils.hasText(dto.getFirstName()) || !StringUtils.hasText(dto.getLastName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("First name and Last name are required"));
            }
            if (!StringUtils.hasText(dto.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Email is required"));
            }
            if (providerRepository.existsByEmail(dto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("Email already exists"));
            }

            Provider provider = new Provider(
                    null,
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    dto.getPhone(),
                    dto.getGender(),
                    dto.getDateOfBirth(),
                    dto.getYearsOfExperience(),
                    dto.getIsActive() != null ? dto.getIsActive() : true,
                    null,
                    null
            );

            Provider saved = providerRepository.save(provider);
            ProviderDto responseDto = mapToDto(saved);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Provider created successfully", responseDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error creating provider: " + e.getMessage()));
        }
    }


    public ResponseEntity<ApiResponse<ProviderDto>> getProviderById(UUID providerId) {
        try {
            if (providerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID cannot be null"));
            }

            Optional<Provider> providerOpt = providerRepository.findById(providerId);
            if (providerOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Provider not found"));
            }

            return ResponseEntity.ok(ApiResponse.ok("Provider found", mapToDto(providerOpt.get())));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching provider: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<PageResponse<ProviderDto>>> getAllProviders(int page, int size) {
        try {
            if (page < 0 || size <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Page index must be >= 0 and size > 0"));
            }

            List<Provider> providers = providerRepository.findAll();

            int totalItems = providers.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, totalItems);

            if (fromIndex >= totalItems) {
                return ResponseEntity.ok(ApiResponse.ok("No providers on this page",
                        new PageResponse<>(Collections.emptyList(), totalItems, page, size, totalPages)));
            }

            List<ProviderDto> providerDtos = providers.subList(fromIndex, toIndex)
                    .stream().map(this::mapToDto).collect(Collectors.toList());

            PageResponse<ProviderDto> response = new PageResponse<>(providerDtos, totalItems, page, size, totalPages);

            return ResponseEntity.ok(ApiResponse.ok("Providers fetched successfully", response));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error fetching providers: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<ProviderDto>> updateProvider(UUID providerId, ProviderDto dto) {
        try {
            if (providerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID cannot be null"));
            }

            Optional<Provider> providerOpt = providerRepository.findById(providerId);
            if (providerOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Provider not found"));
            }

            Provider provider = providerOpt.get();

            if (StringUtils.hasText(dto.getFirstName())) provider.setFirstName(dto.getFirstName());
            if (StringUtils.hasText(dto.getLastName())) provider.setLastName(dto.getLastName());
            if (StringUtils.hasText(dto.getPhone())) provider.setPhone(dto.getPhone());
            if (StringUtils.hasText(dto.getGender())) provider.setGender(dto.getGender());
            if (dto.getDateOfBirth() != null) provider.setDateOfBirth(dto.getDateOfBirth());
            if (dto.getYearsOfExperience() != null) provider.setYearsOfExperience(dto.getYearsOfExperience());
            if (dto.getIsActive() != null) provider.setIsActive(dto.getIsActive());

            Provider updated = providerRepository.save(provider);

            return ResponseEntity.ok(ApiResponse.ok("Provider updated successfully", mapToDto(updated)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error updating provider: " + e.getMessage()));
        }
    }


    public ResponseEntity<ApiResponse<Void>> deleteProvider(UUID providerId) {
        try {
            if (providerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("Provider ID cannot be null"));
            }

            if (!providerRepository.existsById(providerId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("Provider not found"));
            }

            providerRepository.deleteById(providerId);

            return ResponseEntity.ok(ApiResponse.ok("Provider deleted successfully", null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Error deleting provider: " + e.getMessage()));
        }
    }
    private ProviderDto mapToDto(Provider provider) {
        return new ProviderDto(
                provider.getProviderId(),
                provider.getFirstName(),
                provider.getLastName(),
                provider.getEmail(),
                provider.getPhone(),
                provider.getGender(),
                provider.getDateOfBirth(),
                provider.getYearsOfExperience(),
                provider.getIsActive()
        );
    }
}
