package com.kfc.vitals.sf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.kfc.vitals.ServiceStatus;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.ServiceStatusCurrent__c;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SfEnterpriseConnectionTest extends AbstractSfTest {

	private static final String serviceName = "Restaurant Delivery";
	private static final String providerName = "Woking Station KFC";
	private static final String providerId = "11122";
	private static final String status = ServiceStatus.UP.toString();
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	
	
	@Test
	public void canWriteServiceStatusToSalesforce() {

		ServiceStatusCurrent__c serviceStatus = new ServiceStatusCurrent__c();
		serviceStatus.setName(serviceName + "_" + providerId);
		serviceStatus.setService__c(serviceName);
		serviceStatus.setProvider__c(providerName);
		serviceStatus.setServiceProviderId__c(providerId);
		serviceStatus.setStatus__c(status);
		serviceStatus.setStatusMessage__c("Everything OK at " + LocalDateTime.now().format(formatter));
		UpsertResult[] saveResults = conn.upsert("ServiceProviderId__c",new  ServiceStatusCurrent__c[]{(ServiceStatusCurrent__c)serviceStatus});
		log.info("Id is {}",saveResults[0].getId());

	}
	
	

	


}
