package com.kfc.vitals;

public interface ServiceHealthChecker<S extends ServiceProvider<?>,T> {

	HealthCheckResult checkServiceProvider(S serviceProvider,T input);

	ServiceName getServiceName();
	
}
