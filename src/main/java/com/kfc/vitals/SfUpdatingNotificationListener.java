package com.kfc.vitals;

import org.springframework.stereotype.Component;

import com.kfc.vitals.sf.ServiceStatusFactory;
import com.kfc.vitals.sf.SfEnterpriseConnection;
import com.sforce.soap.enterprise.sobject.ServiceStatusCurrent__c;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SfUpdatingNotificationListener implements HealthNotificationListener<String> {

	private SfEnterpriseConnection conn;

	@Override
	public void notify(HealthCheckResult result) {

		ServiceStatusCurrent__c serviceStatus = ServiceStatusFactory.currentStatusFromHealthResult(result);
		conn.upsert("ServiceProviderId__c", new ServiceStatusCurrent__c[] { (ServiceStatusCurrent__c) serviceStatus });

	}

	@Override
	public String getListenerState() {
		return "ok";
	}

}
