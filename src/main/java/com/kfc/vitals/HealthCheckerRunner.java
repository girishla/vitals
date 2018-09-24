package com.kfc.vitals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
/**
 * Runs Health checks for all registered services and service providers. The
 * same service provider may be checked with different inputs to ensure all
 * possible cases are covered; Notifies all registered listeners when health
 * check is completed
 * 
 *
 */
public class HealthCheckerRunner {

	private final List<Service> services;
	private final ServiceHealthCheckerRepository healthCheckerRepository;
	private final List<HealthNotificationListener<?>> listeners;

	public HealthCheckerRunner(List<Service> services, ServiceHealthCheckerRepository healthCheckerRepository,
			List<HealthNotificationListener<?>> listenersList) {
		this.services = services;
		this.healthCheckerRepository = healthCheckerRepository;
		if (listenersList == null) {
			this.listeners = new ArrayList<>();
		} else {
			this.listeners = listenersList;
		}

	}

	public void addListener(HealthNotificationListener<?> healthNotificationListener) {

		listeners.add(healthNotificationListener);
	}

	public void healthCheck() {
		for (Service service : services) {
			for (ServiceProvider<?> serviceProvider : service.getProviders()) {
				ServiceHealthChecker<ServiceProvider<?>, Object> healthChecker = healthCheckerRepository
						.get(service.getName());
				log.info("checking provider {} for service {}", serviceProvider.getName(), service.getName());

				for (Object providerCheckInput : serviceProvider.getProviderCheckInputList()) {
					HealthCheckResult result = healthChecker.checkServiceProvider(serviceProvider, providerCheckInput);
					for (HealthNotificationListener<?> listener : listeners) {
						log.info(">>>>>>>> Calling listener {}", listener.getClass()
								.getName());
						listener.notify(result);
					}
				}

			}
		}
	}

	@Async("threadPoolTaskExecutor")
	public void healthCheckAsync() {
		healthCheck();
	}

}
