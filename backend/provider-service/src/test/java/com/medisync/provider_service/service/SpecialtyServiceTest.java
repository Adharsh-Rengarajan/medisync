package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.SpecialtyDto;
import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.Specialty;
import com.medisync.provider_service.repository.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyService specialtyService;

    private Specialty specialty;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        specialty = new Specialty(
                UUID.randomUUID(),
                "Cardiology",
                "Heart specialist",
                null
        );
    }

    @Test
    void testGetSpecialtyById_Success() {
        when(specialtyRepository.findById(specialty.getSpecialtyId()))
                .thenReturn(Optional.of(specialty));

        ResponseEntity<ApiResponse<SpecialtyDto>> response = specialtyService.getSpecialtyById(specialty.getSpecialtyId());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getSuccess());
        assertEquals("Cardiology", response.getBody().getData().getName());
    }

    @Test
    void testGetSpecialtyById_NotFound() {
        UUID randomId = UUID.randomUUID();
        when(specialtyRepository.findById(randomId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<SpecialtyDto>> response = specialtyService.getSpecialtyById(randomId);

        assertEquals(404, response.getStatusCodeValue());
        assertFalse(response.getBody().getSuccess());
        assertNull(response.getBody().getData());
    }
}
