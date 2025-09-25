package com.medisync.provider_service.dto;

import java.util.UUID;

public class SpecialtyDto {

    private UUID specialtyId;
    private String name;
    private String description;

    public SpecialtyDto() { }

    public SpecialtyDto(UUID specialtyId, String name, String description) {
        this.specialtyId = specialtyId;
        this.name = name;
        this.description = description;
    }

    public UUID getSpecialtyId() { return specialtyId; }
    public void setSpecialtyId(UUID specialtyId) { this.specialtyId = specialtyId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
