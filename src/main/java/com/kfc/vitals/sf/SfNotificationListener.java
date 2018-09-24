package com.kfc.vitals.sf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.kfc.vitals.HealthCheckResult;
import com.kfc.vitals.HealthNotificationListener;
import com.kfc.vitals.ServiceStatusFactory;
import com.sforce.soap.enterprise.sobject.ServiceStatusCurrent__c;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@ConditionalOnProperty("vitals.sf.enabled")
public class SfNotificationListener implements HealthNotificationListener<String> {

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
