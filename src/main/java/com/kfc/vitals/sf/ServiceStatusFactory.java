package com.kfc.vitals.sf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kfc.vitals.HealthCheckResult;
import com.sforce.soap.enterprise.sobject.ServiceStatusCurrent__c;

public class ServiceStatusFactory {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static ServiceStatusCurrent__c currentStatusFromHealthResult(HealthCheckResult result) {

		ServiceStatusCurrent__c serviceStatus = new ServiceStatusCurrent__c();
		serviceStatus.setName(result.getName()
				.toString() + "_"
				+ result.getProvider()
						.getId());
		serviceStatus.setService__c(result.getName()
				.toString());
		serviceStatus.setProvider__c(result.getProvider()
				.getName());
		serviceStatus.setServiceProviderId__c(result.getName()
				.toString() + "_"
				+ result.getProvider()
						.getId());
		serviceStatus.setStatus__c(result.getStatus()
				.toString());
		serviceStatus.setStatusMessage__c(LocalDateTime.now()
				.format(formatter) + " >>> " + result.getStatusMessage());

		return serviceStatus;

	}

}
