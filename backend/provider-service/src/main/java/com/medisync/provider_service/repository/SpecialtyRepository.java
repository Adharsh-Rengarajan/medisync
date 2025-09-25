package com.medisync.provider_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisync.provider_service.entity.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, UUID> {

	Optional<Specialty> findByName(String name);

	boolean existsByName(String name);
}
