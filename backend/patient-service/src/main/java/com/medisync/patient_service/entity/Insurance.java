package com.medisync.patient_service.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "insurances", uniqueConstraints = {
		@UniqueConstraint(name = "uk_insurance_policy_number", columnNames = "policy_number") }, indexes = {
				@Index(name = "idx_insurance_policy_number", columnList = "policy_number"),
				@Index(name = "idx_insurance_provider_name", columnList = "provider_name") })
public class Insurance {

	@Id
	@UuidGenerator
	@Column(name = "insurance_id", nullable = false, updatable = false, columnDefinition = "uuid")
	private UUID insuranceId;

	@NotBlank
	@Size(max = 150)
	@Column(name = "provider_name", nullable = false, length = 150)
	private String providerName;

	@NotBlank
	@Size(max = 100)
	@Column(name = "policy_number", nullable = false, length = 100)
	private String policyNumber;

	@Lob
	@Column(name = "coverage_details")
	private String coverageDetails;

	@Column(name = "valid_till")
	private LocalDate validTill;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	public Insurance() {
	}

	// ===== Getters & Setters =====
	public UUID getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(UUID insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getCoverageDetails() {
		return coverageDetails;
	}

	public void setCoverageDetails(String coverageDetails) {
		this.coverageDetails = coverageDetails;
	}

	public LocalDate getValidTill() {
		return validTill;
	}

	public void setValidTill(LocalDate validTill) {
		this.validTill = validTill;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
