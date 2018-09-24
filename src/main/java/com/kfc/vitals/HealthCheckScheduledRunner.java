package com.kfc.vitals;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class HealthCheckScheduledRunner {

	private HealthCheckerRunner runner;

	@Scheduled(fixedDelay = 600000)
	public void healthCheckAsync() {
		runner.healthCheck();

		log.info("checking health...");

	}

}
