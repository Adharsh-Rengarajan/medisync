package com.medisync.provider_service.repository;

import com.medisync.provider_service.entity.ProviderScheduleOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProviderScheduleOverrideRepository extends JpaRepository<ProviderScheduleOverride, UUID> {

    List<ProviderScheduleOverride> findByProviderId(UUID providerId);

    Optional<ProviderScheduleOverride> findByProviderIdAndOverrideDate(UUID providerId, LocalDate overrideDate);
}
