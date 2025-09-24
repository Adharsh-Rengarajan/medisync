package com.medisync.patient_service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceCreateRequest;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceResponse;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceUpdateRequest;
import com.medisync.patient_service.entity.Insurance;
import com.medisync.patient_service.entity.Patient;
import com.medisync.patient_service.entity.PatientInsurance;
import com.medisync.patient_service.repository.InsuranceRepository;
import com.medisync.patient_service.repository.PatientInsuranceRepository;
import com.medisync.patient_service.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Transactional
public class PatientInsuranceService {

	@Autowired
	private PatientInsuranceRepository repo;
	@Autowired
	private PatientRepository patientRepo;
	@Autowired
	private InsuranceRepository insuranceRepo;

	public PatientInsuranceResponse createPatientInsurance(@Valid PatientInsuranceCreateRequest request) {
		Patient p = patientRepo.findById(request.getPatientId())
				.orElseThrow(() -> new EntityNotFoundException("Patient not found: " + request.getPatientId()));
		Insurance i = insuranceRepo.findById(request.getInsuranceId())
				.orElseThrow(() -> new EntityNotFoundException("Insurance not found: " + request.getInsuranceId()));

		PatientInsurance pi = new PatientInsurance();
		pi.setPatient(p);
		pi.setInsurance(i);
		pi.setPrimary(request.getIsPrimary()); // ✅ uses boolean setter

		return mapToResponse(repo.save(pi));
	}

	public PatientInsuranceResponse updatePatientInsurance(UUID id, @Valid PatientInsuranceUpdateRequest request) {
		PatientInsurance pi = repo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("PatientInsurance not found: " + id));
		if (request.getIsPrimary() != null)
			pi.setPrimary(request.getIsPrimary());
		return mapToResponse(repo.save(pi));
	}

	public PatientInsuranceResponse getById(UUID id) {
		return mapToResponse(
				repo.findById(id).orElseThrow(() -> new EntityNotFoundException("PatientInsurance not found: " + id)));
	}

	public List<PatientInsuranceResponse> getByPatientId(UUID patientId) {
		return repo.findByPatient_PatientId(patientId).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public void deletePatientInsurance(UUID id) {
		if (!repo.existsById(id))
			throw new EntityNotFoundException("PatientInsurance not found: " + id);
		repo.deleteById(id);
	}

	private PatientInsuranceResponse mapToResponse(PatientInsurance pi) {
		PatientInsuranceResponse r = new PatientInsuranceResponse();
		r.setId(pi.getId());
		r.setPatientId(pi.getPatient().getPatientId());
		r.setInsuranceId(pi.getInsurance().getInsuranceId());
		r.setIsPrimary(pi.isPrimary()); // ✅ uses boolean getter
		r.setCreatedAt(pi.getCreatedAt());
		return r;
	}
}
