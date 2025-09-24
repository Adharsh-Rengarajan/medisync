package com.medisync.patient_service.dto.patientinsurance;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PatientInsuranceCreateRequest {

    @NotNull
    private UUID patientId;

    @NotNull
    private UUID insuranceId;

    @NotNull
    private Boolean isPrimary;

    public PatientInsuranceCreateRequest() { }

    public UUID getPatientId() { return patientId; }
    public UUID getInsuranceId() { return insuranceId; }
    public Boolean getIsPrimary() { return isPrimary; }

    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public void setInsuranceId(UUID insuranceId) { this.insuranceId = insuranceId; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }
}
