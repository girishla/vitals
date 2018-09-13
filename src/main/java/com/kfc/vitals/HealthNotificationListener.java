package com.kfc.vitals;

public interface HealthNotificationListener<T> {

	public void notify(HealthCheckResult result);
	public T getListenerState();
}
