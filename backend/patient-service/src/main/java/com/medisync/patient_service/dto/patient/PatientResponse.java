package com.medisync.patient_service.dto.patient;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.medisync.patient_service.enums.Gender;
import com.medisync.patient_service.enums.NotificationPreference;

public class PatientResponse {

	private UUID patientId;
	private String patientCode;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private Gender gender;
	private String phone;
	private String email;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	private NotificationPreference notificationPreference;
	private boolean isActive;
	private Instant createdAt;
	private Instant updatedAt;

	public PatientResponse() {
	}

	// Getters and Setters
	public UUID getPatientId() {
		return patientId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public Gender getGender() {
		return gender;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

	public NotificationPreference getNotificationPreference() {
		return notificationPreference;
	}

	public boolean isActive() {
		return isActive;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setNotificationPreference(NotificationPreference notificationPreference) {
		this.notificationPreference = notificationPreference;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
