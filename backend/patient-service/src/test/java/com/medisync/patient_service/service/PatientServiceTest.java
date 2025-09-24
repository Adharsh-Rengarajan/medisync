package com.medisync.patient_service.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.medisync.patient_service.dto.patient.PatientCreateRequest;
import com.medisync.patient_service.dto.patient.PatientUpdateRequest;
import com.medisync.patient_service.entity.Patient;
import com.medisync.patient_service.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;

class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private PatientService patientService;

	private Patient patient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		patient = new Patient();
		patient.setPatientId(UUID.randomUUID());
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setEmail("john.doe@example.com");
	}

	@Test
	void testCreatePatient() {
		PatientCreateRequest req = new PatientCreateRequest();
		req.setFirstName("John");
		req.setLastName("Doe");
		req.setDob(LocalDate.of(1990, 1, 1));
		req.setEmail("john.doe@example.com");

		when(patientRepository.save(any(Patient.class))).thenReturn(patient);

		assertNotNull(patientService.createPatient(req));
		verify(patientRepository, times(1)).save(any(Patient.class));
	}

	@Test
	void testGetPatientById() {
		when(patientRepository.findById(patient.getPatientId())).thenReturn(Optional.of(patient));

		assertEquals("John", patientService.getPatientById(patient.getPatientId()).getFirstName());
	}

	@Test
	void testGetPatientNotFound() {
		UUID id = UUID.randomUUID();
		when(patientRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> patientService.getPatientById(id));
	}

	@Test
	void testUpdatePatient() {
		when(patientRepository.findById(patient.getPatientId())).thenReturn(Optional.of(patient));
		when(patientRepository.save(any(Patient.class))).thenReturn(patient);

		PatientUpdateRequest req = new PatientUpdateRequest();
		req.setFirstName("Jane");

		assertEquals("Jane", patientService.updatePatient(patient.getPatientId(), req).getFirstName());
	}

	@Test
	void testDeletePatient() {
		when(patientRepository.existsById(patient.getPatientId())).thenReturn(true);
		doNothing().when(patientRepository).deleteById(patient.getPatientId());

		assertDoesNotThrow(() -> patientService.deletePatient(patient.getPatientId()));
	}
}
