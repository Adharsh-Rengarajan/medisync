package com.medisync.patient_service.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceCreateRequest;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceUpdateRequest;
import com.medisync.patient_service.entity.Insurance;
import com.medisync.patient_service.entity.Patient;
import com.medisync.patient_service.entity.PatientInsurance;
import com.medisync.patient_service.repository.InsuranceRepository;
import com.medisync.patient_service.repository.PatientInsuranceRepository;
import com.medisync.patient_service.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;

class PatientInsuranceServiceTest {

	@Mock
	private PatientInsuranceRepository repo;
	@Mock
	private PatientRepository patientRepo;
	@Mock
	private InsuranceRepository insuranceRepo;

	@InjectMocks
	private PatientInsuranceService service;

	private PatientInsurance pi;
	private Patient patient;
	private Insurance insurance;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		patient = new Patient();
		patient.setPatientId(UUID.randomUUID());

		insurance = new Insurance();
		insurance.setInsuranceId(UUID.randomUUID());

		pi = new PatientInsurance();
		pi.setId(UUID.randomUUID());
		pi.setPatient(patient);
		pi.setInsurance(insurance);
		pi.setPrimary(true);
	}

	@Test
	void testCreatePatientInsurance() {
		PatientInsuranceCreateRequest req = new PatientInsuranceCreateRequest();
		req.setPatientId(patient.getPatientId());
		req.setInsuranceId(insurance.getInsuranceId());
		req.setIsPrimary(true);

		when(patientRepo.findById(req.getPatientId())).thenReturn(Optional.of(patient));
		when(insuranceRepo.findById(req.getInsuranceId())).thenReturn(Optional.of(insurance));
		when(repo.save(any(PatientInsurance.class))).thenReturn(pi);

		assertTrue(service.createPatientInsurance(req).getIsPrimary());
	}

	@Test
	void testUpdatePatientInsurance() {
		when(repo.findById(pi.getId())).thenReturn(Optional.of(pi));
		when(repo.save(any(PatientInsurance.class))).thenReturn(pi);

		PatientInsuranceUpdateRequest req = new PatientInsuranceUpdateRequest();
		req.setIsPrimary(false);

		assertFalse(service.updatePatientInsurance(pi.getId(), req).getIsPrimary());
	}

	@Test
	void testGetNotFound() {
		UUID id = UUID.randomUUID();
		when(repo.findById(id)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.getById(id));
	}
}
