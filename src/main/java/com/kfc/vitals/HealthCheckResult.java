package com.kfc.vitals;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckResult {

	private ServiceName name;
	private ServiceProvider provider;
	private ServiceStatus status;
	private LocalDateTime statusTime;
	private String statusMessage;

}
