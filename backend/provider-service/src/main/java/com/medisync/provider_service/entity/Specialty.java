package com.medisync.provider_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class Specialty {

	@Id
	@GeneratedValue
	@Column(name = "specialty_id", columnDefinition = "UUID")
	private UUID specialtyId;

	@Column(nullable = false, unique = true, length = 100)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	public Specialty() {
	}

	public Specialty(UUID specialtyId, String name, String description, LocalDateTime createdAt) {
		this.specialtyId = specialtyId;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
	}

	public UUID getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(UUID specialtyId) {
		this.specialtyId = specialtyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
