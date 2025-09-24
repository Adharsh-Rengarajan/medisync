package com.medisync.patient_service.dto.insurance;

import java.time.LocalDate;
import java.util.UUID;

public class InsuranceResponse {

    private UUID insuranceId;
    private String providerName;
    private String policyNumber;
    private String coverageDetails;
    private LocalDate validTill;

    public InsuranceResponse() { }

    public UUID getInsuranceId() { return insuranceId; }
    public String getProviderName() { return providerName; }
    public String getPolicyNumber() { return policyNumber; }
    public String getCoverageDetails() { return coverageDetails; }
    public LocalDate getValidTill() { return validTill; }

    public void setInsuranceId(UUID insuranceId) { this.insuranceId = insuranceId; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
    public void setCoverageDetails(String coverageDetails) { this.coverageDetails = coverageDetails; }
    public void setValidTill(LocalDate validTill) { this.validTill = validTill; }
}
