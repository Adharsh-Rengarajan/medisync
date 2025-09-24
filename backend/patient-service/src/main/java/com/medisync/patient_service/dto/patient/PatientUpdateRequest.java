package com.medisync.patient_service.dto.patient;

import java.time.LocalDate;

import com.medisync.patient_service.enums.Gender;
import com.medisync.patient_service.enums.NotificationPreference;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class PatientUpdateRequest {

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Past
	private LocalDate dob;

	private Gender gender;

	@Size(max = 20)
	private String phone;

	@Email
	@Size(max = 150)
	private String email;

	@Size(max = 200)
	private String street;

	@Size(max = 100)
	private String city;

	@Size(max = 100)
	private String state;

	@Size(max = 20)
	private String zip;

	@Size(max = 100)
	private String country;

	private NotificationPreference notificationPreference;

	public PatientUpdateRequest() {
	}

	// Getters and Setters
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
}
