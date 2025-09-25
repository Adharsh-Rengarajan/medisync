package com.medisync.provider_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisync.provider_service.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {

	Optional<Provider> findByEmail(String email);

	boolean existsByEmail(String email);
}
