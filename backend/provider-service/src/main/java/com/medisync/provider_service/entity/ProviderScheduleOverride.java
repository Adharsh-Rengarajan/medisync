package com.medisync.provider_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "provider_schedule_overrides", uniqueConstraints = @UniqueConstraint(columnNames = { "provider_id",
		"override_date" }))
public class ProviderScheduleOverride {

	@Id
	@GeneratedValue
	@Column(name = "override_id", columnDefinition = "UUID")
	private UUID overrideId;

	@Column(name = "provider_id", nullable = false, columnDefinition = "UUID")
	private UUID providerId;

	@Column(name = "override_date", nullable = false)
	private LocalDate overrideDate;

	@Column(nullable = false)
	private Boolean available;

	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "end_time")
	private LocalTime endTime;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	public ProviderScheduleOverride() {
	}

	public ProviderScheduleOverride(UUID overrideId, UUID providerId, LocalDate overrideDate, Boolean available,
			LocalTime startTime, LocalTime endTime, LocalDateTime createdAt) {
		this.overrideId = overrideId;
		this.providerId = providerId;
		this.overrideDate = overrideDate;
		this.available = available;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdAt = createdAt;
	}

	public UUID getOverrideId() {
		return overrideId;
	}

	public void setOverrideId(UUID overrideId) {
		this.overrideId = overrideId;
	}

	public UUID getProviderId() {
		return providerId;
	}

	public void setProviderId(UUID providerId) {
		this.providerId = providerId;
	}

	public LocalDate getOverrideDate() {
		return overrideDate;
	}

	public void setOverrideDate(LocalDate overrideDate) {
		this.overrideDate = overrideDate;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
