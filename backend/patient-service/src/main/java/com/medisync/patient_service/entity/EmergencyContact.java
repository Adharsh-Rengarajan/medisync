package com.medisync.patient_service.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "emergency_contacts", indexes = { @Index(name = "idx_contact_patient", columnList = "patient_id"),
		@Index(name = "idx_contact_name", columnList = "name") })
public class EmergencyContact {

	@Id
	@UuidGenerator
	@Column(name = "contact_id", nullable = false, updatable = false, columnDefinition = "uuid")
	private UUID contactId;

	@NotBlank
	@Size(max = 150)
	@Column(name = "name", nullable = false, length = 150)
	private String name;

	@NotBlank
	@Size(max = 100)
	@Column(name = "relationship", nullable = false, length = 100)
	private String relationship;

	@Size(max = 20)
	@Column(name = "phone", length = 20)
	private String phone;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	public EmergencyContact() {
	}

	// ===== Getters & Setters =====
	public UUID getContactId() {
		return contactId;
	}

	public void setContactId(UUID contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
