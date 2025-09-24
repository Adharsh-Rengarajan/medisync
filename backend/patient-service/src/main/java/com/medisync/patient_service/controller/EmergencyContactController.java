package com.medisync.patient_service.controller;

import com.medisync.patient_service.dto.ApiResponse;
import com.medisync.patient_service.dto.emergency.EmergencyContactCreateRequest;
import com.medisync.patient_service.dto.emergency.EmergencyContactResponse;
import com.medisync.patient_service.dto.emergency.EmergencyContactUpdateRequest;
import com.medisync.patient_service.service.EmergencyContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/emergency-contacts")
public class EmergencyContactController {

    @Autowired
    private EmergencyContactService service;

    @PostMapping
    public ResponseEntity<ApiResponse<EmergencyContactResponse>> create(@Valid @RequestBody EmergencyContactCreateRequest request) {
        EmergencyContactResponse response = service.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("EmergencyContact created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmergencyContactResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody EmergencyContactUpdateRequest request) {
        EmergencyContactResponse response = service.updateContact(id, request);
        return ResponseEntity.ok(ApiResponse.ok("EmergencyContact updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmergencyContactResponse>> getById(@PathVariable UUID id) {
        EmergencyContactResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("EmergencyContact fetched successfully", response));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<EmergencyContactResponse>>> getByPatientId(@PathVariable UUID patientId) {
        List<EmergencyContactResponse> list = service.getByPatientId(patientId);
        return ResponseEntity.ok(ApiResponse.ok("EmergencyContacts fetched successfully", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.deleteContact(id);
        return ResponseEntity.ok(ApiResponse.ok("EmergencyContact deleted successfully", null));
    }
}
