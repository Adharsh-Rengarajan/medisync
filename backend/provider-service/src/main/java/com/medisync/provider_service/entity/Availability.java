package com.medisync.provider_service.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "availabilities")
public class Availability {

	@Id
	@GeneratedValue
	@Column(name = "availability_id", columnDefinition = "UUID")
	private UUID availabilityId;

	@Column(name = "provider_id", nullable = false, columnDefinition = "UUID")
	private UUID providerId;

	@Column(name = "day_of_week", nullable = false, length = 20)
	private String dayOfWeek;

	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	public Availability() {
	}

	public Availability(UUID availabilityId, UUID providerId, String dayOfWeek, LocalTime startTime, LocalTime endTime,
			Boolean isActive, LocalDateTime createdAt) {
		this.availabilityId = availabilityId;
		this.providerId = providerId;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public UUID getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(UUID availabilityId) {
		this.availabilityId = availabilityId;
	}

	public UUID getProviderId() {
		return providerId;
	}

	public void setProviderId(UUID providerId) {
		this.providerId = providerId;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
