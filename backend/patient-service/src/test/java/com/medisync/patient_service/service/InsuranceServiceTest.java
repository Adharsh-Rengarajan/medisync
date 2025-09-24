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

import com.medisync.patient_service.dto.insurance.InsuranceCreateRequest;
import com.medisync.patient_service.dto.insurance.InsuranceUpdateRequest;
import com.medisync.patient_service.entity.Insurance;
import com.medisync.patient_service.repository.InsuranceRepository;

import jakarta.persistence.EntityNotFoundException;

class InsuranceServiceTest {

	@Mock
	private InsuranceRepository repo;

	@InjectMocks
	private InsuranceService service;

	private Insurance insurance;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		insurance = new Insurance();
		insurance.setInsuranceId(UUID.randomUUID());
		insurance.setProviderName("ABC Health");
		insurance.setPolicyNumber("POL123");
	}

	@Test
	void testCreateInsurance() {
		InsuranceCreateRequest req = new InsuranceCreateRequest();
		req.setProviderName("ABC Health");
		req.setPolicyNumber("POL123");

		when(repo.save(any(Insurance.class))).thenReturn(insurance);

		assertEquals("ABC Health", service.createInsurance(req).getProviderName());
	}

	@Test
	void testUpdateInsurance() {
		when(repo.findById(insurance.getInsuranceId())).thenReturn(Optional.of(insurance));
		when(repo.save(any(Insurance.class))).thenReturn(insurance);

		InsuranceUpdateRequest req = new InsuranceUpdateRequest();
		req.setProviderName("XYZ Health");

		assertEquals("XYZ Health", service.updateInsurance(insurance.getInsuranceId(), req).getProviderName());
	}

	@Test
	void testGetInsuranceNotFound() {
		UUID id = UUID.randomUUID();
		when(repo.findById(id)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.getInsuranceById(id));
	}
}
