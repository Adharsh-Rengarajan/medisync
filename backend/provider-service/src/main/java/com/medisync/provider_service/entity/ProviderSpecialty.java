package com.medisync.provider_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "provider_specialties", uniqueConstraints = @UniqueConstraint(columnNames = { "provider_id",
		"specialty_id" }))
public class ProviderSpecialty {

	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "UUID")
	private UUID id;

	@Column(name = "provider_id", nullable = false, columnDefinition = "UUID")
	private UUID providerId;

	@Column(name = "specialty_id", nullable = false, columnDefinition = "UUID")
	private UUID specialtyId;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	public ProviderSpecialty() {
	}

	public ProviderSpecialty(UUID id, UUID providerId, UUID specialtyId, LocalDateTime createdAt) {
		this.id = id;
		this.providerId = providerId;
		this.specialtyId = specialtyId;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getProviderId() {
		return providerId;
	}

	public void setProviderId(UUID providerId) {
		this.providerId = providerId;
	}

	public UUID getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(UUID specialtyId) {
		this.specialtyId = specialtyId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
