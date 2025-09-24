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
import com.medisync.patient_service.dto.emergency.EmergencyContactCreateRequest;
import com.medisync.patient_service.dto.emergency.EmergencyContactResponse;
import com.medisync.patient_service.service.EmergencyContactService;

@WebMvcTest(EmergencyContactController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmergencyContactControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmergencyContactService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreateContact() throws Exception {
		EmergencyContactResponse resp = new EmergencyContactResponse();
		resp.setContactId(UUID.randomUUID());
		resp.setName("Jane Doe");

		Mockito.when(service.createContact(any(EmergencyContactCreateRequest.class))).thenReturn(resp);

		EmergencyContactCreateRequest req = new EmergencyContactCreateRequest();
		req.setName("Jane Doe");
		req.setRelationship("Sister");
		req.setPatientId(UUID.randomUUID());

		mockMvc.perform(post("/api/emergency-contacts").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(req))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.name").value("Jane Doe"));
	}

	@Test
	void testGetByPatientId() throws Exception {
		EmergencyContactResponse resp = new EmergencyContactResponse();
		resp.setContactId(UUID.randomUUID());
		resp.setName("Samantha Doe");

		Mockito.when(service.getByPatientId(any(UUID.class))).thenReturn(Collections.singletonList(resp));

		mockMvc.perform(get("/api/emergency-contacts/patient/" + UUID.randomUUID())).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].name").value("Samantha Doe"));
	}
}
