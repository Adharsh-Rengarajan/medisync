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
import com.medisync.patient_service.dto.insurance.InsuranceCreateRequest;
import com.medisync.patient_service.dto.insurance.InsuranceResponse;
import com.medisync.patient_service.service.InsuranceService;

@WebMvcTest(InsuranceController.class)
@AutoConfigureMockMvc(addFilters = false)
class InsuranceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InsuranceService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreateInsurance() throws Exception {
		InsuranceResponse resp = new InsuranceResponse();
		resp.setInsuranceId(UUID.randomUUID());
		resp.setProviderName("ABC Health");

		Mockito.when(service.createInsurance(any(InsuranceCreateRequest.class))).thenReturn(resp);

		InsuranceCreateRequest req = new InsuranceCreateRequest();
		req.setProviderName("ABC Health");
		req.setPolicyNumber("POL123");

		mockMvc.perform(
				post("/api/insurances").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.data.providerName").value("ABC Health"));
	}

	@Test
	void testGetAllInsurances() throws Exception {
		InsuranceResponse resp = new InsuranceResponse();
		resp.setInsuranceId(UUID.randomUUID());
		resp.setProviderName("XYZ Health");

		Mockito.when(service.getAllInsurances()).thenReturn(Collections.singletonList(resp));

		mockMvc.perform(get("/api/insurances")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].providerName").value("XYZ Health"));
	}
}
