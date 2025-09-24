package com.medisync.patient_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.medisync.patient_service.dto.patient.PatientCreateRequest;
import com.medisync.patient_service.dto.patient.PatientResponse;
import com.medisync.patient_service.enums.Gender;
import com.medisync.patient_service.service.PatientService;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreatePatient() throws Exception {
		PatientResponse resp = new PatientResponse();
		resp.setPatientId(UUID.randomUUID());
		resp.setFirstName("John");
		resp.setLastName("Doe");

		Mockito.when(patientService.createPatient(any(PatientCreateRequest.class))).thenReturn(resp);

		PatientCreateRequest req = new PatientCreateRequest();
		req.setFirstName("John");
		req.setLastName("Doe");
		req.setDob(LocalDate.of(1990, 1, 1));
		req.setEmail("john.doe@example.com");
		req.setGender(Gender.MALE);

		mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.firstName").value("John"));
	}

	@Test
	void testGetPatientById() throws Exception {
		PatientResponse resp = new PatientResponse();
		resp.setPatientId(UUID.randomUUID());
		resp.setFirstName("John");

		Mockito.when(patientService.getPatientById(any(UUID.class))).thenReturn(resp);

		mockMvc.perform(get("/api/patients/" + resp.getPatientId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.firstName").value("John"));
	}

	@Test
	void testGetAllPatients() throws Exception {
		PatientResponse resp = new PatientResponse();
		resp.setPatientId(UUID.randomUUID());
		resp.setFirstName("Jane");

		Mockito.when(patientService.getAllPatients()).thenReturn(Collections.singletonList(resp));

		mockMvc.perform(get("/api/patients")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].firstName").value("Jane"));
	}
}
