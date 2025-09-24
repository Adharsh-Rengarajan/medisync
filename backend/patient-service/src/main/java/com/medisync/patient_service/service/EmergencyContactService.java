package com.medisync.patient_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medisync.patient_service.dto.emergency.EmergencyContactCreateRequest;
import com.medisync.patient_service.dto.emergency.EmergencyContactResponse;
import com.medisync.patient_service.dto.emergency.EmergencyContactUpdateRequest;
import com.medisync.patient_service.entity.EmergencyContact;
import com.medisync.patient_service.entity.Patient;
import com.medisync.patient_service.repository.EmergencyContactRepository;
import com.medisync.patient_service.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Transactional
public class EmergencyContactService {

	@Autowired
	private EmergencyContactRepository emergencyContactRepository;

	@Autowired
	private PatientRepository patientRepository;

	public EmergencyContactResponse createContact(@Valid EmergencyContactCreateRequest request) {
		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + request.getPatientId()));

		EmergencyContact contact = new EmergencyContact();
		contact.setName(request.getName().trim());
		contact.setRelationship(request.getRelationship().trim());
		contact.setPhone(request.getPhone());
		contact.setPatient(patient);

		EmergencyContact saved = emergencyContactRepository.save(contact);
		return mapToResponse(saved);
	}

	public EmergencyContactResponse updateContact(UUID contactId, @Valid EmergencyContactUpdateRequest request) {
		EmergencyContact contact = emergencyContactRepository.findById(contactId)
				.orElseThrow(() -> new EntityNotFoundException("EmergencyContact not found with ID: " + contactId));

		if (request.getName() != null && !request.getName().isBlank()) {
			contact.setName(request.getName().trim());
		}
		if (request.getRelationship() != null && !request.getRelationship().isBlank()) {
			contact.setRelationship(request.getRelationship().trim());
		}
		if (request.getPhone() != null) {
			contact.setPhone(request.getPhone());
		}

		EmergencyContact updated = emergencyContactRepository.save(contact);
		return mapToResponse(updated);
	}

	public EmergencyContactResponse getById(UUID contactId) {
		EmergencyContact contact = emergencyContactRepository.findById(contactId)
				.orElseThrow(() -> new EntityNotFoundException("EmergencyContact not found with ID: " + contactId));
		return mapToResponse(contact);
	}

	public List<EmergencyContactResponse> getByPatientId(UUID patientId) {
		return emergencyContactRepository.findByPatient_PatientId(patientId).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public void deleteContact(UUID contactId) {
		if (!emergencyContactRepository.existsById(contactId)) {
			throw new EntityNotFoundException("EmergencyContact not found with ID: " + contactId);
		}
		emergencyContactRepository.deleteById(contactId);
	}

	private EmergencyContactResponse mapToResponse(EmergencyContact contact) {
		EmergencyContactResponse response = new EmergencyContactResponse();
		response.setContactId(contact.getContactId());
		response.setName(contact.getName());
		response.setRelationship(contact.getRelationship());
		response.setPhone(contact.getPhone());
		response.setPatientId(contact.getPatient().getPatientId());
		return response;
	}
}
