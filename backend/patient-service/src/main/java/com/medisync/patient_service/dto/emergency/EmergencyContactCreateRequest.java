package com.medisync.patient_service.dto.emergency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class EmergencyContactCreateRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String relationship;

    @Size(max = 20)
    private String phone;

    @NotNull
    private UUID patientId;

    public EmergencyContactCreateRequest() { }

    public String getName() { return name; }
    public String getRelationship() { return relationship; }
    public String getPhone() { return phone; }
    public UUID getPatientId() { return patientId; }

    public void setName(String name) { this.name = name; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
}
