package com.medisync.patient_service.dto.emergency;

import java.util.UUID;

public class EmergencyContactResponse {

    private UUID contactId;
    private String name;
    private String relationship;
    private String phone;
    private UUID patientId;

    public EmergencyContactResponse() { }

    public UUID getContactId() { return contactId; }
    public String getName() { return name; }
    public String getRelationship() { return relationship; }
    public String getPhone() { return phone; }
    public UUID getPatientId() { return patientId; }

    public void setContactId(UUID contactId) { this.contactId = contactId; }
    public void setName(String name) { this.name = name; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
}
