package com.medisync.patient_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.medisync.patient_service.dto.emergency.EmergencyContactCreateRequest;
import com.medisync.patient_service.dto.emergency.EmergencyContactUpdateRequest;
import com.medisync.patient_service.entity.EmergencyContact;
import com.medisync.patient_service.entity.Patient;
import com.medisync.patient_service.repository.EmergencyContactRepository;
import com.medisync.patient_service.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;

class EmergencyContactServiceTest {

	@Mock
	private EmergencyContactRepository repo;
	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private EmergencyContactService service;

	private EmergencyContact contact;
	private Patient patient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		patient = new Patient();
		patient.setPatientId(UUID.randomUUID());

		contact = new EmergencyContact();
		contact.setContactId(UUID.randomUUID());
		contact.setName("Jane Doe");
		contact.setRelationship("Sister");
		contact.setPhone("1234567890");
		contact.setPatient(patient); // ✅ assign patient
	}

	@Test
	void testCreateContact() {
		EmergencyContactCreateRequest req = new EmergencyContactCreateRequest();
		req.setName("Jane Doe");
		req.setRelationship("Sister");
		req.setPhone("1234567890");
		req.setPatientId(patient.getPatientId());

		when(patientRepository.findById(req.getPatientId())).thenReturn(Optional.of(patient));
		when(repo.save(any(EmergencyContact.class))).thenReturn(contact);

		assertEquals("Jane Doe", service.createContact(req).getName());
	}

	@Test
	void testUpdateContact() {
		when(repo.findById(contact.getContactId())).thenReturn(Optional.of(contact));
		when(repo.save(any(EmergencyContact.class))).thenReturn(contact);

		EmergencyContactUpdateRequest req = new EmergencyContactUpdateRequest();
		req.setPhone("9876543210");

		assertEquals("9876543210", service.updateContact(contact.getContactId(), req).getPhone());
	}

	@Test
	void testGetNotFound() {
		UUID id = UUID.randomUUID();
		when(repo.findById(id)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.getById(id));
	}
}
