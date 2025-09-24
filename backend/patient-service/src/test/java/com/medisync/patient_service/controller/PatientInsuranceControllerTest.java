package com.medisync.patient_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceCreateRequest;
import com.medisync.patient_service.dto.patientinsurance.PatientInsuranceResponse;
import com.medisync.patient_service.service.PatientInsuranceService;

@WebMvcTest(PatientInsuranceController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientInsuranceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientInsuranceService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreatePatientInsurance() throws Exception {
		PatientInsuranceResponse resp = new PatientInsuranceResponse();
		resp.setId(UUID.randomUUID());
		resp.setIsPrimary(true);

		Mockito.when(service.createPatientInsurance(any(PatientInsuranceCreateRequest.class))).thenReturn(resp);

		PatientInsuranceCreateRequest req = new PatientInsuranceCreateRequest();
		req.setPatientId(UUID.randomUUID());
		req.setInsuranceId(UUID.randomUUID());
		req.setIsPrimary(true);

		mockMvc.perform(post("/api/patient-insurances").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(req))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.isPrimary").value(true));
	}

	@Test
	void testGetByPatientId() throws Exception {
		PatientInsuranceResponse resp = new PatientInsuranceResponse();
		resp.setId(UUID.randomUUID());
		resp.setIsPrimary(false);

		Mockito.when(service.getByPatientId(any(UUID.class))).thenReturn(Collections.singletonList(resp));

		mockMvc.perform(get("/api/patient-insurances/patient/" + UUID.randomUUID())).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].isPrimary").value(false));
	}
}
