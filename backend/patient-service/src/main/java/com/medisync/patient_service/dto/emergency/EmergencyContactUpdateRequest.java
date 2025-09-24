package com.medisync.patient_service.dto.emergency;

import jakarta.validation.constraints.Size;

public class EmergencyContactUpdateRequest {

    @Size(max = 150)
    private String name;

    @Size(max = 100)
    private String relationship;

    @Size(max = 20)
    private String phone;

    public EmergencyContactUpdateRequest() { }

    public String getName() { return name; }
    public String getRelationship() { return relationship; }
    public String getPhone() { return phone; }

    public void setName(String name) { this.name = name; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
    public void setPhone(String phone) { this.phone = phone; }
}
