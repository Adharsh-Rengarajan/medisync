package com.medisync.provider_service.service;

import com.medisync.provider_service.dto.ProviderScheduleOverrideDto;
import com.medisync.provider_service.dto.ApiResponse;
import com.medisync.provider_service.entity.ProviderScheduleOverride;
import com.medisync.provider_service.repository.ProviderScheduleOverrideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProviderScheduleOverrideServiceTest {

    @Mock
    private ProviderScheduleOverrideRepository overrideRepository;

    @InjectMocks
    private ProviderScheduleOverrideService overrideService;

    private ProviderScheduleOverride overrideEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        overrideEntity = new ProviderScheduleOverride(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.now(),
                true,
                LocalTime.of(9, 0),
                LocalTime.of(12, 0),
                null
        );
    }

    @Test
    void testGetOverrideById_Success() {
        when(overrideRepository.findById(overrideEntity.getOverrideId()))
                .thenReturn(Optional.of(overrideEntity));

        ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> response =
                overrideService.getOverrideById(overrideEntity.getOverrideId());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getSuccess());
        assertEquals(overrideEntity.getProviderId(), response.getBody().getData().getProviderId());
    }

    @Test
    void testGetOverrideById_NotFound() {
        UUID randomId = UUID.randomUUID();
        when(overrideRepository.findById(randomId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<ProviderScheduleOverrideDto>> response =
                overrideService.getOverrideById(randomId);

        assertEquals(404, response.getStatusCodeValue());
        assertFalse(response.getBody().getSuccess());
        assertNull(response.getBody().getData());
    }
}
