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
	private final Map<ServiceName,ServiceHealthChecker<ServiceProvider<?>, Object>> serviceHealthCheckerMap = new HashMap<>();
	
	@Autowired
	private List<? extends ServiceHealthChecker<ServiceProvider<?>, Object>> serviceHealthCheckerList;

	public ServiceHealthCheckerRepository(List<? extends ServiceHealthChecker<ServiceProvider<?>, Object>> serviceHealthCheckerList) {
		this.serviceHealthCheckerList = serviceHealthCheckerList;
		populateRepo();
	}


	private void populateRepo(){
		for (ServiceHealthChecker<ServiceProvider<?>, Object> healthChecker : serviceHealthCheckerList) {
			serviceHealthCheckerMap.put(healthChecker.getServiceName(), healthChecker);
		}
	}

	
}
