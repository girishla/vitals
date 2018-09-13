package com.kfc.vitals;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckResult {

	private ServiceName name;
	private ServiceProvider provider;
	private ServiceStatus status;
	private LocalDate statusTime;
	private String statusMessage;

}
