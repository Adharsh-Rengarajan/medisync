package com.medisync.provider_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class ProviderScheduleOverrideDto {

    private UUID overrideId;
    private UUID providerId;
    private LocalDate overrideDate;
    private Boolean available;
    private LocalTime startTime;
    private LocalTime endTime;

    public ProviderScheduleOverrideDto() { }

    public ProviderScheduleOverrideDto(UUID overrideId, UUID providerId, LocalDate overrideDate,
                                       Boolean available, LocalTime startTime, LocalTime endTime) {
        this.overrideId = overrideId;
        this.providerId = providerId;
        this.overrideDate = overrideDate;
        this.available = available;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getOverrideId() { return overrideId; }
    public void setOverrideId(UUID overrideId) { this.overrideId = overrideId; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public LocalDate getOverrideDate() { return overrideDate; }
    public void setOverrideDate(LocalDate overrideDate) { this.overrideDate = overrideDate; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}
