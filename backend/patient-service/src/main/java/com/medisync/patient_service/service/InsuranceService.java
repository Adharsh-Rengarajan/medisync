package com.medisync.patient_service.service;

import com.medisync.patient_service.dto.insurance.InsuranceCreateRequest;
import com.medisync.patient_service.dto.insurance.InsuranceResponse;
import com.medisync.patient_service.dto.insurance.InsuranceUpdateRequest;
import com.medisync.patient_service.entity.Insurance;
import com.medisync.patient_service.repository.InsuranceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    public InsuranceResponse createInsurance(@Valid InsuranceCreateRequest request) {
        Insurance insurance = new Insurance();
        insurance.setProviderName(request.getProviderName().trim());
        insurance.setPolicyNumber(request.getPolicyNumber().trim());
        insurance.setCoverageDetails(request.getCoverageDetails());
        if (request.getValidTill() != null) {
            insurance.setValidTill(LocalDate.parse(request.getValidTill()));
        }
        Insurance saved = insuranceRepository.save(insurance);
        return mapToResponse(saved);
    }

    public InsuranceResponse updateInsurance(UUID insuranceId, @Valid InsuranceUpdateRequest request) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));

        if (request.getProviderName() != null && !request.getProviderName().isBlank()) {
            insurance.setProviderName(request.getProviderName().trim());
        }
        if (request.getPolicyNumber() != null && !request.getPolicyNumber().isBlank()) {
            insurance.setPolicyNumber(request.getPolicyNumber().trim());
        }
        if (request.getCoverageDetails() != null) {
            insurance.setCoverageDetails(request.getCoverageDetails());
        }
        if (request.getValidTill() != null) {
            insurance.setValidTill(LocalDate.parse(request.getValidTill()));
        }

        Insurance updated = insuranceRepository.save(insurance);
        return mapToResponse(updated);
    }

    public InsuranceResponse getInsuranceById(UUID insuranceId) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));
        return mapToResponse(insurance);
    }

    public List<InsuranceResponse> getAllInsurances() {
        return insuranceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteInsurance(UUID insuranceId) {
        if (!insuranceRepository.existsById(insuranceId)) {
            throw new EntityNotFoundException("Insurance not found with ID: " + insuranceId);
        }
        insuranceRepository.deleteById(insuranceId);
    }

    private InsuranceResponse mapToResponse(Insurance insurance) {
        InsuranceResponse response = new InsuranceResponse();
        response.setInsuranceId(insurance.getInsuranceId());
        response.setProviderName(insurance.getProviderName());
        response.setPolicyNumber(insurance.getPolicyNumber());
        response.setCoverageDetails(insurance.getCoverageDetails());
        response.setValidTill(insurance.getValidTill());
        return response;
    }
}
