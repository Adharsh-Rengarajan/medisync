package com.medisync.patient_service.controller;

import com.medisync.patient_service.dto.ApiResponse;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceCreateRequest;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceResponse;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceUpdateRequest;
import com.medisync.patient_service.service.PatientInsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient-insurances")
public class PatientInsuranceController {

    @Autowired
    private PatientInsuranceService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientInsuranceResponse>> create(@Valid @RequestBody PatientInsuranceCreateRequest request) {
        PatientInsuranceResponse response = service.createPatientInsurance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("PatientInsurance created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientInsuranceResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody PatientInsuranceUpdateRequest request) {
        PatientInsuranceResponse response = service.updatePatientInsurance(id, request);
        return ResponseEntity.ok(ApiResponse.ok("PatientInsurance updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientInsuranceResponse>> getById(@PathVariable UUID id) {
        PatientInsuranceResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("PatientInsurance fetched successfully", response));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<PatientInsuranceResponse>>> getByPatientId(@PathVariable UUID patientId) {
        List<PatientInsuranceResponse> list = service.getByPatientId(patientId);
        return ResponseEntity.ok(ApiResponse.ok("PatientInsurance fetched successfully", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.deletePatientInsurance(id);
        return ResponseEntity.ok(ApiResponse.ok("PatientInsurance deleted successfully", null));
    }
}
