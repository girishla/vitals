package com.kfc.vitals.services.restaurantdelivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kfc.vitals.Service;
import com.kfc.vitals.ServiceName;
import com.kfc.vitals.ServiceProvider;

import lombok.Getter;

/**
 * Represents Restaurant Delivery as a Service implementation. The delivery service can be supported by
 * any number of Service Provider instances. Providers are registered/added on during runtime.
 * 
 * @author Girish Lakshmanan
 *
 */
@Component
public class RestaurantDeliveryService implements Service {

	private final ServiceName serviceName = ServiceName.RESTAURANT_DELIVERY;

	@Getter
	private final List<ServiceProvider<?>> providers = Collections.synchronizedList(new ArrayList<>());

	@Override
	public ServiceName getName() {
		return serviceName;
	}

	public boolean addProvider(ServiceProvider<RestaurantDeliveryServiceInput> provider) {
		return this.providers.add(provider);

	}

}
