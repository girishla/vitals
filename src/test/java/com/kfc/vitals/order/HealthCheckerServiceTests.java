package com.kfc.vitals.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.kfc.vitals.ApiService;
import com.kfc.vitals.HealthCheckResult;
import com.kfc.vitals.HealthCheckerRunner;
import com.kfc.vitals.HealthNotificationListener;
import com.kfc.vitals.ServiceHealthChecker;
import com.kfc.vitals.ServiceHealthCheckerRepository;
import com.kfc.vitals.ServiceName;
import com.kfc.vitals.ServiceProvider;
import com.kfc.vitals.ServiceStatus;
import com.kfc.vitals.Utils;
import com.kfc.vitals.services.restaurantdelivery.Restaurant;
import com.kfc.vitals.services.restaurantdelivery.RestaurantDeliveryHealthChecker;
import com.kfc.vitals.services.restaurantdelivery.RestaurantDeliveryService;
import com.kfc.vitals.services.restaurantdelivery.RestaurantDeliveryServiceInput;
import com.kfc.vitals.services.restaurantdelivery.RestaurantDeliveryServiceProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HealthCheckerServiceTests {

	private ServiceHealthChecker<ServiceProvider<?>, Object> healthChecker;
	private final RestaurantDeliveryService delService = Mockito.mock(RestaurantDeliveryService.class);
	private final HealthNotificationListener listener = Mockito.mock(HealthNotificationListener.class);
	private final RestaurantDeliveryServiceProvider serviceProvider = Mockito
			.mock(RestaurantDeliveryServiceProvider.class);
	private ServiceHealthCheckerRepository serviceHealthCheckerRepository;
	HealthCheckerRunner healthCheckRunner;

	private static final String PROVIDER_NAME = "LONDON_WATERLOO_KFC";

	@Before
	public void setup() {
		setUpMocks();
	}

	
	@Test
	public void callsListenersWhenHealthCheckInvoked() {
		setUpMocks();
		healthCheckRunner.healthCheck();
		verify(listener, times(1)).notify(any(HealthCheckResult.class));
	}

	@Test
	public void callsListenerWithCorrectServiceProvider() {
		setUpMocks();
		healthCheckRunner.healthCheck();
		ArgumentCaptor<HealthCheckResult> argumentCaptor = ArgumentCaptor.forClass(HealthCheckResult.class);
		verify(listener, times(1)).notify(argumentCaptor.capture());
		assertEquals(argumentCaptor.getValue()
				.getProvider()
				.getName(), PROVIDER_NAME);
	}

	@Test
	public void returnsStatusUpWhenApiReturnsValidData() {

		@SuppressWarnings("unchecked")
		ApiService<RestaurantDeliveryServiceInput, Restaurant> apiService = Mockito.mock(ApiService.class);

		ServiceHealthChecker<RestaurantDeliveryServiceProvider, RestaurantDeliveryServiceInput> healthChecker = new RestaurantDeliveryHealthChecker(
				apiService);

		setUpMocksWithHealthChecker(healthChecker);

		when(apiService.invokeApi(getValidInput()))
				.thenReturn(Utils.readJsonObject(Utils.readFileAsString("restaurantsSingle.json")));

		healthCheckRunner.healthCheck();
		ArgumentCaptor<HealthCheckResult> argumentCaptor = ArgumentCaptor.forClass(HealthCheckResult.class);
		verify(listener, times(1)).notify(argumentCaptor.capture());

		assertEquals(ServiceStatus.UP, argumentCaptor.getValue()
				.getStatus());
	}

	@Test
	public void returnsStatusErrorWhenApiErrors() {

		@SuppressWarnings("unchecked")
		ApiService<RestaurantDeliveryServiceInput, Restaurant> apiService = Mockito.mock(ApiService.class);
		ServiceHealthChecker<RestaurantDeliveryServiceProvider, RestaurantDeliveryServiceInput> healthChecker = new RestaurantDeliveryHealthChecker(
				apiService);

		setUpMocksWithHealthChecker(healthChecker);
		when(apiService.invokeApi(getValidInput())).thenThrow(RuntimeException.class);
		healthCheckRunner.healthCheck();
		ArgumentCaptor<HealthCheckResult> argumentCaptor = ArgumentCaptor.forClass(HealthCheckResult.class);
		verify(listener, times(1)).notify(argumentCaptor.capture());

		assertEquals(ServiceStatus.ERROR, argumentCaptor.getValue()
				.getStatus());
		assertNotEquals(null, argumentCaptor.getValue()
				.getStatusMessage());
	}

	@SuppressWarnings("unchecked")
	private void setUpMocks() {
		healthChecker = (ServiceHealthChecker<ServiceProvider<?>, Object>) Mockito.mock(ServiceHealthChecker.class);
		when(serviceProvider.getProviderInfo()).thenReturn(getValidInput());
		when(delService.getProviders()).thenReturn(Collections.singletonList(serviceProvider));
		when(delService.getName()).thenReturn(ServiceName.RESTAURANT_DELIVERY);
		when(serviceProvider.getName()).thenReturn(PROVIDER_NAME);
		when(healthChecker.getServiceName()).thenReturn(ServiceName.RESTAURANT_DELIVERY);
		when(healthChecker.checkServiceProvider(any(),any())).thenReturn(getHealthSuccessResult());
		
		serviceHealthCheckerRepository = new ServiceHealthCheckerRepository(Collections.singletonList(healthChecker));
		healthCheckRunner = new HealthCheckerRunner(Collections.singletonList(delService),
				serviceHealthCheckerRepository);
		delService.addProvider(serviceProvider);
		healthCheckRunner.addListener(listener);

		
	}

	private HealthCheckResult getHealthSuccessResult() {
		return HealthCheckResult.builder()
				.name(ServiceName.RESTAURANT_DELIVERY)
				.provider(serviceProvider)
				.status(ServiceStatus.UP)
				.statusTime(LocalDate.now())
				.statusMessage("")
				.build();
	}

	@SuppressWarnings("unchecked")
	private void setUpMocksWithHealthChecker(
			ServiceHealthChecker<RestaurantDeliveryServiceProvider, RestaurantDeliveryServiceInput> healthChecker2) {
		when(serviceProvider.getProviderInfo()).thenReturn(getValidInput());
		when(delService.getProviders()).thenReturn(Collections.singletonList(serviceProvider));
		when(delService.getName()).thenReturn(ServiceName.RESTAURANT_DELIVERY);
		when(serviceProvider.getName()).thenReturn(PROVIDER_NAME);

		serviceHealthCheckerRepository = new ServiceHealthCheckerRepository(
				(List<? extends ServiceHealthChecker<ServiceProvider<?>, Object>>) Collections
						.singletonList(healthChecker2));
		healthCheckRunner = new HealthCheckerRunner(Collections.singletonList(delService),
				serviceHealthCheckerRepository);

		delService.addProvider(serviceProvider);
		healthCheckRunner.addListener(listener);
	}

	public RestaurantDeliveryServiceInput getValidInput() {
		return (new RestaurantDeliveryServiceInput()).withAddress("2 Waldemar Avenue, London, Greater London, SW6 5NA")
				.withCodeMarket("UK")
				.withCountryCode("GB")
				.withDate("2018-09-12T10:25:27.970Z")
				.withPostcode("SW6 5NA");
	}

	public RestaurantDeliveryServiceInput getInvalidInput() {
		return (new RestaurantDeliveryServiceInput()).withAddress("2 Waldemar Avenue, London, Greater London, SW6 5NA")
				.withCodeMarket("UK")
				.withCountryCode("GB")
				.withDate("2018-09-12T10:25:27.970Z")
				.withPostcode("GU21 4AJ");
	}

}
