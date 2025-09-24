package com.medisync.patient_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medisync.patient_service.dto.patient.PatientCreateRequest;
import com.medisync.patient_service.dto.patient.PatientResponse;
import com.medisync.patient_service.dto.patient.PatientUpdateRequest;
import com.medisync.patient_service.entity.Patient;
import com.medisync.patient_service.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public PatientResponse createPatient(@Valid PatientCreateRequest request) {
		if (request.getEmail() != null && patientRepository.existsByEmail(request.getEmail())) {
			throw new ValidationException("Email already exists: " + request.getEmail());
		}

		Patient patient = new Patient();
		patient.setFirstName(request.getFirstName().trim());
		patient.setLastName(request.getLastName().trim());
		patient.setDob(request.getDob());
		patient.setGender(request.getGender());
		patient.setPhone(request.getPhone());
		patient.setEmail(request.getEmail());
		patient.setStreet(request.getStreet());
		patient.setCity(request.getCity());
		patient.setState(request.getState());
		patient.setZip(request.getZip());
		patient.setCountry(request.getCountry());
		patient.setNotificationPreference(request.getNotificationPreference());

		return mapToResponse(patientRepository.save(patient));
	}

	public PatientResponse updatePatient(UUID patientId, @Valid PatientUpdateRequest request) {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

		if (request.getFirstName() != null && !request.getFirstName().isBlank())
			patient.setFirstName(request.getFirstName().trim());
		if (request.getLastName() != null && !request.getLastName().isBlank())
			patient.setLastName(request.getLastName().trim());
		if (request.getDob() != null)
			patient.setDob(request.getDob());
		if (request.getGender() != null)
			patient.setGender(request.getGender());
		if (request.getPhone() != null && !request.getPhone().isBlank())
			patient.setPhone(request.getPhone());
		if (request.getEmail() != null && !request.getEmail().isBlank()) {
			if (patientRepository.existsByEmail(request.getEmail())
					&& !request.getEmail().equalsIgnoreCase(patient.getEmail())) {
				throw new ValidationException("Email already exists: " + request.getEmail());
			}
			patient.setEmail(request.getEmail());
		}
		if (request.getStreet() != null)
			patient.setStreet(request.getStreet());
		if (request.getCity() != null)
			patient.setCity(request.getCity());
		if (request.getState() != null)
			patient.setState(request.getState());
		if (request.getZip() != null)
			patient.setZip(request.getZip());
		if (request.getCountry() != null)
			patient.setCountry(request.getCountry());
		if (request.getNotificationPreference() != null)
			patient.setNotificationPreference(request.getNotificationPreference());

		return mapToResponse(patientRepository.save(patient));
	}

	public PatientResponse getPatientById(UUID patientId) {
		return mapToResponse(patientRepository.findById(patientId)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId)));
	}

	public List<PatientResponse> getAllPatients() {
		return patientRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public void deletePatient(UUID patientId) {
		if (!patientRepository.existsById(patientId)) {
			throw new EntityNotFoundException("Patient not found with ID: " + patientId);
		}
		patientRepository.deleteById(patientId);
	}

	private PatientResponse mapToResponse(Patient p) {
		PatientResponse r = new PatientResponse();
		r.setPatientId(p.getPatientId());
		r.setPatientCode(p.getPatientCode());
		r.setFirstName(p.getFirstName());
		r.setLastName(p.getLastName());
		r.setDob(p.getDob());
		r.setGender(p.getGender());
		r.setPhone(p.getPhone());
		r.setEmail(p.getEmail());
		r.setStreet(p.getStreet());
		r.setCity(p.getCity());
		r.setState(p.getState());
		r.setZip(p.getZip());
		r.setCountry(p.getCountry());
		r.setNotificationPreference(p.getNotificationPreference());
		r.setActive(p.isActive());
		r.setCreatedAt(p.getCreatedAt());
		r.setUpdatedAt(p.getUpdatedAt());
		return r;
	}
}
