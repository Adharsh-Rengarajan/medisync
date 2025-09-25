package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.AvailabilityDto;
import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.Availability;
import com.medisync.provider_service.repository.AvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    private Availability availability;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        availability = new Availability(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "MONDAY",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true,
                null
        );
    }

    @Test
    void testGetAvailabilityById_Success() {
        when(availabilityRepository.findById(availability.getAvailabilityId()))
                .thenReturn(Optional.of(availability));

        ResponseEntity<ApiResponse<AvailabilityDto>> response =
                availabilityService.getAvailabilityById(availability.getAvailabilityId());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getSuccess());
        assertEquals("MONDAY", response.getBody().getData().getDayOfWeek());
    }

    @Test
    void testGetAvailabilityById_NotFound() {
        UUID randomId = UUID.randomUUID();
        when(availabilityRepository.findById(randomId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<AvailabilityDto>> response = availabilityService.getAvailabilityById(randomId);

        assertEquals(404, response.getStatusCodeValue());
        assertFalse(response.getBody().getSuccess());
        assertNull(response.getBody().getData());
    }
}
