package com.medisync.patient_service.dto.patientinsurance;

public class PatientInsuranceUpdateRequest {

    private Boolean isPrimary;

    public PatientInsuranceUpdateRequest() { }

    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }
}
