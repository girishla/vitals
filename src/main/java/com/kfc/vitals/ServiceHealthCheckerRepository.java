package com.kfc.vitals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.experimental.Delegate;

@Component
public class ServiceHealthCheckerRepository {

	@Delegate
	private final Map<ServiceName, ServiceHealthChecker<ServiceProvider<?>, Object>> serviceHealthCheckerMap = new HashMap<>();

	/**
	 * Spring DI fails to work correctly with 2 levels nested Generics, hence raw type declaration for ServiceHealthChecker
	 */
	@Autowired
	private @SuppressWarnings("rawtypes") List<? extends ServiceHealthChecker> serviceHealthCheckerList;

	public ServiceHealthCheckerRepository(
			@SuppressWarnings("rawtypes") List<? extends ServiceHealthChecker> serviceHealthCheckerList) {
		this.serviceHealthCheckerList = serviceHealthCheckerList;
		populateRepo();
	}

	@SuppressWarnings("unchecked")
	private void populateRepo() {
		for (ServiceHealthChecker<ServiceProvider<?>, Object> healthChecker : serviceHealthCheckerList) {
			serviceHealthCheckerMap.put(healthChecker.getServiceName(), healthChecker);
		}
	}

}
