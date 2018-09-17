package com.kfc.vitals.sf;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kfc.vitals.HealthCheckerRunner;
import com.kfc.vitals.SfUpdatingNotificationListener;

public class HealthCheckRunnerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private HealthCheckerRunner runner;
	
	@Autowired
	private SfUpdatingNotificationListener listener;

	@Test
	public void checkHealth() {
		runner.addListener(listener);
		runner.healthCheck();
	}
}
