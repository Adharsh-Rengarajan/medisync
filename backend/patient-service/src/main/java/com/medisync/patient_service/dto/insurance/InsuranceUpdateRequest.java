package com.medisync.patient_service.dto.insurance;

import jakarta.validation.constraints.Size;

public class InsuranceUpdateRequest {

    @Size(max = 150)
    private String providerName;

    @Size(max = 100)
    private String policyNumber;

    @Size(max = 2000)
    private String coverageDetails;

    @Size(max = 10)
    private String validTill;

    public InsuranceUpdateRequest() { }

    public String getProviderName() { return providerName; }
    public String getPolicyNumber() { return policyNumber; }
    public String getCoverageDetails() { return coverageDetails; }
    public String getValidTill() { return validTill; }

    public void setProviderName(String providerName) { this.providerName = providerName; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
    public void setCoverageDetails(String coverageDetails) { this.coverageDetails = coverageDetails; }
    public void setValidTill(String validTill) { this.validTill = validTill; }
}
