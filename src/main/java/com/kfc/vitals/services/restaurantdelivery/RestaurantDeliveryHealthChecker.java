package com.kfc.vitals.services.restaurantdelivery;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.kfc.vitals.HealthCheckResult;
import com.kfc.vitals.ServiceHealthChecker;
import com.kfc.vitals.ServiceName;
import com.kfc.vitals.ServiceStatus;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestaurantDeliveryHealthChecker
		implements ServiceHealthChecker<RestaurantDeliveryServiceProvider, RestaurantDeliveryServiceInput> {

	private RestaurantDeliveryApiService apiService;
	
	public RestaurantDeliveryHealthChecker(RestaurantDeliveryApiService apiService){
		this.apiService=apiService;
	}

	@Override
	public HealthCheckResult checkServiceProvider(RestaurantDeliveryServiceProvider serviceProvider,
			RestaurantDeliveryServiceInput input) {

		try {
			
			List<Restaurant> restaurants = apiService.invokeApi(input);
			ServiceStatus status = restaurants.isEmpty() ? ServiceStatus.DOWN : ServiceStatus.UP;

			return getResult(serviceProvider, "Everything OK", status);

		} catch (HttpClientErrorException e) {
			log.info(">>>>>>>>>> API Error {}", e.getResponseBodyAsString());
			return getResult(serviceProvider, "API Error" + ':' + e.getResponseBodyAsString().substring(0,800),
					ServiceStatus.ERROR);
		} catch (Exception e) {
			log.info("Error calling API {}", e.getMessage());
			return getResult(serviceProvider, "API Error" + ':' + e.getMessage(), ServiceStatus.ERROR);
		}

	}

	
	private HealthCheckResult getResult(RestaurantDeliveryServiceProvider serviceProvider, String message,
			ServiceStatus status) {

		return HealthCheckResult.builder()
				.name(ServiceName.RESTAURANT_DELIVERY)
				.provider(serviceProvider)
				.status(status)
				.statusMessage(message)
				.statusTime(LocalDateTime.now())
				.build();
	}

	@Override
	public ServiceName getServiceName() {
		return ServiceName.RESTAURANT_DELIVERY;
	}

}
