package com.medisync.provider_service.dto;

import java.time.LocalTime;
import java.util.UUID;

public class AvailabilityDto {

    private UUID availabilityId;
    private UUID providerId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;

    public AvailabilityDto() { }

    public AvailabilityDto(UUID availabilityId, UUID providerId, String dayOfWeek,
                           LocalTime startTime, LocalTime endTime, Boolean isActive) {
        this.availabilityId = availabilityId;
        this.providerId = providerId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
    }

    public UUID getAvailabilityId() { return availabilityId; }
    public void setAvailabilityId(UUID availabilityId) { this.availabilityId = availabilityId; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
