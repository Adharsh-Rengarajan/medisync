package com.medisync.patient_service.controller;

import com.medisync.patient_service.dto.ApiResponse;
import com.medisync.patient_service.dto.insurance.InsuranceCreateRequest;
import com.medisync.patient_service.dto.insurance.InsuranceResponse;
import com.medisync.patient_service.dto.insurance.InsuranceUpdateRequest;
import com.medisync.patient_service.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/insurances")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<ApiResponse<InsuranceResponse>> create(@Valid @RequestBody InsuranceCreateRequest request) {
        InsuranceResponse response = insuranceService.createInsurance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Insurance created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InsuranceResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody InsuranceUpdateRequest request) {
        InsuranceResponse response = insuranceService.updateInsurance(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Insurance updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InsuranceResponse>> getById(@PathVariable UUID id) {
        InsuranceResponse response = insuranceService.getInsuranceById(id);
        return ResponseEntity.ok(ApiResponse.ok("Insurance fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InsuranceResponse>>> getAll() {
        List<InsuranceResponse> list = insuranceService.getAllInsurances();
        return ResponseEntity.ok(ApiResponse.ok("Insurances fetched successfully", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.ok(ApiResponse.ok("Insurance deleted successfully", null));
    }
}
