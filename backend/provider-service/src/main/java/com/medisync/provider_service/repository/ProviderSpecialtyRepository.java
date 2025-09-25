package com.medisync.provider_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisync.provider_service.entity.ProviderSpecialty;

public interface ProviderSpecialtyRepository extends JpaRepository<ProviderSpecialty, UUID> {

	List<ProviderSpecialty> findByProviderId(UUID providerId);

	List<ProviderSpecialty> findBySpecialtyId(UUID specialtyId);

	Optional<ProviderSpecialty> findByProviderIdAndSpecialtyId(UUID providerId, UUID specialtyId);

	boolean existsByProviderIdAndSpecialtyId(UUID providerId, UUID specialtyId);
}
