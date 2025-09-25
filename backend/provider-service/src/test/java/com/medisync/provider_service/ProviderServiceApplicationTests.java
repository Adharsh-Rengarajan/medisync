package com.medisync.provider_service;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.medisync.provider_service.controller.AvailabilityControllerTest;
import com.medisync.provider_service.controller.ProviderControllerTest;
import com.medisync.provider_service.controller.ProviderScheduleOverrideControllerTest;
import com.medisync.provider_service.controller.ProviderSpecialtyControllerTest;
import com.medisync.provider_service.controller.SpecialtyControllerTest;
import com.medisync.provider_service.service.AvailabilityServiceTest;
import com.medisync.provider_service.service.ProviderScheduleOverrideServiceTest;
import com.medisync.provider_service.service.ProviderServiceTest;
import com.medisync.provider_service.service.ProviderSpecialtyServiceTest;
import com.medisync.provider_service.service.SpecialtyServiceTest;

@Suite
@SelectClasses({ ProviderControllerTest.class, SpecialtyControllerTest.class, AvailabilityControllerTest.class,
		ProviderScheduleOverrideControllerTest.class, ProviderSpecialtyControllerTest.class, ProviderServiceTest.class,
		SpecialtyServiceTest.class, AvailabilityServiceTest.class, ProviderScheduleOverrideServiceTest.class,
		ProviderSpecialtyServiceTest.class })
@SpringBootTest
class ProviderServiceApplicationTests {

	@Test
	void contextLoads() {
	}
}
