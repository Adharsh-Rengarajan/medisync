package com.medisync.patient_service.dto.patientinsurance;

import java.time.Instant;
import java.util.UUID;

public class PatientInsuranceResponse {

    private UUID id;
    private UUID patientId;
    private UUID insuranceId;
    private Boolean isPrimary;
    private Instant createdAt;

    public PatientInsuranceResponse() { }

    public UUID getId() { return id; }
    public UUID getPatientId() { return patientId; }
    public UUID getInsuranceId() { return insuranceId; }
    public Boolean getIsPrimary() { return isPrimary; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public void setInsuranceId(UUID insuranceId) { this.insuranceId = insuranceId; }
    public void setIsPrimary(Boolean primary) { isPrimary = primary; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
